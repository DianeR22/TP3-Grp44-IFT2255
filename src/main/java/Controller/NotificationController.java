package Controller;

import Model.NotificationManager;

/**
 * Contrôleur des notifications.
 * Fournit une interface pour gérer l'affichage et l'interaction avec les notifications.
 */
public class NotificationController {

    /**
     * Ajoute une notification lorsqu'un projet est soumis.
     * @param projetTitre Titre du projet soumis
     */
    public static void ajouterNotificationProjetSoumis(String projetTitre) {
        String message = "Un nouveau projet a été soumis : " + projetTitre;
        NotificationManager.ajouterNotification(message);
        System.out.println("Notification ajoutée : \"" + message + "\"");
    }

    /**
     * Affiche les notifications en séparant celles non vues de celles vues.
     * Marque toutes les notifications comme vues après affichage.
     */
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

    /**
     * Récupère le nombre de notifications non vues.
     * @return Nombre de notifications non vues
     */
    public static int obtenirNombreNotificationsNonVues() {
        return NotificationManager.compterNotificationsNonVues();
    }
}
