package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Représente une notification dans le système.
 */
public class Notification {
    private int id;
    private String message;
    private LocalDateTime timestamp;
    private boolean vue;
    private String quartier; // Quartier associé à la notification

    public Notification(int id, String message, LocalDateTime timestamp, String quartier) {
        this.id = id;
        this.message = message;
        this.timestamp = timestamp;
        this.vue = false;
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
