package View;

import Controller.*;
import Model.GestionRequete;
import Model.Resident;

import java.util.Scanner;

/**
 * La classe ResidentView gère l'affichage du menu et l'interaction du résident
 * avec l'application.
 */
public class ResidentView {

    /**
     * Méthode qui affiche le menu d'un résident et permet à l'utilisateur de choisir parmi
     * des options de gestion des travaux et des requêtes.
     */
    public static void afficherMenuResident() {
        System.out.println("Vous êtes connecté!");

        while(true){
            int nombreNonVues = NotificationController.obtenirNombreNotificationsNonVues();

            System.out.println("\nMenu Résident :");
            System.out.println("1. Modifier son profil");
            System.out.println("2. Consulter les travaux en cours");
            System.out.println("3. Consulter les travaux à venir");
            System.out.println("4. Rechercher des travaux");
            System.out.println("5. Notifications (" + nombreNonVues + " non lues)");
            System.out.println("6. Modifier vos préférences horaires");
            System.out.println("7. Soumettre une requête de travail");
            System.out.println("8. Faire le suivi d'une requête");
            System.out.println("9. Consulter les entraves routieres.");
            System.out.println("10. Retourner au menu principal");

            // Récupérer le input de l'utilisateur
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            // Traitement de l'option choisie
            switch (input) {
                case "1":
                    // Modifier le profil (non définie ici)
                    System.out.println("Vous êtes sur la section : Modifier son profil ! Cette option n'est pas encore implémentée!");
                    retour(scanner);
                    break;
                case "2":
                    // Option pour consulter les travaux en cours
                    System.out.println("Vous êtes sur la section : Rechercher des travaux ! Cette option n'est pas encore implémentée!");
                    retour(scanner);
                    break;
                case "3":
                    System.out.println("Vous êtes sur la section : Consulter les travaux à venir ! Cette option n'est pas encore implémentée!");
                    retour(scanner);
                    break;
                case "4":
                    // Option pour rechercher des travaux (non définie ici)
                    TravauxController.afficherMenu(scanner);
                    retour(scanner);
                    break;
                case "5":
                    NotificationView.afficherZoneNotification(scanner);
                    retour(scanner);
                    break;
                case "6":
                    // Modifier les préférences horaires
                    System.out.println("Vous êtes sur la section : Modifier vos préférences horaires !");
                    Resident res = Resident.getResidentConnecte();
                    PreferenceView.afficherMenuPreferences(res);
                    retour(scanner);
                    break;
                case "7":
                    // Soumettre une requête de travail
                    System.out.println("Vous êtes sur la section : Soumettre une requête de travail !");
                    RequeteController.obtenirInformationsRequete();
                    retour(scanner);
                    break;
                case "8":
                    // Faire suivi
                    System.out.println("Vous êtes sur la section : Faire le suivi d'une requête !");
                    Resident resident = Resident.getResidentConnecte();
                    GestionRequete.suiviRequete(resident);
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

    /**
     * Méthode permettant à l'utilisateur de revenir
     * au menu des résidents ou retourner au menu principal.
     *
     * @param scanner Scanner utilisé pour récupérer l'entrée de l'utilisateur.
     */
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
