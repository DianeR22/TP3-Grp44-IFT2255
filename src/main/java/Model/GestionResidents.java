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
            System.out.println("Erreur dans le chargement des residents. " + e.getMessage());
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

}

