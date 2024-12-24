package Model;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValiderTest {

    @Test
    public void validerTypeTravailTest(){
        // Cas où le type de travail est valide et bien formaté
        assertTrue(Valider.validerTypeTravail("Travaux de gaz ou électricité"));
        assertTrue(Valider.validerTypeTravail("Entretien paysager"));
        assertTrue(Valider.validerTypeTravail("Travaux souterrains"));

        // Cas où le type de travail est valide avec des espaces supplémentaires et des accents
        assertTrue(Valider.validerTypeTravail(" Travaux  routiers"));
        assertTrue(Valider.validerTypeTravail("Entretien         urbain"));
        assertTrue(Valider.validerTypeTravail("Entretien des réseaux de télécommunication   "));

        // Cas où le type de travail est invalide
        assertFalse(Valider.validerTypeTravail("Travaux d'hiver"));
        assertFalse(Valider.validerTypeTravail("Travaux de nuit"));
        assertFalse(Valider.validerTypeTravail("Travail d'éclairage"));
    }
    @Test
    public void testValiderTel() {
        assertTrue(Valider.validerTel("123-456-7890"));
        assertTrue(Valider.validerTel("123.456.7890"));
        assertTrue(Valider.validerTel("123 456 7890"));
        assertFalse(Valider.validerTel("123456789"));
        assertFalse(Valider.validerTel("abc-def-ghij"));
    }

    @Test
    public void testValiderDate() {
        assertTrue(Valider.validerDate("12/05/2024"));
        assertFalse(Valider.validerDate("32/13/2024"));
        assertFalse(Valider.validerDate("12-05-2024"));
        assertFalse(Valider.validerDate("12/5/2024"));
    }

}
