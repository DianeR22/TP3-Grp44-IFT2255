package Model;

import org.junit.Test;

import java.util.Scanner;
import static org.mockito.Mockito.*;

public class UtilisateurTest {

    @Test
    public void testConnexionSuccess() {
        // Créer une instance de Resident d'un résident inscrit
        Resident resident = new Resident("Dupont", "Bob", "bob.dupont@gmail.com", "M0tdepasse24$", "15/02/2001", "514-667-4567", "Montreal, Plateau");

        // Appel de la méthode pour tester
        resident.connexion();

        // Vérifier que la méthode afficherMenu() a été appelée après la connexion réussie
        verify(resident).afficherMenu();
    }

}