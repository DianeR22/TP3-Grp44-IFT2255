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
     * Récupère et affiche les travaux en fonction du filtre sélectionné par l'utilisateur.
     *
     * @param typeFiltre Le type de filtre (0 pour tous, 1 pour arrondissement, 2 pour type de travail)
     * @param filtre Le critère de filtrage (nom de l'arrondissement ou type de travail)
     */
    public static void recupererTravaux(int typeFiltre, String filtre) {
        try {
            JSONObject jsonResponse = getDataFromApi(API_URL_TRAVAUX);
            if (jsonResponse == null) {
                System.out.println("Impossible de récupérer les données de l'API.");
                return;
            }

            // Parcourir le json et extraire l'objet result ainsi que records qui est le tableau d'objets
            JSONArray records = jsonResponse.getJSONObject("result").getJSONArray("records");

            boolean found = false;
            // Parcourir tous les travaux et afficher les informations associées
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

                    // Utilisation de opt() pour récupérer la valeur en tant que Object et conversion de la valeur en String quel que soit le type
                    String organizationName = travail.opt("organizationname") != null ? travail.opt("organizationname").toString() : "";
                    System.out.println("Nom de l'intervenant : " + organizationName);

                    // Utilisation de opt() pour récupérer la valeur en tant que Object et conversion de la valeur en String quel que soit le type
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

