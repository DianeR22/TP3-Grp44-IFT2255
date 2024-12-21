package Controller;

import Model.ServiceAPI;
import View.EntravesView;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Scanner;


public class EntraveController {
    // Faire appel à afficherMenu de la vue Entrave afin d'afficher le menu des entraves
    // routières
    public static void afficherMenu(Scanner scanner){
        EntravesView.afficherMenu(scanner);
    }

    private static final String API_URL_ENTRAVES = "https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=a2bc8014-488c-495d-941b-e7ae1999d1bd";
    public static void recupererEntraves(int typeFiltre, String filtre) {
        try {
            JSONObject jsonResponse = ServiceAPI.getDataFromApi(API_URL_ENTRAVES);
            if (jsonResponse == null) {
                System.out.println("Impossible de récupérer les données de l'API.");
                return;
            }

            JSONArray records = jsonResponse.getJSONObject("result").getJSONArray("records");

            boolean found = false;
            for (int i = 0; i < records.length(); i++) {
                JSONObject entrave = records.getJSONObject(i);
                String id_request = entrave.getString("id_request");
                String rue = entrave.getString("shortname");

                if ((typeFiltre == 0) ||
                        (typeFiltre == 1 && id_request.equalsIgnoreCase(filtre)) ||
                        (typeFiltre == 2 && rue.equalsIgnoreCase(filtre))) {

                    System.out.println("\n**********************************");
                    System.out.println("Identifiant de Travail : " + id_request);
                    System.out.println("Rue : " + rue);
                    System.out.println("Type d'impact sur la rue : " + entrave.getString("streetimpacttype"));
                    System.out.println("************************************");

                    found = true;
                }
            }

            if (!found) {
                System.out.println("\nAucune entrave trouvée avec ce filtre.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Méthode pour récupérer les travaux depuis l'API
    public static JSONArray getEntraves() {
        // Appel à la méthode getDataFromApi pour récupérer les données
        JSONObject jsonResponse = ServiceAPI.getDataFromApi(API_URL_ENTRAVES);

        if (jsonResponse != null) {
            // Récupérer la liste des travaux à partir de la réponse JSON
            JSONArray travaux = jsonResponse.getJSONObject("result").getJSONArray("records");
            return travaux;
        } else {
            System.out.println("Impossible de récupérer les données des travaux.");
            return new JSONArray(); // Retourner un tableau vide en cas d'erreur
        }
    }


    public static void recupererEntravesParTravail(int idTravail) {
        // Récupérer les travaux avec l'API des travaux
        JSONArray travaux = TravauxController.getTravaux();

        // Rechercher le travail correspondant à l'_id donné. (_id est un entier tandis
        // que id est une série de chiffres et lettres)
        String longTravailId = null;
        for (int i = 0; i < travaux.length(); i++) {
            JSONObject travail = travaux.getJSONObject(i);
            int travailShortId = travail.getInt("_id"); // Utilisation de _id

            // Si l'_id correspond à l'entrée de l'utilisateur, on a trouvé le travail
            // en question
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
                entraveTrouvee = true; // Une correspondance a été trouvée
            }
        }

        // Si aucune entrave n'est trouvée
        if (!entraveTrouvee) {
            System.out.println("Aucune entrave associée à ce travail.");
        }
    }
    public static void afficherEntraveDuTravail(JSONObject entrave){

        // Récupérer les informations de l'entrave
        String streetName = entrave.getString("name");
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