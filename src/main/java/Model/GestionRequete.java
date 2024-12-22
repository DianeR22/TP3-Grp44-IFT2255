package Model;

import View.RequeteView;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
/**
 * Classe permettant de faire des opérations comme ajouter une requête à la liste
 * des requêtes, à la supprimer, à sauvegarder une requête ou à
 * charger la liste des requêtes
 */
public class GestionRequete {

    private static final String FICHIER_REQUETES = "data/requetes.json";

    // Initialisation d'une liste de requêtes
    private static List<Requete> listeRequetes = new ArrayList<>();

    // Getter et setter

    /**
     * Permet de récupérer la liste des requêtes
     * @return la liste des requêtes
     */
    public static List<Requete> getListeRequetes() {
        return listeRequetes;
    }

    /**
     * Permet de définir une liste des requêtes
     * @param listeRequetes
     */
    public static void setListeRequetes(List<Requete> listeRequetes) {
        GestionRequete.listeRequetes = listeRequetes;
    }

    /**
     * Permet d'ajouter une requête à la liste des requêtes
     * @param requete
     */
    // Ajouter une requête à la liste des requêtes
    public static void ajouterRequete(Requete requete){
        listeRequetes.add(requete);
    }




    /**
     *  Méthode qui prend l'index de la requête à supprimer et supprime
     *  cette requête de la liste des requêtes ainsi que du fichier JSON.
     * @param index index de la requête dans la liste
     */
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

        // Sauvegarder les requêtes de la liste dans le json des requêtes
        saveRequete();
    }}

    // Méthode pour vider la liste des requêtes
    public static void viderListe() { listeRequetes.clear();
    }

    /**
     * SoumettreRequete permet de créer une requête et de l'ajouter à la liste des requêtes
     *  Elle prend en paramètres toutes les caractéristiques d'une requête en String
     * @param titreTravail titre du travail
     * @param description la description du travail
     * @param typeTravail le type du travail parmi une liste proposée
     * @param dateDebut la date de début souhaitée
     */
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

    /**
     *  Cette méthode sert à sauvegarder une requête à la liste de requêtes dans le
     *  fichier json approprié
     */
    public static void saveRequete(){
        ObjectMapper obj = new ObjectMapper();
        try {
            obj.writeValue(new File(FICHIER_REQUETES), listeRequetes);
            //System.out.println("Requêtes sauvegardées avec succès");
        } catch (IOException e) {
            System.out.println("Erreur dans la sauvegarde de la requête.");
        }
    }


    /**
     * Méthode servant à charger les requetes et à les placer dans le fichier json
     */
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



    /** FiltrerParType sert à filtrer une liste de requêtes qui possède le filtre
     * de type de travail en caractéristique et à afficher ces requêtes
     * @param typeTravaux type de travail entré par l'utilisateur
     * @return une liste de requêtes qui possède ce type de travail en caraactéristique
     */
    public static List<Requete> filtrerParType(String typeTravaux) {
        return listeRequetes.stream()
                .filter(requete -> requete.getTypeTravaux().equalsIgnoreCase(typeTravaux))
                .toList();
    }


    /** FiltrerParQuartier sert à filtrer une liste de requêtes qui possède le filtre
     * de quartier en caractéristique et à afficher ces requêtes
     * @param quartier le quartier entré par l'utilisateur
     * @return la liste de requêtes ayant ce quartier en caractéristique
     */
    public static List<Requete> filtrerParQuartier(String quartier) {
        return listeRequetes.stream()
                .filter(requete -> requete.getResident() != null && requete.getResident().getAdresse().contains(quartier))
                .toList();
    }


    /**
     * FiltrerParDebut permet de récupérer une liste de requêtes dont la date
     * de début est la même et est fixée par l'utilisateur
     * @param dateDebut date choisie par utilisateur
     * @return une liste de requêtes dont la date de début est la même
     */
    public static List<Requete> filtrerParDate(String dateDebut) {
        return listeRequetes.stream()
                .filter(requete -> requete.getDebut().equals(dateDebut))
                .toList();
    }


    /**
     * Méthode qui prend en paramètre le résident souhaitant suivre sa requête.
     * Elle commence par afficher toutes les requêtes afin que le résident puisse choisir ce qu'il souhaite.
     * Elle affiche en appelant afficherSuivi les candidatures liées à la requête en question.
     *
     * @param resident Le résident souhaitant suivre sa requête.
     */
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

    /**
     * Prend en paramètre une requête et affiche les candidatures associées.
     * Si l'état de la candidature est : en attente, le résident peut choisir la candidature et laisser un message
     * à l'intervenant. Si l'état est : confirmée par l'intervenant, le résident devrait fermer la requête.
     *
     * @param requete La requête pour laquelle afficher les candidatures.
     */
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
