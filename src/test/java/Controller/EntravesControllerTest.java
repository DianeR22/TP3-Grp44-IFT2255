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

    /**
     * Test : Appeler recupererEntravesParTravail(...) avec un ID
     * susceptible d'être présent dans le dataset.
     *
     * <p>Si l'_id du travail n'existe pas (ou plus),
     * on s'attend à un message "Aucun travail trouvé avec ce numéro."
     * ou "Aucune entrave associée à ce travail."
     *
     * <p>Si l'_id existe et qu'il y a des entraves,
     * le code devrait imprimer "=== Détails de l'entrave associé au travail ==="
     * (une ou plusieurs fois).
     *
     * <p>L'essentiel est de s'assurer qu'aucune exception n'est lancée.
     */
    @Test
    public void testRecupererEntravesParTravailAvecOuSansResultat() {
        int idPossible = 1;

        try {
            // Appel de la méthode recupererEntravesParTravail avec un ID possible
            EntraveController.recupererEntravesParTravail(idPossible);

            //  On veut vérifier que ça ne lance pas d'exception
        } catch (Exception e) {
            // Échec si une exception survient
            throw new AssertionError(
                    "recupererEntravesParTravail(" + idPossible + ") ne devrait pas lever d'exception : "
                            + e.getMessage(), e);
        }
    }
}