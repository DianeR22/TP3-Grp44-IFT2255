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
 */
public class NotificationManager {
    private static final List<Notification> notifications = new ArrayList<>();
    private static final Set<String> quartiersAbonnes = new HashSet<>(); // Quartiers auxquels l'utilisateur est abonné
    private static final String FICHIER_RESIDENTS = "data/residents.json";
    private static int nextId = 1;

    public static void ajouterNotification(String message, String quartier) {
        Notification notification = new Notification(nextId++, message, java.time.LocalDateTime.now(), quartier);
        notifications.add(notification);
    }

    public static List<Notification> obtenirNotificationsNonVues() {
        return notifications.stream()
                .filter(notification -> !notification.isVue() && quartiersAbonnes.contains(notification.getQuartier()))
                .collect(Collectors.toList());
    }

    public static List<Notification> obtenirNotificationsVues() {
        return notifications.stream()
                .filter(notification -> notification.isVue() && quartiersAbonnes.contains(notification.getQuartier()))
                .collect(Collectors.toList());
    }

    public static int compterNotificationsNonVues() {
        return (int) notifications.stream()
                .filter(notification -> !notification.isVue() && quartiersAbonnes.contains(notification.getQuartier()))
                .count();
    }

    public static void marquerToutesCommeVues() {
        notifications.forEach(Notification::marquerCommeVue);
    }

    public static void ajouterQuartier(String quartier) {
        quartiersAbonnes.add(quartier);
    }

    public static void retirerQuartier(String quartier) {
        quartiersAbonnes.remove(quartier);
    }

    public static Set<String> obtenirQuartiersAbonnes() {
        return new HashSet<>(quartiersAbonnes);
    }

    /**
     * Charge les résidents depuis un fichier JSON et les abonne à leur quartier.
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
