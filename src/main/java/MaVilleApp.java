import Controller.ConnexionController;
import Model.GestionCandidatures;
import Model.GestionIntervenants;
import Model.GestionRequete;
import Model.GestionResidents;
import View.RequeteView;

public class MaVilleApp {
    public static void main(String[] args) {


        // Charger les requêtes, les intervenants et les résidents initialisés
        GestionRequete.chargeRequetes();
        GestionResidents.chargeResidents();
        GestionIntervenants.chargeIntervenants();

        // Aucune candidature pour le moment
        GestionCandidatures.chargeCandidatures();

        // Afficher le menu et obtenir le input de l'utilisateur nécessaire
        while (true) {
            ConnexionController.afficherMenuPrincipal();
        }
    }
}