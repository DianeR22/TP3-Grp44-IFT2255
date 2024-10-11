import java.util.Scanner;

public class Intervenant extends Utilisateur {
    // // Attributs spécifiques à l'intervenant
    private String type;
    private String identifiantVille;

    // Constructeur avec arguments, certains sont hérités de la classe utilisateur
    public Intervenant(String nom, String prenom, String motDePasse, String adresseCourriel, String type, String identifiantVille) {
        super(nom, prenom, motDePasse, adresseCourriel);
        this.type = type;
        this.identifiantVille = identifiantVille;
    }

    // Constructeur sans arguments
    public Intervenant() {

    }

    // Getter et setter
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdentifiantVille() {
        return identifiantVille;
    }

    public void setIdentifiantVille(String identifiantVille) {
        this.identifiantVille = identifiantVille;
    }

    // Méthode inscription qui collecte les informations nécessaires à l'inscription de l'intervenant
    @Override
    public void inscription() {
        // Initialisation d'un scanner afin d'obtenir l'input de l'intervenant
        Scanner scanner = new Scanner(System.in);
        // Nom et prénom de l'intervenant
        System.out.println("Veuillez entre votre nom.");
        nom = scanner.nextLine();
        System.out.println("Veuillez entrer votre prenom.");
        prenom = scanner.nextLine();

        // L'adresse courriel est entrée par le résident et est validée
        while(true){
            System.out.println("Veuillez entre votre adresse courriel");
            adresseCourriel = scanner.nextLine();
            if (Valider.validerEmail(adresseCourriel)) {
                break;
            } else {
                System.out.println("Adresse email invalide");}
        }

        // Le mot de passe est entrée par l'utilisateur. Il doit contenir un minimum
        // de 8 caractères, une majuscule, un chiffre et un caractère spécial
        while(true){
            System.out.println("Veuillez entrer votre mot de passe. Celui-ci doit contenir 8 à 20 caractères, une majuscule, un chiffre et un caractère spécial.");
            motDePasse = scanner.next();
            if (Valider.validerMDP(motDePasse)){
                break;
            } else { System.out.println("Mot de passe invalide.");}
        }

        // Le type de l'intervenant est entré et validé
        while(true){
            System.out.println("Veuillez entrer votre type (public ou privé ou individu).");
            type = scanner.next();
            if (type.equalsIgnoreCase("Public") || type.equalsIgnoreCase("Privé") || type.equalsIgnoreCase("Individu")) {
                break;
            } else {System.out.println("Type invalide");}
        }

        // L'identifiant de la ville de l'intervenant est entré puis validé
        while(true){
            System.out.println("Veuillez entrer l`identifiant de la ville (code à 8 chiffres)");
            identifiantVille = scanner.next();
            if (Valider.validerIdentifiantVille(identifiantVille)) {
                break;
            } else {
                System.out.println("Identifiant de la ville invalide");}
        }


        System.out.println("Vous etes inscrit!");

        System.out.println("\nInformations recueillies :");
        System.out.println("Nom : " + nom);
        System.out.println("Prénom: " + prenom);
        System.out.println("Adresse e-mail : " + adresseCourriel);
        System.out.println("Type: " + type);
        System.out.println("Identifiant de la ville: " + identifiantVille);

        System.out.println("Merci pour vos informations! Souhaitez-vous vous connecter?\n");


        // Demander à l'intervenant s'il souhaite se connecter
        String reponse = "";
        reponse = scanner.next();
        if ((reponse).equalsIgnoreCase("Oui")){
            super.connexion();
        } else {
            scanner.close();
            System.exit(0);
            System.out.println("Au revoir.");
        }
    }

    protected static void afficherMenuIntervenant() {
        System.out.println("Vous êtes connecté!");

        System.out.println("\nMenu Intervenant :");
        System.out.println("1. Modifier son profil");
        System.out.println("2. Consulter la liste des requêtes de travail");
        System.out.println("3. Soumettre un nouveau projet de travaux");
        System.out.println("4. Mettre à jour les informations d'un chantier");
        System.out.println("5. Retourner au menu principal");

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
    }

}
