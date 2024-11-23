package Model;

import java.util.Scanner;

public class Resident extends Utilisateur {
    // Attributs spécifiques au résident
    private String dateNaissance;
    private String telephone;
    private String adresse;

    private Scanner scanner;
    private Resident resident;
    private Resident residentConnecte;


    // Constructeur avec arguments, certains sont hérités de la classe utilisateur
    public Resident(String nom, String prenom, String adresseCourriel, String motDePasse, String dateNaissance, String telephone, String adresse) {
        super(nom, prenom, motDePasse, adresseCourriel);
        this.dateNaissance = dateNaissance;
        this.telephone = telephone;
        this.adresse = adresse;
    }

    // Constructeur sans arguement
    public Resident() {
        super();
    }

    // Getter et setter
    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public Resident getResident() {
        return resident;
    }

    public void setResident(Resident resident) {
        this.resident = resident;
    }

    public Resident getResidentConnecte() {
        return residentConnecte;
    }

    public void setResidentConnecte(Resident residentConnecte) {
        this.residentConnecte = residentConnecte;
    }

    // Méthode inscription qui collecte les informations nécessaires à l'inscription du résident
    @Override
    public void inscription() {
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

        // Ajouter le résident à la liste et sauvegarder
        GestionResidents.ajouterResident(this);

        System.out.println("Vous êtes inscrit!");
        afficherInformations();

        // Demander au résident s'il souhaite se connecter
        demanderConnexion(scanner);

    }

    // Méthode pour obtenir le nom ou prénom
    private String obtenirNom(String type) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Veuillez entrer votre " + type + ".");
        return scanner.nextLine();
    }

    // Méthode pour obtenir la date de naissance
    private String obtenirDateNaissance() {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Veuillez entrer votre date de naissance (AAAA/MM/JJ).");
            String dateNaissance = scanner.nextLine();
            if (!Valider.validerDate(dateNaissance)) {
                System.out.println("Date de naissance invalide.");
            } else if (!Valider.validerAge(dateNaissance)) {
                System.out.println("Vous ne pouvez pas vous inscrire, car vous avez moins de 16 ans.");
                return null;
            }
            return dateNaissance;
        }
    }

    // Méthode pour obtenir et valider le téléphone
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
    // Méthode pour obtenir l'email
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

    // Méthode pour obtenir et valider le mot de passe
    private String obtenirMotDePasse() {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Veuillez entrer votre mot de passe.");
            String motDePasse = scanner.nextLine();
            if (Valider.validerMDP(motDePasse)) {
                return motDePasse;
            } else {
                System.out.println("Mot de passe invalide.");
            }
        }
    }

    // Méthode pour obtenir et valider l'adresse
    private String obtenirAdresse() {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Veuillez entrer votre adresse.");
            String adresse = scanner.nextLine();
            if (!adresse.isEmpty()) {
                return adresse;
            } else {
                System.out.println("L'adresse ne peut être vide.");
            }
        }
    }

    // Méthode pour afficher les informations recueillies
    private void afficherInformations() {
        System.out.println("\nInformations recueillies:");
        System.out.println("Nom: " + nom);
        System.out.println("Prénom: " + prenom);
        System.out.println("Date de naissance : " + dateNaissance);
        System.out.println("Adresse e-mail : " + adresseCourriel);
        System.out.println("Numéro de téléphone: " + telephone);
        System.out.println("Votre adresse: " + adresse);
    }

    // Méthode pour demander au résident s'il souhaite se connecter
    private void demanderConnexion(Scanner scanner) {
        System.out.println("\nMerci pour vos informations! Souhaitez-vous vous connecter? (Oui/Non)");
        String reponse = scanner.next();
        if (reponse.equalsIgnoreCase("Oui")) {
            super.connexion();
        } else {
            System.out.println("Au revoir !");
            System.exit(0);
        }
    }


}

