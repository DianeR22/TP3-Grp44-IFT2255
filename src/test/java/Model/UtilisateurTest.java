package Model;

import org.junit.Test;
import java.util.Scanner;
import static org.mockito.Mockito.*;

public class UtilisateurTest {

    @Test
    public void testConnexionFailure() {
        // Créer un scanner de Mock
        Scanner scannerMock = mock(Scanner.class);

        // Simuler des entrées invalides
        when(scannerMock.next()).thenReturn("bob.dupont@gmail.com", "MauvaisMDP");

        // Créer une instance de Resident d'un résident inscrit
        Resident resident = new Resident(
                "Dupont",
                "Bob",
                "bob.dupont@gmail.com",
                "M0tdepasse24$",
                "15/02/2001",
                "514-667-4567",
                "Montreal, Plateau"
        );

        // Injecter le mock de Scanner dans l'instance du résident
        resident.setScanner(scannerMock);

        // Mocker la méthode verifierConnexion pour un échec de connexion (false)
        Resident spyResident = spy(resident);
        when(spyResident.verifierConnexion(anyString(), anyString())).thenReturn(false);

        // Appel de la méthode connexion
        spyResident.connexion(resident);

        // Vérifier que la méthode afficherMenu n'a pas été appelée
        verify(spyResident, never()).afficherMenu();
    }
}