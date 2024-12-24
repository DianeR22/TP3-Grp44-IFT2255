package Model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Gestionnaire des notifications.
 *
 * Cette classe gère les notifications, notamment leur ajout, leur filtrage
 * (vues ou non vues), ainsi que la gestion des abonnements aux quartiers.
 */
public class NotificationManager {
    private static final List<Notification> notifications = new ArrayList<>();
    private static final Set<String> quartiersAbonnes = new HashSet<>(); // Quartiers auxquels l'utilisateur est abonné
    private static final String FICHIER_RESIDENTS = "data/residents.json";
    private static int nextId = 1;

    /**
     * Ajoute une nouvelle notification au système.
     *
     * @param message  Le message de la notification.
     * @param quartier Le quartier associé à la notification.
     */
    public static void ajouterNotification(String message, String quartier) {
        Notification notification = new Notification(nextId++, message, java.time.LocalDateTime.now(), quartier);
        notifications.add(notification);
    }

    /**
     * Récupère les notifications non vues par l'utilisateur, filtrées par
     * les quartiers auxquels il est abonné.
     *
     * @return Une liste de notifications non vues.
     */
    public static List<Notification> obtenirNotificationsNonVues() {
        return notifications.stream()
                .filter(notification -> !notification.isVue() && quartiersAbonnes.contains(notification.getQuartier()))
                .collect(Collectors.toList());
    }

    /**
     * Récupère les notifications déjà vues par l'utilisateur, filtrées par
     * les quartiers auxquels il est abonné.
     *
     * @return Une liste de notifications vues.
     */
    public static List<Notification> obtenirNotificationsVues() {
        return notifications.stream()
                .filter(notification -> notification.isVue() && quartiersAbonnes.contains(notification.getQuartier()))
                .collect(Collectors.toList());
    }

    /**
     * Compte le nombre de notifications non vues pour les quartiers auxquels
     * l'utilisateur est abonné.
     *
     * @return Le nombre de notifications non vues.
     */
    public static int compterNotificationsNonVues() {
        return (int) notifications.stream()
                .filter(notification -> !notification.isVue() && quartiersAbonnes.contains(notification.getQuartier()))
                .count();
    }

    /**
     * Marque toutes les notifications comme vues, quelle que soit leur association
     * avec un quartier ou leur état actuel.
     */
    public static void marquerToutesCommeVues() {
        notifications.forEach(Notification::marquerCommeVue);
    }

    /**
     * Ajoute un quartier à la liste des abonnements de l'utilisateur.
     *
     * @param quartier Le nom du quartier à ajouter.
     */
    public static void ajouterQuartier(String quartier) {
        quartiersAbonnes.add(quartier);
    }

    /**
     * Retire un quartier de la liste des abonnements de l'utilisateur.
     *
     * @param quartier Le nom du quartier à retirer.
     */
    public static void retirerQuartier(String quartier) {
        quartiersAbonnes.remove(quartier);
    }

    /**
     * Récupère la liste des quartiers auxquels l'utilisateur est abonné.
     *
     * @return Un ensemble contenant les noms des quartiers abonnés.
     */
    public static Set<String> obtenirQuartiersAbonnes() {
        return new HashSet<>(quartiersAbonnes);
    }

    /**
     * Charge les résidents depuis un fichier JSON et les abonne à leur quartier respectif.
     *
     * Cette méthode lit un fichier JSON contenant les informations des résidents,
     * identifie leurs quartiers, et ajoute ces derniers à la liste des abonnements.
     *
     * Si le fichier n'existe pas ou si une erreur survient lors de la lecture,
     * un message d'erreur est affiché.
     */
    public static void chargerEtAbonnerResidents() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File fichier = new File(FICHIER_RESIDENTS);
            if (!fichier.exists()) {
                System.out.println("Fichier des résidents introuvable.");
                return;
            }

            // Charger tous les résidents depuis le fichier JSON
            List<Resident> residents = objectMapper.readValue(
                    fichier,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Resident.class)
            );

            // Abonner chaque résident à son quartier
            for (Resident resident : residents) {
                if (resident.getAdresse() != null && !resident.getAdresse().isEmpty()) {
                    ajouterQuartier(resident.getAdresse());
                }
            }

            System.out.println("Tous les résidents ont été abonnés à leur quartier.");
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des résidents : " + e.getMessage());
        }
    }
}
