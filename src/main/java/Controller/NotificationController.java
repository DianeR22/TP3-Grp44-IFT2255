package Controller;

import Model.NotificationManager;

import java.util.Set;

/**
 * Contrôleur des notifications.
 */
public class NotificationController {

    public static void ajouterNotificationProjetSoumis(String projetTitre, String quartier) {
        String message = "Un nouveau projet a été soumis : " + projetTitre;
        NotificationManager.ajouterNotification(message, quartier);
        System.out.println("Notification ajoutée pour le quartier : " + quartier);
    }

    public static void consulterNotifications() {
        var nonVues = NotificationManager.obtenirNotificationsNonVues();
        var vues = NotificationManager.obtenirNotificationsVues();

        if (nonVues.isEmpty() && vues.isEmpty()) {
            System.out.println("Aucune notification disponible.");
            return;
        }

        if (!nonVues.isEmpty()) {
            System.out.println("----- Notifications Non Vues -----");
            nonVues.forEach(System.out::println);
        }

        if (!vues.isEmpty()) {
            System.out.println("----- Notifications Vues -----");
            vues.forEach(System.out::println);
        }

        NotificationManager.marquerToutesCommeVues();
    }

    public static void ajouterAbonnementQuartier(String quartier) {
        NotificationManager.ajouterQuartier(quartier);
        System.out.println("Vous êtes maintenant abonné au quartier : " + quartier);
    }

    public static Set<String> obtenirQuartiersAbonnes() {
        return NotificationManager.obtenirQuartiersAbonnes();
    }
    public static int obtenirNombreNotificationsNonVues() {
        return NotificationManager.compterNotificationsNonVues();
    }
    public static void retirerAbonnementQuartier(String quartier) {
        NotificationManager.retirerQuartier(quartier);
        System.out.println("Vous êtes maintenant désabonné du quartier : " + quartier);
    }
}
