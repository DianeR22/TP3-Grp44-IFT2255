package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Gestionnaire des notifications.
 * Permet d'ajouter, récupérer et marquer les notifications comme vues.
 */
public class NotificationManager {
    private static final List<Notification> notifications = new ArrayList<>(); // Liste des notifications
    private static int nextId = 1; // Génère les identifiants uniques pour les notifications

    /**
     * Ajoute une nouvelle notification au système.
     * @param message Le message de la notification
     */
    public static void ajouterNotification(String message) {
        Notification notification = new Notification(nextId++, message, java.time.LocalDateTime.now());
        notifications.add(notification);
    }

    /**
     * Récupère la liste des notifications non vues.
     * @return Liste des notifications non vues
     */
    public static List<Notification> obtenirNotificationsNonVues() {
        return notifications.stream()
                .filter(notification -> !notification.isVue())
                .collect(Collectors.toList());
    }

    /**
     * Récupère la liste des notifications vues.
     * @return Liste des notifications vues
     */
    public static List<Notification> obtenirNotificationsVues() {
        return notifications.stream()
                .filter(Notification::isVue)
                .collect(Collectors.toList());
    }

    /**
     * Compte le nombre de notifications non vues.
     * @return Nombre de notifications non vues
     */
    public static int compterNotificationsNonVues() {
        return (int) notifications.stream()
                .filter(notification -> !notification.isVue())
                .count();
    }

    /**
     * Marque toutes les notifications comme vues.
     */
    public static void marquerToutesCommeVues() {
        notifications.forEach(Notification::marquerCommeVue);
    }
}
