package Controller;

import Model.*;
import View.IntervenantView;
import View.RequeteView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Contrôleur pour la gestion des fonctionnalités liées aux intervenants.
 *
 * Cette classe gère les interactions avec les intervenants, y compris la soumission
 * de projets, le suivi et la gestion des candidatures, ainsi que la détection
 * de conflits avec les préférences des résidents.
 */

public class IntervenantController {

    /**
     * Affiche le menu principal des intervenants.
     *
     * Cette méthode redirige vers la vue associée aux intervenants
     * pour leur permettre d'interagir avec les fonctionnalités de l'application.
     */
    public static void afficherMenuIntervenant() {
        IntervenantView.afficherMenuIntervenant();
    }

    /**
     * Affiche le menu de gestion des requêtes pour les intervenants.
     *
     * Cette méthode redirige vers la vue des requêtes, où les intervenants peuvent
     * consulter, répondre ou gérer les requêtes soumises.
     */
    public static void afficherMenuRequete() {
        RequeteView.afficherMenuRequete();
    }

    /**
     * Permet à un intervenant de soumettre sa candidature.
     *
     * Cette méthode utilise la classe {@link GestionIntervenants} pour
     * gérer le processus de soumission de candidature.
     */
    public static void soumettreCandidature() {
        GestionIntervenants.soumettreCandidature();
    }

    /**
     * Permet à un intervenant de supprimer sa candidature existante.
     *
     * Cette méthode utilise la classe {@link GestionIntervenants} pour
     * gérer le processus de suppression de candidature.
     */
    public static void supprimerCandidature() {
        GestionIntervenants.supprimerCandidature();
    }


    /**
     * Permet à un intervenant de suivre la candidature d'un résident.
     *
     * Cette méthode utilise la classe {@link GestionCandidatures} pour
     * gérer le processus de suivi de la candidature. **/
    public static void suivreCandidature(Intervenant intervenant) {
        if (intervenant != null) {
            GestionCandidatures.suiviCandidature(intervenant);
        } else {
            System.out.println("Intervenant est nul. Impossible de suivre la candidature.");
        }
    }

    /**
     * Permet à un intervenant de soumettre son projet.
     *
     * Cette méthode solicite les informations nécessaires pour soumettre un
     * projet (titre, description, type de travaux, et quartiers affectés).**/
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

        } else {
            System.out.println("Erreur lors de la création du projet. Veuillez vérifier les informations fournies.");
        }
    }

    /**
     * Demande à l'utilisateur de fournir une date valide avec un nombre limité de tentatives.
     *
     * Cette méthode affiche une invite pour demander une date au format `YYYY-MM-DD`,
     * et tente de parser la saisie de l'utilisateur. Après trois tentatives infructueuses,
     * elle retourne {@code null}.
     *
     * @param prompt  Le message d'invite à afficher à l'utilisateur.
     * @param scanner L'objet {@link Scanner} utilisé pour lire les entrées utilisateur.
     * @return La date saisie par l'utilisateur sous forme d'objet {@link LocalDate}, ou {@code null}
     *         si le format est invalide après trois tentatives.
     */
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

    /**
     * Demande à l'utilisateur de fournir une heure valide avec un nombre limité de tentatives.
     *
     * Cette méthode affiche une invite pour demander une heure au format `HH:mm`,
     * et tente de parser la saisie de l'utilisateur. Après trois tentatives infructueuses,
     * elle retourne {@code null}.
     *
     * @param prompt  Le message d'invite à afficher à l'utilisateur.
     * @param scanner L'objet {@link Scanner} utilisé pour lire les entrées utilisateur.
     * @return L'heure saisie par l'utilisateur sous forme d'objet {@link LocalTime}, ou {@code null}
     *         si le format est invalide après trois tentatives.
     */
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

    /**
     * Recherche les préférences des résidents pour les rues et les codes postaux fournis.
     *
     * @param rues          Les rues affectées par les préférences.
     * @param codesPostaux Les codes postaux affectés par les préférences.
     * @return Une liste des préférences des résidents qui conflitent avec les préférences des rues
     * et les résidents.**/
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