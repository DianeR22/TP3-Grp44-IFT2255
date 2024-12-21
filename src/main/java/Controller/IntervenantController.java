package Controller;

import Model.GestionIntervenants;
import Model.GestionRequete;
import Model.Intervenant;
import View.IntervenantView;
import View.RequeteView;

public class IntervenantController {

    // Faire appel à la vie intervenant pour afficher le menu de l'intervenant
    public static void afficherMenuIntervenant(){
        IntervenantView.afficherMenuIntervenant();
    }

    // Afficher le menu des requêtes de l'intervenat
    public static void afficherMenuRequete(){
        RequeteView.afficherMenuRequete();
    }

    // Appel de la méthode soumettreCandidature dans le modèle de GestionIntervenants
    public static void soumettreCandidature(){
    GestionIntervenants.soumettreCandidature();
}

    // Appel de la méthode soumettreCandidature dans le modèle de GestionIntervenants
    public static void supprimerCandidature(){
        GestionIntervenants.supprimerCandidature();
    }

}