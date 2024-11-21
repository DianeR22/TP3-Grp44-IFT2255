import Controller.ConnexionController;
import Model.GestionIntervenants;
import Model.GestionRequete;
import Model.GestionResidents;

public class MaVilleApp {
    public static void main(String[] args) {

        // Initialiser les requêtes
        GestionRequete.initialiserRequetes();
        // Initiliser les residents
        GestionResidents.initialiserResidents();
        // Initialiser les intervenants
        GestionIntervenants.initialiserIntervenants();


        // Afficher le menu et obtenir le input de l'utilisateur nécessaires
        while (true) {
            ConnexionController.afficherMenuPrincipal();


        }



}}