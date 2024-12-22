package Model;
import java.util.List;
import java.util.Scanner;

/**
 * La classe Intervenant représente un utilisateur du système.
 * Elle hérite de la classe Utilisateur et possède des fonctionnalités liées à l'inscription,
 * la connexion, et la gestion d'informations des intervenants.
 */
public class Intervenant extends Utilisateur {
    // // Attributs spécifiques à l'intervenant
    private String type;
    private String identifiantVille;
    private Intervenant intervenant;

    // Variable statique pour l'intervenant connecté
    private static Intervenant intervenantConnecte;

    /**
     * Constructeur de la classe Intervenant avec des paramètres pour initialiser les attributs.
     *
     * @param nom Le nom de l'intervenant.
     * @param prenom Le prénom de l'intervenant.
     * @param adresseCourriel L'adresse e-mail de l'intervenant.
     * @param motDePasse Le mot de passe de l'intervenant.
     * @param type Le type de l'intervenant (Public, Privé, Individu).
     * @param identifiantVille L'identifiant de la ville de l'intervenant.
     */
    public Intervenant(String nom, String prenom, String adresseCourriel, String motDePasse, String type, String identifiantVille) {
        super(nom, prenom, motDePasse, adresseCourriel);
        this.type = type;
        this.identifiantVille = identifiantVille;
    }

    /**
     * Constructeur sans arguments pour créer un intervenant sans initialisation.
     */
    public Intervenant() {

    }


    /**
     * Vérifie l'égalité de l'intervenant avec un autre objet en utilisant l'adresse courriel.
     *
     * @param obj L'objet à comparer.
     * @return true si les deux objets sont égaux, false sinon.
     */
    @Override
    public boolean equals(Object obj) {
        // Vérifie si ces objets sont identiques
        if (this == obj) {
            return true;
        }
        // Vérifie si ces objets appartiennent à la même classe
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        // Vérifie si ces objets ont la même adresse courriel
        Intervenant intervenant = (Intervenant) obj;
        return this.adresseCourriel != null && this.adresseCourriel.equals(intervenant.adresseCourriel);
    }

    /**
     * Génère un code de hachage basé sur l'adresse courriel pour garantir une cohérence avec
     * la méthode equals.
     *
     * @return Le code de hachage de l'adresse courriel.
     */
    @Override
    public int hashCode() {
        return adresseCourriel != null ? adresseCourriel.hashCode() : 0;
    }

    // Getter et setter
    /**
     * Retourne le type de l'intervenant.
     *
     * @return Le type de l'intervenant (par exemple, "Public", "Privé", ou "Individu").
     */
    public String getType() {
        return type;
    }

    /**
     * Définit le type de l'intervenant.
     * @param type Le type à attribuer à l'intervenant.
     */
    public void setType(String type) {
        this.type = type;
    }


    /**
     * Retourne l'identifiant de la ville de l'intervenant.
     *
     * @return L'identifiant de la ville (code à 8 chiffres).
     */
    public String getIdentifiantVille() {
        return identifiantVille;
    }

    /**
     * Définit l'identifiant de la ville de l'intervenant.
     *
     * @param identifiantVille L'identifiant de la ville (code à 8 chiffres).
     */
    public void setIdentifiantVille(String identifiantVille) {
        this.identifiantVille = identifiantVille;
    }

    /**
     * Retourne l'intervenant actuel.
     *
     * @return L'objet Intervenant correspondant à l'intervenant actuel.
     */
    public Intervenant getIntervenant() {
        return intervenant;
    }

    /**
     * Définit l'intervenant actuel.
     *
     * @param intervenant L'intervenant à définir comme l'intervenant
     */
    public void setIntervenant(Intervenant intervenant) {
        this.intervenant = intervenant;
    }

    /**
     * Retourne l'intervenant connecté.
     *
     * @return L'intervenant actuellement connecté
     */
    public static Intervenant getIntervenantConnecte() {
        return intervenantConnecte;
    }

    /**
     * Définit l'intervenant connecté.
     *
     * @param intervenant L'intervenant à définir comme celui qui est connecté.
     */
    public static void setIntervenantConnecte(Intervenant intervenant) {
        Intervenant.intervenantConnecte = intervenant;
    }

    /**
     * Cette méthode permet de connecter un intervenant en vérifiant son adresse e-mail
     * par rapport aux intervenants inscrits dans le système. Elle permet de trouver
     * l'intervenant connecté et d'avoir une variable intervenantConnecte
     * utile pour associer une candidature à l'intervenant par exemple.
     *
     * @param utilisateur L'utilisateur qui se connecte.
     * @param email L'adresse e-mail de l'intervenant.
     * @return L'intervenant trouvé si l'adresse e-mail est valide, sinon null.
     */
    public static Intervenant connecterIntervenant(Utilisateur utilisateur, String email) {
        // Prendre la liste des intervenants depuis le fichier JSON
        List<Intervenant> intervenants = GestionIntervenants.chargerIntervenantsDepuisFichier();

        // Chercher l'intervenant avec l'email donné
        for (Intervenant intervenant : intervenants) {
            if (intervenant.getAdresseCourriel().equals(email)) {
                // Intervenant trouvé est l'intervenant présentement connecté
                intervenantConnecte = intervenant;
                return intervenant;
            }
        }
        return null;

    }

    public static void deconnecterResident() {
        intervenantConnecte = null; // Déconnecte le résident
    }

    /**
     * La méthode d'inscription permet à un intervenant de s'inscrire en collectant
     * ses informations et en les enregistrant dans le système.
     *
     * @param utilisateur L'utilisateur qui s'inscrit.
     */
    @Override
    public void inscription(Utilisateur utilisateur) {
        // Initialisation d'un scanner afin d'obtenir l'input de l'intervenant
        Scanner scanner = new Scanner(System.in);

        // Nom et prénom de l'intervenant
        nom = obtenirNom("Nom");
        prenom = obtenirNom("Prénom");

        // L'adresse courriel est entrée par le résident et est validée
        adresseCourriel = obtenirEmail();

        // Le mot de passe est entrée par l'utilisateur. Il doit contenir un minimum
        // de 8 caractères, une majuscule, un chiffre et un caractère spécial
        motDePasse = obtenirMotDePasse();

        // Le type de l'intervenant est entré et validé
        type = obtenirType();

        // L'identifiant de la ville de l'intervenant est entré puis validé
        identifiantVille = obtenirIdentifiantVille();

        // Ajouter le résident à la liste et sauvegarder
        GestionIntervenants.ajouterIntervenant(this);

        System.out.println("Vous êtes inscrit!");
        afficherInformations();

        // Demander à l'intervenant s'il souhaite se connecter
        demanderConnexion(scanner, utilisateur);
    }

    /**
     * Permet de collecter un nom ou un prénom.
     *
     * @param type Le type (nom ou prénom) à collecter.
     * @return Le nom ou prénom entré par l'utilisateur.
     */
    private String obtenirNom(String type) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Veuillez entrer votre " + type + ".");
        return scanner.nextLine();
    }

    /**
     * Permet de collecter et valider l'adresse e-mail de l'intervenant.
     *
     * @return L'adresse e-mail valide.
     */
    private String obtenirEmail() {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Veuillez entrer votre adresse courriel.");
            String adresseCourriel = scanner.nextLine();
            if (Valider.validerEmail(adresseCourriel)) {
                return adresseCourriel;
            } else {
                System.out.println("Adresse email invalide.");
            }
        }
    }

    /**
     * Permet de collecter et valider le mot de passe de l'intervenant
     * et de procéder à la validation de celui-ci.
     *
     * @return Le mot de passe valide.
     */
    private String obtenirMotDePasse() {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Veuillez entrer votre mot de passe.");
            String motDePasse = scanner.next();
            if (Valider.validerMDP(motDePasse)) {
                return motDePasse;
            } else {
                System.out.println("Mot de passe invalide.");
            }
        }
    }

    /**
     * Permet de collecter et valider le type d'intervenant.
     *
     * @return Le type d'intervenant valide.
     */
    private String obtenirType() {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Veuillez entrer votre type (Public, Privé, Individu).");
            String type = scanner.next();
            if (type.equalsIgnoreCase("Public") || type.equalsIgnoreCase("Privé") || type.equalsIgnoreCase("Individu")) {
                return type;
            } else {
                System.out.println("Type invalide.");
            }
        }
    }

    /**
     * Permet de collecter et valider l'identifiant de la ville de l'intervenant.
     *
     * @return L'identifiant de la ville valide.
     */
    private String obtenirIdentifiantVille() {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Veuillez entrer l'identifiant de la ville (code à 8 chiffres).");
            String identifiantVille = scanner.next();
            if (Valider.validerIdentifiantVille(identifiantVille)) {
                return identifiantVille;
            } else {
                System.out.println("Identifiant de la ville invalide.");
            }
        }
    }

    /**
     * Affiche les informations de l'intervenant après l'inscription.
     */
    private void afficherInformations() {
        System.out.println("\nInformations recueillies :");
        System.out.println("Nom : " + nom);
        System.out.println("Prénom : " + prenom);
        System.out.println("Adresse e-mail : " + adresseCourriel);
        System.out.println("Type : " + type);
        System.out.println("Identifiant de la ville : " + identifiantVille);
    }

    /**
     * Demande à l'intervenant s'il souhaite se connecter après inscription.
     *
     * @param scanner Le scanner utilisé pour lire la réponse.
     * @param utilisateur L'utilisateur qui s'inscrit.
     */
    private void demanderConnexion(Scanner scanner, Utilisateur utilisateur) {
        System.out.println("Merci pour vos informations! Souhaitez-vous vous connecter? (Oui/Non)");
        String reponse = scanner.next();
        if (reponse.equalsIgnoreCase("Oui")) {

            super.connexion(utilisateur);
        } else {
            System.out.println("Au revoir.");
            System.exit(0);
        }
    }

}
