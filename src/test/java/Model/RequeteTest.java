package Model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RequeteTest {

    @Test
    public void soumettreRequete() {

        // Charger les requêtes
        GestionRequete.chargeRequetes();
        // Créer une requête avec des caractéristiques données
        GestionRequete.soumettreRequete("Réparation de route", "Routes endommagées à cause de nids de poules", "Travaux routiers", "02/01/2025");


        // Récupérer la requête en dernier index de la liste
        Requete requete = GestionRequete.getListeRequetes().get(GestionRequete.getListeRequetes().size() - 1);

        // Tester les caractéristiques attendues de la requête
        assertEquals("Réparation de route", requete.getTitreTravail());
        assertEquals("Routes endommagées à cause de nids de poules", requete.getDescription());
        assertEquals("Travaux routiers", requete.getTypeTravaux());
        assertEquals("02/01/2025", requete.getDebut());

        GestionRequete.supprimerRequete(GestionRequete.getListeRequetes().size() - 1);
    }
}