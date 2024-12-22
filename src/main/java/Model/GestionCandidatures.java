package Model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class GestionCandidatures {

    // Initialisation d'une liste de candidatures
    private static List<Candidature> listeCandidatures = new ArrayList<>();

    // Fichier json des candidatures
    private static final String FICHIER_CANDIDATURES = "data/candidatures.json";


    // Getter et setter
    public static List<Candidature> getListeCandidatures() {
        return listeCandidatures;
    }

    public static void setListeCandidatures(List<Candidature> listeCandidatures) {
        GestionCandidatures.listeCandidatures = listeCandidatures;
    }

    // Méthode servant à ajouter une candidature et à la sauvegarder dans le fichier json
    public static void ajouterCandidature(Candidature candidature){
        if (candidature == null) {
            System.out.println("Candidature invalide !");
            return;
        }
        listeCandidatures.add(candidature);
        saveCandidature();
        System.out.println("Nouvelle candidature soumise!");
    }

    // Méthode qui supprime la candidature voulue et charge les candidatures
    // afin d'être à jour
    public static void supprimerCandidature(Candidature candidature){
        if (candidature == null) {
            System.out.println("Candidature invalide !");
            return;
        }
            listeCandidatures.remove(candidature);
            chargeCandidatures();
            System.out.println("Nouvelle candidature soumise!");
        }


    // Cette méthode sert à charger les candidatures à la liste de candidatures du
    // fichier json approprié
    public static void chargeCandidatures(){
        ObjectMapper obj = new ObjectMapper();
        try{
            File file = new File(FICHIER_CANDIDATURES);
            if (file.exists()){
                listeCandidatures = obj.readValue(file, obj.getTypeFactory().constructCollectionType(List.class, Candidature.class));
            }
        } catch(IOException e){
            System.out.println("Erreur dans le chargement des candidatures." + e.getMessage());
        }
    }


    // Méthode servant à sauvegarder une candidature et à la placer dans le fichier json
    public static void saveCandidature(){
        ObjectMapper obj = new ObjectMapper();
        try {
            obj.writeValue(new File(FICHIER_CANDIDATURES), listeCandidatures);
            System.out.println("Candidature sauvegardée avec succès");
        } catch (IOException e) {
            System.out.println("Erreur dans la sauvegarde de la candidature.");
        }
    }

    // Cette méthode permet de récupérer les candidatures d'un intervenant spécifique
    public static List<Candidature> getCandidaturesDeIntervenant(Intervenant intervenant) {
        return listeCandidatures.stream()
                // Pour chaque candidature, voir si l'intervenant correspond
                .filter(c -> c.getIntervenant().equals(intervenant))
                .collect(Collectors.toList());
    }

    public static void suiviCandidature(Intervenant intervenant){

        // Extraire les candidatures postulés par intervenant
        List<Candidature> candidaturesSuivis = getCandidaturesDeIntervenant(intervenant);
        // Vérifie s'il y a des candidatures
        if (candidaturesSuivis.isEmpty()) {
            System.out.println("Aucune candidature en suivi pour cet intervenant.");
            return;
        }
        // Affiche les candidatures et les informations
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

        // Demande à l'intervenant de sélectionner une candidature à confirmer
        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrez le numéro de la candidature à confirmer: ");
        int choix = scanner.nextInt();
        scanner.nextLine();

        if (choix < 1 || choix > candidaturesSuivis.size()) {
            System.out.println("Choix invalide.");
            return;
        }

        // Confirme la candidature sélectionnée
        Candidature candidatureChoisie = candidaturesSuivis.get(choix - 1);
        candidatureChoisie.setEtat("Confirmée par l'intervenant");

        System.out.println("Candidature confirmée pour la requête : " + candidatureChoisie.getRequete().getTitreTravail());
    }

}
