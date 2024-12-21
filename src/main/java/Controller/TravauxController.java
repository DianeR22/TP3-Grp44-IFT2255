package Controller;

import Model.ServiceAPI;
import View.TravauxView;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Scanner;

import static Model.ServiceAPI.getDataFromApi;

public class TravauxController {
    // Faire appel à afficherMenu de la vue Travaux pour afficher le menu des travaux
    // en cours ou à venir
    public static void afficherMenu(Scanner scanner){
        TravauxView.afficherMenu(scanner);
    }

// Méthode pour récupérer et afficher les travaux en fonction du filtre
private static final String API_URL_TRAVAUX = " https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=cc41b532-f12d-40fb-9f55-eb58c9a2b12b";
    public static void recupererTravaux(int typeFiltre, String filtre) {
    try {
        JSONObject jsonResponse = getDataFromApi(API_URL_TRAVAUX);
        if (jsonResponse == null) {
            System.out.println("Impossible de récupérer les données de l'API.");
            return;
        }

        JSONArray records = jsonResponse.getJSONObject("result").getJSONArray("records");

        boolean found = false;
        for (int i = 0; i < records.length(); i++) {
            JSONObject travail = records.getJSONObject(i);
            String quartier = travail.getString("boroughid");
            String type = travail.getString("reason_category");

            if ((typeFiltre == 0) ||
                    (typeFiltre == 1 && quartier.equalsIgnoreCase(filtre)) ||
                    (typeFiltre == 2 && type.equalsIgnoreCase(filtre))) {

                System.out.println("\n***********************************");
                System.out.println("Travail ID : " + travail.getInt("_id"));
                System.out.println("Arrondissement : " + quartier);
                System.out.println("Statut actuel : " + travail.getString("currentstatus"));
                System.out.println("Motif : " + travail.getString("reason_category"));

                String organizationName = travail.opt("organizationname") != null ? travail.opt("organizationname").toString() : "";
                System.out.println("Nom de l'intervenant : " + organizationName);

                String submitterCategory = travail.opt("submittercategory") != null ? travail.opt("submittercategory").toString() : "";
                System.out.println("Catégorie de l'intervenant: " + submitterCategory);
                System.out.println("*************************************");

                found = true;
            }
        }

        if (!found) {
            System.out.println("\nAucun travail trouvé avec ce filtre.");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    // Méthode pour récupérer les travaux depuis l'API
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

