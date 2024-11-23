package View;

import Controller.ConnexionController;
import Model.Intervenant;
import Model.Resident;

import java.util.Scanner;

public class ConnexionView {

    // Affichage du menu principal à l'utilisateur
    public static void afficherMenuPrincipal() {

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

        Scanner scanner = new Scanner(System.in);
        // Choix de l'utilisateur
        String choix = scanner.nextLine();

        // Traiter le choix de l'utilisateur
        ConnexionController.traiterChoix(choix, resident, intervenant);

    }
}
