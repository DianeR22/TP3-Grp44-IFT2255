package Controller;

import Model.Resident;
import Model.Preference;
import View.ResidentView;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ResidentController {

    private static final String[] JOURS_DE_LA_SEMAINE = {
            "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"
    };

    public static void afficherMenuResident() {
        ResidentView.afficherMenuResident();
    }

    public static void ajouterPreference(Resident resident) {
        Scanner scanner = new Scanner(System.in);

        // Étape 1 : Récupérer le jour de la semaine
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

        // Étape 2 : Récupérer l'heure de début
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

        // Étape 3 : Récupérer l'heure de fin
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

        // Étape 4 : Ajouter la préférence au modèle et sauvegarder
        Preference preference = new Preference(resident.getAdresseCourriel());
        preference.ajouterPreference(jour, heureDebut, heureFin);

        System.out.println("Préférence ajoutée avec succès !");
    }

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
