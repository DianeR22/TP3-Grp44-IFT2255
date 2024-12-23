package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Représente une notification dans le système.
 * Chaque notification contient un message, une date de création et un statut indiquant si elle a été vue ou non.
 */
public class Notification {
    private int id; // Identifiant unique de la notification
    private String message; // Contenu de la notification
    private LocalDateTime timestamp; // Date et heure de création de la notification
    private boolean vue; // Indique si la notification a été vue

    /**
     * Constructeur pour initialiser une notification avec ses attributs.
     * @param id Identifiant unique
     * @param message Contenu de la notification
     * @param timestamp Date et heure de création
     */
    public Notification(int id, String message, LocalDateTime timestamp) {
        this.id = id;
        this.message = message;
        this.timestamp = timestamp;
        this.vue = false; // Par défaut, une notification est non vue
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public boolean isVue() {
        return vue;
    }

    /**
     * Marque la notification comme vue.
     */
    public void marquerCommeVue() {
        this.vue = true;
    }

    /**
     * Retourne une représentation formatée de la notification.
     * @return Une chaîne de caractères représentant la notification
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return "[" + timestamp.format(formatter) + "] " + message;
    }
}
