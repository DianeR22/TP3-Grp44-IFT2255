import java.util.Scanner;

public class MaVilleApp {
    public static void main(String[] args) {
        // Initialisation d'un scanner afin d'obtenir l'input de l'utilisateur
        Scanner scanner = new Scanner(System.in);

        while(true) {
            // Affichage du menu principal à l'utilisateur
            System.out.println("Bienvenue sur l'application MaVille !");
            System.out.println("\nMenu principal:");
            System.out.println("1. Se connecter en tant que résident");
            System.out.println("2. Se connecter en tant qu'intervenant");
            System.out.println("3. S'inscrire sur l'application MaVille");
            System.out.println("4. Quitter l`application MaVille");
            System.out.print("Choisissez une option (1, 2, 3 ou 4) : ");
            String option = scanner.nextLine();

            // Création d'une instance de résident avec le constructeur sans argument
            Resident resident = new Resident();
            // Création d'une instance d'intervenanat avec le constructeur sans argument
            Intervenant intervenant = new Intervenant();

            // Utilisation d'un bloc switch pour appeler les fonctions appropriées selon l'option
            // choisie par l'utilisateur
            switch (option) {
                case "1":
                    resident.connexion();
                    break;
                case "2":
                    intervenant.connexion();
                    break;
                case "3":
                    String input1 = "";
                    System.out.println("Souhaitez-vous vous inscrire en tant que résident ou intervenant ? (Entrez R ou I)");
                    while (true){
                        input1 = scanner.nextLine();
                        if ((input1).equalsIgnoreCase("r")) {
                            resident.inscription();
                            break;
                        } else if (input1.equalsIgnoreCase("i")) {
                            intervenant.inscription();
                            break;
                        } else {
                            System.out.println("Option invalide. Veuillez réessayer en entrant l'option R ou I.");}
                    }
                    break;
                case "4":
                    System.out.println("Au revoir !");
                    scanner.close();
                    System.exit(0); // Fin de l'exécution du programme
                    break;
                default:
                    System.out.println("Option invalide. Veuillez entrer une option entre 1 et 4.");
                    break;
            }
        }
    }
}



