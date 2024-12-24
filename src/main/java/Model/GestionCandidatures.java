package Model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Cette classe gère diverses opérations sur les candidatures : ajout, suppression,
 * chargement, sauvegarde et suivi.
 */
public class GestionCandidatures {

    // Initialisation d'une liste de candidatures
    private static List<Candidature> listeCandidatures = new ArrayList<>();

    // Fichier JSON des candidatures
    private static final String FICHIER_CANDIDATURES = "data/candidatures.json";

    /**
     * Retourne la liste des candidatures.
     *
     * @return La liste des candidatures.
     */
    public static List<Candidature> getListeCandidatures() {
        return listeCandidatures;
    }

    /**
     * Définit la liste des candidatures.
     *
     * @param listeCandidatures La nouvelle liste des candidatures.
     */
    public static void setListeCandidatures(List<Candidature> listeCandidatures) {
        GestionCandidatures.listeCandidatures = listeCandidatures;
    }

    /**
     * Ajoute une candidature à la liste et la sauvegarde dans le fichier JSON.
     *
     * @param candidature La candidature.
     */
    public static void ajouterCandidature(Candidature candidature) {
        if (candidature == null) {
            System.out.println("Candidature invalide !");
            return;
        }

        listeCandidatures.add(candidature);
        saveCandidature();
        System.out.println("Votre candidature a été soumise avec succès !");
    }

    /**
     * Supprime une candidature de la liste et recharge les candidatures depuis le fichier JSON.
     *
     * @param candidature La candidature à supprimer.
     */
    public static void supprimerCandidature(Candidature candidature) {
        if (candidature == null) {
            System.out.println("Candidature invalide !");
            return;
        }

        listeCandidatures.remove(candidature);
        saveCandidature();
        System.out.println("Candidature supprimée avec succès !");
    }

    /**
     * Charge les candidatures depuis le fichier JSON et les ajoute à la liste des candidatures.
     */
    public static void chargeCandidatures() {
        ObjectMapper obj = new ObjectMapper();
        try {
            File file = new File(FICHIER_CANDIDATURES);
            if (file.exists()) {
                listeCandidatures = obj.readValue(
                        file,
                        obj.getTypeFactory().constructCollectionType(List.class, Candidature.class)
                );
            }
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement des candidatures : " + e.getMessage());
        }
    }

    /**
     * Sauvegarde la liste des candidatures dans un fichier JSON.
     */
    public static void saveCandidature() {
        ObjectMapper obj = new ObjectMapper();
        obj.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            obj.writeValue(new File(FICHIER_CANDIDATURES), listeCandidatures);
        } catch (IOException e) {
            System.out.println("Erreur lors de la sauvegarde des candidatures : " + e.getMessage());
        }
    }

    /**
     * Retourne les candidatures liées à un intervenant.
     *
     * @param intervenant L'intervenant concerné.
     * @return Une liste des candidatures de cet intervenant.
     */
    public static List<Candidature> getCandidaturesDeIntervenant(Intervenant intervenant) {
        return listeCandidatures.stream()
                .filter(c -> c.getIntervenant().equals(intervenant))
                .collect(Collectors.toList());
    }

    /**
     * Permet à un intervenant de suivre ses candidatures.
     *
     * @param intervenant L'intervenant concerné.
     */
    public static void suiviCandidature(Intervenant intervenant) {
        List<Candidature> candidaturesSuivis = getCandidaturesDeIntervenant(intervenant);

        if (candidaturesSuivis.isEmpty()) {
            System.out.println("Aucune candidature en suivi pour cet intervenant.");
            return;
        }

        System.out.println("Liste des candidatures suivies :");
        for (int i = 0; i < candidaturesSuivis.size(); i++) {
            Candidature candidature = candidaturesSuivis.get(i);
            System.out.println((i + 1) + ". Requête : " + candidature.getRequete().getTitreTravail());
            System.out.println("État : " + candidature.getEtat());
            System.out.println("Date début : " + candidature.getDateDebut());
            System.out.println("Date fin : " + candidature.getDateFin());
            System.out.println("Message du résident : " + (candidature.getMessageResident() == null ? "Aucun" : candidature.getMessageResident()));
            System.out.println();
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrez le numéro de la candidature à confirmer (ou 0 pour annuler) : ");
        int choix = scanner.nextInt();
        scanner.nextLine();

        if (choix < 1 || choix > candidaturesSuivis.size()) {
            System.out.println("Choix invalide. Aucune candidature confirmée.");
            return;
        }

        Candidature candidatureChoisie = candidaturesSuivis.get(choix - 1);
        candidatureChoisie.setEtat("Confirmée par l'intervenant");
        System.out.println("La candidature a été confirmée avec succès !");
        saveCandidature();
    }

    /**
     * Vérifie si les dates de début et de fin sont valides.
     *
     * @param dateDebut La date de début.*/
    public static boolean validerDates(String dateDebut, String dateFin) {
        try {
            // Convertir les chaînes en LocalDate
            LocalDate debut = LocalDate.parse(dateDebut);
            LocalDate fin = LocalDate.parse(dateFin);

            // Vérifier que la date de fin est postérieure ou égale à la date de début
            if (!fin.isAfter(debut) && !fin.isEqual(debut)) {
                System.out.println("Erreur : La date de fin doit être postérieure ou égale à la date de début.");
                return false;
            }
            return true;
        } catch (Exception e) {
            // Gérer les exceptions de formatage des dates
            System.out.println("Erreur : Format de date invalide. Utilisez le format YYYY-MM-DD.");
            return false;
        }
    }

}