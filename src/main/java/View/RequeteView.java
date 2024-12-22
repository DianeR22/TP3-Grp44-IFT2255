package View;

import Controller.IntervenantController;
import Controller.RequeteController;
import Model.GestionRequete;
import Model.Intervenant;
import Model.Requete;
import Model.Valider;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class RequeteView {

    // Affichage du menu des requêtes pour l'intervenant
    public static void afficherMenuRequete() {
        while (true) {
            Scanner scanner = new Scanner(System.in);

            // Menu principal pour consulter les travaux
            System.out.println("\n--- Menu des requêtes de travail ---");
            System.out.println("1. Afficher toutes les requêtes");
            System.out.println("2. Filtrer les requêtes");
            System.out.println("3. Soumettre une candidature");
            System.out.println("4. Supprimer une candidature");
            System.out.println("5. Suivre sa candidature");
            System.out.println("6. Retour au menu principal");
            System.out.println("Entrez une option entre 1 et 5.");

            String choix = scanner.nextLine();

            switch (choix) {
                case "1":
                    afficherRequetes();
                    retour(scanner);
                    break;
                case "2":
                    filtrerRequetes(scanner);
                    retour(scanner);
                    break;
                case "3":
                    System.out.println("Vous êtes sur la section : Soumettre une candidature !");
                    IntervenantController.soumettreCandidature();
                    retour(scanner);
                    break;
                case "4":
                    System.out.println("Vous êtes sur la section : Supprimer une candidature !\n");
                    IntervenantController.supprimerCandidature();
                    retour(scanner);
                    break;
                case "5":
                    System.out.println("Vous êtes sur la section : Suivre une candidature !\n");
                    Intervenant intervenant = Intervenant.getIntervenantConnecte();
                    IntervenantController.suivreCandidature(intervenant);
                    retour(scanner);
                    break;
                case "6":
                   IntervenantController.afficherMenuIntervenant();
                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
            }
        }
    }

    // Méthode qui filtre les requêtes selon le choix de l'utilisateur
    private static void filtrerRequetes(Scanner scanner) {
        System.out.println("\n--- Filtrer les requêtes ---");
        System.out.println("1. Par type");
        System.out.println("2. Par quartier");
        System.out.println("3. Par date de début");
        System.out.print("Votre choix : ");

        int choix = scanner.nextInt();
        scanner.nextLine();

        List<Requete> requetesFiltrees;
        switch (choix) {
            case 1:
                System.out.print("Entrez le type de travail : ");
                String type = scanner.nextLine();
                requetesFiltrees = GestionRequete.filtrerParType(type);
                break;
            case 2:
                System.out.print("Entrez le quartier: ");
                String quartier = scanner.nextLine();
                requetesFiltrees = GestionRequete.filtrerParQuartier(quartier);
                break;
            case 3:
                System.out.print("Entrez la date de début (format YYYY/MM/DD) : ");
                String dateDebut = scanner.nextLine();
                requetesFiltrees = GestionRequete.filtrerParDate(dateDebut);
                break;
            default:
                System.out.println("Choix invalide. Retour au menu précédent.");
                return;
        }

        // Affichage des requêtes filtrées
        if (requetesFiltrees.isEmpty()) {
            System.out.println("Aucune requête correspondante.");
        } else {
            System.out.println("\n**** Voici les requêtes filtrées ****");
            for (Requete requete : requetesFiltrees) {
                System.out.println("Titre : " + requete.getTitreTravail());
                System.out.println("Type de travail: " + requete.getTypeTravaux());
                System.out.println("Description : " + requete.getDescription());
                System.out.println("Date de début: " + requete.getDebut());
                System.out.println();
            }
        }
    }

    // Permet d'obtenir les caractéristiques de la requête du résident
    public static void obtenirInformationsRequete() {
        Scanner scanner = new Scanner(System.in);

        // Récupérer les informations de la requête
        System.out.println("Entrez le titre du travail: ");
        String titreTravail = scanner.nextLine();

        System.out.println("Entrez une description détaillée du travail : ");
        String description = scanner.nextLine();

        // Demande du type de travaux
        // Liste des types de travaux possibles
        System.out.println("Entrez le type de travail parmi les suivants : ");
        for (Valider.TypeTravail travail : Valider.TypeTravail.values()) {
            System.out.println("- " + travail.getDescription());
        }

        boolean typeValide = false;
        String typeTravailChoisi = "";
        while (!typeValide) {
            typeTravailChoisi = scanner.nextLine().toUpperCase();
            if (Valider.validerTypeTravail(typeTravailChoisi)) {
                typeValide = true;
            } else {
                System.out.println("Type de travail invalide. Veuillez réessayer.");
            }
        }

        // Demande de la date
        String dateDebut = "";
        boolean dateValide = false;
        while (!dateValide) {
            System.out.println("Entrez la date de début espérée (format: JJ/MM/AAAA) : ");
            dateDebut = scanner.nextLine();

            if (Valider.validerDate(dateDebut)) {
                dateValide = true;
            } else {
                System.out.println("Le format de la date n'est pas correct. Veuillez réessayer.");
            }
        }

        // Soumettre la requête
        RequeteController.soumettreRequete(titreTravail, description, typeTravailChoisi, dateDebut);
    }

    // Affichage de toutes les requêtes
    public static void afficherRequetes() {
        List<Requete> listeRequetes = GestionRequete.getListeRequetes();

        if (listeRequetes.isEmpty()) {
            System.out.println("Aucune requête trouvée.");
        } else {
            System.out.println("**** Liste des requêtes de travail ****\n");
            for (Requete requete : listeRequetes) {
                System.out.println("Titre : " + requete.getTitreTravail());
                System.out.println("Type de travail : " + requete.getTypeTravaux());
                System.out.println("Description : " + requete.getDescription());
                System.out.println("Date de début : " + requete.getDebut());
                System.out.println();
            }
        }
    }

    // Permet de revenir en arrière pour l'utilisateur
    private static void retour(Scanner scanner) {
        System.out.println("Appuyez sur entrée pour revenir au menu.");
        scanner.nextLine();
    }
}