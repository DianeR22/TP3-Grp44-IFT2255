package View;

import Controller.ConnexionController;
import Model.Intervenant;
import Model.Resident;

import java.util.Scanner;

/**
 * La classe {@code ConnexionView} gère l'affichage du menu principal et les interactions de l'utilisateur
 * avec le menu pour se connecter ou s'inscrire sur l'application MaVille.
 */
public class ConnexionView {

    /**
     * Affiche le menu principal à l'utilisateur pour lui permettre de choisir une option.
     */
    public static void afficherMenuPrincipal() {

        // Affichage du message de bienvenue et des options disponibles
        System.out.println("Bienvenue sur l'application MaVille !");
        System.out.println("\nMenu principal:");
        System.out.println("1. Se connecter en tant que résident");
        System.out.println("2. Se connecter en tant qu'intervenant");
        System.out.println("3. S'inscrire sur l'application MaVille");
        System.out.println("4. Quitter l'application MaVille");
        System.out.print("Choisissez une option (1, 2, 3 ou 4) : ");

        // Création d'une instance de résident avec le constructeur sans argument
        Resident resident = new Resident();
        // Création d'une instance d'intervenant avec le constructeur sans argument
        Intervenant intervenant = new Intervenant();

        // Lecture de la saisie de l'utilisateur
        Scanner scanner = new Scanner(System.in);
        // Choix de l'utilisateur
        String choix = scanner.nextLine();

        // Traiter le choix de l'utilisateur en appelant la méthode appropriée dans le contrôleur
        ConnexionController.traiterChoix(choix, resident, intervenant);

    }
}