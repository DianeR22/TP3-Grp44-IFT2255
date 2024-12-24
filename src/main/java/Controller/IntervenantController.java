package Controller;

import Model.GestionCandidatures;
import Model.GestionIntervenants;
import Model.GestionProjets;
import Model.GestionResidents;
import Model.Intervenant;
import Model.Preference;
import Model.Projet;
import Model.Resident;
import Model.Valider;
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
 * <p>Cette classe gère les interactions avec les intervenants, y compris :
 * <ul>
 *   <li>La soumission et la suppression de candidature</li>
 *   <li>Le suivi des candidatures</li>
 *   <li>La soumission de projets</li>
 *   <li>La détection de conflits avec les préférences des résidents</li>
 * </ul>
 */
public class IntervenantController {

    /**
     * Affiche le menu principal des intervenants.
     *
     * <p>Cette méthode redirige vers la vue associée aux intervenants
     * pour leur permettre d'interagir avec les fonctionnalités de l'application.
     *
     * @see IntervenantView#afficherMenuIntervenant()
     */
    public static void afficherMenuIntervenant() {
        IntervenantView.afficherMenuIntervenant();
    }

    /**
     * Affiche le menu de gestion des requêtes pour les intervenants.
     *
     * <p>Cette méthode redirige vers la vue des requêtes, où les intervenants peuvent
     * consulter, répondre ou gérer les requêtes soumises.
     *
     * @see RequeteView#afficherMenuRequete()
     */
    public static void afficherMenuRequete() {
        RequeteView.afficherMenuRequete();
    }

    /**
     * Permet à un intervenant de soumettre sa candidature.
     *
     * <p>Cette méthode utilise la classe {@link GestionIntervenants} pour
     * gérer le processus de soumission de candidature.
     *
     * @see GestionIntervenants#soumettreCandidature()
     */
    public static void soumettreCandidature() {
        GestionIntervenants.soumettreCandidature();
    }

    /**
     * Permet à un intervenant de supprimer sa candidature existante.
     *
     * <p>Cette méthode utilise la classe {@link GestionIntervenants} pour
     * gérer le processus de suppression de candidature.
     *
     * @see GestionIntervenants#supprimerCandidature()
     */
    public static void supprimerCandidature() {
        GestionIntervenants.supprimerCandidature();
    }

    /**
     * Permet à un intervenant de suivre la candidature d'un résident.
     *
     * <p>Cette méthode utilise la classe {@link GestionCandidatures} pour
     * gérer le processus de suivi de la candidature.
     *
     * @param intervenant L'intervenant qui souhaite suivre une candidature.
     * @see GestionCandidatures#suiviCandidature(Intervenant)
     */
    public static void suivreCandidature(Intervenant intervenant) {
        if (intervenant != null) {
            GestionCandidatures.suiviCandidature(intervenant);
        } else {
            System.out.println("Intervenant est nul. Impossible de suivre la candidature.");
        }
    }

    /**
     * Permet à un intervenant de soumettre un projet, en tenant compte :
     * <ul>
     *   <li>de la validation du format de la date (YYYY-MM-DD)</li>
     *   <li>de la validation du format de l'heure (HH:mm)</li>
     *   <li>de la plage horaire [08:00, 17:00]</li>
     *   <li>et du fait que l'heure de fin doit être strictement supérieure à l'heure de début</li>
     * </ul>
     *
     * <p>Après avoir récupéré toutes les informations, la méthode détecte
     * d'éventuels conflits avec les préférences de résidents.
     * En cas de conflits, l'intervenant a le choix de modifier les horaires.
     */
    public static void soumettreProjet() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Titre du projet :");
        String titre = scanner.nextLine().trim();

        System.out.println("Description du projet :");
        String description = scanner.nextLine().trim();

        // (Exemple) Validation du type de travaux (3 tentatives).
        String typeTravaux = null;
        int attemptsType = 0;
        while (attemptsType < 3) {
            System.out.println("Type de travaux (choisissez parmi les catégories valides) :");
            for (Valider.TypeTravail type : Valider.TypeTravail.values()) {
                System.out.println("- " + type.getDescription());
            }
            String input = scanner.nextLine().trim();
            if (Valider.validerTypeTravail(input)) {
                typeTravaux = input;
                break;
            } else {
                attemptsType++;
                System.out.println("Type de travaux invalide. Veuillez réessayer.");
            }
        }
        if (typeTravaux == null) {
            System.out.println("3 tentatives échouées. Veuillez réessayer plus tard.");
            return;
        }

        // Demande des quartiers, rues et codes postaux
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

        List<String> codesPostaux = retryCodesPostaux(scanner);
        if (codesPostaux == null) {
            return;
        }

        // 1) Récupération de la date de début
        LocalDate dateDebut = retryDate("Date de début (YYYY-MM-DD) :", scanner);
        if (dateDebut == null) {
            // échec ou retour menu
            return;
        }

        // 2) Récupération de la date de fin (doit être postérieure à dateDebut)
        LocalDate dateFin = retryDateFinApresDateDebut(dateDebut, "Date de fin (YYYY-MM-DD) :", scanner);
        if (dateFin == null) {
            return;
        }

        // 3) Récupération de l’heure de début (08:00 - 17:00)
        LocalTime heureDebut = retryTimeDansPlage("Heure de début (HH:mm) :", scanner,
                LocalTime.of(8, 0),  // min = 08:00
                LocalTime.of(17, 0)  // max = 17:00
        );
        if (heureDebut == null) {
            return;
        }

        // 4) Récupération de l’heure de fin (08:00 - 17:00, et > heureDebut)
        LocalTime heureFin = retryTimeFinApresHeureDebut(heureDebut,
                "Heure de fin (HH:mm) :",
                scanner,
                LocalTime.of(8, 0),   // min = 08:00
                LocalTime.of(17, 0)   // max = 17:00
        );
        if (heureFin == null) {
            return;
        }

        // 5) Détection d’éventuels conflits + création du projet
        List<Preference.PreferenceEntry> preferencesConflits = trouverConflitsResidents(rues, quartiers, codesPostaux);
        if (!preferencesConflits.isEmpty()) {
            System.out.println("\nConflits trouvés avec les préférences des résidents :");
            for (Preference.PreferenceEntry pref : preferencesConflits) {
                System.out.println("- Jour : " + pref.getJour()
                        + ", Heure début : " + pref.getHeureDebut()
                        + ", Heure fin : " + pref.getHeureFin());
            }
            System.out.println("Voulez-vous modifier les horaires du projet pour éviter les conflits ? (Oui/Non)");
            while (true) {
                String reponse = scanner.nextLine().trim();
                if (reponse.equalsIgnoreCase("Oui")) {
                    // On réitère la saisie pour heureDebut / heureFin
                    heureDebut = retryTimeDansPlage("Nouvelle heure de début (HH:mm) :", scanner,
                            LocalTime.of(8, 0), LocalTime.of(17, 0));
                    if (heureDebut == null) return;

                    heureFin = retryTimeFinApresHeureDebut(heureDebut, "Nouvelle heure de fin (HH:mm) :",
                            scanner, LocalTime.of(8, 0), LocalTime.of(17, 0));
                    if (heureFin == null) return;

                    break;
                } else if (reponse.equalsIgnoreCase("Non")) {
                    System.out.println("Les horaires du projet resteront inchangés.");
                    break;
                } else {
                    System.out.println("Veuillez répondre par 'Oui' ou 'Non'.");
                }
            }
        }

        Projet projet = new Projet(titre, description, typeTravaux,
                quartiers, rues, codesPostaux,
                dateDebut, dateFin, heureDebut, heureFin);

        if (projet != null) {
            GestionProjets.sauvegarderProjet(projet);
            System.out.println("Projet soumis avec succès !");
            // NotificationController.ajouterNotificationProjetSoumis(titre);
        } else {
            System.out.println("Erreur lors de la création du projet. Veuillez vérifier les informations fournies.");
        }
    }

    // ---------------------------------------------------------------------
    // MÉTHODES DE "RETRY" POUR LES DATES
    // ---------------------------------------------------------------------

    /**
     * Tente de récupérer une date au format <b>YYYY-MM-DD</b> en 3 essais.
     *
     * <p>Si les 3 tentatives échouent (mauvais format), on affiche un message
     * et on propose à l’utilisateur de revenir à un menu.
     *
     * @param prompt  Le message à afficher pour inviter l'utilisateur à saisir une date.
     * @param scanner Le {@link Scanner} pour la saisie utilisateur.
     * @return Un objet {@link LocalDate} parsé avec succès, ou {@code null}
     *         si l'utilisateur a échoué 3 fois ou a choisi de retourner au menu.
     */
    private static LocalDate retryDate(String prompt, Scanner scanner) {
        int attempts = 0;
        while (attempts < 3) {
            System.out.println(prompt);
            try {
                String input = scanner.nextLine().trim();
                return LocalDate.parse(input);
            } catch (Exception e) {
                attempts++;
                System.out.println("Format invalide. Veuillez respecter le format YYYY-MM-DD.");
            }
        }
        System.out.println("3 tentatives échouées. Veuillez réessayer plus tard.");
        return null;
    }

    /**
     * Tente de récupérer une date de fin <b>(YYYY-MM-DD)</b> en 3 essais,
     * en s'assurant qu'elle soit strictement postérieure à {@code dateDebut}.
     *
     * <p>Si le format est invalide ou si la date de fin n'est pas > dateDebut,
     * on incrémente le compteur de tentatives et on réessaye jusqu'à 3 fois.
     *
     * @param dateDebut La date de début déjà validée.
     * @param prompt    Le message à afficher pour inviter l'utilisateur à saisir une date de fin.
     * @param scanner   Le {@link Scanner} pour la saisie utilisateur.
     * @return Un objet {@link LocalDate} si la saisie a réussi, ou {@code null}
     *         après 3 échecs ou retour menu.
     */
    private static LocalDate retryDateFinApresDateDebut(LocalDate dateDebut, String prompt, Scanner scanner) {
        int attempts = 0;
        while (attempts < 3) {
            System.out.println(prompt);
            try {
                String input = scanner.nextLine().trim();
                LocalDate candidate = LocalDate.parse(input);
                if (candidate.isAfter(dateDebut)) {
                    return candidate;
                } else {
                    attempts++;
                    System.out.println("La date de fin doit être après la date de début. Réessayez.");
                }
            } catch (Exception e) {
                attempts++;
                System.out.println("Format invalide. Veuillez respecter le format YYYY-MM-DD.");
            }
        }
        System.out.println("3 tentatives échouées. Veuillez réessayer plus tard.");
        return null;
    }

    // ---------------------------------------------------------------------
    // MÉTHODES DE "RETRY" POUR LES HEURES
    // ---------------------------------------------------------------------

    /**
     * Tente de récupérer une heure au format <b>HH:mm</b>, en 3 essais,
     * tout en vérifiant que l'heure se situe dans l'intervalle
     * [{@code minTime}, {@code maxTime}] inclus.
     *
     * <p>Si l'heure est hors plage ou en cas de format invalide, on incrémente
     * le compteur de tentatives. Après 3 échecs, on redirige l'utilisateur
     * vers un menu et renvoie {@code null}.
     *
     * @param prompt   Le message d'invite (ex. : "Heure de début (HH:mm) :")
     * @param scanner  Le {@link Scanner} pour la saisie utilisateur.
     * @param minTime  L'heure minimale autorisée.
     * @param maxTime  L'heure maximale autorisée.
     * @return Un objet {@link LocalTime} si valide, sinon {@code null} après 3 échecs.
     */
    private static LocalTime retryTimeDansPlage(String prompt,
                                                Scanner scanner,
                                                LocalTime minTime,
                                                LocalTime maxTime) {
        int attempts = 0;
        while (attempts < 3) {
            System.out.println(prompt);
            try {
                String input = scanner.nextLine().trim();
                LocalTime candidate = LocalTime.parse(input);

                // Vérification de la plage
                if (candidate.isBefore(minTime) || candidate.isAfter(maxTime)) {
                    attempts++;
                    System.out.println("L'heure doit être comprise entre "
                            + minTime + " et " + maxTime + ".");
                } else {
                    return candidate;
                }
            } catch (Exception e) {
                attempts++;
                System.out.println("Format invalide. Veuillez respecter le format HH:mm (ex: 08:30).");
            }
        }
        System.out.println("3 tentatives échouées. Veuillez réessayer plus tard.");
        return null;
    }

    /**
     * Tente de récupérer l'heure de fin en s'assurant qu'elle se situe
     * dans l'intervalle [{@code minTime}, {@code maxTime}] <b>et</b>
     * qu'elle soit strictement supérieure à {@code heureDebut}.
     *
     * <p>Après 3 échecs (format invalide, hors plage, ou pas > heureDebut),
     * l'utilisateur est redirigé vers un menu et la méthode renvoie {@code null}.
     *
     * @param heureDebut L'heure de début déjà validée.
     * @param prompt     Le message d'invite pour l'heure de fin.
     * @param scanner    Le {@link Scanner} pour la saisie utilisateur.
     * @param minTime    L'heure minimale autorisée (ex. 08:00).
     * @param maxTime    L'heure maximale autorisée (ex. 17:00).
     * @return Un objet {@link LocalTime} si valide, sinon {@code null} après 3 échecs.
     */
    private static LocalTime retryTimeFinApresHeureDebut(LocalTime heureDebut,
                                                         String prompt,
                                                         Scanner scanner,
                                                         LocalTime minTime,
                                                         LocalTime maxTime) {
        int attempts = 0;
        while (attempts < 3) {
            System.out.println(prompt);
            try {
                String input = scanner.nextLine().trim();
                LocalTime candidate = LocalTime.parse(input);

                // 1) Vérifier la plage 08:00 - 17:00
                if (candidate.isBefore(minTime) || candidate.isAfter(maxTime)) {
                    attempts++;
                    System.out.println("L'heure doit être comprise entre "
                            + minTime + " et " + maxTime + ".");
                    continue;
                }

                // 2) Vérifier heureFin > heureDebut
                if (!candidate.isAfter(heureDebut)) {
                    attempts++;
                    System.out.println("L'heure de fin doit être postérieure à l'heure de début. Veuillez réessayer.");
                    continue;
                }

                // Si tout va bien, on renvoie candidate
                return candidate;

            } catch (Exception e) {
                attempts++;
                System.out.println("Format invalide. Veuillez respecter le format HH:mm (ex: 08:30).");
            }
        }
        System.out.println("3 tentatives échouées. Veuillez réessayer plus tard.");
        return null;
    }

    /**
     * Permet de saisir et valider des codes postaux en 3 tentatives maximum.
     *
     * <p>L'utilisateur entre plusieurs codes postaux séparés par des virgules (ex: "H2Y 1N9,H3B 2S1").
     * Chaque code est alors vérifié via {@link Valider#validerCodePostal(String)}.
     *
     * @param scanner Le {@link Scanner} pour la saisie utilisateur.
     * @return Une {@code List<String>} contenant tous les codes validés,
     *         ou {@code null} après 3 échecs (puis redirection vers un menu).
     */
    private static List<String> retryCodesPostaux(Scanner scanner) {
        int attempts = 0;
        while (attempts < 3) {
            System.out.println("Codes postaux affectés (séparés par des virgules) :");
            String codesPostauxInput = scanner.nextLine().trim();

            // Si l'utilisateur laisse vide, on peut retourner une liste vide
            if (codesPostauxInput.isEmpty()) {
                return new ArrayList<>();
            }

            // On sépare par virgule
            String[] splitted = codesPostauxInput.split("\\s*,\\s*");
            boolean allValid = true;

            // Vérification un par un
            for (String code : splitted) {
                if (!Valider.validerCodePostal(code)) {
                    attempts++;
                    allValid = false;
                    System.out.println("Format invalide pour le code postal : " + code);
                    System.out.println("Veuillez respecter le format ex: H2Y 1N9.");
                    break; // On sort de la boucle pour retaper la liste
                }
            }

            // Si tout est valide, on renvoie la liste
            if (allValid) {
                return new ArrayList<>(List.of(splitted));
            }

            // Si on n'a pas tout validé, on redemande tant que attempts < 3
            if (attempts >= 3) {
                break;
            }
        }

        // 3 échecs ou plus
        System.out.println("3 tentatives échouées. Veuillez réessayer plus tard.");
        return null;
    }

    /**
     * Détecte les conflits avec les préférences des résidents pour un projet donné.
     *
     * <p>Cette méthode examine la liste des résidents (rue, quartier, code postal)
     * et compare avec ceux spécifiés pour le projet afin de trouver
     * ceux potentiellement affectés. Elle retourne la liste de préférences
     * (jours/heures) associées à ces résidents.
     *
     * @param ruesProjet         Liste des rues affectées par le projet.
     * @param quartiersProjet    Liste des quartiers affectés par le projet.
     * @param codesPostauxProjet Liste des codes postaux affectés par le projet.
     * @return Une liste de {@link Preference.PreferenceEntry} représentant
     *         les conflits potentiels avec les horaires/jours préférés de résidents.
     */
    public static List<Preference.PreferenceEntry> trouverConflitsResidents(
            List<String> ruesProjet,
            List<String> quartiersProjet,
            List<String> codesPostauxProjet
    ) {
        if (ruesProjet == null) ruesProjet = new ArrayList<>();
        if (quartiersProjet == null) quartiersProjet = new ArrayList<>();
        if (codesPostauxProjet == null) codesPostauxProjet = new ArrayList<>();

        List<Preference.PreferenceEntry> conflits = new ArrayList<>();
        List<Resident> residents = GestionResidents.chargerResidentsDepuisFichier();

        for (Resident resident : residents) {
            String rueResident = resident.getRue();
            String quartierResident = resident.getQuartier();
            String codePostalResident = resident.getCodePostal();

            // Vérifie si ce résident est concerné (rue, quartier ou code postal)
            boolean memeRue = (rueResident != null && ruesProjet.contains(rueResident));
            boolean memeQuartier = (quartierResident != null && quartiersProjet.contains(quartierResident));
            boolean memeCodePostal = (codePostalResident != null && codesPostauxProjet.contains(codePostalResident));

            if (memeRue || memeQuartier || memeCodePostal) {
                // Charger la préférence de ce résident
                Preference preference = new Preference(resident.getAdresseCourriel());
                if (preference.getPreferences() != null) {
                    conflits.addAll(preference.getPreferences());
                }
            }
        }
        return conflits;
    }
}