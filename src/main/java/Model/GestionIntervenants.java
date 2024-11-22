package Model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestionIntervenants{

    // Initialisation d'une liste d'intervenants
    private static List<Intervenant> listeIntervenants = new ArrayList<>();

    // Fichier json des intervenants
    private static final String FICHIER_INTERVENANTS = "data/intervenants.json";

    // Getter et setter
    public static List<Intervenant> getListeIntervenants() {
        return listeIntervenants;
    }

    public static void setListeIntervenants(List<Intervenant> listeIntervenants) {
        GestionIntervenants.listeIntervenants = listeIntervenants;
    }

    // Méthode pour ajouter un intervenant à la liste des intervenants et à le sauvegarder dans le fichier json
    public static void ajouterIntervenant(Intervenant intervenant) {
        listeIntervenants.add(intervenant);
        saveIntervenant();
    }


    // Cette méthode sert à charger les intervenants à la liste d'intervenants du
    // fichier json approprié
    public static void chargeIntervenants(){
        ObjectMapper obj = new ObjectMapper();
        try{
            File file = new File(FICHIER_INTERVENANTS);
            if (file.exists()){
                listeIntervenants = obj.readValue(file, obj.getTypeFactory().constructCollectionType(List.class, Intervenant.class));
            }
        } catch(IOException e){
            System.out.println("Erreur dans le chargement des intervenants." + e.getMessage());
        }
    }

    // Méthode servant à sauvegarder un intervenant et à le placer dans le fichier json
    public static void saveIntervenant(){
        ObjectMapper obj = new ObjectMapper();
        try {
            obj.writeValue(new File(FICHIER_INTERVENANTS), listeIntervenants);
            System.out.println("Intervenants sauvegardees avec succes");
        } catch (IOException e) {
            System.out.println("Erreur dans la sauvegarde.");
        }
    }

    // Méthode qui permet à l'intervenant de soumettre une candidature
    public static void soumettreCandidature(){
        // Récupérer le input de l'utilisateur
        Scanner scanner = new Scanner(System.in);

        // Affichage de toutes les requêtes
        System.out.println("Voici la liste des requêtes de travail: ");
        List<Requete> listeRequetes = GestionRequete.getListeRequetes();

        if (listeRequetes.isEmpty()) {
            System.out.println("Aucune requête disponible pour le moment.");
            return;
        }

        // Affichage des titres des requêtes de façon numérotée
        for (int i = 0; i < listeRequetes.size(); i++) {
            System.out.println((i + 1) + ". " + listeRequetes.get(i).getTitreTravail());
        }

        System.out.println("Entrer le numéro de la requête que vous souhaitez:");
        int index = scanner.nextInt();

        // Valider l'index
        if (index < 1 || index > listeRequetes.size()) {
            System.out.println("Choix invalide. Retour au menu principal.");
            return;
        }

        // Récupérer la requête choisie dans la liste
        Requete requeteChoisie = listeRequetes.get(index-1);

        // Demander les dates
        System.out.print("Entrez la date de début (format : YYYY-MM-DD) : ");
        String dateDebut = scanner.nextLine();

        System.out.print("Entrez la date de fin (format : YYYY-MM-DD) : ");
        String dateFin = scanner.nextLine();

        // Récupérer l'intervenant en question
        Intervenant intervenant = Intervenant.getIntervenantConnecte();

        // Créer une instance de candidature
        Candidature candidature = new Candidature(dateDebut, dateFin, requeteChoisie, intervenant);
        // Ajouter la candidature à la liste de candidatures
        GestionCandidatures.ajouterCandidature(candidature);

        System.out.println("Votre candidature a été soumise avec succès pour la requête: requete.getTitreTravail()");
    }

}
