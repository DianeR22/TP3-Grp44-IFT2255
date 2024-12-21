package View;

import Controller.ConnexionController;
import Controller.ResidentController;
import Controller.TravauxController;
import Model.ServiceAPI;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static Controller.TravauxController.recupererTravaux;

public class TravauxView {

    private static final Map<String, String> reasonCategoryMap = new HashMap<>() {{
        put("Travaux de construction", "Construction/rénovation sans excavation");
        put("Travaux résidentiels", "Toiture - Rénovation");
        put("Travaux souterrains", "Forage/excavation exploratoire");
        put("Travaux de gaz ou électricité", "S-3 Infrastructure souterraine majeure - Réseaux de gaz");
        put("Travaux routiers", "Réseaux routier - Réfection et travaux corrélatifs");
        put("Travaux de signalisation et éclairage", "Feux de signalisation - Ajout/réparation");
        put("Entretien urbain", "Égouts et aqueducs - Inspection et nettoyage");
        put("Autres travaux", "Autre");
    }};

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

    // Afficher par quartier
    public static void afficherTravauxParQuartier(Scanner scanner){
        System.out.println("Veuillez entrer le quartier souhaité: ");
        String filtre = scanner.nextLine();
        recupererTravaux(1, filtre);

    }

    // Afficher par type
    public static void afficherTravauxParType(Scanner scanner){
        afficherTypesDeTravaux();
        System.out.println("Veuillez entrer le type souhaité:");
        String filtre = scanner.nextLine();
        // Vérification si le type existe dans le mapping
        String reasonCategory = reasonCategoryMap.get(filtre);

        if (reasonCategory != null) {
            System.out.println("Recherche des travaux dans la catégorie: " + reasonCategory);
            TravauxController.recupererTravaux(2, reasonCategory);  // Appel à la fonction qui utilise l'API
        } else {
            System.out.println("Le type de travaux n'est pas valide ou il n'est pas répertorié.");
        }

    }

    // Aucun filtrage
    public static void afficherTousLesTravaux(Scanner scanner) {
        System.out.println("Voici la liste de tous les travaux en cours ou a venir: ");
        recupererTravaux(0, null);  // 0 pour afficher tous les travaux

    }

    // Permet à l'utilisateur de revenir en arrière au besoin
    private static void retour(Scanner scanner){
        System.out.println("Voulez-vous revenir au menu des travaux en cours (1) ou retourner au menu des résidents (2)?. Veuillez repondre par 1 ou par 2.");
        String reponse = scanner.nextLine();
        if (reponse.equalsIgnoreCase("1")) {
            //
        } else{
            System.out.println("Retour au menu des résidents!");
            ResidentController.afficherMenuResident();

            }
        }



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
    }






