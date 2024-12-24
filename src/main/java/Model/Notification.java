package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Représente une notification dans le système.
 *
 * Une notification contient un identifiant unique, un message, un horodatage,
 * un état indiquant si elle a été vue ou non, et un quartier associé.*/
public class Notification {
    private int id;
    private String message;
    private LocalDateTime timestamp;
    private boolean vue;
    private String quartier; // Quartier associé à la notification

    /**
     * Initialise une nouvelle notification avec un identifiant, un message,
     * un horodatage et un quartier associé.
     *
     * @param id        L'identifiant unique de la notification.
     * @param message   Le message de la notification.
     * @param timestamp L'horodatage de la notification.
     * @param quartier  Le quartier associé à la notification.*/
    public Notification(int id, String message, LocalDateTime timestamp, String quartier) {
        this.id = id;
        this.message = message;
        this.timestamp = timestamp;
        this.vue = false;
        this.quartier = quartier;
    }

    /**
     * Retourne l'identifiant unique de la notification.
     *
     * @return L'identifiant de la notification.
     */
    public int getId() {
        return id;
    }

    /**
     * Retourne le message de la notification.
     *
     * @return Le message de la notification.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Retourne l'horodatage de la notification.
     *
     * @return L'horodatage sous forme d'objet {@link LocalDateTime}.
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Indique si la notification a été vue par l'utilisateur.
     *
     * @return {@code true} si la notification a été vue, sinon {@code false}.
     */
    public boolean isVue() {
        return vue;
    }

    /**
     * Retourne le nom du quartier associé à la notification.
     *
     * @return Le nom du quartier.
     */
    public String getQuartier() {
        return quartier;
    }

    /**
     * Marque la notification comme vue par l'utilisateur.
     */
    public void marquerCommeVue() {
        this.vue = true;
    }

    /**
     * Retourne une représentation textuelle de la notification.
     *
     * Cette méthode formatte l'horodatage au format "dd-MM-yyyy HH:mm"
     * et inclut le message ainsi que le quartier associé.
     *
     * @return Une chaîne représentant la notification.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return "[" + timestamp.format(formatter) + "] " + message + " (Quartier : " + quartier + ")";
    }
}
