package Model;

import View.RequeteView;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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

    // Méthode qui prend en param. l'index de la requête à supprimer et supprime
    // cette requête de la liste des requêtes ainsi que du fichier JSON.
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
        //RequeteView.afficherRequetes();
        saveRequete();
        //System.out.println(size);
    }}

    // Méthode pour vider la liste des requêtes
    public static void viderListe() { listeRequetes.clear();
    }

    // SoumettreRequete permet de créer une requête et de l'ajouter à la liste des requêtes
    // Elle prend en paramètres toutes les caractéristiques d'une requête en String
    public static void soumettreRequete(String titreTravail,String description, String typeTravail, String dateDebut){
        Requete requete = new Requete(titreTravail, description, typeTravail, dateDebut);
        GestionRequete.ajouterRequete(requete);

        Resident resident = Resident.getResidentConnecte();
        System.out.println("Le resident connecté est" + resident);
        if (resident != null) {
            requete.setResident(resident);
        }
        GestionRequete.saveRequete();
        System.out.println("Votre requête a été soumise avec succès!");
    }

    // Cette méthode sert à sauvegarder une requête à la liste de requêtes dans le
    // fichier json approprié
    public static void saveRequete(){
        ObjectMapper obj = new ObjectMapper();
        try {
            obj.writeValue(new File(FICHIER_REQUETES), listeRequetes);
            //System.out.println("Requêtes sauvegardées avec succès");
        } catch (IOException e) {
            System.out.println("Erreur dans la sauvegarde de la requête.");
        }
    }

    // Méthode servant à charger les requetes et à les placer dans le fichier json
    public static void chargeRequetes(){
        ObjectMapper obj = new ObjectMapper();
        try{
            File file = new File(FICHIER_REQUETES);
            if (file.exists()){
                listeRequetes = obj.readValue(file, obj.getTypeFactory().constructCollectionType(List.class, Requete.class));
            }

        }catch(IOException e){
            System.out.println("Erreur dans le chargement des requêtes.");
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

    // Méthode qui prend en param. le resident souhaitant suivre sa requête. Elle
    // commence par afficher toutes les requêtes afin que le résident puisse choisir
    // ce qu'il souhaite. Elle affiche en appelant afficherSuivi les candidatures
    // liées à la requête en question.
    public static void suiviRequete(Resident resident){

        Scanner scanner = new Scanner(System.in);

        // Récupérer les requêtes du résident spécifique
        List<Requete> listeRequetes = getRequetesDeResident(resident);

        if (listeRequetes.isEmpty()) {
            System.out.println("Vous n'avez soumis aucune requête.");
            return;
        }

        System.out.println("Vos requêtes: ");
        for (int i = 0; i < listeRequetes.size(); i++){
            Requete requete = listeRequetes.get(i);
            System.out.println((i+1) + ". " + requete.getTitreTravail());
        }

        System.out.println("Entrez le numéro de la requête que vous voulez suivre: ");
        int index = scanner.nextInt();

        // Valider l'index
        if (index < 1 || index > listeRequetes.size()) {
            System.out.println("Choix invalide.");
            return;
        }

        Requete requeteChoisie = listeRequetes.get(index-1);
        afficherSuiviRequete(requeteChoisie);
    }

    // Prend en param. une requête et affiche les candidatures associées. Si l'état de
    // la candidature est : en attaente, le résident peut choisir la candidature et laisser un message
    //  à l'intervenant. Si l'état est : confirmée par l'intervenant. Le résident devrait
    // fermer la requête.
    public static void afficherSuiviRequete(Requete requete) {
        System.out.println("Candidatures reçues pour votre requête : " + requete.getTitreTravail());

        // Liste des candidatures associées à cette requête
        List<Candidature> candidatures = new ArrayList<>();
        for (Candidature candidature : GestionCandidatures.getListeCandidatures()) {
            if (candidature.getRequete().equals(requete)) {
                candidatures.add(candidature);
            }
        }

        // Vérifie s'il y a des candidatures
        if (candidatures.isEmpty()) {
            System.out.println("Aucune candidature pour cette requête.");
            return;
        }

        // Affiche les candidatures avec numérotation
        System.out.println("Liste des candidatures :");
        for (int i = 0; i < candidatures.size(); i++) {
            Candidature candidature = candidatures.get(i);
            System.out.println((i + 1) + ". Intervenant : " + candidature.getIntervenant().getNom() + " " + candidature.getIntervenant().getPrenom());
            System.out.println("Type d'intervenant : " + candidature.getIntervenant().getType());
            System.out.println("Date de début : " + candidature.getDateDebut());
            System.out.println("Date de fin : " + candidature.getDateFin());
            System.out.println("État de la candidature : " + candidature.getEtat());
            System.out.println("---------------------------");
        }

        // Demande au résident de sélectionner une candidature
        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrez le numéro de la candidature à laquelle vous souhaitez répondre (1-" + candidatures.size() + ") : ");
        int choix = scanner.nextInt();
        scanner.nextLine();

        if (choix < 1 || choix > candidatures.size()) {
            System.out.println("Choix invalide.");
            return;
        }

        Candidature candidatureChoisie = candidatures.get(choix - 1);

        // Vérifie si la candidature est confirmée par l'intervenant
        if ("Confirmé par l'intervenant".equalsIgnoreCase(candidatureChoisie.getEtat())) {
            System.out.print("La candidature est confirmée par l'intervenant. Voulez-vous fermer la requête ? (O/N) : ");
            String fermerRequete = scanner.nextLine().trim().toUpperCase();
            if (fermerRequete.equals("O")) {
                requete.setEtat("Fermée");
                System.out.println("La requête a été fermée.");
                return;
            } else {
                System.out.println("La requête reste ouverte.");
                return;
            }
        }

        // Demander au résident s'il veut ajouter un message
        System.out.print("Voulez-vous ajouter un message à l'intervenant ? (O/N) : ");
        String reponse = scanner.nextLine().trim().toUpperCase();

        String message = "";
        if (reponse.equals("O")) {
            System.out.print("Entrez votre message pour l'intervenant : ");
            message = scanner.nextLine();
        } else if (!reponse.equals("N")) {
            System.out.println("Candidature acceptée. État mis à jour.");
            return;
        }

        // Met à jour la candidature
        candidatureChoisie.setMessageResident(message.isEmpty() ? null : message);
        candidatureChoisie.setEtat("Accepté par le résident.");

    }

    // Méthode qui prend en param. un resident et retourne une liste
    // de requête associée à un résident
    public static List<Requete> getRequetesDeResident(Resident resident) {
        // Vérifier si la liste de requêtes est vide
        if (listeRequetes == null || listeRequetes.isEmpty()) {
            return List.of(); // Retourner une liste vide si aucune requête n'est présente
        }

        // Filtrer les requêtes par le résident
        List<Requete> requetes = listeRequetes.stream()
                .filter(c -> {
                    // Si le résident de la requête est null, on l'ignore
                    if (c.getResident() == null) {
                        return false;
                    }
                    // Comparaison entre le résident de la requête et le résident passé en paramètre
                    return c.getResident().equals(resident);
                })
                .collect(Collectors.toList());

        return requetes;
    }
}
