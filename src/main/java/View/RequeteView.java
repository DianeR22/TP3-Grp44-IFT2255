package View;

import Controller.RequeteController;
import Model.GestionRequete;
import Model.Requete;

import java.util.List;
import java.util.Scanner;

public class RequeteView {

    // Méthode qui sert à demander les informations nécessaire à la requête
    // et qui récolte l'information pour soumettre la requête
    public static void obtenirInformationsRequete() {

        // Récupérer le input de l'utilisateur
        Scanner scanner = new Scanner(System.in);

        // Récupérer les informations de la requête
        System.out.println("Entrez le titre du travail: ");
        String titreTravail = scanner.nextLine();

        System.out.println("Entrez une description détaillée du travail : ");
        String description = scanner.nextLine();


        System.out.println("Entrez le type de travail: ");
        String typeTravail = scanner.nextLine();

        System.out.println("Entrez la date de début espérée (format: JJ/MM/AAAA) : ");
        String dateDebut = scanner.nextLine();

        RequeteController.soumettreRequete(titreTravail, description, typeTravail, dateDebut);
    }

    // Méthode servant à afficher la liste de toutes les requêtes avec
    // leurs caractéristiques
    public static void afficherRequetes() {
        List<Requete> listeRequetes =GestionRequete.getListeRequetes();

        if (listeRequetes.isEmpty()) {
            System.out.println("Aucune requête trouvée");
        } else {

            for (Requete requete : listeRequetes) {
                System.out.println("Titre : " + requete.getTitreTravail());
                System.out.println("Type de travail: " + requete.getTypeTravaux());
                System.out.println("Description : " + requete.getDescription());
                System.out.println("Date de début: " + requete.getDebut());
                System.out.println();
            }
        }
    }
}
