package Model;

import Controller.IntervenantController;
import Controller.ResidentController;

import java.util.List;
import java.util.Scanner;

public abstract class Utilisateur {
    // Attributs communs d'un résident et d'un intervenant
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
        System.out.println("Vous êtes sur la page de connexion!");

        int tentative = 0;
        while (tentative < 3) {
            System.out.println("Veuillez entrer votre adresse courriel.");
            adresseCourriel = scanner.next();
            System.out.println("Veuillez entrer votre mot de passe.");
            motDePasse = scanner.next();

            if (verifierConnexion(adresseCourriel, motDePasse)) {
                System.out.println("Connexion réussie.");
                afficherMenu();
                return;  // Connexion réussie
            } else {
                tentative++;
                if (tentative < 3) {
                    System.out.println("Adresse courriel ou mot de passe invalide. Veuillez réessayer.");
                }

            }
        }

        // Après 3 tentatives échouées
        System.out.println("Vous avez échoué à vous connecter après 3 tentatives. Veuillez réessayer plus tard. Le programme va maintenant se terminer.");
        System.exit(0);
    }



    // Cette méthode prend en paramètre l'email et mot de passe et vérifie qu'il s'agit bien
    // d'un résident ou intervenant inscrit et que les identifiants sont valides
    boolean verifierConnexion(String email, String mdp){

        // Récupérer les listes des résidents et des intervenants
        List<Resident> residents = GestionResidents.getListeResidents();
        List<Intervenant> intervenants = GestionIntervenants.getListeIntervenants();

        // Vérifier si c'est un résident
        if (this instanceof Resident) {
            for (Resident resident : residents) {
                // Vérifier que l'email et mdp sont valides
                if (resident.getAdresseCourriel().equals(email) && resident.getMotDePasse().equals(mdp)) {
                    return true;
                }
            }
        }

        // Vérifier si c'est un intervenant
        if (this instanceof Intervenant) {
            for (Intervenant intervenant : intervenants) {
                // Vérifier que l'email et mdp sont valides
                if (intervenant.getAdresseCourriel().equals(email) && intervenant.getMotDePasse().equals(mdp)) {
                    return true;
                }
            }
        }
        return false;
    }
    // Méthode afficherMenu qui n'a pas de valeur de retour et qui identifie
    // le type de l'objet (soit résident ou intervenant) et appelle la
    // méthode adéquate en conséquence
    void afficherMenu(){
        if (this instanceof Resident) {
            ResidentController.afficherMenuResident();
        } else if (this instanceof Intervenant) {
            IntervenantController.afficherMenuIntervenant();
        }

    }

}
