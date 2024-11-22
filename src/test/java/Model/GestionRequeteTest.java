package Model;

import junit.framework.TestCase;
import org.junit.Test;

public class GestionRequeteTest extends TestCase {

    @Test
    public void ajouterRequete() {

        Requete requete = new Requete("Égouts", "Problème de canalisation", "Égouts et acqueducs", "05/08/2025");

        // / Ajouter la requête à la gestion des requêtes
        GestionRequete.ajouterRequete(requete);
        assertEquals(1, GestionRequete.getListeRequetes().size());

        Requete nouvelleRequete = GestionRequete.getListeRequetes().get(0);
        // Vérifier que les informations sont correctes
        assertEquals("Égouts", nouvelleRequete.getTitreTravail());
        assertEquals("Problème de canalisation", nouvelleRequete.getDescription());
        assertEquals("Égouts et aqueducs", nouvelleRequete.getTypeTravaux());
        assertEquals("05/08/2025", nouvelleRequete.getDebut());
    }
}