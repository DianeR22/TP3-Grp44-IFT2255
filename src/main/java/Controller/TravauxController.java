package Controller;

import Model.ServiceAPI;
import View.TravauxView;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Scanner;

import static Model.ServiceAPI.getDataFromApi;

public class TravauxController {

    /**
     * Affiche le menu des travaux en cours ou à venir.
     *
     * @param scanner L'objet Scanner utilisé pour la saisie utilisateur
     */
    public static void afficherMenu(Scanner scanner){
        TravauxView.afficherMenu(scanner);
    }

    // URL de l'API pour récupérer les données des travaux
    private static final String API_URL_TRAVAUX = " https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=cc41b532-f12d-40fb-9f55-eb58c9a2b12b";
    /**
     * Récupère et filtre les travaux depuis l'API en fonction du type de filtre et du critère fourni.
     *
     * Cette méthode envoie une requête à l'API spécifiée par {@link #API_URL_TRAVAUX}, récupère
     * les travaux disponibles, et applique un filtre basé sur le type et le critère spécifiés.
     *
     * @param typeFiltre Le type de filtre à appliquer :
     *                   <ul>
     *                     <li>0 : Aucun filtre, retourne tous les travaux.</li>
     *                     <li>1 : Filtrer par quartier. Le paramètre {@code filtre} doit contenir le nom du quartier.</li>
     *                     <li>2 : Filtrer par type de travaux. Le paramètre {@code filtre} doit contenir la catégorie de travaux.</li>
     *                   </ul>
     * @param filtre     Le critère utilisé pour le filtrage. Peut-être le nom d'un quartier ou d'une catégorie
     *                   de travaux en fonction du type de filtre.
     * @return Un tableau JSON (`JSONArray`) contenant les travaux filtrés, ou {@code null} si une erreur survient
     *         ou si les données de l'API ne peuvent pas être récupérées.*/
    public static JSONArray recupererTravaux(int typeFiltre, String filtre) {
        try {
            JSONObject jsonResponse = getDataFromApi(API_URL_TRAVAUX);
            if (jsonResponse == null) {
                System.out.println("Impossible de récupérer les données de l'API.");
                return null;
            }

            // Parcourir le json et extraire l'objet result ainsi que records qui est le tableau d'objets
            JSONArray records = jsonResponse.getJSONObject("result").getJSONArray("records");
            JSONArray travauxFiltres = new JSONArray();

            // Parcourir tous les travaux et ajouter ceux qui correspondent au filtre
            for (int i = 0; i < records.length(); i++) {
                JSONObject travail = records.getJSONObject(i);
                String quartier = travail.getString("boroughid");
                String type = travail.getString("reason_category");

                if ((typeFiltre == 0) ||
                        (typeFiltre == 1 && quartier.equalsIgnoreCase(filtre)) ||
                        (typeFiltre == 2 && type.equalsIgnoreCase(filtre))) {
                    travauxFiltres.put(travail); // Ajouter le travail filtré
                }
            }

            if (travauxFiltres.length() == 0) {
                System.out.println("\nAucun travail trouvé avec ce filtre.");
            }

            return travauxFiltres; // Retourne le JSONArray avec les travaux filtrés

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Récupère un tableau des travaux depuis l'API.
     *
     * @return Un tableau JSON contenant les travaux récupérés
     */
    public static JSONArray getTravaux() {
        // Récupérer les données des travaux en appelant getDataFromApi
        JSONObject jsonResponse = ServiceAPI.getDataFromApi(API_URL_TRAVAUX);

        if (jsonResponse != null) {
            // Récupérer la liste des travaux à partir de la réponse JSON
            JSONArray travaux = jsonResponse.getJSONObject("result").getJSONArray("records");
            return travaux;
        } else {
            System.out.println("Impossible de récupérer les données des travaux.");
            return new JSONArray(); // Retourner un tableau vide en cas d'erreur
        }
    }
}

