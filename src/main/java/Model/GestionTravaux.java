package Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GestionTravaux {
    private static final String FICHIER_TRAVAUX = "data/reason_categories.json";
    private static final String API_URL_TRAVAUX = " https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=cc41b532-f12d-40fb-9f55-eb58c9a2b12b";

    public static JSONObject extraireReasonCategories(){

        JSONObject jsonResponse = ServiceAPI.getDataFromApi(API_URL_TRAVAUX);
        if (jsonResponse == null) {
            System.out.println("Impossible de récupérer les données de l'API.");
            return null;
        }
        JSONArray records = jsonResponse.getJSONObject("result").getJSONArray("records");
        Set<String> uniqueCategories = new HashSet<>();

        for (int i = 0; i < records.length(); i++) {
            JSONObject record = records.getJSONObject(i);
            String reasonCategory = record.optString("reason_category", "");
            if (!reasonCategory.isEmpty()) {
                uniqueCategories.add(reasonCategory);
            }
        }
        JSONObject result = new JSONObject();
        result.put("categories", uniqueCategories);
        return result;
    }
    // Méthode pour écrire un JSONObject dans un fichier JSON local
    public static void ecrireJsonDansFichier(JSONObject jsonObject, String cheminFichier) {
        try (FileWriter file = new FileWriter(cheminFichier)) {
            file.write(jsonObject.toString(4)); // Indentation pour une meilleure lisibilité
            file.flush();
            System.out.println("Fichier JSON créé avec succès à : " + cheminFichier);
        } catch (IOException e) {
            System.out.println("Erreur lors de l'écriture du fichier JSON : " + e.getMessage());
        }
    }

    public static void sauvegarderCategoriesDansFichier() {
        JSONObject categoriesJson = extraireReasonCategories();
        if (categoriesJson != null) {
            ecrireJsonDansFichier(categoriesJson, FICHIER_TRAVAUX);
        }
    }





}
