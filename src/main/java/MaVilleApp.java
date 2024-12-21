import Controller.ConnexionController;
import Model.GestionCandidatures;
import Model.GestionIntervenants;
import Model.GestionRequete;
import Model.GestionResidents;
import Model.GestionTravaux;


public class MaVilleApp {
    public static void main(String[] args) {

        // Charger les requêtes, les intervenants et les résidents initialisés
        GestionRequete.chargeRequetes();
        GestionResidents.chargeResidents();
        GestionIntervenants.chargeIntervenants();

        // Récupérer les types de travaux appelés reason_categoory dans l'API des travaux
        // et les placer dans un json pour le mapping
        GestionTravaux.sauvegarderCategoriesDansFichier();



        // Afficher le menu et obtenir le input de l'utilisateur nécessaire
        while (true) {
            ConnexionController.afficherMenuPrincipal();
        }
    }
}