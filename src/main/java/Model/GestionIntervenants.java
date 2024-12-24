package Model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Classe qui gère les intervenants. Possible d'ajouter un intervenant à la liste,
 * de charger un intervenant, de sauvegarder la liste des intervenants, de charger une liste
 * d'intervenants depuis le fichier json des intervenants pour en récupérer une liste,
 * de soumettre une candidature ou de retirer une candidature.
 */
public class GestionIntervenants{

    // Initialisation d'une liste d'intervenants
    private static List<Intervenant> listeIntervenants = new ArrayList<>();

    // Fichier json des intervenants
    private static final String FICHIER_INTERVENANTS = "data/intervenants.json";

    // Getter et setter

    /**
     * Récupèrer la liste des intervenants.
     * @return la liste des intervenants
     */
    public static List<Intervenant> getListeIntervenants() {
        return listeIntervenants;
    }

    /**
     * Définit la liste des intervenants
     * @param listeIntervenants
     */
    public static void setListeIntervenants(List<Intervenant> listeIntervenants) {
        GestionIntervenants.listeIntervenants = listeIntervenants;
    }

    // Méthode pour ajouter un intervenant à la liste des intervenants et à le sauvegarder dans le fichier json

    /**
     * Ajoute un intervenant à la liste des intervenants
     * @param intervenant
     */
    public static void ajouterIntervenant(Intervenant intervenant) {
        listeIntervenants.add(intervenant);
        saveIntervenant();
    }

    // Cette méthode sert à charger les intervenants à la liste d'intervenants du
    // fichier json approprié

    /**
     * Procède au chargement des intervenants présents dans le fichier json, donc inscrits.
     */
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

    /**
     * Sauvegarde d'un intervenant en sauvegardant la liste des intervenants dans le fichier
     * json.
     */
    public static void saveIntervenant(){
        ObjectMapper obj = new ObjectMapper();
        try {
            obj.writeValue(new File(FICHIER_INTERVENANTS), listeIntervenants);
        
        } catch (IOException e) {
            System.out.println("Erreur dans la sauvegarde.");
        }
    }

    // Méthode pour charger les résidents depuis le fichier JSON

    /**
     * Permet de charger les intervenants depuis le fichier json pour en extraire
     * une liste
     * @return une liste des intervenants
     */
    public static List<Intervenant> chargerIntervenantsDepuisFichier() {
        // Spécifiez le chemin de votre fichier JSON
        File fichier = new File("data/intervenants.json");

        // Créez un ObjectMapper pour mapper le JSON à des objets Java
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Lire le fichier JSON et mapper son contenu à une liste d'objets Intervenant
            List<Intervenant> intervenants = objectMapper.readValue(fichier, objectMapper.getTypeFactory().constructCollectionType(List.class, Intervenant.class));
            return intervenants;
        } catch (IOException e) {
            e.printStackTrace();
            return null;  // En cas d'erreur, retourner null
        }
    }

    /** Méthode qui permet à l'intervenant de soumettre une candidature en entrant
    // les informations essentielles. Elle ajoute la candidature à la liste des
    // candidatures et au fichier json correspondant.
    */
    public static void soumettreCandidature() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Voici la liste des requêtes de travail: ");
        List<Requete> listeRequetes = GestionRequete.getListeRequetes();

        if (listeRequetes.isEmpty()) {
            System.out.println("Aucune requête disponible pour le moment.");
            return;
        }

        for (int i = 0; i < listeRequetes.size(); i++) {
            System.out.println((i + 1) + ". " + listeRequetes.get(i).getTitreTravail());
        }

        System.out.println("Entrer le numéro de la requête que vous souhaitez:");
        int index = scanner.nextInt();
        scanner.nextLine();

        if (index < 1 || index > listeRequetes.size()) {
            System.out.println("Choix invalide. Retour au menu principal.");
            return;
        }

        int tentatives = 0;
        final int MAX_TENTATIVES = 3;
        boolean datesValides = false;
        String dateDebut = null;
        String dateFin = null;

        while (tentatives < MAX_TENTATIVES) {
            try {
                System.out.print("Entrez la date de début (format : YYYY-MM-DD) : ");
                dateDebut = scanner.nextLine().trim();

                System.out.print("Entrez la date de fin (format : YYYY-MM-DD) : ");
                dateFin = scanner.nextLine().trim();

                if (GestionCandidatures.validerDates(dateDebut, dateFin)) {
                    datesValides = true;
                    break;
                }
            } catch (Exception e) {
                // Aucun message d'erreur supplémentaire ici
            }

            tentatives++;
            if (tentatives < MAX_TENTATIVES) {
                System.out.println("Veuillez réessayer. Tentative " + tentatives + " sur " + MAX_TENTATIVES + ".");
            }
        }

        if (!datesValides) {
            System.out.println("Tentatives échouées. Veuillez réessayer plus tard.");
            return;
        }

        Intervenant intervenant = Intervenant.getIntervenantConnecte();
        if (intervenant == null) {
            System.out.println("Erreur : Aucun intervenant connecté.");
            return;
        }

        Requete requeteChoisie = listeRequetes.get(index - 1);
        Candidature candidature = new Candidature(dateDebut, dateFin, requeteChoisie, intervenant);
        GestionCandidatures.ajouterCandidature(candidature);
    }

    /** Méthode permettant de supprimer une candidature. Elle affiche les candidatures
    // d'un intervenant et retire celle que l'intervenant choisit. Les candidatures
     restantes sont finalement chargées dans le json.
     */
    public static void supprimerCandidature() {

        Scanner scanner = new Scanner(System.in);

        // Récupérer l'intervenant connecté
        Intervenant intervenant = Intervenant.getIntervenantConnecte();

        // Récupérer les candidatures soumises de l'intervenant
        List<Candidature> listeCandidatures = GestionCandidatures.getCandidaturesDeIntervenant(intervenant);

        if (listeCandidatures.isEmpty()) {
            System.out.println("Vous n'avez soumis aucune candidature pour le moment.");
            return;
        }

        System.out.println("Voici la liste des candidatures que vous avez soumise: ");

        // Afficher les candidatures de l'intervenant numérotées
        for (int i = 0; i < listeCandidatures.size(); i++){
            Candidature candidature = listeCandidatures.get(i);
            System.out.println((i+1) + ". " + candidature.getRequete().getTitreTravail() + "| Date de début: "
            + candidature.getDateDebut() + " | Date de fin: " + candidature.getDateFin());
        }

        System.out.println("Entrez le numéro de la candidature que vous souhaitez supprimer: ");
        int index = scanner.nextInt();

        // Valider l'index
        if (index < 1 || index > listeCandidatures.size()) {
            System.out.println("Choix invalide.");
            return;
        }

        // Récupérer et supprimer la candidature choisie
        Candidature candidatureChoisie = listeCandidatures.get(index-1);
        GestionCandidatures.supprimerCandidature(candidatureChoisie);

        System.out.println("Votre candidature pour le travail \"" + candidatureChoisie.getRequete().getTitreTravail() +
                "\" a été retirée avec succès.");

        // Charger la liste des candidatures actuelle après suppression
        GestionCandidatures.saveCandidature();
    }
}
