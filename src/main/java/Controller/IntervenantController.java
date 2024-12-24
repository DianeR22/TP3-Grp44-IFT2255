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
     */
    public static void soumettreCandidature() {
        GestionIntervenants.soumettreCandidature();
    }

    /**
     * Permet à un intervenant de supprimer sa candidature existante.
     */
    public static void supprimerCandidature() {
        GestionIntervenants.supprimerCandidature();
    }

    /**
     * Permet à un intervenant de suivre la candidature d'un résident.
     */
    public static void suivreCandidature(Intervenant intervenant) {
        if (intervenant != null) {
            GestionCandidatures.suiviCandidature(intervenant);
        } else {
            System.out.println("Intervenant est nul. Impossible de suivre la candidature.");
        }
    }

    /**
     * Permet à un intervenant de soumettre un projet.
     *
     * Cette méthode sollicite les informations nécessaires (titre, description, etc.),
     * en donnant 3 tentatives maximum pour chaque saisie invalide (type, dates, heures).
     * Si les 3 tentatives sont épuisées, on propose de retourner au menu des intervenants ou au menu principal.
     */
    public static void soumettreProjet() {
        Scanner scanner = new Scanner(System.in);

        // 1) Titre
        System.out.println("Titre du projet :");
        String titre = scanner.nextLine().trim();

        // 2) Description
        System.out.println("Description du projet :");
        String description = scanner.nextLine().trim();

        // 3) Type de travaux (3 tentatives max)
        String typeTravaux = demanderTypeTravaux(scanner);
        if (typeTravaux == null) {
            // L'utilisateur a épuisé ses 3 tentatives
            retourMenus(scanner);
            return;
        }

        // 4) Quartiers, Rues, Codes Postaux
        System.out.println("Quartiers affectés (séparés par des virgules) :");
        String quartiersInput = scanner.nextLine().trim();
        List<String> quartiers = quartiersInput.isEmpty()
                ? new ArrayList<>()
                : new ArrayList<>(List.of(quartiersInput.split("\\s*,\\s*")));

        System.out.println("Rues affectées (séparées par des virgules) :");
        String ruesInput = scanner.nextLine().trim();
        List<String> rues = ruesInput.isEmpty()
                ? new ArrayList<>()
                : new ArrayList<>(List.of(ruesInput.split("\\s*,\\s*")));

        System.out.println("Codes postaux affectés (séparés par des virgules) :");
        String codesPostauxInput = scanner.nextLine().trim();
        List<String> codesPostaux = codesPostauxInput.isEmpty()
                ? new ArrayList<>()
                : new ArrayList<>(List.of(codesPostauxInput.split("\\s*,\\s*")));

        // 5) Date de début (3 tentatives max)
        LocalDate dateDebut = demanderDate("Date de début (YYYY-MM-DD) :", scanner);
        if (dateDebut == null) {
            // 3 tentatives échouées
            retourMenus(scanner);
            return;
        }

        // 6) Date de fin (3 tentatives max)
        LocalDate dateFin = demanderDate("Date de fin (YYYY-MM-DD) :", scanner);
        if (dateFin == null) {
            // 3 tentatives échouées
            retourMenus(scanner);
            return;
        }
        // Vérifier que la date de fin est après la date de début
        if (!dateFin.isAfter(dateDebut)) {
            System.out.println("La date de fin doit être après la date de début. Veuillez réessayer.");
            retourMenus(scanner);
            return;
        }

        // 7) Heure de début (3 tentatives max)
        LocalTime heureDebut = demanderHeure("Heure de début (HH:mm) :", scanner);
        if (heureDebut == null) {
            retourMenus(scanner);
            return;
        }

        // 8) Heure de fin (3 tentatives max)
        LocalTime heureFin = demanderHeure("Heure de fin (HH:mm) :", scanner);
        if (heureFin == null) {
            retourMenus(scanner);
            return;
        }
        // Vérifier la cohérence de la plage horaire
        if (!Valider.validerPlageHoraire(heureDebut, heureFin)) {
            System.out.println("Les heures doivent être valides (heure fin > heure début).");
            retourMenus(scanner);
            return;
        }

        // 9) Vérifier les conflits de préférences
        List<Preference.PreferenceEntry> preferencesConflits = trouverConflitsResidents(rues, quartiers,codesPostaux);
        if (!preferencesConflits.isEmpty()) {
            System.out.println("\nConflits trouvés avec les préférences des résidents :");
            for (Preference.PreferenceEntry preference : preferencesConflits) {
                System.out.println("- Jour : " + preference.getJour()
                        + ", Heure début : " + preference.getHeureDebut()
                        + ", Heure fin : " + preference.getHeureFin());
            }
            System.out.println("Voulez-vous modifier les horaires du projet pour éviter les conflits ? (Oui/Non)");
            while (true) {
                String reponse = scanner.nextLine().trim();
                if (reponse.equalsIgnoreCase("Oui")) {
                    // Redemander éventuellement heureDebut, heureFin
                    heureDebut = demanderHeure("Nouvelle heure de début (HH:mm) :", scanner);
                    if (heureDebut == null) {
                        retourMenus(scanner);
                        return;
                    }
                    heureFin = demanderHeure("Nouvelle heure de fin (HH:mm) :", scanner);
                    if (heureFin == null) {
                        retourMenus(scanner);
                        return;
                    }
                    break;

                } else if (reponse.equalsIgnoreCase("Non")) {
                    System.out.println("Les horaires du projet resteront inchangés.");
                    break;

                } else {
                    System.out.println("Veuillez répondre par 'Oui' ou 'Non'.");
                }
            }
        }

        // 10) Création et sauvegarde du projet
        Projet projet = new Projet(
                titre, description, typeTravaux,
                quartiers, rues, codesPostaux,
                dateDebut, dateFin, heureDebut, heureFin);

        if (projet != null) {
            GestionProjets.sauvegarderProjet(projet);
            System.out.println("Projet soumis avec succès !");
            // NotificationController.ajouterNotificationProjetSoumis(titre); // selon vos besoins
        } else {
            System.out.println("Erreur lors de la création du projet. Veuillez vérifier les informations fournies.");
        }
    }

    /* ================================================================================
       MÉTHODES PRIVÉES POUR SAISIE AVEC 3 TENTATIVES
       ================================================================================ */

    /**
     * Demande un "type de travaux" en se basant sur les types disponibles (Valider.TypeTravail),
     * avec 3 tentatives max. Retourne null si on échoue.
     */
    private static String demanderTypeTravaux(Scanner scanner) {
        int attemptsType = 0;
        while (attemptsType < 3) {
            System.out.println("Type de travaux (choisissez parmi les catégories valides) :");
            for (Valider.TypeTravail type : Valider.TypeTravail.values()) {
                System.out.println("- " + type.getDescription());
            }
            String saisie = scanner.nextLine().trim();
            if (Valider.validerTypeTravail(saisie)) {
                return saisie;
            } else {
                attemptsType++;
                System.out.println("Type de travaux invalide. Veuillez réessayer.");
            }
        }
        // Échec après 3 tentatives
        System.out.println("Nombre maximum de tentatives atteint pour le type de travaux.");
        return null;
    }

    /**
     * Demande une date au format YYYY-MM-DD, avec 3 tentatives max.
     * Retourne null si on échoue.
     */
    private static LocalDate demanderDate(String prompt, Scanner scanner) {
        int attempts = 0;
        while (attempts < 3) {
            System.out.println(prompt);
            String saisie = scanner.nextLine().trim();
            try {
                return LocalDate.parse(saisie);
            } catch (Exception e) {
                attempts++;
                System.out.println("Format invalide. Veuillez réessayer (format attendu YYYY-MM-DD).");
            }
        }
        System.out.println("Nombre maximum de tentatives atteint pour la saisie de la date.");
        return null;
    }

    /**
     * Demande une heure au format HH:mm, avec 3 tentatives max.
     * Retourne null si on échoue.
     */
    private static LocalTime demanderHeure(String prompt, Scanner scanner) {
        int attempts = 0;
        while (attempts < 3) {
            System.out.println(prompt);
            String saisie = scanner.nextLine().trim();
            try {
                return LocalTime.parse(saisie);
            } catch (Exception e) {
                attempts++;
                System.out.println("Format invalide. Veuillez réessayer (format attendu HH:mm).");
            }
        }
        System.out.println("Nombre maximum de tentatives atteint pour la saisie de l'heure.");
        return null;
    }

    /**
     * Méthode utilitaire : propose à l'utilisateur de revenir au menu intervenants (1)
     * ou au menu principal (2). Déclenche l'action correspondante.
     */
    private static void retourMenus(Scanner scanner) {
        System.out.println("\nVoulez-vous revenir au menu des intervenants (1) "
                + "ou retourner au menu principal (2)?");
        System.out.println("Veuillez répondre par 1 ou par 2.");

        String reponse = scanner.nextLine().trim();
        if (reponse.equals("1")) {
            // On retourne au menu Intervenant
            afficherMenuIntervenant();
        } else {
            // Sinon on retourne au menu principal (adaptez selon votre architecture)
            // Par exemple :
            // MainController.afficherMenuPrincipal();
            System.out.println("Retour au menu principal !");
        }
    }

    /* ================================================================================
       DÉTECTION DES CONFLITS
       ================================================================================ */

    /**
     * Recherche les préférences des résidents pour les rues et les codes postaux fournis.
     *
     * @param rues         Les rues affectées par le projet.
     * @param codesPostaux Les codes postaux affectés par le projet.
     * @return Une liste des préférences des résidents qui conflitent
     *         avec les horaires (jours / heures) de ces résidents.
     */
    /**
     * Exemple de méthode (simplifiée) pour détecter les conflits.
     */
    public static List<Preference.PreferenceEntry> trouverConflitsResidents(
            List<String> ruesProjet,
            List<String> quartiersProjet,
            List<String> codesPostauxProjet
    ) {
        // Sécuriser
        if (ruesProjet == null) ruesProjet = new ArrayList<>();
        if (quartiersProjet == null) quartiersProjet = new ArrayList<>();
        if (codesPostauxProjet == null) codesPostauxProjet = new ArrayList<>();

        List<Preference.PreferenceEntry> conflits = new ArrayList<>();

        // Charger tous les résidents
        List<Resident> residents = GestionResidents.chargerResidentsDepuisFichier();

        for (Resident resident : residents) {
            // Récupérer la rue, le quartier, le code postal
            String rueResident = resident.getRue();           // ex. "1234 Sainte-Catherine"
            String quartierResident = resident.getQuartier(); // ex. "Ville-Marie"
            String codePostalResident = resident.getCodePostal(); // ex. "H2X 1Y4"

            // Vérifier si ce résident est affecté (rue OU quartier OU code postal)
            boolean memeRue = (rueResident != null && ruesProjet.contains(rueResident));
            boolean memeQuartier = (quartierResident != null && quartiersProjet.contains(quartierResident));
            boolean memeCodePostal = (codePostalResident != null && codesPostauxProjet.contains(codePostalResident));

            if (memeRue || memeQuartier || memeCodePostal) {
                // S'il y a match, on récupère les préférences
                Preference preference = new Preference(resident.getAdresseCourriel());
                if (preference.getPreferences() != null) {
                    conflits.addAll(preference.getPreferences());
                }
            }
        }
        return conflits;
    }
}