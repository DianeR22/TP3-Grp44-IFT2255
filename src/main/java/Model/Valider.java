package Model;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
/**
 * La classe Valider permet de procéder à une vérification de l'information rentrée
 * par un utilisateur.
 */
public class Valider {

    // Expressions regulières (regex) utilisées pour valider des chaînes de caractères (date, numéro de tel, courriel, mdp, identifiant)
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static Pattern PHONE_PATTERN = Pattern.compile("^\\d{3}[-. ]?\\d{3}[-. ]?\\d{4}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private static Pattern PASSWORD = Pattern.compile("^(?=.*[0-9])"
            + "(?=.*[a-z])(?=.*[A-Z])"
            + "(?=.*[@#$%^&+=?~!*()_\\-\\{\\}|\\[\\]\\\\:\"/'])"
            + "(?=\\S+$).{8,20}$");
    private static final Pattern CODE_PATTERN = Pattern.compile("[A-Za-z]\\d[A-Za-z] \\d[A-Za-z]\\d$");

    private static final String[] JOURS_DE_LA_SEMAINE = {
            "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"
    };

    private static final Pattern IDENTIFIANT = Pattern.compile("\\d{8}");


    /**
     * Valide le numéro de téléphone entré. Il peut y avoir des tirets ou des espaces entre les blocs de numéros.
     *
     * @param numTel Le numéro de téléphone à valider.
     * @return true si le numéro de téléphone est valide, false sinon.
     */
    public static boolean validerTel(String numTel) {
        return PHONE_PATTERN.matcher(numTel).matches();
    }



    /**
     * Valide si la date est correcte (au format JJ/MM/AAAA).
     *
     * @param date La date à valider.
     * @return true si la date est valide, false sinon.
     */
    public static boolean validerDate(String date) {
        try {
            // Parser la date avec le format voulu
            LocalDate.parse(date, DATE_FORMATTER);
            return true;  // Si aucun exception, la date est valide
        } catch (DateTimeParseException e) {
            // Format est incorrect
            return false;
        }
    }

    /**
     * Valide si le résident a au moins 16 ans, en fonction de la date de naissance.
     *
     * @param DateDeNaissance La date de naissance à valider.
     * @return {@code true} si le résident a 16 ans ou plus, {@code false} sinon.
     */
    public static boolean validerAge(String DateDeNaissance){

        // Parser la date avec le DATE_FORMATTER (au format JJ/MM/AAAA)
        LocalDate anneeNaissance = LocalDate.parse(DateDeNaissance, DATE_FORMATTER);

        // Date courante
        LocalDate currentDate = LocalDate.now();

        // Appel de la méthode calculerAge
        int age = calculerAge(anneeNaissance, currentDate);
        return age >= 16;

    }

    /**
     * Calcule l'âge en années entre 2 dates.
     *
     * @param anneeNaissance La date de naissance.
     * @param currentDate    La date actuelle.
     * @return L'âge en années.
     */
    private static int calculerAge(LocalDate anneeNaissance, LocalDate currentDate) {
        Period period = Period.between(anneeNaissance, currentDate);
        return period.getYears();
    }
    /**
     * Valide l'adresse email au format local-part@domain.
     *
     * @param adresseCourriel L'adresse email à valider.
     * @return {@code true} si l'email est valide, {@code false} sinon.
     */
    public static boolean validerEmail(String adresseCourriel) {
        return EMAIL_PATTERN.matcher(adresseCourriel).matches();
    }
    /**
     * Valide le mot de passe selon ces critères:
     * - entre 8 et 20 caractères
     * - contient une majuscule, un chiffre, et un caractère spécial.
     *
     * @param motDePasse Le mot de passe à valider.
     * @return {@code true} si le mot de passe est valide, {@code false} sinon.
     */
    public static boolean validerMDP(String motDePasse) {
        return PASSWORD.matcher(motDePasse).matches();
    }

    /**
     * Valide l'identifiant de la ville, qui doit être composé de 8 chiffres.
     *
     * @param identifiant L'identifiant à valider.
     * @return {@code true} si l'identifiant est valide, {@code false} sinon.
     */
    public static boolean validerIdentifiantVille(String identifiant) {
        return IDENTIFIANT.matcher(identifiant).matches();
    }

    public static boolean validerCodePostal(String code){
        return CODE_PATTERN.matcher(code).matches();

    }

    /**
     * Valide le type de travail entré par l'utilisateur en le comparant avec la liste des types de travail acceptés.
     *
     * @param typeTravailChoisi Le type de travail à valider.
     * @return {@code true} si le type de travail est valide, {@code false} sinon.
     */
    public static boolean validerTypeTravail(String typeTravailChoisi) {
        // On enlève les espaces inutiles de ce que l'utilisateur a entré comme type
        String typeTravailArrange = typeTravailChoisi.trim().replaceAll("\\s+", " ");

        // Normalisation pour enlever les accents de ce que l'utilisateur a entré comme type
        String typeTravailArrangeSansAccents = Normalizer.normalize(typeTravailArrange, Normalizer.Form.NFD);
        typeTravailArrangeSansAccents = typeTravailArrangeSansAccents.replaceAll("[^\\p{ASCII}]", "");


        // Comparaison pour voir si le type choisi correspond à un dans la liste
        for (TypeTravail travail : TypeTravail.values()) {

            // On enlève les underscores et on met des espaces du type de travail de la liste
            String travailNom = travail.name().replaceAll("_", " ").toLowerCase();

            // Normalisation de l'énumération pour enlever les accents du type de travail de la liste
            String travailNomSansAccents = Normalizer.normalize(travailNom, Normalizer.Form.NFD);
            travailNomSansAccents = travailNomSansAccents.replaceAll("[^\\p{ASCII}]", "");


            // On compare les deux chaînes sans prendre en compte la casse
            if (travailNomSansAccents.equalsIgnoreCase(typeTravailArrangeSansAccents)) {
                return true; // Les chaines sont équivalentes
            }
        }
        return false;
    }

    /**
     * L'énumération {@code TypeTravail} contient les types de travail possibles.
     */
    public enum TypeTravail {

        TRAVAUX_ROUTIERS("Travaux routiers"),
        TRAVAUX_DE_GAZ_OU_ELECTRICITE("Travaux de gaz ou électricité"),
        CONSTRUCTION_OU_RENOVATION("Construction ou rénovation"),
        ENTRETIEN_PAYSAGER("Entretien paysager"),
        TRAVAUX_LIES_AUX_TRANSPORTS_EN_COMMUN("Travaux liés aux transports en commun"),
        TRAVAUX_DE_SIGNALISATION_ET_ECLAIRAGE("Travaux de signalisation et éclairage"),
        TRAVAUX_SOUTERRAINS("Travaux souterrains"),
        TRAVAUX_RESIDENTIELS("Travaux résidentiels"),
        ENTRETIEN_URBAIN("Entretien urbain"),
        ENTRETIEN_DES_RESEAUX_DE_TELECOMMUNICATION("Entretien des réseaux de télécommunication");

        private final String description;

        TypeTravail(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    /**
     * Retourne une liste des descriptions des types de travaux valides.
     *
     * @return Une liste des descriptions des types de travaux valides.
     */
    public static List<String> getTypesTravauxValides() {
        List<String> typesTravaux = new ArrayList<>();
        for (TypeTravail type : TypeTravail.values()) {
            typesTravaux.add(type.getDescription());
        }
        return typesTravaux;
    }

    /**
     * Vérifie les conflits entre un projet et les préférences horaires des résidents.
     *
     * @param projet Le projet soumis.
     * @return Une liste des entrées de préférences qui entrent en conflit avec le projet.
     */
    public static List<Preference.PreferenceEntry> verifierConflits(Projet projet) {
        List<Preference.PreferenceEntry> conflits = new ArrayList<>();

        // Charger tous les résidents
        List<Resident> residents = GestionResidents.chargerResidentsDepuisFichier();

        // Filtrer les résidents par quartiers affectés
        for (Resident resident : residents) {
            if (projet.getQuartiersAffectes().contains(resident.getAdresse())) {
                // Charger les préférences du résident
                Preference preferences = new Preference(resident.getAdresseCourriel());

                for (Preference.PreferenceEntry preference : preferences.getPreferences()) {
                    // Vérifier si le jour du projet et la préférence sont en conflit
                    if (projet.getDateDebut().getDayOfWeek().toString().equalsIgnoreCase(preference.getJour())) {
                        // Vérifier l'overlap entre les heures du projet et les heures de préférence
                        if (isHoraireConflit(projet.getHeureDebut(), projet.getHeureFin(),
                                preference.getHeureDebut(), preference.getHeureFin())) {
                            conflits.add(preference);
                        }
                    }
                }
            }
        }

        return conflits;
    }

    /**
     * Vérifie si deux plages horaires se chevauchent.
     *
     * @param debutProjet Heure de début du projet.
     * @param finProjet Heure de fin du projet.
     * @param debutPref Heure de début de la préférence.
     * @param finPref Heure de fin de la préférence.
     * @return true si les plages horaires se chevauchent, false sinon.
     */
    private static boolean isHoraireConflit(LocalTime debutProjet, LocalTime finProjet,
                                            LocalTime debutPref, LocalTime finPref) {
        return (debutProjet.isBefore(finPref) && finProjet.isAfter(debutPref));
    }

    /**
     * Valide si le jour est un jour valide de la semaine.
     *
     * @param jour Le jour à valider.
     * @return true si le jour est valide, false sinon.
     */
    public static boolean validerJour(String jour) {
        return Arrays.stream(JOURS_DE_LA_SEMAINE).anyMatch(j -> j.equalsIgnoreCase(jour));
    }

    /**
     * Vérifie si une heure donnée se trouve dans une plage horaire spécifique.
     *
     * @param heure         L'heure à valider.
     * @param debutJournee  L'heure de début de la plage horaire.
     * @param finJournee    L'heure de fin de la plage horaire.
     * @return {@code true} si l'heure est comprise entre {@code debutJournee} et {@code finJournee} (inclus),
     *         sinon {@code false}.
     */
    public static boolean validerDansPlage(LocalTime heure, LocalTime debutJournee, LocalTime finJournee) {
        return !heure.isBefore(debutJournee) && !heure.isAfter(finJournee);
    }

    /**
     * Vérifie si une heure donnée se trouve dans la plage horaire standard de la journée.
     *
     * La plage horaire est définie entre 08:00 et 17:00.
     *
     * @param heure L'heure à valider.
     * @return {@code true} si l'heure est comprise entre 08:00 et 17:00 (inclus),
     *         sinon {@code false}.
     */
    public static boolean validerHeureDansPlage(LocalTime heure) {
        LocalTime debutJournee = LocalTime.of(8, 0);
        LocalTime finJournee = LocalTime.of(17, 0);

        return validerDansPlage(heure, debutJournee, finJournee);
    }

    /**
     * Vérifie si une plage horaire définie par une heure de début et une heure de fin
     * est valide et respecte les contraintes de la journée.
     *
     * Les contraintes sont les suivantes :
     * <ul>
     *   <li>Les heures doivent être comprises entre 08:00 et 17:00 (inclus).</li>
     *   <li>L'heure de fin doit être strictement postérieure à l'heure de début.</li>
     * </ul>
     *
     * @param heureDebut L'heure de début de la plage.
     * @param heureFin   L'heure de fin de la plage.
     * @return {@code true} si la plage horaire est valide, sinon {@code false}.
     */
    public static boolean validerPlageHoraire(LocalTime heureDebut, LocalTime heureFin) {
        LocalTime debutJournee = LocalTime.of(8, 0);
        LocalTime finJournee = LocalTime.of(17, 0);

        // Validate both start and end times individually and ensure start < end
        return validerDansPlage(heureDebut, debutJournee, finJournee) &&
                validerDansPlage(heureFin, debutJournee, finJournee) &&
                heureFin.isAfter(heureDebut);
    }
}



