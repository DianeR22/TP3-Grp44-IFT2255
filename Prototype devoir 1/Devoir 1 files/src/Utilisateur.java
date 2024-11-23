import java.util.Scanner;

public abstract class Utilisateur {
    // Attributs commun d'un résident et d'un intervenant
    protected String nom;
    protected String prenom;
    protected String motDePasse;
    protected String adresseCourriel;

    // Constructeur avec arguments
    public Utilisateur(String nom, String prenom, String motDePasse, String adresseCourriel) {
        this.nom = nom;
        this.prenom = prenom;
        this.motDePasse = motDePasse;
        this.adresseCourriel = adresseCourriel;
    }

    // Constructeur sans argument
    public Utilisateur() {

    }

    //Getter et setter
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getAdresseCourriel() {
        return adresseCourriel;
    }

    public void setAdresseCourriel(String adresseCourriel) {
        this.adresseCourriel = adresseCourriel;
    }

    // Méthode abstraite inscription qui doit être implémentée par les classes filles de la
    // classe mère utilisateur
    public abstract void inscription();

    // Méthode connexion qui n'a pas de valeur de retour et qui permet à un utilisateur
    // de se connecter en entrant une adresse courriel et un mot de passe
    public void connexion() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Vous êtes sur la page de connexion! ");
        // L'adresse courriel est entrée par l'utilisateur et est validée
        while(true){
            System.out.println("Veuillez entrer votre adresse courriel.");
            adresseCourriel = scanner.nextLine();
            if (Valider.validerEmail(adresseCourriel)) {
                break;
            } else {
                System.out.println("Adresse courriel invalide");}
        }
        while(true){
            System.out.println("Veuillez entrer votre mot de passe. Celui-ci doit contenir 8 à 20 caractères, une majuscule, un chiffre et un caractère spécial.");
            motDePasse = scanner.next();
            if (Valider.validerMDP(motDePasse)){
                break;
            } else { System.out.println("Mot de passe invalide.");}
        }

        // Lorsqu'un utilisateur se connecte, un menu s'affiche dépendemment qu'il est
        // un résident ou un intervenant
        afficherMenu();
    }

    // Méthode afficherMenu qui n'a pas de valeur de retour et qui identifie
    // le type de l'objet (soit résident ou intervenant) et appelle la
    // méthode adéquate en conséquence
    private void afficherMenu(){
        if (this instanceof Resident) {
            Resident.afficherMenuResident();
        } else if (this instanceof Intervenant) {
            Intervenant.afficherMenuIntervenant();
        }

    }

}
