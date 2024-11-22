package Model;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class ConnexionTest {
  
@Test 
public void testTraiterChoixIntervenant() { 
    // Créer deux mock de résident et intervenant 
    Resident resident = mock(Resident.class); 
    Intervenant intervenant = mock(Intervenant.class); 

    // Appel de traiterChoix avec option 2: Se connecter en tant qu'intervenant 
    Connexion.traiterChoix("2", resident, intervenant); 

    // Vérifier que la connexion est appelé pour l'intervenant 
    verify(intervenant).connexion(); 

    // Vérifier que la connexion n'est pas appelé pour le résident 
    verify(resident, never()).connexion(); 
} 

@Test 
public void testTraiterChoixResident(){ 
    // Créer deux mock de résident et intervenant 
    Resident resident = mock(Resident.class); 
    Intervenant intervenant = mock(Intervenant.class); 

    // Appel de traiterChoix avec option 1: Se connecter en tant que resident 
    Connexion.traiterChoix("1", resident, intervenant); 

    // Vérifier que la connexion est appelé pour l'intervenant 
    verify(resident).connexion(); 

    // Vérifier que la connexion n'est pas appelé pour le résident 
    verify(intervenant, never()).connexion(); 

  @Test 
    public void testTraiterChoixInscription(){ 
        // Créer deux mock de résident et intervenant 
        Resident resident = mock(Resident.class); 
        Intervenant intervenant = mock(Intervenant.class); 
 
        // Appel de traiterChoix avec option invalide 
        Connexion.traiterChoix("3", resident, intervenant); 
 
        // Verifier que les méthodes connexion de resident et intervenant ne 
        // sont pas appelées 
        verify(resident, never()).connexion(); 
        verify(intervenant, never()).connexion(); 
    } 
}
