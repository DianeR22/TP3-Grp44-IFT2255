package Model;

import junit.framework.TestCase;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class GestionRequeteTest{

    @Test
    public void ajouterRequete() {

        GestionRequete.chargeRequetes();

        Requete requete = new Requete("Égouts", "Problème de canalisation", "Travaux souterrains", "05/08/2025");

        // / Ajouter la requête à la gestion des requêtes
        GestionRequete.ajouterRequete(requete);

        Requete nouvelleRequete = GestionRequete.getListeRequetes().get(GestionRequete.getListeRequetes().size() - 1);
        // Vérifier que les informations sont correctes
        assertEquals("Égouts", nouvelleRequete.getTitreTravail());
        assertEquals("Problème de canalisation", nouvelleRequete.getDescription());
        assertEquals("Travaux souterrains", nouvelleRequete.getTypeTravaux());
        assertEquals("05/08/2025", nouvelleRequete.getDebut());

        GestionRequete.supprimerRequete(GestionRequete.getListeRequetes().size() - 1);
    }
}