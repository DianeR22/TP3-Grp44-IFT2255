import Controller.ConnexionController;
import Model.*;

/**
 * Classe principale de l'application MaVille.
 * Cette classe initialise les données nécessaires au démarrage de l'application
 * et affiche le menu principal à l'utilisateur.
 */
public class MaVilleApp {
    /**
     * Méthode principale qui démarre l'application MaVille.
     * Elle charge les données initiales (requêtes, intervenants, résidents, candidatures)
     * et affiche le menu principal.
     *
     * @param args Les arguments de ligne de commande (pas utilisés ici).
     */
    public static void main(String[] args) {

        // Charger les requêtes, les intervenants et les résidents initialisés
        GestionRequete.chargeRequetes();
        GestionResidents.chargeResidents();
        GestionIntervenants.chargeIntervenants();
        GestionCandidatures.chargeCandidatures();


        // Récupérer les types de travaux appelés reason_category dans l'API des travaux
        // et les placer dans un json pour le mapping
        GestionTravaux.sauvegarderCategoriesDansFichier();


        // Afficher le menu et obtenir le input de l'utilisateur nécessaire
        while (true) {
            ConnexionController.afficherMenuPrincipal();
        }
    }
}