package Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class GestionTravaux {
    private static final String FICHIER_TRAVAUX = "data/reason_categories.json";
    private static final String API_URL_TRAVAUX = " https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=cc41b532-f12d-40fb-9f55-eb58c9a2b12b";

    // Cette méthode permet de récupérer tous les types de travail de l'API des
    // travaux de façon distincte et d'afficher le tout dans le fichier json
    // reason_categorie.json
    public static JSONObject extraireReasonCategories(){
        // Récupérer les données
        JSONObject jsonResponse = ServiceAPI.getDataFromApi(API_URL_TRAVAUX);
        if (jsonResponse == null) {
            System.out.println("Impossible de récupérer les données de l'API.");
            return null;
        }
        // Parcourir le json et extraire l'objet result ainsi que records qui est le tableau d'objets
        JSONArray records = jsonResponse.getJSONObject("result").getJSONArray("records");

        // Initialiser un ensemble pour garder les types (ou catégories) distincts
        Set<String> uniqueCategories = new HashSet<>();

        // Parcourir le tableau d'objets et récupérer les catégories
        for (int i = 0; i < records.length(); i++) {
            JSONObject record = records.getJSONObject(i);
            String reasonCategory = record.optString("reason_category", "");
            if (!reasonCategory.isEmpty()) {
                uniqueCategories.add(reasonCategory);
            }
        }
        // Placer les données récoltées dans le json
        JSONObject result = new JSONObject();
        result.put("categories", uniqueCategories);
        return result;
    }

    // Méthode pour écrire un JSONObject dans un fichier JSON local
    public static void ecrireJsonDansFichier(JSONObject jsonObject, String cheminFichier) {
        try (FileWriter file = new FileWriter(cheminFichier)) {
            file.write(jsonObject.toString(4));
            file.flush();
            System.out.println("Fichier JSON créé avec succès à : " + cheminFichier);
        } catch (IOException e) {
            System.out.println("Erreur lors de l'écriture du fichier JSON : " + e.getMessage());
        }
    }

    // Méthode qui sauvegarde extrait les catégories et les place dans un fichier json.
    // Elle est appelé dans maVilleApp.
    public static void sauvegarderCategoriesDansFichier() {
        JSONObject categoriesJson = extraireReasonCategories();
        if (categoriesJson != null) {
            ecrireJsonDansFichier(categoriesJson, FICHIER_TRAVAUX);
        }
    }





}
