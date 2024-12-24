package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Représente une notification dans le système.
 */
public class Notification {
    private int id; // Identifiant unique
    private String message; // Contenu de la notification
    private LocalDateTime timestamp; // Date et heure de création de la notification
    private boolean vue; // Statut de la notification (vue ou non)
    private String quartier; // Quartier associé à la notification

    /**
     * Constructeur pour initialiser une notification avec ses attributs.
     * @param id Identifiant unique
     * @param message Contenu de la notification
     * @param timestamp Date et heure de création
     * @param quartier Quartier associé
     */
    public Notification(int id, String message, LocalDateTime timestamp, String quartier) {
        this.id = id;
        this.message = message;
        this.timestamp = timestamp;
        this.vue = false; // Par défaut, une notification est non vue
        this.quartier = quartier;
    }

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

    public String getQuartier() {
        return quartier;
    }

    public void marquerCommeVue() {
        this.vue = true;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return "[" + timestamp.format(formatter) + "] " + message + " (Quartier : " + quartier + ")";
    }
}
