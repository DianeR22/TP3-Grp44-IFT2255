package Model;

import jdk.jshell.execution.Util;

import java.util.List;
import java.util.Scanner;

public class Intervenant extends Utilisateur {
    // // Attributs spécifiques à l'intervenant
    private String type;
    private String identifiantVille;
    private Intervenant intervenant;

    // Variable statique pour l'intervenant connecté
    private static Intervenant intervenantConnecte;

    // Constructeur avec arguments, certains sont hérités de la classe utilisateur
    public Intervenant(String nom, String prenom, String adresseCourriel, String motDePasse, String type, String identifiantVille) {
        super(nom, prenom, motDePasse, adresseCourriel);
        this.type = type;
        this.identifiantVille = identifiantVille;
    }

    // Constructeur sans arguments
    public Intervenant() {

    }


    // La méthode equals sert à vérifier que les 2 objets sont
    // égaux s'ils ont la même adresse courriel
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

    // Garantir que le code de hachage et cohérent avec la méthode equals
    @Override
    public int hashCode() {
        // Retourne le code de hachage de l'adresse, 0 si null
        return adresseCourriel != null ? adresseCourriel.hashCode() : 0;
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

    public Intervenant getIntervenant() {
        return intervenant;
    }

    public void setIntervenant(Intervenant intervenant) {
        this.intervenant = intervenant;
    }

    public static Intervenant getIntervenantConnecte() {
        return intervenantConnecte;
    }

    public static void setIntervenantConnecte(Intervenant intervenant) {
        Intervenant.intervenantConnecte = intervenant;
    }

    // Cette méthode prend en param. l'utilisateur et son email. Elle sert à connecter
    // l'intervenant en trouvant l'intervenant présentement connecté en comparant
    // son adresse courriel avec l'adresse courriel de tous les intervenants inscrits.
    // Elle permet trouver l'intervenant connecté et d'avoir une variable intervenantConnecte
    // utile pour associer une candidature à l'intervenant postulant celle-ci
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


    // Méthode inscription qui collecte les informations nécessaires à l'inscription de l'intervenant
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
