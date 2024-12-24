package Controller;

import Model.Resident;
import Model.Preference;
import View.ResidentView;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Contrôleur pour la gestion des fonctionnalités liées aux résidents.
 *
 * Cette classe gère les interactions des résidents avec l'application,
 * notamment l'ajout, la suppression et l'affichage de leurs préférences
 * horaires, ainsi que la navigation dans les menus associés.
 */
public class ResidentController {

    private static final String[] JOURS_DE_LA_SEMAINE = {
            "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"
    };

    /**
     * Affiche le menu principal des résidents.
     *
     * Cette méthode redirige vers la vue associée aux résidents pour leur permettre
     * d'interagir avec les fonctionnalités disponibles, comme la gestion des travaux
     * ou la modification de leurs préférences.
     */
    public static void afficherMenuResident() {
        ResidentView.afficherMenuResident();
    }


    /**
     * Ajoute une nouvelle préférence horaire pour un résident.
     *
     * Cette méthode demande à l'utilisateur de fournir :
     * <ul>
     *   <li>Un jour de la semaine.</li>
     *   <li>Une heure de début (entre 08:00 et 17:00).</li>
     *   <li>Une heure de fin (entre 08:00 et 17:00, après l'heure de début).</li>
     * </ul>
     * Une fois validées, ces informations sont ajoutées à la liste des préférences
     * du résident, et sauvegardées dans le modèle.
     *
     * @param resident L'objet {@link Resident} correspondant à l'utilisateur connecté.
     */
    public static void ajouterPreference(Resident resident) {
        Scanner scanner = new Scanner(System.in);

        // Récupérer le jour de la semaine
        String jour = null;
        while (true) {
            System.out.println("Veuillez entrer le jour de votre préférence (ex: Lundi, Mardi, etc.):");
            jour = scanner.nextLine().trim();
            boolean jourValide = false;

            // Vérifier si le jour est valide
            for (String j : JOURS_DE_LA_SEMAINE) {
                if (j.equalsIgnoreCase(jour)) {
                    jourValide = true;
                    break;
                }
            }

            if (jourValide) {
                break; // Sortir de la boucle si le jour est valide
            } else {
                System.out.println("Jour invalide. Veuillez entrer un jour valide.");
            }
        }

        // Récupérer l'heure de début
        LocalTime heureDebut = null;
        while (true) {
            System.out.println("Veuillez entrer l'heure de début de votre préférence (format: HH:mm):");
            String heureDebutInput = scanner.nextLine().trim();
            try {
                heureDebut = LocalTime.parse(heureDebutInput, DateTimeFormatter.ofPattern("HH:mm"));
                // Validate that it is within the range
                LocalTime heureMin = LocalTime.of(8, 0);  // 08:00
                LocalTime heureMax = LocalTime.of(17, 0); // 17:00
                if (heureDebut.isBefore(heureMin) || heureDebut.isAfter(heureMax)) {
                    System.out.println("L'heure de début doit être entre 08:00 et 17:00. Veuillez réessayer.");
                } else {
                    break; // Exit the loop if the time is valid
                }
            } catch (DateTimeParseException e) {
                System.out.println("Format d'heure invalide. Veuillez réessayer avec le format HH:mm");
            }
        }

        // Récupérer l'heure de fin
        LocalTime heureFin = null;
        while (true) {
            System.out.println("Veuillez entrer l'heure de fin de votre préférence (format: HH:mm):");
            String heureFinInput = scanner.nextLine().trim();
            try {
                heureFin = LocalTime.parse(heureFinInput, DateTimeFormatter.ofPattern("HH:mm"));
                // Validate that it is within the range
                LocalTime heureMin = LocalTime.of(8, 0);  // 08:00
                LocalTime heureMax = LocalTime.of(17, 0); // 17:00
                if (heureFin.isBefore(heureMin) || heureFin.isAfter(heureMax)) {
                    System.out.println("L'heure de fin doit être entre 08:00 et 17:00. Veuillez réessayer.");
                } else if (!heureFin.isAfter(heureDebut)) {
                    System.out.println("L'heure de fin doit être après l'heure de début. Veuillez réessayer.");
                } else {
                    break; // Exit the loop if the time is valid
                }
            } catch (DateTimeParseException e) {
                System.out.println("Format d'heure invalide. Veuillez réessayer avec le format HH:mm");
            }
        }

        // Ajouter la préférence au modèle et save
        Preference preference = new Preference(resident.getAdresseCourriel());
        preference.ajouterPreference(jour, heureDebut, heureFin);

        System.out.println("Préférence ajoutée avec succès !");
    }

    /**
     * Affiche les préférences horaires actuelles du résident.
     *
     * Cette méthode récupère les préférences associées au résident connecté
     * et les affiche dans un format lisible. Si aucune préférence n'est trouvée,
     * un message approprié est affiché.
     *
     * @param resident L'objet {@link Resident} correspondant à l'utilisateur connecté.
     */
    public static void afficherPreferences(Resident resident) {
        System.out.println("\nVos préférences actuelles :");
        Preference preference = new Preference(resident.getAdresseCourriel());

        if (preference.getPreferences().isEmpty()) {
            System.out.println("Aucune préférence trouvée.");
        } else {
            int index = 1;
            for (Preference.PreferenceEntry entry : preference.getPreferences()) {
                System.out.println(index + ". Jour : " + entry.getJour() +
                        ", Heure de début : " + entry.getHeureDebut() +
                        ", Heure de fin : " + entry.getHeureFin());
                index++;
            }
        }
    }

    /**
     * Supprime une préférence horaire pour un résident.
     *
     * Cette méthode affiche les préférences actuelles du résident, puis
     * demande à l'utilisateur de sélectionner la préférence à supprimer
     * par son numéro. La préférence sélectionnée est ensuite supprimée
     * et les changements sont sauvegardés.
     *
     * @param resident L'objet {@link Resident} correspondant à l'utilisateur connecté.
     */
    public static void supprimerPreference(Resident resident) {
        Scanner scanner = new Scanner(System.in);
        Preference preference = new Preference(resident.getAdresseCourriel());

        if (preference.getPreferences().isEmpty()) {
            System.out.println("Aucune préférence à supprimer.");
            return;
        }

        afficherPreferences(resident); // Affiche les préférences avant suppression

        System.out.println("\nEntrez le numéro de la préférence que vous souhaitez supprimer :");
        int choix = scanner.nextInt();
        scanner.nextLine(); // Consomme la nouvelle ligne

        if (choix < 1 || choix > preference.getPreferences().size()) {
            System.out.println("Numéro invalide. Aucune préférence supprimée.");
        } else {
            preference.supprimerPreference(choix - 1); // Supprimer la préférence par index
            System.out.println("Préférence supprimée avec succès !");
        }
    }
}
