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

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{3}[-. ]?\\d{3}[-. ]?\\d{4}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern PASSWORD = Pattern.compile(
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])" +
                    "(?=.*[@#$%^&+=?~!*()_\\-\\{\\}|\\[\\]\\\\:\"/'])" +
                    "(?=\\S+$).{8,20}$"
    );
    private static final Pattern CODE_PATTERN = Pattern.compile("[A-Za-z]\\d[A-Za-z] \\d[A-Za-z]\\d$");
    private static final Pattern IDENTIFIANT = Pattern.compile("\\d{8}");

    // On considère un format "1234 Sainte-Catherine, Ville-Marie"
    // (chiffres + espace + nom de rue,virgule,quartier).
    private static final Pattern ADRESSE_RESIDENT_PATTERN = Pattern.compile("^\\d+\\s+[^,]+,\\s*[^,]+$");

    private static final String[] JOURS_DE_LA_SEMAINE = {
            "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"
    };

    /**
     * Valide le numéro de téléphone entré. Il peut y avoir des tirets ou des espaces.
     */
    public static boolean validerTel(String numTel) {
        return PHONE_PATTERN.matcher(numTel).matches();
    }

    /**
     * Valide si la date est correcte (format JJ/MM/AAAA).
     */
    public static boolean validerDate(String date) {
        try {
            LocalDate.parse(date, DATE_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Valide si la personne a au moins 16 ans, en fonction de la date de naissance (JJ/MM/AAAA).
     */
    public static boolean validerAge(String DateDeNaissance){
        LocalDate anneeNaissance = LocalDate.parse(DateDeNaissance, DATE_FORMATTER);
        LocalDate currentDate = LocalDate.now();
        int age = Period.between(anneeNaissance, currentDate).getYears();
        return age >= 16;
    }

    /**
     * Valide l'adresse email au format local-part@domain.
     */
    public static boolean validerEmail(String adresseCourriel) {
        return EMAIL_PATTERN.matcher(adresseCourriel).matches();
    }

    /**
     * Valide le mot de passe (8-20 caractères, majuscule, chiffre, caractère spécial).
     */
    public static boolean validerMDP(String motDePasse) {
        return PASSWORD.matcher(motDePasse).matches();
    }

    /**
     * Valide l'identifiant de la ville (8 chiffres).
     */
    public static boolean validerIdentifiantVille(String identifiant) {
        return IDENTIFIANT.matcher(identifiant).matches();
    }

    /**
     * Valide le code postal canadien (ex. H2Y 1N9).
     */
    public static boolean validerCodePostal(String code){
        return CODE_PATTERN.matcher(code).matches();
    }

    /**
     * Valide l'adresse du résident. Exemple attendu : "1234 Sainte-Catherine, Ville-Marie"
     * (chiffres + rue + quartier).
     */
    public static boolean validerAdresseResident(String adresse) {
        return ADRESSE_RESIDENT_PATTERN.matcher(adresse).matches();
    }

    /**
     * Valide si le type de travail entré par l'utilisateur est dans l'énumération TypeTravail.
     */
    public static boolean validerTypeTravail(String typeTravailChoisi) {
        String typeTravailArrange = typeTravailChoisi.trim().replaceAll("\\s+", " ");
        String typeTravailArrangeSansAccents = Normalizer.normalize(typeTravailArrange, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");

        for (TypeTravail travail : TypeTravail.values()) {
            String travailNom = travail.name().replaceAll("_", " ").toLowerCase();
            String travailNomSansAccents = Normalizer
                    .normalize(travailNom, Normalizer.Form.NFD)
                    .replaceAll("[^\\p{ASCII}]", "");

            if (travailNomSansAccents.equalsIgnoreCase(typeTravailArrangeSansAccents)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Énumération TypeTravail (exemples).
     */
    public enum TypeTravail {
        TRAVAUX_ROUTIERS("Travaux routiers"),
        TRAVAUX_DE_GAZ_OU_ELECTRICITE("Travaux de gaz ou électricité"),
        CONSTRUCTION_OU_RENOVATION("Construction ou rénovation"),
        ENTRETIEN_PAYSAGER("Entretien paysager"),
        TRAVAUX_LIES_AUX_TRANSPORTS_EN_COMMUNS("Travaux liés aux transports en commun"),
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
     * Retourne la liste des descriptions possibles pour guider l'utilisateur.
     */
    public static List<String> getTypesTravauxValides() {
        List<String> typesTravaux = new ArrayList<>();
        for (TypeTravail type : TypeTravail.values()) {
            typesTravaux.add(type.getDescription());
        }
        return typesTravaux;
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