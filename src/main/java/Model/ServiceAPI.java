package Model;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Classe qui permet de récupérer des données via une API.
 */
public class ServiceAPI {
    /**
     * Méthode pour récupérer les données depuis l'API.
     *
     * @param URL l'URL de l'API
     * @return Un objet {@link JSONObject} contenant les données reçues de l'API.
     *         Retourne null si erreur
     */
    public static JSONObject getDataFromApi(String URL) {
        try {
            // Connexion à l'API
            URL url = new URL(URL);
            // HttpURLConnection est créé et utilisé pour faire une requête GET
            HttpURLConnection connec = (HttpURLConnection) url.openConnection();
            connec.setRequestMethod("GET");
            connec.setConnectTimeout(5000);
            connec.setReadTimeout(5000);

            // Vérifier le code de réponse de l'API (200 = OK)
            int status = connec.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                // Lire la réponse ligne par ligne avec un buffer
                BufferedReader input = new BufferedReader(new InputStreamReader(connec.getInputStream()));
                StringBuilder content = new StringBuilder();
                String inputLine;
                // On ajoute chaque ligne à un stringBuilder pour reconstruire la réponse
                while ((inputLine = input.readLine()) != null) {
                    content.append(inputLine);
                }
                input.close();
                connec.disconnect();

                // Retourner un objet JSON (usage d'une librairie présente
                // dans le directory libs
                return new JSONObject(content.toString());
            } else {
                System.out.println("Erreur: " + status);
                connec.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
