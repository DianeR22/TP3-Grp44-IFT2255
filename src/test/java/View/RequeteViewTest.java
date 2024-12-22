package View;

import Model.GestionRequete;
import Model.Requete;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class RequeteViewTest extends TestCase {

    @Test
    public void testAfficherRequetes() {

        // Initialiser une liste de requêtes vide
        List<Requete> listeRequetes = new ArrayList<>();

        // Ajouter la liste vide à la gestion des requêtes
        GestionRequete.setListeRequetes(listeRequetes);

        // Créer un ByteArrayOutputStream pour enregistrer les données de sortie
        // dans un tableau
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        // Appel de la méthode afficherRequetes
        RequeteView.afficherRequetes();

        // Vérifier qu'aucune requête n'a été trouvée
        assertTrue(output.toString().contains("Aucune requête trouvée"));
    }
}