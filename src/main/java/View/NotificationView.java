package View;

import Controller.ResidentController;
import java.util.Scanner;

/**
 * La classe NotificationView gère l'affichage et l'interaction de la zone de notification
 * avec des options de consultation, personnalisation et retour au menu principal.
 */
public class NotificationView {

    /**
     * Affiche la zone de notification avec les options disponibles.
     *
     * @param scanner Scanner utilisé pour capturer l'entrée de l'utilisateur.
     */
    public static void afficherZoneNotification(Scanner scanner) {
        while (true) {
            System.out.println("---- Zone de Notification ----");
            System.out.println("1. Consulter");
            System.out.println("2. Personnaliser");
            System.out.println("3. Retour au Menu Principal");
            System.out.println("Veuillez entrer votre choix (1-3) :");

            String choix = scanner.nextLine();

            switch (choix) {
                case "1":
                    consulterNotifications();
                    break;
                case "2":
                    personnaliserNotifications(scanner);
                    break;
                case "3":
                    System.out.println("Retour au menu principal.");
                    ResidentController.afficherMenuResident();
                    return;
                default:
                    System.out.println("Choix invalide. Veuillez entrer un nombre entre 1 et 3.");
            }
        }
    }

    /**
     * Méthode pour consulter les notifications.
     * Affiche une liste fictive de notifications pour l'exemple.
     */
    private static void consulterNotifications() {
        System.out.println("Voici vos notifications :");
        System.out.println("- Notification 1 : Nouveau travail ajouté dans votre quartier.");
        System.out.println("- Notification 2 : Un service sera interrompu demain.");
        System.out.println("- Notification 3 : Mise à jour des travaux routiers.");
    }

    /**
     * Méthode pour personnaliser les préférences de notification.
     * Permet à l'utilisateur de choisir les types de notifications à recevoir.
     *
     * @param scanner Scanner utilisé pour capturer les choix de l'utilisateur.
     */
    private static void personnaliserNotifications(Scanner scanner) {
        System.out.println("Personnalisez vos préférences de notification :");
        System.out.println("1. Activer/Désactiver les notifications pour les travaux routiers");
        System.out.println("2. Activer/Désactiver les notifications pour les interruptions de service");
        System.out.println("3. Activer/Désactiver toutes les notifications");
        System.out.println("Veuillez entrer votre choix :");

        String choix = scanner.nextLine();
        switch (choix) {
            case "1":
                System.out.println("Notifications pour les travaux routiers mises à jour.");
                break;
            case "2":
                System.out.println("Notifications pour les interruptions de service mises à jour.");
                break;
            case "3":
                System.out.println("Toutes les notifications ont été activées/désactivées.");
                break;
            default:
                System.out.println("Choix invalide. Retour au menu des notifications.");
        }
    }
}
