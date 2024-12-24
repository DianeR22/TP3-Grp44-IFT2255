package Controller;

import Model.NotificationManager;

import java.util.Set;

/**
 * Contrôleur pour la gestion des notifications.
 * Cette classe permet d'ajouter, consulter et gérer les notifications
 * ainsi que les abonnements aux quartiers pour recevoir des notifications.
 */
public class NotificationController {

    /**
     * Ajoute une notification lorsqu'un projet est soumis.
     *
     * Cette méthode crée un message de notification pour un projet soumis et
     * l'associe à un quartier spécifique.
     *
     * @param projetTitre Le titre du projet soumis.
     * @param quartier    Le quartier associé à la notification.
     */
    public static void ajouterNotificationProjetSoumis(String projetTitre, String quartier) {
        String message = "Un nouveau projet a été soumis : " + projetTitre;
        NotificationManager.ajouterNotification(message, quartier);
        System.out.println("Notification ajoutée pour le quartier : " + quartier);
    }

    /**
     * Affiche les notifications non vues et vues pour l'utilisateur.
     *
     * Cette méthode récupère les notifications associées aux quartiers auxquels
     * l'utilisateur est abonné, les affiche, et marque toutes les notifications
     * comme vues après leur consultation.
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
     * Retourne le nombre de notifications non vues pour les quartiers abonnés.
     *
     * @return Le nombre de notifications non vues.
     */
    public static int obtenirNombreNotificationsNonVues() {
        return NotificationManager.compterNotificationsNonVues();
    }

    /**
     * Ajoute un quartier à la liste des abonnements de l'utilisateur.
     *
     * Après l'abonnement, l'utilisateur recevra des notifications liées
     * au quartier spécifié.
     *
     * @param quartier Le nom du quartier à ajouter à la liste des abonnements.
     */
    public static void ajouterAbonnementQuartier(String quartier) {
        NotificationManager.ajouterQuartier(quartier);
        System.out.println("Vous êtes maintenant abonné au quartier : " + quartier);
    }

    /**
     * Retire un quartier de la liste des abonnements de l'utilisateur.
     *
     * Après le désabonnement, l'utilisateur ne recevra plus de notifications
     * pour le quartier spécifié.
     *
     * @param quartier Le nom du quartier à retirer de la liste des abonnements.
     */
    public static void retirerAbonnementQuartier(String quartier) {
        NotificationManager.retirerQuartier(quartier);
        System.out.println("Vous êtes maintenant désabonné du quartier : " + quartier);
    }

    /**
     * Récupère la liste des quartiers auxquels l'utilisateur est abonné.
     *
     * @return Un ensemble contenant les noms des quartiers abonnés.
     */
    public static Set<String> obtenirQuartiersAbonnes() {
        return NotificationManager.obtenirQuartiersAbonnes();
    }
}
