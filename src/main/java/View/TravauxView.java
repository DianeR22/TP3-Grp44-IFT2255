package View;

import Controller.ConnexionController;
import Controller.ResidentController;
import Model.ServiceAPI;

import java.util.Scanner;
public class TravauxView {

    static ServiceAPI serviceAPI = new ServiceAPI();

    public static void afficherMenu(Scanner scanner) {

        while (true) {
            // Menu principal pour consulter les travaux pour le résident
            System.out.println("---- Menu des Travaux en Cours ou à Venir ----");
            System.out.println("1. Filtrer par quartier");
            System.out.println("2. Filtrer par type de travaux");
            System.out.println("3. Afficher tous les travaux");
            System.out.println("4. Quitter");
            System.out.println("Entrez une option entre 1 et 4.");

            String choix = scanner.nextLine();

            switch (choix) {
                case "1":
                    afficherTravauxParQuartier(scanner);
                    retour(scanner);
                    break;
                case "2":
                    afficherTravauxParType(scanner);
                    retour(scanner);
                    break;
                case "3":
                    afficherTousLesTravaux(scanner);
                    retour(scanner);
                    break;
                case "4":
                    System.out.println("Au revoir !");
                    ResidentController.afficherMenuResident();
                    break;
                default:
                    System.out.println("Option invalide.");
            }
        }
    }

    // Afficher par quartier
    public static void afficherTravauxParQuartier(Scanner scanner){
        System.out.println("Veuillez entrer le quartier souhaité: ");
        String filtre = scanner.nextLine();
        serviceAPI.recupererTravaux(1, filtre);

    }

    // Afficher par type
    public static void afficherTravauxParType(Scanner scanner){
        System.out.println("Veuillez entrer le type souhaité:");
        String filtre = scanner.nextLine();
        serviceAPI.recupererTravaux(2, filtre);

    }

    // Aucun filtrage
    public static void afficherTousLesTravaux(Scanner scanner) {
        System.out.println("Voici la liste de tous les travaux en cours ou a venir: ");
        serviceAPI.recupererTravaux(0, null);  // 0 pour afficher tous les travaux

    }

    // Permet à l'utilisateur de revenir en arrière au besoin
    private static void retour(Scanner scanner){
        System.out.println("Voulez-vous revenir au menu des travaux en cours (1) ou retourner au menu des résidents (2)?. Veuillez repondre par 1 ou par 2.");
        String reponse = scanner.nextLine();
        if (reponse.equalsIgnoreCase("1")) {
            //
        } else{
            System.out.println("Retour au menu des résidents!");
            ResidentController.afficherMenuResident();

            }
        }
    }






