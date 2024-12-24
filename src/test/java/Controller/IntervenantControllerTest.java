package Controller;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import static org.junit.Assert.fail;

/**
 * Simples tests unitaires pour IntervenantController,
 */
public class IntervenantControllerTest {
    @Test
    public void testSoumettreProjetEchecTypeTravaux() {
        // On simule 3 inputs invalides, la méthode échoue et s'arrête
        String input = String.join("\n",
                "???",     // 1er essai invalide
                "???2",    // 2ème essai invalide
                "???3",    // 3ème essai invalide
                "\n"        // faut retry depuis le début vu que le nombre d'essais est dépassé
        ) + "\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        try {
            IntervenantController.soumettreProjet();
            // Aucune exception attendue. La méthode imprime un message "3 tentatives échouées..." et se termine.
        } catch (Exception e) {
            fail("soumettreProjet() ne devrait pas lancer d'exception : " + e.getMessage());
        }
    }

    /**
     * Test #2 :
     * SoumettreProjet() - on passe un typeTravaux correct, mais échoue sur le code postal (3 tentatives invalides),
     * donc pas de sauvegarde de projet.
     */
    @Test
    public void testSoumettreProjetEchecCodePostal() {
        // Input simulé :
        // 1) Titre
        // 2) Description
        // 3) TypeTravaux correct (p.ex. "Travaux routiers")
        // 4) Quartiers -> vide
        // 5) Rues -> vide
        // 6) Code postal ->  on rate 3 fois : "bla1", "bla2", "bla3"
        String input = String.join("\n",
                "TitreTest",
                "DescriptionTest",
                "Travaux routiers",
                "",
                "",
                "bla1", // 1er code postal invalide
                "bla2", // 2ème invalide
                "bla3"  // 3ème invalide
        ) + "\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        try {
            IntervenantController.soumettreProjet();
        } catch (Exception e) {
            fail("soumettreProjet() ne devrait pas lancer d'exception en cas d'échec code postal : " + e.getMessage());
        }
    }

    /**
     * Test #3 :
     * SoumettreProjet() - tout correct jusqu'à la dateFin <= dateDebut,
     * ce qui force le retour sans sauvegarder.
     */
    @Test
    public void testSoumettreProjetDateFinInvalide() {
        // Simule un titre, description, typeTravaux correct,
        // codePostal correct (ex. "H2Y 1N9"), dateDebut=2025-05-10,
        // dateFin=2025-05-09 => invalide, la méthode s'arrête.
        String input = String.join("\n",
                "ProjetXYZ",
                "DescriptionXYZ",
                "Travaux routiers",   // type correct
                "",                  // quartiers => vide
                "",                  // rues => vide
                "H2Y 1N9",           // code postal valide
                "2025-05-10",        // dateDebut => valide
                "2024-01-01"         // dateFin => pas > dateDebut
        ) + "\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        try {
            IntervenantController.soumettreProjet();
        } catch (Exception e) {
            fail("soumettreProjet() ne devrait pas lancer d'exception pour dateFin <= dateDebut : " + e.getMessage());
        }
    }
}