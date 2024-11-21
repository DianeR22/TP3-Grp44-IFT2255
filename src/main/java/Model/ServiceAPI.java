package Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

// Classe qui permet l'utilisation des API pour les travaux et entraves routières
public class ServiceAPI {


    private static final String API_URL_ENTRAVES = "https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=a2bc8014-488c-495d-941b-e7ae1999d1bd";
    private static final String API_URL_TRAVAUX = " https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=cc41b532-f12d-40fb-9f55-eb58c9a2b12b";


    // Méthode pour récupérer et afficher les travaux en fonction du filtre
    public void recupererTravaux(int typeFiltre, String filtre) {
        try {

            // Connexion à l'API
            URL url = new URL(API_URL_TRAVAUX);
            // HttpURLConnection est créé et utilisé pour faire une requête GET
            HttpURLConnection connec = (HttpURLConnection) url.openConnection();
            connec.setRequestMethod("GET");
            connec.setConnectTimeout(5000);
            connec.setReadTimeout(5000);

            // Vérifier le code de réponse de l'API (200 signifie OK)
            int status = connec.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                // Lire la réponse ligne par ligne avec un buffer
                BufferedReader input = new BufferedReader(new InputStreamReader(connec.getInputStream()));
                String inputLine;
                // On ajoute chaque ligne à un stringBuilder pour reconstruire la réponse
                StringBuilder content = new StringBuilder();
                while ((inputLine = input.readLine()) != null) {
                    content.append(inputLine);
                }
                // Fermer les flux
                input.close();

                // On convertit la réponse en un objet JSON (usage d'une librairie présente
                // dans le directory libs
                JSONObject jsonResponse = new JSONObject(content.toString());

                // Accéder à la section "records" qui contient les travaux
                JSONArray records = jsonResponse.getJSONObject("result").getJSONArray("records");

                // Afficher les informations des travaux en fonction du filtre
                boolean found = false;
                // Boucle pour extraire les travaux et les caractéristiques de ces travaux
                for (int i = 0; i < records.length(); i++) {
                    JSONObject travail = records.getJSONObject(i);
                    String quartier = travail.getString("boroughid");
                    String type = travail.getString("reason_category");

                    // Appliquer les filtres
                    if ((typeFiltre == 0) ||  // Afficher tous les travaux
                            (typeFiltre == 1 && quartier.equalsIgnoreCase(filtre)) || // Par quartier
                            (typeFiltre == 2 && type.equalsIgnoreCase(filtre))) {  // Par type

                        // Afficher chaque travail de manière lisible et formatée
                        System.out.println("\n***********************************");
                        System.out.println("Travail ID : " + travail.getString("id"));
                        System.out.println("Arrondissement : " + quartier);
                        System.out.println("Statut actuel : " + travail.getString("currentstatus"));
                        System.out.println("Motif : " + travail.getString("reason_category"));

                        // Utilisation de opt() pour récupérer la valeur en tant que Object
                        Object nomOrganisationObjet = travail.opt("organizationname");
                        // Conversion de la valeur en String quel que soit le type
                        String organizationName = (nomOrganisationObjet != null) ? nomOrganisationObjet.toString() :"";
                        System.out.println("Nom de l'intervenant : " + organizationName);

                        // Utilisation de opt() pour récupérer la valeur en tant que Object
                        Object categorieObjet = travail.opt("submittercategory");
                        // Conversion de la valeur en String quel que soit le type
                        String submitterCategory = (categorieObjet !=null ) ? categorieObjet.toString( ):"";
                        System.out.println("Catégorie de l'intervenant: " + submitterCategory);
                        System.out.println("*************************************");

                        found = true;
                    }
                }

                if (!found) {
                    System.out.println("\nAucun travail trouvé avec ce filtre.");
                }
            } else {
                System.out.println("Erreur: " + status);
            }

            connec.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Méthode pour récupérer et afficher les entraves en fonction du filtre
    public void recupererEntraves(int typeFiltre, String filtre) {
        try {
            // Connexion à l'API
            URL url = new URL(API_URL_ENTRAVES);
            // HttpURLConnection est créé et utilisé pour faire une requête GET
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            // Vérifier le code de réponse de l'API (200 signifie OK)
            int status = conn.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                // Lire la réponse ligne par ligne avec un buffer
                BufferedReader input = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                // On ajoute chaque ligne à un stringBuilder pour reconstruire la réponse
                StringBuilder content = new StringBuilder();
                while ((inputLine = input.readLine()) != null) {
                    content.append(inputLine);
                }
                // Fermer les flux
                input.close();

                // On convertit la réponse en un objet JSON (usage d'une librairie présente
                // dans le directory libs
                JSONObject jsonResponse = new JSONObject(content.toString());

                // Accéder à la section "records" qui contient les entraves
                JSONArray records = jsonResponse.getJSONObject("result").getJSONArray("records");

                // Afficher les informations des entraves en fonction du filtre
                boolean found = false;
                for (int i = 0; i < records.length(); i++) {
                    JSONObject entrave = records.getJSONObject(i);
                    String id_request = entrave.getString("id_request");
                    String rue = entrave.getString("shortname");

                    // Appliquer les filtres
                    if ((typeFiltre == 0) ||  // Afficher toutes les entraves
                            (typeFiltre == 1 && id_request.equalsIgnoreCase(filtre)) || // Par travail
                            (typeFiltre == 2 && rue.equalsIgnoreCase(filtre))) {  // Par type

                        // Afficher chaque entraves de manière lisible et formatée
                        System.out.println("\n====================================");
                        System.out.println("Identifiant de Travail : " + id_request);
                        System.out.println("Rue : " + rue);
                        System.out.println("Type d'impact sur la rue : " + entrave.getString("streetimpacttype"));
                        System.out.println("====================================");

                        found = true;
                    }
                }

                if (!found) {
                    System.out.println("\nAucune entrave trouvée avec ce filtre.");
                }
            } else {
                System.out.println("Erreur: " + status);
            }

            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
