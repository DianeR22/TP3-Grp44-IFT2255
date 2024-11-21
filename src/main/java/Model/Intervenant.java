package Model;

import java.util.Scanner;

public class Intervenant extends Utilisateur {
    // // Attributs spécifiques à l'intervenant
    private String type;
    private String identifiantVille;
    private Intervenant intervenant;

    // Constructeur avec arguments, certains sont hérités de la classe utilisateur
    public Intervenant(String nom, String prenom, String adresseCourriel, String motDePasse, String type, String identifiantVille) {
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


        System.out.println("Vous êtes inscrit!");
        afficherInformations();

        // Demander à l'intervenant s'il souhaite se connecter
        demanderConnexion(scanner);
    }

    // Méthode pour obtenir le nom ou prénom
    private String obtenirNom(String type) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Veuillez entrer votre " + type + ".");
        return scanner.nextLine();
    }

    // Méthode pour obtenir et valider l'adresse email
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

    // Méthode pour obtenir et valider le mot de passe
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

    // Méthode pour obtenir et valider le type d'intervenant
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

    // Méthode pour obtenir et valider l'identifiant de la ville
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

    // Méthode pour afficher les informations recueillies
    private void afficherInformations() {
        System.out.println("\nInformations recueillies :");
        System.out.println("Nom : " + nom);
        System.out.println("Prénom : " + prenom);
        System.out.println("Adresse e-mail : " + adresseCourriel);
        System.out.println("Type : " + type);
        System.out.println("Identifiant de la ville : " + identifiantVille);
    }

    // Méthode pour demander à l'intervenant s'il souhaite se connecter
    private void demanderConnexion(Scanner scanner) {
        System.out.println("Merci pour vos informations! Souhaitez-vous vous connecter? (Oui/Non)");
        String reponse = scanner.next();
        if (reponse.equalsIgnoreCase("Oui")) {
            super.connexion();
        } else {
            System.out.println("Au revoir.");
            System.exit(0);
        }
    }

    // Méthode qui permet à l'intervenant de soumettre une candidature. Elle prend
    // en paramètres la requête à laquelle postule l'intervenant et l'intervenant lui-même
    // et n'a pas de valeur de retour
    public static void soumettreCandidature(Requete requete, Intervenant intervenant){

        // Récupérer le input de l'utilisateur
        Scanner scanner = new Scanner(System.in);

        System.out.println("Entrez la requête pour laquelle vous soumettez votre candidature: ");
        String requeteChoisie = scanner.nextLine();

        System.out.println("Entrez la date de début : ");
        String dateDebut = scanner.nextLine();

        System.out.println("Entrez le type de fin : ");
        String dateFin = scanner.nextLine();

        // Associer l'intervenant à la requête
        requete.setIntervenant(intervenant);
        System.out.println("Votre candidature a été soumise avec succès pour la requête: " + requete.getTitreTravail());
    }


}
