package Model;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;


public class GestionResidentsTest {

    @Test
    public void ajouterResident() {
        // Charger les résident dans json
        GestionResidents.chargeResidents();

        // Création d'un résident test
        Resident resident = new Resident(
                "Test",
                "User",
                "test.user@gmail.com",
                "TestPassword1",
                "01/01/1990",
                "514-123-4567",
                "123 Rue Papineau, Ville-Marie",
                "H1E 7Y5"
        );

        // Ajout résident
        GestionResidents.ajouterResident(resident);

        // Vérifier que le résident a bien été ajouté à la liste
        assertTrue("Le résident devrait être ajouté",
                GestionResidents.getListeResidents().contains(resident)
        );

        GestionResidents.supprimerResident(GestionResidents.getListeResidents().size()-1);
    }

    /**
     * Test #2 :
     * Vérifie que chargerResidentsDepuisFichier() ne duplique pas les résidents
     * lorsqu'on l'appelle plusieurs fois de suite.
     * (Sans ajout/suppression : on se contente de charger plusieurs fois).
     *
     * Condition : si le JSON est bien formé et qu'il n'y a pas d'ajout ailleurs,
     * le nombre de résidents devrait rester stable.
     */
    @Test
    public void testChargerPlusieursFoisSansDuplication() {
        // Premier chargement
        List<Resident> firstLoad = GestionResidents.chargerResidentsDepuisFichier();
        int initialSize = (firstLoad == null) ? 0 : firstLoad.size();

        // Deuxième chargement
        List<Resident> secondLoad = GestionResidents.chargerResidentsDepuisFichier();
        int secondSize = (secondLoad == null) ? 0 : secondLoad.size();

        // On compare
        assertEquals(
                "Le nombre de résidents ne devrait pas changer après un second chargement (sans ajout).",
                initialSize,
                secondSize
        );
    }

    /**
     * Test #3 :
     * Après avoir chargé le fichier JSON, on vérifie que toutes
     * les adresses sont validées/corrigées si nécessaire.
     *
     * <p>
     *   - Si une adresse est invalide, la classe la corrige en
     *     "1234 Rue Sainte-Catherine Ouest, Ville-Marie".
     *   - On vérifie simplement qu'aucune adresse n'est restée invalide.
     *   - Ce test n'ajoute ni ne supprime aucun résident, il se contente
     *     de lire la liste et de constater les éventuelles corrections.
     * </p>
     */
    @Test
    public void testValidationAdressesSansModification() {
        // Chargement (corrige les adresses invalides si trouvé)
        List<Resident> residents = GestionResidents.chargerResidentsDepuisFichier();
        if (residents == null) {
            fail("La liste de résidents ne devrait pas être null.");
        }

        for (Resident r : residents) {
            // On s'assure que l'adresse est maintenant conforme
            boolean estValide = Valider.validerAdresseResident(r.getAdresse());
            assertTrue(
                    "Adresse invalide détectée après chargement/correction pour le résident : "
                            + r.getAdresseCourriel()
                            + ", adresse = " + r.getAdresse(),
                    estValide
            );
        }
    }
}