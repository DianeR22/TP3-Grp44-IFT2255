package Model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GestionResidents{

    // Initialisation d'une liste statique de résidents
    private static List<Resident> listeResidents = new ArrayList<>();

    private static final String FICHIER_RESIDENTS = "data/residents.json";

    private static Resident residentConnecte;

    // Getter et setter

    public static List<Resident> getListeResidents() {
        return listeResidents;
    }

    public static void setListeResidents(List<Resident> listeResidents) {
        GestionResidents.listeResidents = listeResidents;
    }

    public static Resident getResidentConnecte() {
        return residentConnecte;
    }

    public static void setResidentConnecte(Resident resident) {
        GestionResidents.residentConnecte = resident;
    }

    // Méthode pour ajouter un résident à la liste des résidents
    public static void ajouterResident(Resident resident) {
        listeResidents.add(resident);
        saveResident();
    }

    // Cette méthode sert à charger un résident à la liste de résidents du
    // fichier json approprié
    public static void chargeResidents(){
        ObjectMapper obj = new ObjectMapper();
        try{
            File file = new File(FICHIER_RESIDENTS);
            if (file.exists()){
                listeResidents = obj.readValue(file, obj.getTypeFactory().constructCollectionType(List.class, Resident.class));
            }

        }catch(IOException e){
            System.out.println("Erreur dans le chargement des residents." + e.getMessage());
        }
    }

    // Méthode servant à sauvegarder un resident et à le placer dans le fichier json
    public static void saveResident(){
        ObjectMapper obj = new ObjectMapper();
        try {
            obj.writeValue(new File(FICHIER_RESIDENTS), listeResidents);
        } catch (IOException e) {
            System.out.println("Erreur dans la sauvegarde.");
        }
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

