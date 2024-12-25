package Model;

import org.junit.*;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class GestionProjetsTest {

    private String backupFileContent = "";  // Contenu original de projets.json

    @Before
    public void backupFile() throws IOException {
        // Avant chaque test, on sauvegarde le contenu existant (s'il existe).
        Path filePath = Paths.get("data/projets.json");
        if (Files.exists(filePath)) {
            backupFileContent = Files.readString(filePath);
        } else {
            backupFileContent = ""; // Fichier inexistant => on met une chaîne vide
        }
    }

    @After
    public void restoreFile() throws IOException {
        // Après chaque test, on restaure le fichier à son état initial.
        Path filePath = Paths.get("data/projets.json");
        if (backupFileContent.isEmpty()) {
            // S'il était vide/inexistant avant, on le supprime s'il a été créé
            Files.deleteIfExists(filePath);
        } else {
            // Sinon, on réécrit l'ancien contenu
            Files.writeString(filePath, backupFileContent);
        }
    }

    /**
     * Test n°1 : Vérifie qu'on peut sauvegarder un projet puis le recharger.
     */
    @Test
    public void testSauvegarderEtChargerProjet() {
        // On crée un projet factice
        Projet projet = new Projet(
                "Réfection de route",
                "Refaire la chaussée",
                "Travaux routiers",
                List.of("Quartier Nord"),
                List.of("Rue Principale"),
                List.of("12345"),
                LocalDate.now().plusDays(10),      // dateDebut dans 10 jours
                LocalDate.now().plusDays(20),      // dateFin dans 20 jours
                LocalTime.of(8, 0),   // 08:00
                LocalTime.of(17, 0)   // 17:00
        );

        // Sauvegarde du projet
        GestionProjets.sauvegarderProjet(projet);

        // Chargement de tous les projets
        List<Projet> projets = GestionProjets.chargerProjets();
        Assert.assertFalse("La liste de projets ne doit pas être vide", projets.isEmpty());

        // On vérifie que le dernier projet (celui qu'on vient d'ajouter) correspond
        Projet dernier = projets.get(projets.size() - 1);
        Assert.assertEquals("Réfection de route", dernier.getTitre());
        Assert.assertEquals("Refaire la chaussée", dernier.getDescription());
        Assert.assertEquals("Travaux routiers", dernier.getTypeTravaux());
    }

    /**
     * Test n°2 : Vérifie que getProjetsAvenirProchains3Mois
     * retourne bien un projet si sa dateDebut est dans les 3 mois à venir.
     */
    @Test
    public void testGetProjetsAvenirProchains3Mois_ProjetValide() {
        // Projet commençant dans 1 mois => doit apparaître dans la liste
        Projet projet = new Projet(
                "Pose de lampadaires",
                "Installer de nouveaux lampadaires",
                "Éclairage public",
                List.of("Quartier Est"),
                List.of("Avenue Lumière"),
                List.of("54321"),
                LocalDate.now().plusMonths(1),  // 1 mois après la date du jour
                LocalDate.now().plusMonths(2),  // Fin dans 2 mois
                LocalTime.of(7, 30),
                LocalTime.of(16, 0)
        );

        GestionProjets.sauvegarderProjet(projet);

        // Récupération
        List<Projet> projetsProches = GestionProjets.getProjetsAvenirProchains3Mois();
        // On s'attend à retrouver ce projet dans la liste
        boolean trouve = false;
        for (Projet p : projetsProches) {
            if (p.getTitre().equals("Pose de lampadaires")) {
                trouve = true;
                break;
            }
        }
        Assert.assertTrue("Le projet doit être présent dans la liste des 3 prochains mois", trouve);
    }
}