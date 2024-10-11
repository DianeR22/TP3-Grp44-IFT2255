import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

// Valider est une classe qui permet de procéder à une vérification de l'information rentrée par un utilisateur
public class Valider {

    // Expressions regulières (regex) utilisées pour valider des chaînes de caractères (date, numéro de tel, courriel, mdp, identifiant)
    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{2}/\\d{2}/\\d{4}$");
    private static Pattern PHONE_PATTERN = Pattern.compile("^\\d{3}[-. ]?\\d{3}[-. ]?\\d{4}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private static Pattern PASSWORD = Pattern.compile("^(?=.*[0-9])"
            + "(?=.*[a-z])(?=.*[A-Z])"
            + "(?=.*[@#$%^&+=?~!*()_\\-\\{\\}|\\[\\]\\\\:\"/'])" 
            + "(?=\\S+$).{8,20}$");

    private static final Pattern IDENTIFIANT = Pattern.compile("\\d{8}");

    // Valider le numéro de téléphone entré, il peut y avoir des tiret ou des espaces entre les blocs de numéros
    public static boolean validerTel(String numTel) {
        return PHONE_PATTERN.matcher(numTel).matches();
    }

    // Valider la date en format JJ/MM/AAAA
    public static boolean validerDate(String dateNaissance) {
        return DATE_PATTERN.matcher(dateNaissance).matches();
    }
    // Méthode qui a pour paramètre la date de naissance du résident et qui renvoie True
    // si le résident a au moins 16 ans.
    public static boolean validerAge(String DateDeNaissance){

        // Format souhaité pour la date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Parser la date avec le formatter
        LocalDate anneeNaissance = LocalDate.parse(DateDeNaissance, formatter);

        // Date courante
        LocalDate currentDate = LocalDate.now();

        // Appel de la méthode calculerAge
        int age = calculerAge(anneeNaissance, currentDate);
        return age >= 16;

    }

    // Méthode qui a pour valeur de retour un int qui correspond à la période en années entre deux dates
    // placées en paramètres
    private static int calculerAge(LocalDate anneeNaissance, LocalDate currentDate) {
        Period period = Period.between(anneeNaissance, currentDate);
        return period.getYears();
    }

    // Valider le courriel en format local-part@domain
    public static boolean validerEmail(String adresseCourriel) {
        return EMAIL_PATTERN.matcher(adresseCourriel).matches();
    }
    // Valider le mot de passe: min 8 caractères et max 20, une majuscule, un caractère spécial et un chiffre
    public static boolean validerMDP(String motDePasse) {
        return PASSWORD.matcher(motDePasse).matches();
    }

    // Valider l'identifier qui doit avoir 8 chiffres
    public static boolean validerIdentifiantVille(String identifiant) {
        return IDENTIFIANT.matcher(identifiant).matches();
    }


}
