package Model;

import View.RequeteView;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GestionRequete {

    private static final String FICHIER_REQUETES = "data/requetes.json";

    // Initialisation d'une liste de requêtes
    private static List<Requete> listeRequetes = new ArrayList<>();

    // Getter et setter
    public static List<Requete> getListeRequetes() {
        return listeRequetes;
    }

    public static void setListeRequetes(List<Requete> listeRequetes) {
        GestionRequete.listeRequetes = listeRequetes;
    }

    // Ajouter une requête à la liste des requêtes
    public static void ajouterRequete(Requete requete){
        listeRequetes.add(requete);
    }

    public static void supprimerRequete(int index){

        int size = listeRequetes.size();
        System.out.println(size);
        // Trouver l'index de la requête à supprimer
        if (index >=0 && index <= size) {
            Requete requeteSupprimee = listeRequetes.get(index);
            // Supprimer la requête de la liste
            listeRequetes.remove(requeteSupprimee);

            // Dissocier le résident de la requête
        if (requeteSupprimee.getResident() != null) {
            requeteSupprimee.setResident(null); // Retirer la référence du résident
        }
        System.out.println("Requête supprimée avec succès!");
            // Vérifier immédiatement que la requête est supprimée
        RequeteView.afficherRequetes();
        saveRequete();
        System.out.println(size);
    }}

    // Méthode pour vider la liste des requêtes
    public static void viderListe() { listeRequetes.clear();
    }

    // SoumettreRequete permet de créer une requête et de l'ajouter à la liste des requêtes
    // Elle prend en paramètres toutes les caractéristiques d'une requête en String
    public static void soumettreRequete(String titreTravail,String description, String typeTravail, String dateDebut){
        Requete requete = new Requete(titreTravail, description, typeTravail, dateDebut);
        GestionRequete.ajouterRequete(requete);

        Resident resident = GestionResidents.getResidentConnecte();
        if (resident != null) {
            requete.setResident(resident);
        }
        GestionRequete.saveRequete();
    }

    // Cette méthode sert à charger une requête à la liste de requêtes dans le
    // fichier json approprié
    public static void saveRequete(){
        ObjectMapper obj = new ObjectMapper();
        try {
            obj.writeValue(new File(FICHIER_REQUETES), listeRequetes);
            System.out.println("Requetes sauvegardees avec succes");
        } catch (IOException e) {
            System.out.println("Erreur dans la sauvegarde.");
        }
    }

    // Méthode servant à sauvegarder une requete et à le placer dans le fichier json
    public static void chargeRequetes(){
        ObjectMapper obj = new ObjectMapper();
        try{
            File file = new File(FICHIER_REQUETES);
            if (file.exists()){
                listeRequetes = obj.readValue(file, obj.getTypeFactory().constructCollectionType(List.class, Requete.class));
                //System.out.println("Requetes chargees avec succes");
            }

        }catch(IOException e){
            System.out.println("Erreur dans le chargement.");
        }
    }


    // Méthode pour filtrer les requêtes par type de travaux
    public static List<Requete> filtrerParType(String typeTravaux) {
        return listeRequetes.stream()
                .filter(requete -> requete.getTypeTravaux().equalsIgnoreCase(typeTravaux))
                .toList();
    }

    // Méthode pour filtrer les requêtes par quartier
    public static List<Requete> filtrerParQuartier(String quartier) {
        return listeRequetes.stream()
                .filter(requete -> requete.getResident() != null && requete.getResident().getAdresse().contains(quartier))
                .toList();
    }

    // Méthode pour filtrer les requêtes par date de début
    public static List<Requete> filtrerParDate(String dateDebut) {
        return listeRequetes.stream()
                .filter(requete -> requete.getDebut().equals(dateDebut))
                .toList();
    }

    // Initialisation de 3 requêtes
    public static void initialiserRequetes() {
        Requete requete1 = new Requete( "Réparation du réseau d'égouts",
                "Réparer une fuite sur la conduite principale d'égout sous la rue principale",
                "Égouts",
                "2024-12-03");
        Requete requete2 = new Requete("Déblocage de conduits souterrains",
                "Éliminer l'obstruction causée par des racines d'arbres bloquant les canalisations",
                "Égouts",
                "2024-11-20");
        Requete requete3 = new Requete("Réparation des nids de poule", "Réparer les nids de poule sur la rue principale", "Entretien de route","2024-11-20");

        // Ajouter ces intervenants à la liste
        ajouterRequete(requete1);
        ajouterRequete(requete2);
        ajouterRequete(requete3);
    }

}
