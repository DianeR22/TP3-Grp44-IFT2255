package View;

import Controller.NotificationController;

import java.util.Scanner;

/**
 * Gère l'affichage et l'interaction avec les notifications.
 */
public class NotificationView {

    public static void afficherZoneNotification(Scanner scanner) {
        while (true) {
            System.out.println("---- Zone de Notification ----");
            System.out.println("1. Consulter les notifications");
            System.out.println("2. S'abonner à un quartier");
            System.out.println("3. Retirer un abonnement à un quartier");
            System.out.println("4. Voir les quartiers abonnés");
            System.out.println("5. Retourner au menu principal");
            System.out.println("Veuillez entrer votre choix (1-5) :");

            String choix = scanner.nextLine();

            switch (choix) {
                case "1":
                    NotificationController.consulterNotifications();
                    break;
                case "2":
                    System.out.println("Entrez le nom du quartier auquel vous souhaitez vous abonner :");
                    String quartier = scanner.nextLine();
                    NotificationController.ajouterAbonnementQuartier(quartier);
                    break;
                case "3":
                    System.out.println("Entrez le nom du quartier dont vous souhaitez vous désabonner :");
                    String quartierRetirer = scanner.nextLine();
                    NotificationController.retirerAbonnementQuartier(quartierRetirer);
                    break;
                case "4":
                    var quartiers = NotificationController.obtenirQuartiersAbonnes();
                    if (quartiers.isEmpty()) {
                        System.out.println("Vous n'êtes abonné à aucun quartier.");
                    } else {
                        System.out.println("Quartiers abonnés : " + String.join(", ", quartiers));
                    }
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }
        }
    }
}
