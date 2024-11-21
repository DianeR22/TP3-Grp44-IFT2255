package View;

import Controller.ConnexionController;
import Controller.RequeteController;

import java.util.Scanner;

public class IntervenantView {
    public static void afficherMenuIntervenant() {
        while(true){
        System.out.println("Vous êtes connecté!");

        System.out.println("\nMenu Intervenant :");
        System.out.println("1. Modifier son profil");
        System.out.println("2. Consulter la liste des requêtes de travail");
        System.out.println("3. Soumettre un nouveau projet de travaux");
        System.out.println("4. Mettre à jour les informations d'un chantier");
        System.out.println("5. Retourner au menu principal");

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();


        // Traitement de l'option choisie
        switch (input) {
            case "1":
                // Appeler la méthode pour modifier le profil
                System.out.println("Option non implémentée");
                retour(scanner);
                break;
            case "2":
                // Option pour consulter la liste des requêtes de travail
                RequeteController.afficherRequetes();
                retour(scanner);
                break;
            case "3":
                System.out.println("Option non implémentée");
                retour(scanner);
                break;
            case "4":
                System.out.println("Option non implémentée");
                retour(scanner);
                break;
            case "5":
                ConnexionController.afficherMenuPrincipal();
                break;

            default:
                // Si l'option est invalide
                System.out.println("Option invalide. Veuillez essayer à nouveau.");
        }
        }
    }

    private static void retour(Scanner scanner){
        System.out.println("Voulez-vous revenir au menu des intervenants (1) ou retourner au menu principal (2)? Veuillez repondre par 1 ou par 2.");
        String reponse = scanner.nextLine();
        if (reponse.equalsIgnoreCase("1")) {
            //
        } else{
            System.out.println("Retour au menu principal!");
            ConnexionController.afficherMenuPrincipal();

        }
    }
}

