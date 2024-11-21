package Model;

import java.util.ArrayList;
import java.util.List;

public class GestionIntervenants{

    // Initialisation d'une liste statique d'intervenants
    private static List<Intervenant> listeIntervenants = new ArrayList<>();

    // Getter et setter
    public static List<Intervenant> getListeIntervenants() {
        return listeIntervenants;
    }

    public static void setListeIntervenants(List<Intervenant> listeIntervenants) {
        GestionIntervenants.listeIntervenants = listeIntervenants;
    }

    // Méthode pour ajouter un intervenant à la liste des intervenants
    public static void ajouterIntervenant(Intervenant intervenant) {
        listeIntervenants.add(intervenant);
    }

    // Initialisation des intervenants
    public static void initialiserIntervenants() {
        Intervenant intervenant1 = new Intervenant("Marie", "Akloul", "marie.akloul@gmail.com", "Securit3%", "Entreprise publique", "12345678");
        Intervenant intervenant2 = new Intervenant("Jean", "Desjardins", "jean.desjardins@gmail.com", "Travaux!2023", "Entrepreneur privé", "87654321");
        Intervenant intervenant3 = new Intervenant("Amine", "Ibrir", "amine.ibrir@gmail.com", "Travail@2024", "Particulier", "11223344");

        // Ajouter ces intervenants à la liste
        ajouterIntervenant(intervenant1);
        ajouterIntervenant(intervenant2);
        ajouterIntervenant(intervenant3);
    }

}
