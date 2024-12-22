package Model;

import org.junit.Test;
import static org.junit.Assert.assertTrue;


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
                "Montreal"
        );

        // Ajout résident
        GestionResidents.ajouterResident(resident);

        // Vérifier que le résident a bien été ajouté à la liste
        assertTrue("Le résident devrait être ajouté à la liste",
                GestionResidents.getListeResidents().contains(resident)
        );

        GestionResidents.supprimerResident(GestionResidents.getListeResidents().size()-1);
    }
}