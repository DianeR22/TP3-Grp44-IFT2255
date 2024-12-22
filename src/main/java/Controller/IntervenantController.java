package Controller;

import Model.GestionCandidatures;
import Model.GestionIntervenants;
import Model.Intervenant;
import View.IntervenantView;
import View.RequeteView;

/**
 * Contrôleur pour la gestion des intervenants. Permet d'afficher les menus associés
 * et d'interagir avec les candidatures des intervenants.
 */
public class IntervenantController {

    /**
     * Affiche le menu principal de l'intervenant.
     */
    public static void afficherMenuIntervenant(){
        IntervenantView.afficherMenuIntervenant();
    }

    /**
     * Affiche le menu des requêtes de l'intervenant.
     */
    public static void afficherMenuRequete(){
        RequeteView.afficherMenuRequete();
    }

    /**
     * Soumet une candidature via la méthode soumettreCandidature du modèle GestionIntervenants.
     */
    public static void soumettreCandidature(){
        GestionIntervenants.soumettreCandidature();
    }

    /**
     * Supprime une candidature via la méthode supprimerCandidature du modèle GestionIntervenants.
     */
    public static void supprimerCandidature(){
        GestionIntervenants.supprimerCandidature();
    }

    /**
     * Suit une candidature spécifique via la méthode suiviCandidature du modèle GestionCandidatures.
     *
     * @param intervenant L'intervenant dont on veut permettre de suivre la candidature.
     */
    public static void suivreCandidature(Intervenant intervenant){
        GestionCandidatures.suiviCandidature(intervenant);
    }

}