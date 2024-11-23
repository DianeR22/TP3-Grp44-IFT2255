package Model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RequeteTest {

    @Test
    public void soumettreRequete() {
        // Réinitialiser l'état avant chaque test en vidant la liste
        GestionRequete.viderListe();

        // Créer une requête avec des caractéristiques données
        GestionRequete.soumettreRequete("Réparation de route", "Routes endommagées à cause de nids de poules", "Entretien de route", "02/01/2025");

        // La liste, qui a été vidée, doit avoir une size de 1 après l'ajout d'une requête
        assertEquals(1, GestionRequete.getListeRequetes().size());

        // Récupérer la requête en index 0 de la liste
        Requete requete = GestionRequete.getListeRequetes().get(0);

        // Tester les caractéristiques attendues de la requête
        assertEquals("Réparation de route", requete.getTitreTravail());
        assertEquals("Routes endommagées à cause de nids de poules", requete.getDescription());
        assertEquals("Entretien de route", requete.getTypeTravaux());
        assertEquals("02/01/2025", requete.getDebut());
    }


}