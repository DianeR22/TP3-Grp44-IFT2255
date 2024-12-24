package View;

import Controller.ResidentController;
import Controller.TravauxController;
import Model.GestionProjets;
import Model.Projet;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static Controller.TravauxController.recupererTravaux;

/**
 * La classe TravauxView gère l'affichage du menu de recherche des travaux
 * et permet à l'utilisateur résident de filtrer les travaux.
 */
public class TravauxView {

    // Mapping des catégories de travaux (API) -> exemple
    private static final Map<String, String> reasonCategoryMap = Map.ofEntries(
            Map.entry("Travaux de construction", "Construction/rénovation sans excavation"),
            Map.entry("Travaux résidentiels", "Trottoirs - Construction"),
            Map.entry("Travaux souterrains", "Construction/rénovation avec excavation"),
            Map.entry("Travaux de gaz ou électricité", "S-3 Infrastructure souterraine majeure - Réseaux de gaz"),
            Map.entry("Travaux routiers", "Réseaux routier - Réfection et travaux corrélatifs"),
            Map.entry("Travaux de signalisation et éclairage", "Feux de signalisation - Ajout/réparation"),
            Map.entry("Entretien urbain", "Égouts et aqueducs - Inspection et nettoyage"),
            Map.entry("Autres travaux", "Autre")
            // Ajoutez ici d'autres mappings si nécessaire
    );

    /**
     * Affiche le menu principal pour la recherche des travaux.
     * Permet à l'utilisateur de choisir entre plusieurs options de filtrage.
     *
     * @param scanner Scanner utilisé pour récupérer l'entrée de l'utilisateur.
     */
    public static void afficherMenu(Scanner scanner) {

        while (true) {
            // Menu principal pour rechercher les travaux pour le résident
            System.out.println("---- Menu de la recherche/consultation des travaux en cours ----");
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
                    afficherTousLesTravaux();
                    retour(scanner);
                    break;
                case "4":
                    System.out.println("Au revoir !");
                    ResidentController.afficherMenuResident();
                    return;
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
        String filtreQuartier = scanner.nextLine();

        // 1) Récupérer et filtrer les travaux de l'API
        JSONArray travauxFiltresAPI = recupererTravaux(1, filtreQuartier);

        // 2) Récupérer et filtrer les projets locaux
        List<Projet> projetsFiltres = filtrerProjetsParQuartier(filtreQuartier);

        // 3) Afficher l'ensemble (API + Projets)
        System.out.println("\n===== TRAVAUX DE L'API FILTRÉS PAR QUARTIER =====");
        afficherTravauxAPI(travauxFiltresAPI);

        System.out.println("\n===== PROJETS LOCAUX FILTRÉS PAR QUARTIER =====");
        afficherProjetsLocaux(projetsFiltres);
    }

    /**
     * Affiche les travaux filtrés par type de travaux.
     *
     * @param scanner Scanner utilisé pour récupérer l'entrée de l'utilisateur.
     */
    public static void afficherTravauxParType(Scanner scanner){
        // Afficher les types de travaux acceptés à l'utilisateur
        afficherTypesDeTravaux();
        System.out.println("Veuillez entrer le type souhaité (par ex. 'Travaux routiers'): ");
        String choixTypeUtilisateur = scanner.nextLine();

        // Vérification si ce type existe dans le mapping
        String reasonCategory = reasonCategoryMap.get(choixTypeUtilisateur);

        if (reasonCategory != null) {
            // 1) Filtrer les travaux API
            JSONArray travauxFiltresAPI = TravauxController.recupererTravaux(2, reasonCategory);

            // 2) Filtrer les projets locaux (on suppose que typeTravaux d’un projet
            // correspond à la clé du mapping, ex. "Travaux routiers" ou "Travaux de construction", etc.)
            List<Projet> projetsFiltres = filtrerProjetsParType(choixTypeUtilisateur);

            // 3) Affichage
            System.out.println("\n===== TRAVAUX DE L'API FILTRÉS PAR TYPE =====");
            afficherTravauxAPI(travauxFiltresAPI);

            System.out.println("\n===== PROJETS LOCAUX FILTRÉS PAR TYPE =====");
            afficherProjetsLocaux(projetsFiltres);

        } else {
            System.out.println("Le type de travaux n'est pas valide ou n'est pas répertorié dans le mapping.");
        }
    }

    /**
     * Affiche tous les travaux (API + projets prévus/en cours).
     */
    public static void afficherTousLesTravaux() {
        // 1) Récupération de tous les travaux de l'API
        JSONArray travauxAPI = recupererTravaux(0, null);  // 0 pour tous

        // 2) Récupération de tous les projets locaux dont le statut est 'Prévu' ou 'En cours'
        List<Projet> projetsLocaux = filtrerProjetsSansFiltre();

        // 3) Affichage
        System.out.println("\n===== TOUS LES TRAVAUX DE L'API =====");
        afficherTravauxAPI(travauxAPI);

        System.out.println("\n===== TOUS LES PROJETS LOCAUX (PRÉVU/EN COURS) =====");
        afficherProjetsLocaux(projetsLocaux);
    }

    /**
     * Permet à l'utilisateur de revenir en arrière au menu des travaux ou retourner au menu des résidents.
     *
     * @param scanner Scanner utilisé pour récupérer l'entrée de l'utilisateur.
     */
    private static void retour(Scanner scanner){
        System.out.println("\nVoulez-vous revenir au menu des travaux (1) ou retourner au menu des résidents (2)?");
        String reponse = scanner.nextLine();
        if (reponse.equalsIgnoreCase("1")) {
            // ne rien faire, on laissera la boucle principale ré-afficher le menu
        } else {
            System.out.println("Retour au menu des résidents!");
            ResidentController.afficherMenuResident();
        }
    }

    /* *******************************************************************************************
     * Méthodes d'affichage : Travaux issus de l'API
     * ******************************************************************************************* */

    /**
     * Affiche les travaux de l'API à partir du JSONArray fourni.
     *
     * @param travaux Le JSONArray contenant les travaux à afficher
     */
    public static void afficherTravauxAPI(JSONArray travaux) {
        if (travaux == null || travaux.length() == 0) {
            System.out.println("Aucun travail de l'API à afficher.");
            return;
        }

        try {
            for (int i = 0; i < travaux.length(); i++) {
                JSONObject travail = travaux.getJSONObject(i);
                String quartier = travail.optString("boroughid", "");
                String type = travail.optString("reason_category", "");
                String currentStatus = travail.optString("currentstatus", "");
                String organizationName = travail.optString("organizationname", "");
                String submitterCategory = travail.optString("submittercategory", "");

                System.out.println("\n***********************************");
                System.out.println("Travail ID : " + travail.optInt("_id", -1));
                System.out.println("Quartier : " + quartier);
                System.out.println("Statut actuel : " + currentStatus);
                System.out.println("Motif (reason_category) : " + type);
                System.out.println("Nom de l'intervenant : " + organizationName);
                System.out.println("Catégorie de l'intervenant: " + submitterCategory);
                System.out.println("*************************************");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Affiche une liste de projets locaux.
     *
     * @param projets liste de projets
     */
    public static void afficherProjetsLocaux(List<Projet> projets) {
        if (projets == null || projets.isEmpty()) {
            System.out.println("Aucun projet local à afficher.");
            return;
        }

        for (Projet projet : projets) {
            System.out.println("\n================================");
            System.out.println("Titre : " + projet.getTitre());
            System.out.println("Description : " + projet.getDescription());
            System.out.println("Type de travaux : " + projet.getTypeTravaux());
            System.out.println("Quartiers affectés : " + projet.getQuartiersAffectes());
            System.out.println("Rues affectées : " + projet.getRuesAffectees());
            System.out.println("Codes postaux : " + projet.getCodesPostauxAffectes());
            System.out.println("Date début : " + projet.getDateDebut());
            System.out.println("Date fin : " + projet.getDateFin());
            System.out.println("Heure début : " + projet.getHeureDebut());
            System.out.println("Heure fin : " + projet.getHeureFin());
            System.out.println("Statut : " + projet.getStatut());
            System.out.println("================================");
        }
    }

    /**
     * Récupère tous les projets puis filtre sur :
     * - Statut "Prévu" ou "En cours"
     */
    private static List<Projet> filtrerProjetsSansFiltre() {
        List<Projet> tousLesProjets = GestionProjets.chargerProjets();
        List<Projet> resultat = new ArrayList<>();
        for (Projet p : tousLesProjets) {
            if (p.getStatut() != null &&
                    (p.getStatut().equalsIgnoreCase("Prévu")
                            || p.getStatut().equalsIgnoreCase("En cours"))) {
                resultat.add(p);
            }
        }
        return resultat;
    }

    /**
     * Filtre les projets par quartier (et statut "Prévu" ou "En cours").
     */
    private static List<Projet> filtrerProjetsParQuartier(String quartierRecherche) {
        List<Projet> tousLesProjets = GestionProjets.chargerProjets();
        List<Projet> resultat = new ArrayList<>();
        for (Projet p : tousLesProjets) {
            if (p.getStatut() != null &&
                    (p.getStatut().equalsIgnoreCase("Prévu")
                            || p.getStatut().equalsIgnoreCase("En cours"))) {

                // Vérifier si le quartier figure dans la liste des quartiersAffectes
                if (p.getQuartiersAffectes() != null && !p.getQuartiersAffectes().isEmpty()) {
                    for (String q : p.getQuartiersAffectes()) {
                        if (q.equalsIgnoreCase(quartierRecherche)) {
                            resultat.add(p);
                            break; // on arrête dès qu'on trouve un match
                        }
                    }
                }
            }
        }
        return resultat;
    }

    /**
     * Filtre les projets par type (et statut "Prévu" ou "En cours").
     */
    private static List<Projet> filtrerProjetsParType(String typeRecherche) {
        List<Projet> tousLesProjets = GestionProjets.chargerProjets();
        List<Projet> resultat = new ArrayList<>();
        for (Projet p : tousLesProjets) {
            if (p.getStatut() != null &&
                    (p.getStatut().equalsIgnoreCase("Prévu")
                            || p.getStatut().equalsIgnoreCase("En cours"))) {

                if (p.getTypeTravaux() != null && p.getTypeTravaux().equalsIgnoreCase(typeRecherche)) {
                    resultat.add(p);
                }
            }
        }
        return resultat;
    }
    /**
     * Affiche la liste des types de travaux disponibles.
     *
     * Cette méthode parcourt et affiche tous les types de travaux définis
     * dans la clé de la map 'reasonCategoryMap'. Ces types servent d'exemples
     * pour aider l'utilisateur à sélectionner un type valide.
     */
    public static void afficherTypesDeTravaux() {
        System.out.println("Exemples de types de travaux disponibles :");
        reasonCategoryMap.keySet().forEach(type -> System.out.println("- " + type));
    }
}