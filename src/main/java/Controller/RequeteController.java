package Controller;

import Model.GestionRequete;
import Model.Requete;
import Model.Resident;
import View.RequeteView;

import java.util.List;

/**
 * Contrôleur pour la gestion des requêtes. Permet d'obtenir les informations
 * des requêtes en demandant ces informatinos dans RequeteView,
 * de soumettre une requête et d'afficher toutes les requêtes.
 */
public class RequeteController {

    /**
     * Appelle la méthode obtenirInformationsRequete de la vue Requete pour
     * collecter les informations à la nouvelle d'une requête.
     */
    public static void obtenirInformationsRequete(){
        RequeteView.obtenirInformationsRequete();
    }

    /**
     * Appelle la méthode soumettreRequete de la classe modèle GestionRequete
     * pour ajouter une nouvelle requête à la liste des requêtes et au json des requêtes.
     *
     * @param titreTravail Le titre du travail.
     * @param description Une description du travail.
     * @param typeTravail Le type de travail demandé.
     * @param dateDebut La date de début du travail.
     */
    public static void soumettreRequete(String titreTravail, String description, String typeTravail, String dateDebut){
        GestionRequete.soumettreRequete(titreTravail, description, typeTravail, dateDebut);
    }

    /**
     * Appelle la méthode afficherRequetes de la vue Requete pour afficher la liste
     * de toutes les requêtes.
     */
    public static void afficherRequetes() {
        RequeteView.afficherRequetes();
    }

    public static List<Requete> filtrerParType(String type) {
        return GestionRequete.filtrerParType(type);

    }

    public static List<Requete> filtrerParQuartier(String quartier) {
        return GestionRequete.filtrerParQuartier(quartier);
    }

    public static List<Requete> filtrerParDate(String dateDebut) {
        return GestionRequete.filtrerParDate(dateDebut);
    }
}

