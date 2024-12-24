package Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
/**
 * La classe GestionTravaux permet de gérer les travaux en prenant les catégories de
 * travaux depuis une API externe et en les sauvegardant dans un fichier JSON.
 * Cela permet de mapper ces catégories à notre liste de type de travaux utilisée
 * lors de la recherche de travaux par exemple.
 */
public class GestionTravaux {

    // Chemin du fichier pour les catégories des travaux
    private static final String FICHIER_TRAVAUX = "data/reason_categories.json";
    // Lien vers l'API des travaux
    private static final String API_URL_TRAVAUX = " https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=cc41b532-f12d-40fb-9f55-eb58c9a2b12b";

    /**
     * Cette méthode permet de récupérer tous les types de travail (catégories) de l'API des travaux de façon distincte.
     * Elle prend les données, puis les retourne en forme de JSONObject.
     *
     * @return Un JSONObject contenant les catégories extraites de l'API, ou null en cas d'erreur.
     */
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

    /**
     * Cette méthode permet d'écrire un JSONObject dans un fichier JSON.
     *
     * @param jsonObject Le JSONObject à écrire.
     * @param cheminFichier Le chemin du fichier dans lequel sauvegarder les données.
     */
    public static void ecrireJsonDansFichier(JSONObject jsonObject, String cheminFichier) {
        try (FileWriter file = new FileWriter(cheminFichier)) {
            file.write(jsonObject.toString(4));
            file.flush();
            System.out.println("Fichier JSON créé avec succès à : " + cheminFichier);
        } catch (IOException e) {
            System.out.println("Erreur lors de l'écriture du fichier JSON : " + e.getMessage());
        }
    }

    /**
     * Cette méthode extrait les catégories de travaux via l'API et les sauvegarde dans un fichier JSON.
     * Elle est appelée depuis la classe MaVilleApp.
     */
    public static void sauvegarderCategoriesDansFichier() {
        JSONObject categoriesJson = extraireReasonCategories();
        if (categoriesJson != null) {
            ecrireJsonDansFichier(categoriesJson, FICHIER_TRAVAUX);
        }
    }
}
