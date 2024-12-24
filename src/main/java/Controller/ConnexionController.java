package Controller;

import Model.Connexion;
import Model.Intervenant;
import Model.Resident;
import View.ConnexionView;

/**
 * Le contrôleur de la connexion qui gère l'affichage du menu principal
 * et le traitement des choix de l'utilisateur en fonction de son rôle.
 */
public class ConnexionController {

    /**
     * Affiche le menu principal de la connexion.
     */
    public static void afficherMenuPrincipal(){
        ConnexionView.afficherMenuPrincipal();
    }

    /**
     * Traite les choix effectués par l'utilisateur en fonction de son rôle.
     *
     * @param choix Le choix de l'utilisateur sous forme de String.
     * @param resident L'objet résident.
     * @param intervenant L'objet intervenant.
     */
    public static void traiterChoix(String choix, Resident resident, Intervenant intervenant){
        Connexion.traiterChoix(choix, resident, intervenant);
    }
}