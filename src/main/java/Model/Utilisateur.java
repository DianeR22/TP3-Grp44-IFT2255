package Model;

import Controller.IntervenantController;
import Controller.ResidentController;

import java.util.List;
import java.util.Scanner;
/**
 * Classe abstraite d'un utilisateur dans le système. Elle possède les
 * informations et méthodes qui sont communes à un résident et un intervenant, comme
 * la connexion et l'inscription par exemple.
 */
public abstract class Utilisateur {
    // Attributs communs d'un résident et d'un intervenant
    protected String nom;
    protected String prenom;
    protected String motDePasse;
    protected String adresseCourriel;

    private static Utilisateur utilisateur;

    /**
     * Constructeur avec arguments pour initialiser un utilisateur.
     *
     * @param nom Le nom de l'utilisateur.
     * @param prenom Le prénom de l'utilisateur.
     * @param motDePasse Le mot de passe de l'utilisateur.
     * @param adresseCourriel L'adresse courriel de l'utilisateur.
     */
    public Utilisateur(String nom, String prenom, String motDePasse, String adresseCourriel) {
        this.nom = nom;
        this.prenom = prenom;
        this.motDePasse = motDePasse;
        this.adresseCourriel = adresseCourriel;
    }

    /**
     * Constructeur sans argument pour permettre l'initialisation d'un utilisateur sans attributs initiaux.
     */
    public Utilisateur() {

    }


    //Getter et setter

    /**
     * Récupère le nom de l'utilisateur.
     *
     * @return Le nom de l'utilisateur.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Définit le nom de l'utilisateur.
     *
     * @param nom Le nouveau nom de l'utilisateur.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }
    /**
     * Récupère le prénom de l'utilisateur.
     *
     * @return Le prénom de l'utilisateur.
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Définit le prénom de l'utilisateur.
     *
     * @param prenom Le nouveau prénom de l'utilisateur.
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Récupère le mot de passe de l'utilisateur.
     *
     * @return Le mot de passe de l'utilisateur.
     */
    public String getMotDePasse() {
        return motDePasse;
    }

    /**
     * Définit le mot de passe de l'utilisateur.
     *
     * @param motDePasse Le nouveau mot de passe de l'utilisateur.
     */
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    /**
     * Récupère l'adresse courriel de l'utilisateur.
     *
     * @return L'adresse courriel de l'utilisateur.
     */
    public String getAdresseCourriel() {
        return adresseCourriel;
    }

    /**
     * Définit l'adresse courriel de l'utilisateur.
     *
     * @param adresseCourriel  Le nouvel adresse courriel de l'utilisateur.
     */
    public void setAdresseCourriel(String adresseCourriel) {
        this.adresseCourriel = adresseCourriel;
    }



    /**
     * Méthode abstraite d'inscription que les classes filles doivent implémenter
     * pour gérer l'inscription des utilisateurs.
     *
     * @param utilisateur L'utilisateur s'inscrit.
     */
    public abstract void inscription(Utilisateur utilisateur);

    /**
     * Permet à un utilisateur de se connecter en entrant son adresse courriel et mot de passe.
     * L'utilisateur peut essayer de se connecter trois fois. En cas de 3 mauvaises tentatives,
     * le programme se termine.
     *
     * @param utilisateur L'utilisateur qui tente de se connecter.
     */

    public void connexion(Utilisateur utilisateur) {
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

                // Vérifier le type de l'utilisateur
                if (utilisateur instanceof Resident) {
                    Resident.connecterResident((Resident) utilisateur, adresseCourriel); // Cast pour utiliser la méthode spécifique
                } else if (utilisateur instanceof Intervenant) {
                    Intervenant.connecterIntervenant((Intervenant) utilisateur, adresseCourriel);
                }
                afficherMenu();
                return;  // Connexion réussie
            } else {
                tentative++;
                if (tentative < 3) {
                    System.out.println("Adresse courriel ou mot de passe invalide. Veuillez réessayer.");
                }

            }
        }

        // Après 3 mauvaises tentatives
        System.out.println("Vous avez échoué à vous connecter après 3 tentatives. Veuillez réessayer plus tard. Le programme va maintenant se terminer.");
        System.exit(0);
    }


    /**
     * Vérifie si les identifiants (adresse courriel et mot de passe) sont valides pour un résident ou un intervenant.
     *
     * @param email L'adresse courriel à vérifier.
     * @param mdp Le mot de passe à vérifier.
     * @return true si l'adresse courriel et le mot de passe correspondent à un résident ou intervenant valide, false sinon.
     */
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

    /**
     * Afficher un menu adapté au type d'utilisateur (résident ou intervenant) après une connexion réussie.
     * Selon le type d'utilisateur, la méthode adéquate est appelée.
     */
    void afficherMenu(){
        if (this instanceof Resident) {
            ResidentController.afficherMenuResident();
        } else if (this instanceof Intervenant) {
            IntervenantController.afficherMenuIntervenant();
        }
    }
}
