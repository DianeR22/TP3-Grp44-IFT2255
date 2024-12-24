package Model;

import java.util.Scanner;

/**
 * Classe Connexion permettant de gérer les choix des utilisateurs
 * pour la connexion ou l'inscription dans le système.
 */
public class Connexion {

    /**
     * Traite le choix de l'utilisateur en fonction de l'option choisie.
     * Cette méthode prend en paramètre le choix et des instances initiales
     * d'un résident et d'un intervenant afin de pouvoir appeler les méthodes appropriées.
     *
     * @param choix      L'option sélectionnée par l'utilisateur.
     * @param resident   Instance du résident.
     * @param intervenant Instance de l'intervenant.
     */
    public static void traiterChoix(String choix, Resident resident, Intervenant intervenant) {


        // Utilisation d'un bloc switch pour appeler les fonctions appropriées selon l'option
        // choisie par l'utilisateur
        switch (choix) {
            case "1":
                resident.connexion(resident);
                break;
            case "2":
                intervenant.connexion(intervenant);
                break;
            case "3":
                demanderTypeInscription(resident, intervenant);
                break;
            case "4":
                System.out.println("Au revoir !");
                System.exit(0); // Fin du programme
                break;
            default:
                System.out.println("Option invalide. Veuillez entrer une option entre 1 et 4.");
                break;
        }
    }

    /**
    * Demande à l'utilisateur le type d'inscription dont il est question, soit un intervenant
    * ou un résident.
    *
    * @param resident  Instance du résident.
    * @param intervenant  Instance de l'intervenant.
     */
    public static void demanderTypeInscription(Resident resident, Intervenant intervenant) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Souhaitez-vous vous inscrire en tant que résident ou intervenant ? (Entrez R ou I)");
        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("R")) {
                resident.inscription(resident);
                break;
            } else if (input.equalsIgnoreCase("I")) {
                intervenant.inscription(intervenant);
                break;
            } else {
                System.out.println("Option invalide. Veuillez réessayer en entrant l'option R ou I.");
            }
        }
    }
}