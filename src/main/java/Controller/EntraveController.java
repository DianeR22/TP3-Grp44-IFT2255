package Controller;

import Model.ServiceAPI;
import View.EntravesView;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Scanner;

/**
 * Contrôleur pour la gestion des entraves routières. Permet d'afficher les informations
 * liées aux entraves, de les filtrer et de récupérer les détails d'un travail voulu.
 */
public class EntraveController {

    /**
     * Affiche le menu des entraves routières à l'utilisateur.
     *
     * @param scanner L'objet Scanner pour récupérer les entrées de l'utilisateur.
     */
    public static void afficherMenu(Scanner scanner){
        EntravesView.afficherMenu(scanner);
    }

    private static final String API_URL_ENTRAVES = "https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=a2bc8014-488c-495d-941b-e7ae1999d1bd";

    /**
     * Récupère les entraves en fonction du filtre de l'utilisateur.
     *
     * @param typeFiltre Le type de filtre (0 pour tous, 1 pour rue).
     * @param filtre Le filtre, nom de la rue ou aucun.
     * @return un JSONArray contenant les entraves filtrées ou non
     */
    public static JSONArray recupererEntraves(int typeFiltre, String filtre) {
        JSONArray entravesFiltrees = new JSONArray(); // Pour stocker les entraves filtrées

        try {
            JSONObject jsonResponse = ServiceAPI.getDataFromApi(API_URL_ENTRAVES);
            if (jsonResponse == null) {
                System.out.println("Impossible de récupérer les données de l'API.");
                return null;
            }

            // Parcourir le json et extraire l'objet result ainsi que records qui est le tableau d'objets
            JSONArray records = jsonResponse.getJSONObject("result").getJSONArray("records");

            boolean found = false;
            // Parcourir le tableau d'objets records pour extraire les objets
            for (int i = 0; i < records.length(); i++) {
                JSONObject entrave = records.getJSONObject(i);
                String rue = entrave.getString("shortname");

                // Identifier le filtre
                if (typeFiltre == 0 || (typeFiltre == 1 && rue.equalsIgnoreCase(filtre))) {
                    // Ajouter l'entrave filtrée au JSONArray
                    entravesFiltrees.put(entrave);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("\nAucune entrave trouvée avec ce filtre.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return entravesFiltrees; // Retourner le JSONArray avec les entraves filtrées
    }

    /**
     * Récupère toutes les entraves avec l'API et les retourne en forme de tableau.
     *
     * @return Un tableau avec les entraves récupérées.
     */
    public static JSONArray getEntraves() {
        // Appel à la méthode getDataFromApi pour récupérer les données
        JSONObject jsonResponse = ServiceAPI.getDataFromApi(API_URL_ENTRAVES);

        if (jsonResponse != null) {
            // Récupérer la liste des entraves à partir de la réponse JSON
            JSONArray travaux = jsonResponse.getJSONObject("result").getJSONArray("records");
            return travaux;
        } else {
            System.out.println("Impossible de récupérer les données des travaux.");
            return new JSONArray(); // Retourner un tableau vide en cas d'erreur
        }
    }

    /**
     * Récupère et affiche les entraves associées à un travail particulier avec
     * l'identifiant du travail entré par l'utilisateur.
     *
     * @param idTravail L'identifiant du travail dont on veut connaître les entraves associées.
     */
    public static void recupererEntravesParTravail(int idTravail) {

        // Récupérer les travaux avec l'API des travaux
        JSONArray travaux = TravauxController.getTravaux();

        // Rechercher le travail correspondant à l'id donné.
        String longTravailId = null;
        for (int i = 0; i < travaux.length(); i++) {
            JSONObject travail = travaux.getJSONObject(i);
            int travailShortId = travail.getInt("_id");

            // Si l'_id du travail correspond à l'entrée idTravail de l'utilisateur, on a trouvé le travail
            // en question et on extrait son long id composé de chiffres et lettres
            if (travailShortId == idTravail) {
                longTravailId = travail.getString("id");
                break;
            }
        }

        // Si aucun travail correspondant à l'_id n'est trouvé
        if (longTravailId == null) {
            System.out.println("Aucun travail trouvé avec ce numéro.");
            return;
        }

        // Récupérer les entraves via l'API des entraves
        JSONArray entraves = getEntraves();

        // Parcourir les entraves pour afficher celles associées au travail trouvé
        boolean entraveTrouvee = false; // Indicateur pour vérifier si une entrave est trouvée
        for (int j = 0; j < entraves.length(); j++) {
            JSONObject entrave = entraves.getJSONObject(j);
            String entraveId = entrave.getString("id_request");

            // Si les IDs correspondent, afficher les informations de l'entrave
            if (longTravailId.equals(entraveId)) {
                afficherEntraveDuTravail(entrave);
                entraveTrouvee = true; // Entrave trouvée
            }
        }

        if (!entraveTrouvee) {
            System.out.println("Aucune entrave associée à ce travail.");
        }
    }

    /**
     * Affiche les informations d'une entrave.
     *
     * @param entrave L'objet JSON représentant une entrave dont on veut les détails.
     */
    public static void afficherEntraveDuTravail(JSONObject entrave){

        // Récupérer les informations de l'entrave
        String streetName = entrave.getString("shortname");
        String streetImpactType = entrave.getString("streetimpacttype");
        String sidewalkBlockedType = entrave.getString("sidewalk_blockedtype");

        // Afficher les informations de l'entrave associée au travail demandé
        System.out.println("=== Détails de l'entrave associé au travail ===");
        System.out.println("Rue : " + streetName);
        System.out.println("Type d'impact sur la rue : " + streetImpactType);
        System.out.println("Blocage du trottoir : " + sidewalkBlockedType);
        System.out.println("------------------------------");
    }
}