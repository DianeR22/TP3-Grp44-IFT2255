package Controller;

import Model.GestionRequete;
import Model.Requete;
import Model.Resident;
import View.RequeteView;

public class RequeteController {


    // Faire appel à obtenirinformationRequete de la vue de Requete pour
    // collecter les informations nécessaires
    public static void obtenirInformationsRequete(){
        RequeteView.obtenirInformationsRequete();
    }

    // Faire appel à soumettreRequete de la classe modèle Requete pour ajouter
    // la requete à la liste des requêtes
    public static void soumettreRequete(String titreTravail, String description, String typeTravail, String dateDebut){
       GestionRequete.soumettreRequete(titreTravail, description, typeTravail, dateDebut);
    }

    // Faire appel à afficherRequetes de la vue Requete pour afficher la liste des
    // requêtes
    public static void afficherRequetes() {
        RequeteView.afficherRequetes();}
}

