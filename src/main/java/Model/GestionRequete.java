package Model;

import java.util.ArrayList;
import java.util.List;

public class GestionRequete {

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

    // Méthode pour vider la liste des requêtes
    public static void viderListe() { listeRequetes.clear();
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
