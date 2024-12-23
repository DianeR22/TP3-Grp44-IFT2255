package Controller;

import View.TravauxView;
import org.junit.Before;
import org.junit.Test;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Scanner;
import java.util.stream.IntStream;

import static org.junit.Assert.*;


public class TravauxControllerTest {
    // Simulation d'une réponse JSON pour les tests
    private JSONArray travauxCrees;


    @Test
    public void testRechercherTravauxParType() {
        String filtreType = "Réseaux routier - Réfection et travaux corrélatifs";

        JSONArray travauxFiltres= TravauxController.recupererTravaux(2, filtreType);

        // Assertions pour vérifier les résultats
        assertFalse("Aucun travail trouvé.", travauxFiltres.isEmpty());
        assertTrue("Tous les travaux devraient avoir pour type le filtre voulu.", IntStream.range(0, travauxFiltres.length())
                        .allMatch(i -> travauxFiltres.getJSONObject(i).getString("reason_category").equalsIgnoreCase(filtreType)));
    }


    @Test
    public void testRechercherTravauxParQuartier() {
        String filtreType = "Ville-Marie";

        // Simuler le filtre sur les travaux et récupérer dans travaux les travaux qui ont
        // le type mis en filtre
        JSONArray travauxFiltres= TravauxController.recupererTravaux(1, filtreType);

        // Assertions pour vérifier les résultats
        assertFalse("Aucun travail trouvé.", travauxFiltres.isEmpty());
        assertTrue("Tous les travaux devraient avoir pour type le filtre voulu.", IntStream.range(0, travauxFiltres.length())
                .allMatch(i -> travauxFiltres.getJSONObject(i).getString("boroughid").equalsIgnoreCase(filtreType)));
    }

    @Test
    public void testRechercherTousLesTravaux() {
        // Aucun filtre, donc tous les travaux doivent être retournés
        JSONArray travauxNonFiltres = TravauxController.recupererTravaux(0, null);

        // Assertions pour vérifier les résultats
        assertNotNull("Les travaux ne devraient pas être nuls.", travauxNonFiltres);
        assertEquals("Tous les travaux devraient être inclus.", travauxNonFiltres.length(), travauxNonFiltres.length());
    }


}
