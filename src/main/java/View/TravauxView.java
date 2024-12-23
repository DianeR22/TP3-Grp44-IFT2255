package View;

import Controller.ConnexionController;
import Controller.ResidentController;
import Controller.TravauxController;
import Model.ServiceAPI;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static Controller.TravauxController.recupererTravaux;

/**
 * La classe TravauxView gère l'affichage du menu de recherche des travaux
 * et permet à l'utilisateur résident de filtrer les travaux.
 */
public class TravauxView {


    /**
     * Affiche le menu principal pour la recherche des travaux.
     * Permet à l'utilisateur de choisir entre plusieurs options de filtrage.
     *
     * @param scanner Scanner utilisé pour récupérer l'entrée de l'utilisateur.
     */
    public static void afficherMenu(Scanner scanner) {

        while (true) {
            // Menu principal pour rechercher les travaux pour le résident
            System.out.println("---- Menu de la recherche des travaux ----");
            System.out.println("1. Filtrer par quartier");
            System.out.println("2. Filtrer par type de travaux");
            System.out.println("3. Afficher tous les travaux");
            System.out.println("4. Quitter");
            System.out.println("Entrez une option entre 1 et 4.");

            String choix = scanner.nextLine();

            switch (choix) {
                case "1":
                    afficherTravauxParQuartier(scanner);
                    retour(scanner);
                    break;
                case "2":
                    afficherTravauxParType(scanner);
                    retour(scanner);
                    break;
                case "3":
                    afficherTousLesTravaux(scanner);
                    retour(scanner);
                    break;
                case "4":
                    System.out.println("Au revoir !");
                    ResidentController.afficherMenuResident();
                    break;
                default:
                    System.out.println("Option invalide.");
            }
        }
    }

    /**
     * Affiche les travaux filtrés par quartier.
     *
     * @param scanner Scanner utilisé pour récupérer l'entrée de l'utilisateur.
     */
    public static void afficherTravauxParQuartier(Scanner scanner){
        System.out.println("Veuillez entrer le quartier souhaité: ");
        String filtre = scanner.nextLine();
        JSONArray travauxFiltres = recupererTravaux(1, filtre);
        afficherTravaux(travauxFiltres);
    }

    /**
     * Affiche les travaux filtrés par type de travaux. L'utilisateur choisit un type
     * et ce type est validé. La recherche se fait ensuite.
     *
     * @param scanner Scanner utilisé pour récupérer l'entrée de l'utilisateur.
     */
    public static void afficherTravauxParType(Scanner scanner){
        // Afficher les types de travaux acceptés à l'utilisateur
        afficherTypesDeTravaux();
        System.out.println("Veuillez entrer le type souhaité:");
        String filtre = scanner.nextLine();
        // Vérification si le type existe dans le mapping
        String reasonCategory = reasonCategoryMap.get(filtre);

        if (reasonCategory != null) {
            JSONArray travauxFiltres = TravauxController.recupererTravaux(2, reasonCategory);  // Appel à la fonction qui utilise l'API
            afficherTravaux(travauxFiltres);
        } else {
            System.out.println("Le type de travaux n'est pas valide ou il n'est pas répertorié.");
        }
    }

    /**
     * Affiche tous les travaux sans aucun filtrage.
     *
     * @param scanner Scanner utilisé pour récupérer l'entrée de l'utilisateur.
     */
    public static void afficherTousLesTravaux(Scanner scanner) {
        System.out.println("Voici la liste de tous les travaux en cours ou à venir: ");
        JSONArray travauxFiltres = recupererTravaux(0, null);  // 0 pour afficher tous les travaux
        afficherTravaux(travauxFiltres);
    }

    /**
     * Permet à l'utilisateur de revenir en arrière au menu des travaux ou retourner au menu des résidents.
     *
     * @param scanner Scanner utilisé pour récupérer l'entrée de l'utilisateur.
     */
    private static void retour(Scanner scanner){
        System.out.println("Voulez-vous revenir au menu des travaux en cours (1) ou retourner au menu des résidents (2)? Veuillez répondre par 1 ou par 2.");
        String reponse = scanner.nextLine();
        if (reponse.equalsIgnoreCase("1")) {
            //
        } else {
            System.out.println("Retour au menu des résidents!");
            ResidentController.afficherMenuResident();
        }
    }

    /**
     * Affiche les travaux à partir du JSONArray fourni.
     *
     * @param travaux Le JSONArray contenant les travaux à afficher
     */
    public static void afficherTravaux(JSONArray travaux) {
        try {
            if (travaux != null && travaux.length() > 0) {
                for (int i = 0; i < travaux.length(); i++) {
                    JSONObject travail = travaux.getJSONObject(i);
                    String quartier = travail.getString("boroughid");
                    String type = travail.getString("reason_category");

                    System.out.println("\n***********************************");
                    System.out.println("Travail ID : " + travail.getInt("_id"));
                    System.out.println("Quartier : " + quartier);
                    System.out.println("Statut actuel : " + travail.getString("currentstatus"));
                    System.out.println("Motif : " + type);

                    // Utilisation de opt() pour récupérer la valeur en tant que Object et conversion de la valeur en String
                    String organizationName = travail.opt("organizationname") != null ? travail.opt("organizationname").toString() : "";
                    System.out.println("Nom de l'intervenant : " + organizationName);

                    // Utilisation de opt() pour récupérer la valeur en tant que Object et conversion de la valeur en String
                    String submitterCategory = travail.opt("submittercategory") != null ? travail.opt("submittercategory").toString() : "";
                    System.out.println("Catégorie de l'intervenant: " + submitterCategory);
                    System.out.println("*************************************");
                }
            } else {
                System.out.println("Aucun travail à afficher.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Affiche les différents types de travaux parmi lesquels l'utilisateur peut choisir.
     */
    public static void afficherTypesDeTravaux() {
        System.out.println("Choisissez le type de travaux:");
        System.out.println("1. Travaux routiers");
        System.out.println("2. Travaux de gaz ou électricité");
        System.out.println("3. Construction ou rénovation");
        System.out.println("4. Entretien paysager");
        System.out.println("5. Travaux liés aux transports en commun");
        System.out.println("6. Travaux de signalisation et éclairage");
        System.out.println("7. Travaux souterrains");
        System.out.println("8. Travaux résidentiels");
        System.out.println("9. Entretien urbain");
        System.out.println("10. Entretien des réseaux de télécommunication");
        System.out.println("11. Autres");
    }



    // Mapping des catégories de travaux
    private static final Map<String, String> reasonCategoryMap = new HashMap<>() {{
        put("Travaux de construction", "Construction/rénovation sans excavation");
        put("Travaux résidentiels", "Trottoirs - Construction");
        put("Travaux souterrains", "Construction/rénovation avec excavation");
        put("Travaux de gaz ou électricité", "S-3 Infrastructure souterraine majeure - Réseaux de gaz");
        put("Travaux routiers", "Réseaux routier - Réfection et travaux corrélatifs");
        put("Travaux de signalisation et éclairage", "Feux de signalisation - Ajout/réparation");
        put("Entretien urbain", "Égouts et aqueducs - Inspection et nettoyage");
        put("Autres travaux", "Autre");
    }};
}

