package Model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


// Classe qui permet l'utilisation des API pour les travaux et entraves routières
// Les méthodes suivantes permettent d'afficher les travaux et les entraves (en faisant usage
// de filtres au besoin)
public class ServiceAPI {
    private static final String OUTPUT_FILE = "data/reason_categories.json";

    // Méthode pour récupérer les données depuis l'API
    public static JSONObject getDataFromApi(String URL) {
        try {
            URL url = new URL(URL);
            HttpURLConnection connec = (HttpURLConnection) url.openConnection();
            connec.setRequestMethod("GET");
            connec.setConnectTimeout(5000);
            connec.setReadTimeout(5000);

            int status = connec.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                BufferedReader input = new BufferedReader(new InputStreamReader(connec.getInputStream()));
                StringBuilder content = new StringBuilder();
                String inputLine;
                while ((inputLine = input.readLine()) != null) {
                    content.append(inputLine);
                }
                input.close();
                connec.disconnect();

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
