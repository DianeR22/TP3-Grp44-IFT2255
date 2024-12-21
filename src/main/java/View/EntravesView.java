package View;

import Controller.ConnexionController;
import Controller.EntraveController;
import Controller.ResidentController;
import Controller.TravauxController;
import Model.ServiceAPI;

import java.util.Scanner;

public class EntravesView {

    static ServiceAPI serviceAPI = new ServiceAPI();

    public static void afficherMenu(Scanner scanner) {
        while(true) {
            // Menu principal pour consulter les travaux
            System.out.println("---- Menu des entraves routières causées par les travaux en cours ----");
            System.out.println("1. Entraves associées a un travail particulier");
            System.out.println("2. Entraves associées a une rue");
            System.out.println("3. Afficher toutes les entraves");
            System.out.println("4. Quitter le menu des entraves routières");
            System.out.println("Entrez une option entre 1 et 4.");

            String choix = scanner.nextLine();

            switch (choix) {
                case "1":
                    afficherEntraveParTravail(scanner);
                    retour(scanner);
                    break;
                case "2":
                    afficherEntraveParRue(scanner);
                    retour(scanner);
                    break;
                case "3":
                    afficherToutesLesEntraves(scanner);
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

    // Aucun filtrage
    public static void afficherToutesLesEntraves(Scanner scanner){
        System.out.println("Voici la liste de toutes les entraves associées aux travaux en cours: ");
        EntraveController.recupererEntraves(0, null);

        }

    // Filtrer par travail
    public static void afficherEntraveParTravail(Scanner scanner){
        System.out.println("Voici tous les travaux en cours: ");
        // Afficher tous les travaux sans filtre
        TravauxController.recupererTravaux(0, null);
        System.out.println("Veuillez entrer le numéro du travail souhaité: ");
        int idTravail = scanner.nextInt();
        EntraveController.recupererEntravesParTravail(idTravail);


    }

    // Filtrer par rue
    public static void afficherEntraveParRue(Scanner scanner){
        System.out.println("Veuillez entrer la rue souhaitée:");
        String filtre = scanner.nextLine();
        EntraveController.recupererEntraves(2, filtre);
    }

    // Permet de revenir en arrière selon les besoins de l'utilisateur
    private static void retour(Scanner scanner){
        System.out.println("Voulez-vous revenir au menu des entraves routières en cours (1) ou retourner au menu des résidents (2)? Veuillez répondre par 1 ou par 2.");
        String reponse = scanner.nextLine();
        if (reponse.equalsIgnoreCase("1")) {
            //
        } else{
            System.out.println("Retour au menu des résidents!");
            ResidentController.afficherMenuResident();

        }
    }
}








