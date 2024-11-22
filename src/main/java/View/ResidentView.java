package View;

import Controller.ConnexionController;
import Controller.EntraveController;
import Controller.RequeteController;
import Controller.TravauxController;
import Model.Resident;

import java.util.Scanner;

public class ResidentView {

    // Méthode qui affiche le menu d'un résident
    public static void afficherMenuResident() {
        System.out.println("Vous êtes connecté!");

        while(true){
        System.out.println("\nMenu Résident :");
        System.out.println("1. Modifier son profil");
        System.out.println("2. Consulter les travaux en cours ou à venir");
        System.out.println("3. Rechercher des travaux");
        System.out.println("4. Notifications");
        System.out.println("5. Permettre une planification participative");
        System.out.println("6. Soumettre une requête de travail");
        System.out.println("7. Signaler un problème à la ville");
        System.out.println("8. Laisser un avis");
        System.out.println("9. Consulter les entraves routieres.");
        System.out.println("10. Retourner au menu principal");

        // Récupérer le input de l'utilisateur
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        // Traitement de l'option choisie
        switch (input) {
            case "1":
                // Modifier le profil (non définie ici)
                System.out.println("Option non implémentée");
                retour(scanner);
                break;
            case "2":
                // Option pour consulter les travaux en cours ou à venir
                TravauxController.afficherMenu(scanner);
                retour(scanner);
                break;
            case "3":
                // Option pour rechercher des travaux (non définie ici)
                System.out.println("Option non implémentée");
                retour(scanner);
                break;
            case "4":
                // Notifications (non définie ici)
                System.out.println("Option non implémentée");
                retour(scanner);
                break;
            case "5":
                // Permettre une planification participative (non définie ici)
                System.out.println("Option non implémentée");
                retour(scanner);
                break;
            case "6":
                // Soumettre une requête de travail
                RequeteController.obtenirInformationsRequete();
                retour(scanner);
                break;
            case "7":
                // Signaler un problème (non définie ici)
                System.out.println("Option non implémentée");
                retour(scanner);
                break;
            case "8":
                // Laisser un avis (non définie ici)
                System.out.println("Option non implémentée");
                retour(scanner);
                break;

            case "9":
                // Consulter les entraves
                EntraveController.afficherMenu(scanner);
                retour(scanner);
                break;
            case "10":
                ConnexionController.afficherMenuPrincipal();
                break;
            default:
                System.out.println("Option invalide. Veuillez essayer à nouveau.");
        }
        }
    }

    // Méthode permettant à l'utilisateur de revenir au menu qu'il souhaite
    private static void retour(Scanner scanner){
        System.out.println("Voulez-vous revenir au menu des résidents (1) ou retourner au menu principal (2)? Veuillez repondre par 1 ou par 2.");
        String reponse = scanner.nextLine();
        if (reponse.equalsIgnoreCase("1")) {
            //
        } else{
            System.out.println("Retour au menu principal!");
            ConnexionController.afficherMenuPrincipal();
        }
    }

}
