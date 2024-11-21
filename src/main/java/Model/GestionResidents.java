package Model;

import java.util.ArrayList;
import java.util.List;

public class GestionResidents{

    // Initialisation d'une liste statique de résidents
    private static List<Resident> listeResidents = new ArrayList<>();

    // Getter et setter
    public static List<Resident> getListeResidents() {
        return listeResidents;
    }

    public static void setListeResidents(List<Resident> listeResidents) {
        GestionResidents.listeResidents = listeResidents;
    }

    // Méthode pour ajouter un résident à la liste des résidents
    public static void ajouterResident(Resident resident) {
        listeResidents.add(resident);
    }

    // Initialisation des résidents
    public static void initialiserResidents() {
        Resident resident1 = new Resident("Dupont", "Bob", "bob.dupont@gmail.com", "M0tdepasse24$", "15/02/2001", "514-667-4567", "Montreal, Plateau");
        Resident resident2 = new Resident("Habib", "Marie", "marie.habib@gmail.com", "Securit3%", "20/05/1967", "514-987-0000", "Montreal, Rosemont");
        Resident resident3 = new Resident("Gauthier", "Thomas", "thomas.gauthier@gmail.com", "DonneeS321%", "10/12/1998", "438-246-1357", "Montreal, Hochelaga");

        // Ajouter ces résidents à la liste
        ajouterResident(resident1);
        ajouterResident(resident2);
        ajouterResident(resident3);
    }

}

