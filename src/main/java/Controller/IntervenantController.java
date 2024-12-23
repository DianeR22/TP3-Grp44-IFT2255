package Controller;

import Model.*;
import View.IntervenantView;
import View.RequeteView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IntervenantController {

    public static void afficherMenuIntervenant() {
        IntervenantView.afficherMenuIntervenant();
    }

    public static void afficherMenuRequete() {
        RequeteView.afficherMenuRequete();
    }

    public static void soumettreCandidature() {
        GestionIntervenants.soumettreCandidature();
    }

    public static void supprimerCandidature() {
        GestionIntervenants.supprimerCandidature();
    }

    public static void suivreCandidature(Intervenant intervenant) {
        if (intervenant != null) {
            GestionCandidatures.suiviCandidature(intervenant);
        } else {
            System.out.println("Intervenant est nul. Impossible de suivre la candidature.");
        }
    }

    public static void soumettreProjet() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Titre du projet :");
        String titre = scanner.nextLine().trim();

        System.out.println("Description du projet :");
        String description = scanner.nextLine().trim();

        String typeTravaux = null;
        int attemptsType = 0;
        while (attemptsType < 3) {
            System.out.println("Type de travaux (choisissez parmi les catégories valides) :");
            for (Valider.TypeTravail type : Valider.TypeTravail.values()) {
                System.out.println("- " + type.getDescription());
            }
            String typeTravauxInput = scanner.nextLine().trim();
            if (Valider.validerTypeTravail(typeTravauxInput)) {
                typeTravaux = typeTravauxInput;
                break;
            } else {
                attemptsType++;
                System.out.println("Type de travaux invalide. Veuillez réessayer.");
            }
        }
        if (typeTravaux == null) {
            System.out.println("Nombre maximum de tentatives atteint. Veuillez réessayer plus tard.");
            return;
        }

        System.out.println("Quartiers affectés (séparés par des virgules) :"); //not used in the preference detection
        String quartiersInput = scanner.nextLine().trim();
        List<String> quartiers = quartiersInput.isEmpty() ? new ArrayList<>() : new ArrayList<>(List.of(quartiersInput.split("\\s*,\\s*")));

        System.out.println("Rues affectées (séparées par des virgules) :"); // Super important for our preference detection
        String ruesInput = scanner.nextLine().trim();
        List<String> rues = ruesInput.isEmpty() ? new ArrayList<>() : new ArrayList<>(List.of(ruesInput.split("\\s*,\\s*")));

        System.out.println("Codes postaux affectés (séparés par des virgules) :");
        String codesPostauxInput = scanner.nextLine().trim();
        List<String> codesPostaux = codesPostauxInput.isEmpty() ? new ArrayList<>() : new ArrayList<>(List.of(codesPostauxInput.split("\\s*,\\s*")));

        LocalDate dateDebut = retryDate("Date de début (YYYY-MM-DD) :", scanner);
        if (dateDebut == null) return;

        LocalDate dateFin = retryDate("Date de fin (YYYY-MM-DD) :", scanner);
        if (dateFin == null || !dateFin.isAfter(dateDebut)) {
            System.out.println("La date de fin doit être après la date de début. Veuillez réessayer plus tard.");
            return;
        }

        LocalTime heureDebut = retryTime("Heure de début (HH:mm) :", scanner);
        if (heureDebut == null) return;

        LocalTime heureFin = retryTime("Heure de fin (HH:mm) :", scanner);
        if (heureFin == null || !Valider.validerPlageHoraire(heureDebut, heureFin)) {
            System.out.println("Les heures doivent être valides. Veuillez réessayer plus tard.");
            return;
        }

        List<Preference.PreferenceEntry> preferencesConflits = trouverConflitsResidents(rues, codesPostaux);
        if (!preferencesConflits.isEmpty()) {
            System.out.println("\nConflits trouvés avec les préférences des résidents :");
            for (Preference.PreferenceEntry preference : preferencesConflits) {
                System.out.println("- Jour : " + preference.getJour() + ", Heure début : " + preference.getHeureDebut() + ", Heure fin : " + preference.getHeureFin());
            }
            System.out.println("Voulez-vous modifier les horaires du projet pour éviter les conflits ? (Oui/Non)");
            String reponse;
            while (true) {
                reponse = scanner.nextLine().trim();
                if (reponse.equalsIgnoreCase("Oui")) {
                    heureDebut = retryTime("Nouvelle heure de début (HH:mm) :", scanner);
                    heureFin = retryTime("Nouvelle heure de fin (HH:mm) :", scanner);
                    break;
                } else if (reponse.equalsIgnoreCase("Non")) {
                    System.out.println("Les horaires du projet resteront inchangés.");
                    break;
                } else {
                    System.out.println("Veuillez répondre par 'Oui' ou 'Non'.");
                }
            }
        }

        Projet projet = new Projet(titre, description, typeTravaux, quartiers, rues, codesPostaux, dateDebut, dateFin, heureDebut, heureFin);
        if (projet != null) {
            GestionProjets.sauvegarderProjet(projet);
            System.out.println("Projet soumis avec succès !");
            // Ajouter une notification pour ce projet
            NotificationController.ajouterNotificationProjetSoumis(titre);
        } else {
            System.out.println("Erreur lors de la création du projet. Veuillez vérifier les informations fournies.");
        }
    }

    private static LocalDate retryDate(String prompt, Scanner scanner) {
        int attempts = 0;
        while (attempts < 3) {
            System.out.println(prompt);
            try {
                return LocalDate.parse(scanner.nextLine().trim());
            } catch (Exception e) {
                attempts++;
                System.out.println("Format invalide. Veuillez réessayer.");
            }
        }
        System.out.println("Nombre maximum de tentatives atteint.");
        return null;
    }

    private static LocalTime retryTime(String prompt, Scanner scanner) {
        int attempts = 0;
        while (attempts < 3) {
            System.out.println(prompt);
            try {
                return LocalTime.parse(scanner.nextLine().trim());
            } catch (Exception e) {
                attempts++;
                System.out.println("Format invalide. Veuillez réessayer.");
            }
        }
        System.out.println("Nombre maximum de tentatives atteint.");
        return null;
    }

    private static List<Preference.PreferenceEntry> trouverConflitsResidents(List<String> rues, List<String> codesPostaux) {
        List<Preference.PreferenceEntry> preferencesConflits = new ArrayList<>();
        List<Resident> residents = GestionResidents.chargerResidentsDepuisFichier();

        rues = (rues == null) ? new ArrayList<>() : new ArrayList<>(rues);
        codesPostaux = (codesPostaux == null) ? new ArrayList<>() : new ArrayList<>(codesPostaux);

        for (Resident resident : residents) {
            boolean residentAffecte =
                    (rues.contains(resident.getAdresse())) ||
                            (codesPostaux.contains(resident.getCodePostal()));

            if (residentAffecte) {
                Preference preference = new Preference(resident.getAdresseCourriel());
                if (preference.getPreferences() != null) {
                    preferencesConflits.addAll(preference.getPreferences());
                }
            }
        }

        return preferencesConflits;
    }
}