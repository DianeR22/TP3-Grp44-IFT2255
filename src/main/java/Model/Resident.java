package Model;

import java.util.List;
import java.util.Scanner;

/**
 * La classe Resident représente un type d'utilisateur avec des informations
 * comme la date de naissance, l'adresse, le mot de passe et permet la
 * gestion de l'inscription, de la connexion et de toutes les informations
 * associées à un résident dans le système.
 *
 * Cette classe hérite de la classe Utilisateur.
 */
public class Resident extends Utilisateur {
    // Attributs spécifiques au résident
    private String dateNaissance;
    private String telephone;
    private String adresse; // Nom de la rue
    private String codePostal;

    private Resident resident;
    private static Resident residentConnecte;

    /**
     * Constructeur de la classe Resident permettant d'instancier un résident avec ses attributs
     * et ceux hérités par la classe Utilisateur.
     *
     * @param nom Le nom du résident.
     * @param prenom Le prénom du résident.
     * @param adresseCourriel L'adresse courriel du résident.
     * @param motDePasse Le mot de passe du résident.
     * @param dateNaissance La date de naissance du résident.
     * @param telephone Le numéro de téléphone du résident.
     * @param adresse L'adresse du résident.
     */
    public Resident(String nom, String prenom, String adresseCourriel, String motDePasse, String dateNaissance,
                    String telephone, String adresse, String codePostal) {
        super(nom, prenom, motDePasse, adresseCourriel);
        this.dateNaissance = dateNaissance;
        this.telephone = telephone;
        this.adresse = adresse;
        this.codePostal = codePostal;
    }

    /**
     * Vérifie si deux objets Resident sont égaux en comparant les adresse courriel.
     *
     * @param obj L'objet à comparer.
     * @return true si les objets sont égaux, false sinon.
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
        Resident resident = (Resident) obj;
        return this.adresseCourriel != null && this.adresseCourriel.equals(resident.adresseCourriel);
    }

    /**
     * Retourne le code de hachage de l'adresse courriel pour garantir une
     * cohérence avec la méthode equals
     *
     * @return Le code de hachage de l'adresse courriel.
     */
    @Override
    public int hashCode() {
        // Retourne le code de hachage de l'adresse, 0 si null
        return adresseCourriel != null ? adresseCourriel.hashCode() : 0;
    }

    /**
     * Constructeur sans argument, nécessaire pour certaines initialisations.
     */
    public Resident() {
        super();
    }

    // Getter et setter
    /**
     * Retourne la date de naissance du résident.
     *
     * @return La date de naissance du résident.
     */
    public String getDateNaissance() {
        return dateNaissance;
    }

    /**
     * Définit la date de naissance du résident.
     *
     * @param dateNaissance La date de naissance à attribuer au résident.
     */
    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    /**
     * Retourne le telephone du résident.
     *
     * @return Le telephone du résident.
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Définit le telephone du résident.
     *
     * @param telephone Le telephone à attribuer au résident.
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * Retourne l'adresse du résident.
     *
     * @return L'adresse du résident.
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * Définit l'adresse du résident.
     *
     * @param adresse L'adresse à attribuer au résident.
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }


    /**
     * Retourne le résident.
     *
     * @return Le résident.
     */
    public Resident getResident() {
        return resident;
    }

    /**
     * Définit le résident.
     *
     * @param resident Le résident à attribuer au résident.
     */
    public void setResident(Resident resident) {
        this.resident = resident;
    }

    /**
     * Retourne le résident connecté.
     *
     * @return Le résident connecté.
     */
    public static Resident getResidentConnecte() {
        return residentConnecte;
    }

    /**
     * Définit le résident connecté.
     *
     * @param residentConnecte le résident connecté à attribuer.
     */
    public void setResidentConnecte(Resident residentConnecte) {
        Resident.residentConnecte = residentConnecte;
    }

    /**
     * Permet de connecter un résident en comparant l'adresse courriel fournie avec les résidents inscrits.
     * Elle permet trouver le résident connecté et d'avoir une variable residentConnecte
     * utile pour associer une requête à un résident par exemple.
     * @param utilisateur L'utilisateur qui se connecte.
     * @param email L'adresse courriel du résident.
     * @return Le résident connecté, ou null si aucun résident avec cette adresse n'est trouvé.
     */
    public static Resident connecterResident(Utilisateur utilisateur, String email) {
        // Prendre la liste des résidents depuis le fichier JSON
        List<Resident> residents = GestionResidents.chargerResidentsDepuisFichier();

        // Chercher le résident avec l'email donné
        for (Resident resident : residents) {
            if (resident.getAdresseCourriel().equals(email)) {
                residentConnecte = resident;
                return resident;  // Résident trouvé
            }
        }
        return null;

    }

    public static void deconnecterResident() {
        residentConnecte = null; // Déconnecte le résident
    }


   /** Effectue l'inscription d'un résident, en collectant toutes les informations nécessaires
    * et en validant les entrées.
    * Après l'inscription, il est proposé au résident de se connecter.
    *
    * @param utilisateur L'utilisateur souhaitant s'inscrire.
    */
    @Override
    public void inscription(Utilisateur utilisateur) {
        Scanner scanner = new Scanner(System.in);

        nom = obtenirNom("Nom");
        prenom = obtenirNom("Prénom");

        // La date de naissance est entrée par le résident. Son format est vérifiée.
        // L'âge du résident est vérifiée et doit être minimum de 16 ans.
        dateNaissance = obtenirDateNaissance();

        // Le téléphone du résident est entré optionnellement par le résident et est validé
        telephone = obtenirTelephone();

        // L'adresse courriel est entrée par le résident et est validée
        adresseCourriel = obtenirEmail();

        // Le mot de passe est entrée par l'utilisateur. Il doit contenir un minimum
        // de 8 caractères, une majuscule, un chiffre et un caractère spécial.
        motDePasse = obtenirMotDePasse();


        // L'adresse du résident est entrée et permet de l'associer à un quartier de Montréal.
        adresse = obtenirAdresse();

        // L'utilisateur entre un code postal et ce code est vérifié.
        codePostal = obtenirCodePostal();

        // Ajouter le résident à la liste et sauvegarder
        GestionResidents.ajouterResident(this);

        System.out.println("Vous êtes inscrit!");
        afficherInformations();
        Preference preferences = new Preference(adresseCourriel);
        // Demander au résident s'il souhaite se connecter
        demanderConnexion(scanner, utilisateur);

    }

    // Méthodes privées pour obtenir et valider les informations personnelles du résident

    /**
     * Demande à l'utilisateur de saisir son nom ou prénom.
     *
     * @param type Le type d'information demandé ("nom" ou "prénom").
     * @return La valeur saisie par l'utilisateur.
     */
    private String obtenirNom(String type) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Veuillez entrer votre " + type + ".");
        return scanner.nextLine();
    }

    /**
     * Demande à l'utilisateur de saisir sa date de naissance et la valide.
     *
     * Cette méthode s'assure que la date de naissance est au format valide (JJ/MM/AAAA)
     * et que l'utilisateur a au moins 16 ans. Elle redemande l'entrée en cas d'erreur.
     *
     * @return La date de naissance valide saisie par l'utilisateur.
     */
    private String obtenirDateNaissance() {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Veuillez entrer votre date de naissance (JJ/MM/AAAA).");
            String dateNaissance = scanner.nextLine();

            // Vérifie si la date est valide
            if (!Valider.validerDate(dateNaissance)) {
                System.out.println("Date de naissance invalide. Veuillez entrer la date au format JJ/MM/AAAA.");
            }
            // Vérifie si l'âge est valide
            else if (!Valider.validerAge(dateNaissance)) {
                System.out.println("Vous ne pouvez pas vous inscrire, car vous avez moins de 16 ans.");
            }
            // Si la date est valide et l'âge est suffisant, retourner la date
            else {
                return dateNaissance;
            }
        }
    }

    /**
     * Demande à l'utilisateur de saisir son numéro de téléphone et le valide.
     *
     * L'utilisateur peut entrer "passer" pour ignorer cette étape.
     * La méthode vérifie que le numéro est valide ou qu'il a choisi de passer.
     *
     * @return Le numéro de téléphone valide ou "passer".
     */
    private String obtenirTelephone() {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Veuillez entrer votre numéro de téléphone (ou \"passer\").");
            String telephone = scanner.nextLine();
            if (telephone.equalsIgnoreCase("passer") || Valider.validerTel(telephone)) {
                return telephone;
            } else {
                System.out.println("Numéro de téléphone invalide.");
            }
        }
    }
    /**
     * Demande à l'utilisateur de saisir son adresse courriel et la valide.
     *
     * Cette méthode vérifie que l'adresse courriel est au format correct.
     * En cas d'erreur, elle redemande une nouvelle saisie.
     *
     * @return L'adresse courriel valide saisie par l'utilisateur.
     */
    private String obtenirEmail() {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Veuillez entrer votre adresse courriel.");
            String adresseCourriel = scanner.nextLine();
            if (Valider.validerEmail(adresseCourriel)) {
                return adresseCourriel;
            } else {
                System.out.println("Adresse courriel invalide.");
            }
        }
    }

    /**
     * Demande à l'utilisateur de saisir un mot de passe et le valide.
     *
     * Le mot de passe doit respecter les règles suivantes :
     * <ul>
     *   <li>Entre 8 et 20 caractères.</li>
     *   <li>Contient au moins un chiffre.</li>
     *   <li>Contient au moins une lettre majuscule.</li>
     *   <li>Contient au moins un caractère spécial.</li>
     * </ul>
     * En cas d'erreur, la méthode redemande une saisie valide.
     *
     * @return Le mot de passe valide saisi par l'utilisateur.
     */
    private String obtenirMotDePasse() {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Veuillez entrer votre mot de passe. Celui-ci doit contenir au minimum 8 caractères et maximum 20. Il doit contenir au moins un chiffre, une majuscule et un caractère spécial");
            String motDePasse = scanner.nextLine();
            if (Valider.validerMDP(motDePasse)) {
                return motDePasse;
            } else {
                System.out.println("Mot de passe invalide.");
            }
        }
    }

    /**
     * Demande à l'utilisateur de saisir un code postal et le valide.
     *
     * Le code postal doit respecter le format "A1B 2C3". En cas d'erreur,
     * la méthode redemande une saisie valide.
     *
     * @return Le code postal valide saisi par l'utilisateur.
     */
    private String obtenirCodePostal() {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Veuillez entrer votre code postal.");
            String code = scanner.nextLine();
            if (Valider.validerCodePostal(code)) {
                return motDePasse;
            } else {
                System.out.println("Code postal invalide. Il doit être de la forme A1B 2C3.");
            }
        }
    }

    /**
     * Demande à l'utilisateur de saisir son adresse et vérifie qu'elle n'est pas vide.
     *
     * En cas d'entrée vide, la méthode redemande une saisie valide.
     *
     * @return L'adresse valide saisie par l'utilisateur.
     */
    private String obtenirAdresse() {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Veuillez entrer votre adresse (rue).");
            String adresse = scanner.nextLine();
            if (!adresse.isEmpty()) {
                return adresse;
            } else {
                System.out.println("L'adresse ne peut être vide.");
            }
        }
    }

    /**
     * Affiche les informations personnelles recueillies auprès de l'utilisateur.
     *
     * Cette méthode affiche les informations telles que le nom, le prénom,
     * la date de naissance, l'adresse courriel, le numéro de téléphone et l'adresse.
     */
    private void afficherInformations() {
        System.out.println("\nInformations recueillies:");
        System.out.println("Nom: " + nom);
        System.out.println("Prénom: " + prenom);
        System.out.println("Date de naissance : " + dateNaissance);
        System.out.println("Adresse e-mail : " + adresseCourriel);
        System.out.println("Numéro de téléphone: " + telephone);
        System.out.println("Votre adresse: " + adresse);
    }

    /**
     * Demande à l'utilisateur s'il souhaite se connecter après avoir fourni ses informations.
     *
     * Si l'utilisateur répond "Oui", la méthode appelle la fonction de connexion.
     * Sinon, elle termine l'application.
     *
     * @param scanner     L'objet {@link Scanner} utilisé pour lire la réponse de l'utilisateur.
     * @param utilisateur L'objet {@link Utilisateur} représentant l'utilisateur à connecter.
     */
    private void demanderConnexion(Scanner scanner, Utilisateur utilisateur) {
        System.out.println("\nMerci pour vos informations! Souhaitez-vous vous connecter? (Oui/Non)");
        String reponse = scanner.next();
        if (reponse.equalsIgnoreCase("Oui")) {
            super.connexion(utilisateur);
        } else {
            System.out.println("Au revoir !");
            System.exit(0);
        }
    }
}

