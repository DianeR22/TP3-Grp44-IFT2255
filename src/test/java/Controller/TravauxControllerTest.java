package Controller;

import org.junit.Before;
import org.junit.Test;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.stream.IntStream;

import static org.junit.Assert.*;


public class TravauxControllerTest {
    // Simulation d'une réponse JSON pour les tests
    private JSONArray travauxCrees;


    @Before
    public void setUp() {
        // Création de données fictives pour les tests
        travauxCrees = new JSONArray();

        travauxCrees.put(new JSONObject()
                .put("_id", 1)
                .put("boroughid", "Sainte_Catherine")
                .put("reason_category", "TRAVAUX ROUTIERS")
                .put("currentstatus", "En cours")
                .put("organizationname", "Entrepreneur XYZ")
                .put("submittercategory", "Privé"));

        travauxCrees.put(new JSONObject()
                .put("_id", 2)
                .put("boroughid", "Montreal-Rosemont")
                .put("reason_category", "ENTRETIEN PAYSAGER")
                .put("currentstatus", "Prévu")
                .put("organizationname", "Municipalité Y")
                .put("submittercategory", "Public"));
    }

    @Test
    public void testRechercherTravauxParType() {
        String filtreType = "TRAVAUX ROUTIERS";

        // Simuler le filtre sur les travaux et récupérer dans travaux les travaux qui ont
        // le type mis en filtre
        JSONArray resultats = new JSONArray();
        for (int i = 0; i < travauxCrees.length(); i++) {
            JSONObject travail = travauxCrees.getJSONObject(i);
            if (travail.getString("reason_category").equalsIgnoreCase(filtreType)) {
                resultats.put(travail);
            }
        }

        // Assertions pour vérifier les résultats
        assertFalse("Aucun travail trouvé.", resultats.isEmpty());
        assertTrue("Tous les travaux devraient avoir pour type le filtre voulu.", IntStream.range(0, resultats.length())
                        .allMatch(i -> resultats.getJSONObject(i).getString("reason_category").equalsIgnoreCase(filtreType)));
    }

    @Test
    public void testRechercherTousLesTravaux() {
        // Aucun filtre, donc tous les travaux doivent être retournés
        JSONArray resultats = travauxCrees;

        // Assertions pour vérifier les résultats
        assertNotNull("Les travaux ne devraient pas être nuls.", resultats);
        assertEquals("Tous les travaux devraient être inclus.", travauxCrees.length(), resultats.length());
    }


}
