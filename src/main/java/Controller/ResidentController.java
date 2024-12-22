package Controller;
import View.ResidentView;

/**
 * Contrôleur pour la gestion des résidents. Permet d'afficher le menu des résidents.
 */
public class ResidentController {

    /**
     * Appelle la méthode afficherMenuResident de la vue Resident pour afficher
     * le menu des résidents.
     */
    public static void afficherMenuResident(){
        ResidentView.afficherMenuResident();
    }
}