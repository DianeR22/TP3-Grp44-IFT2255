package Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Gestionnaire des notifications.
 */
public class NotificationManager {
    private static final List<Notification> notifications = new ArrayList<>();
    private static final Set<String> quartiersAbonnes = new HashSet<>(); // Quartiers auxquels l'utilisateur est abonné
    private static int nextId = 1;

    public static void ajouterNotification(String message, String quartier) {
        if (quartiersAbonnes.contains(quartier)) {
            Notification notification = new Notification(nextId++, message, java.time.LocalDateTime.now(), quartier);
            notifications.add(notification);
        }
    }

    public static List<Notification> obtenirNotificationsNonVues() {
        return notifications.stream()
                .filter(notification -> !notification.isVue())
                .collect(Collectors.toList());
    }

    public static List<Notification> obtenirNotificationsVues() {
        return notifications.stream()
                .filter(Notification::isVue)
                .collect(Collectors.toList());
    }

    public static int compterNotificationsNonVues() {
        return (int) notifications.stream()
                .filter(notification -> !notification.isVue())
                .count();
    }

    public static void marquerToutesCommeVues() {
        notifications.forEach(Notification::marquerCommeVue);
    }

    public static void ajouterQuartier(String quartier) {
        quartiersAbonnes.add(quartier);
    }

    public static Set<String> obtenirQuartiersAbonnes() {
        return new HashSet<>(quartiersAbonnes);
    }
    public static void retirerQuartier(String quartier) {
        quartiersAbonnes.remove(quartier);
    }
}
