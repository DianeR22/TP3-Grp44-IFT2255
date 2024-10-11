import java.util.Scanner;

public class Resident extends Utilisateur {
    // Attributs spécifiques au résident
    private String dateNaissance;
    private String telephone;
    private String adresse;

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

    // Méthode inscription qui collecte les informations nécessaires à l'inscription du résident
    @Override
    public void inscription() {
        // Initialisation d'un scanner afin d'obtenir l'input du résident
        Scanner scanner = new Scanner(System.in);
        // Nom et prénom du résident
        System.out.println("Veuillez entrer votre nom.");
        nom = scanner.nextLine();
        System.out.println("Veuillez entrer votre prénom.");
        prenom = scanner.nextLine();


        // La date de naissance est entrée par le résident. Son format est vérifiée.
        // L'âge du résident est vérifiée et doit être minimum de 16 ans.
        while(true){
            System.out.println("Veuillez entrer votre date de naissance dont le format doit être : JJ/MM/AAAA.");
            dateNaissance = scanner.nextLine();
            if (!Valider.validerDate(dateNaissance)){
                System.out.println("Date de naissance invalide.");
            }
            if (!Valider.validerAge(dateNaissance)){
                System.out.println("Vous ne pouvez pas vous inscrire, car vous avez moins de 16 ans. Vous allez être redirigé vers le menu principal.\n");
                return;
            }
            break;
        }

        // Le téléphone du résident est entré optionnellement par le résident et est validé
        while(true){
            System.out.println("Veuillez entrer votre numéro de téléphone. Entrer \"passer\" si vous ne souhaitez pas entrer cette information.");
            telephone = scanner.nextLine();
            if (telephone.equalsIgnoreCase("Passer")) {
                break; }

            if (!Valider.validerTel(telephone)){
                System.out.println("Numéro de téléphone invalide.");}
        }

        // L'adresse courriel est entrée par le résident et est validée
        while(true){
            System.out.println("Veuillez entrer votre adresse courriel.");
            adresseCourriel = scanner.nextLine();
            if (Valider.validerEmail(adresseCourriel)) {
                break;
            } else {
                System.out.println("Adresse courriel invalide");}
        }

        // Le mot de passe est entrée par l'utilisateur. Il doit contenir un minimum
        // de 8 caractères, une majuscule, un chiffre et un caractère spécial.
        while(true){
            System.out.println("Veuillez entrer votre mot de passe. Celui-ci doit contenir 8 à 20 caractères, une majuscule, un chiffre et un caractère spécial.");
            motDePasse = scanner.nextLine();
            if (Valider.validerMDP(motDePasse)){
                break;
            } else { System.out.println("Mot de passe invalide.");}
        }

        // L'adresse du résident est entrée et permet de l'associer à un quartier de Montréal.
        while(true){
            System.out.println("Veuillez entrer votre adresse.");
            adresse = scanner.nextLine();
            if (!adresse.isEmpty()){
                break;
            }  else { System.out.println("L'adresse ne peut être vide. Veuillez réessayer.");}
        }

        System.out.println("Vous êtes inscrit!");

        // Affichage des informations du résident
        System.out.println("\nInformations recueillies:");
        System.out.println("Nom: " + nom);
        System.out.println("Prénom: " + prenom);
        System.out.println("Date de naissance : " + dateNaissance);
        System.out.println("Adresse e-mail : " + adresseCourriel);
        System.out.println("Numéro de téléphone: " + telephone);
        System.out.println("Votre adresse: " + adresse);

        System.out.println("\nMerci pour vos informations! Souhaitez-vous vous connecter?\n");

        // Demander au résident s'il souhaite se connecter
        String reponse = "";
        reponse = scanner.next();
        if ((reponse).equalsIgnoreCase("Oui")){
            super.connexion();
        } else {
            scanner.close();
            System.exit(0);
            System.out.println("Au revoir !");
        }
    }
    // Méthode qui n'a pas de valeur de retour et qui affiche le menu d'un résident
    protected static void afficherMenuResident() {
        System.out.println("Vous êtes connecté!");

        System.out.println("\nMenu Résident :");
        System.out.println("1. Consulter les travaux en cours ou à venir");
        System.out.println("2. Rechercher des travaux");
        System.out.println("3. Notifications");
        System.out.println("4. Permettre une planification participative");
        System.out.println("5. Soumettre une requête de travail");
        System.out.println("6. Signaler un problème à la ville");
        System.out.println("7. Laisser un avis");
        System.out.println("8. Retourner au menu principal");

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

    }
}
