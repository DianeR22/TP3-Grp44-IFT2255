package Controller;

import org.json.JSONArray;
import org.junit.Test;

import java.util.stream.IntStream;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class EntravesControllerTest {
    @Test
    public void testRechercherToutesLesEntraves() {

        // Aucun filtre
        // Récupérer une liste de entraves filtrés pour vérifier s'ils sont exactes
        JSONArray entravesNonFiltrees = EntraveController.recupererEntraves(0, null);

        // Assertions pour vérifier les résultats
        assertNotNull("Les travaux ne devraient pas être nuls.", entravesNonFiltrees);
        assertEquals("Tous les travaux devraient être inclus.", entravesNonFiltrees.length(), entravesNonFiltrees.length());
    }

    @Test
    public void testRechercherEntravesParRue() {

        // Filtre choisi pour le quartier
        String filtreType = "Saint-Dominique";

        // Récupérer une liste de travaux filtrés pour vérifier s'ils sont exactes
        JSONArray entravesFiltres = EntraveController.recupererEntraves(1, filtreType);

        // Assertions pour vérifier les résultats
        assertFalse("Aucune entrave trouvé.", entravesFiltres.isEmpty());
        assertTrue("Tous les travaux devraient avoir pour type le filtre voulu.", IntStream.range(0, entravesFiltres.length())
                .allMatch(i -> entravesFiltres.getJSONObject(i).getString("shortname").equalsIgnoreCase(filtreType)));
    }
}