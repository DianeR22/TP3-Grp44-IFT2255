package Model;

import org.json.JSONObject;
import org.junit.*;

import java.io.IOException;
import java.nio.file.*;
import java.util.Objects;

public class GestionTravauxTest {

    private String backupContent = "";  // Contenu initial de reason_categories.json
    private static final String FICHIER_TRAVAUX = "data/reason_categories.json";

    @Before
    public void backupFile() throws IOException {
        // Avant chaque test, on sauvegarde le contenu actuel du fichier s'il existe
        Path filePath = Paths.get(FICHIER_TRAVAUX);
        if (Files.exists(filePath)) {
            backupContent = Files.readString(filePath);
        } else {
            backupContent = "";  // Fichier inexistant => chaîne vide
        }
    }

    @After
    public void restoreFile() throws IOException {
        // Après chaque test, on restaure le contenu initial
        Path filePath = Paths.get(FICHIER_TRAVAUX);
        if (backupContent.isEmpty()) {
            // S'il n'existait pas avant, on le supprime s'il a été créé
            Files.deleteIfExists(filePath);
        } else {
            // Sinon, on réécrit l'ancien contenu
            Files.writeString(filePath, backupContent);
        }
    }

    /**
     * Test n°1 : Vérifie que extraireReasonCategories() ne renvoie pas null.
     * (Suppose l'API en ligne. Si l'API est inaccessible, ce test peut échouer.)
     */
    @Test
    public void testExtraireReasonCategories() {
        JSONObject result = GestionTravaux.extraireReasonCategories();
        // On s'attend à obtenir un JSONObject non null, contenant une clé "categories"
        Assert.assertNotNull("Le résultat ne doit pas être null si l'API est accessible.", result);
        Assert.assertTrue("Le JSONObject doit contenir la clé 'categories'.",
                result.has("categories"));
    }

    /**
     * Test n°2 : Vérifie l'écriture d'un JSONObject dans un fichier
     * via ecrireJsonDansFichier().
     */
    @Test
    public void testEcrireJsonDansFichier() throws IOException {
        // On crée un JSONObject simple
        JSONObject jsonTest = new JSONObject();
        jsonTest.put("cleTest", "valeurTest");

        // On définit un fichier temporaire (au lieu d'utiliser reason_categories.json)
        String tempFilename = "data/test_ecrire.json";
        Path tempPath = Paths.get(tempFilename);
        // On supprime au cas où il existerait déjà
        Files.deleteIfExists(tempPath);

        // On appelle la méthode
        GestionTravaux.ecrireJsonDansFichier(jsonTest, tempFilename);

        // Vérification : le fichier a été créé et contient la clé "cleTest"
        Assert.assertTrue("Le fichier JSON doit être créé", Files.exists(tempPath));
        String contenu = Files.readString(tempPath);
        Assert.assertTrue("Le contenu doit inclure 'cleTest'",
                contenu.contains("cleTest"));

        // Nettoyage : on supprime le fichier temporaire
        Files.deleteIfExists(tempPath);
    }

    /**
     * Test n°3 : Vérifie la sauvegarde des catégories dans reason_categories.json
     * via la méthode sauvegarderCategoriesDansFichier().
     *
     * On l'appelle, puis on relit le fichier pour vérifier qu'un champ "categories" est présent.
     */
    @Test
    public void testSauvegarderCategoriesDansFichier() throws IOException {
        // Appel de la méthode
        GestionTravaux.sauvegarderCategoriesDansFichier();

        // Lecture du fichier reason_categories.json
        Path filePath = Paths.get(FICHIER_TRAVAUX);
        Assert.assertTrue("Le fichier reason_categories.json doit exister après sauvegarde",
                Files.exists(filePath));

        String contenu = Files.readString(filePath);
        // Vérification basique : on doit trouver "categories" dans le JSON
        Assert.assertTrue("Le fichier JSON doit contenir la clé 'categories'",
                contenu.contains("categories"));
    }
}