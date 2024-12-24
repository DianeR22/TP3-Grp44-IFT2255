package View;

import Controller.ResidentController;
import Model.Resident;

import java.util.Scanner;

/**
 * La classe PreferenceView affiche un menu pour gérer les préférences
 * horaires d'un résident.
 */
public class PreferenceView {

    /**
     * Affiche le sous-menu pour gérer les préférences.
     *
     * @param resident Le résident connecté.
     */
    public static void afficherMenuPreferences(Resident resident) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Menu Préférences ---");
            System.out.println("1. Ajouter une préférence");
            System.out.println("2. Supprimer une préférence");
            System.out.println("3. Afficher vos préférences");
            System.out.println("4. Retour au menu principal");

            System.out.print("Veuillez choisir une option : ");
            String choix = scanner.nextLine();

            switch (choix) {
                case "1":
                    System.out.println("Ajout d'une nouvelle préférence !");
                    ResidentController.ajouterPreference(resident);
                    break;
                case "2":
                    System.out.println("Suppression d'une préférence !");
                    ResidentController.supprimerPreference(resident);
                    break;
                case "3":
                    System.out.println("Affichage de vos préférences !");
                    ResidentController.afficherPreferences(resident);
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Option invalide. Veuillez essayer à nouveau.");
            }
        }
    }
}
