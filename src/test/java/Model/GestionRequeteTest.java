package Model;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class GestionRequeteTest{

    @Test
    public void ajouterRequete() {

        GestionRequete.chargeRequetes();

        Requete requete = new Requete("Égouts", "Problème de canalisation", "Travaux souterrains", "05/08/2025");

        // / Ajouter la requête 
        GestionRequete.ajouterRequete(requete);

        Requete nouvelleRequete = GestionRequete.getListeRequetes().get(GestionRequete.getListeRequetes().size() - 1);
        // Vérifier que les informations sont correctes
        assertEquals("Égouts", nouvelleRequete.getTitreTravail());
        assertEquals("Problème de canalisation", nouvelleRequete.getDescription());
        assertEquals("Travaux souterrains", nouvelleRequete.getTypeTravaux());
        assertEquals("05/08/2025", nouvelleRequete.getDebut());

        GestionRequete.supprimerRequete(GestionRequete.getListeRequetes().size() - 1);
    }

    @Test
    public void filtrerParTypeAvecPlusieursResultats() {
        // Ajouter des requêtes temporaires
        Requete requete1 = new Requete("Réparation pont", "Réparation du pont de traversée", "Travaux routiers", "01/07/2025");
        Requete requete2 = new Requete("Réparation route", "Réparation des nids de poule sur l'autoroute", "Travaux routiers", "05/08/2025");
        GestionRequete.ajouterRequete(requete1);
        GestionRequete.ajouterRequete(requete2);

        // Filtrer par type "Travaux routiers"
        List<Requete> resultat = GestionRequete.filtrerParType("Travaux routiers");

        // Vérifications
        assertEquals(2, resultat.size());
        assertEquals("Réparation pont", resultat.get(0).getTitreTravail());
        assertEquals("Réparation route", resultat.get(1).getTitreTravail());

        // Nettoyer les données en supprimant les requêtes ajoutées
        GestionRequete.supprimerRequete(GestionRequete.getListeRequetes().size() - 1); // Supprimer requête 2
        GestionRequete.supprimerRequete(GestionRequete.getListeRequetes().size() - 1); // Supprimer requête 1
    }
}