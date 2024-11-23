package Controller;

import Model.Connexion;
import Model.Intervenant;
import Model.Resident;
import View.ConnexionView;
public class ConnexionController {

    // Faire appel à afficherMenuPrincipal pour mettre la vue connexion
    // (ConnexionView) afin d'afficher le menu principal la connexion
    public static void afficherMenuPrincipal(){
        ConnexionView.afficherMenuPrincipal();
    }

    // Faire appel à traiterChoix de la classe modèle Connexion afin de procéder
    // aux traitements des inputs de l'utilisateur en fonction de son rôle
    public static void traiterChoix(String choix, Resident resident, Intervenant intervenant){
        Connexion.traiterChoix(choix, resident, intervenant);
    }
}
