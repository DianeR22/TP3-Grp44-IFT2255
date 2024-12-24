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

/**
 * La classe TravauxView gère l'affichage du menu de recherche des travaux
 * et permet à l'utilisateur résident de filtrer les travaux.
 */
public class TravauxView {

    // Mapping des catégories de travaux (API) -> exemple
    // Clé = ce que l'utilisateur va taper ("Travaux routiers"),
    // Valeur = ce qu'on envoie à l'API ("Réseaux routier - Réfection et travaux corrélatifs")
    private static final Map<String, String> reasonCategoryMap = Map.ofEntries(
            Map.entry("Travaux de construction", "Construction/rénovation sans excavation"),
            Map.entry("Travaux résidentiels", "Trottoirs - Construction"),
            Map.entry("Travaux souterrains", "Construction/rénovation avec excavation"),
            Map.entry("Travaux de gaz ou électricité", "S-3 Infrastructure souterraine majeure - Réseaux de gaz"),
            Map.entry("Travaux routiers", "Réseaux routier - Réfection et travaux corrélatifs"),
            Map.entry("Travaux de signalisation et éclairage", "Feux de signalisation - Ajout/réparation"),
            Map.entry("Entretien urbain", "Égouts et aqueducs - Inspection et nettoyage"),
            Map.entry("Autres travaux", "Autre")
    );

    /**
     * Affiche le menu principal pour la recherche des travaux.
     * Permet à l'utilisateur de choisir entre plusieurs options de filtrage.
     *
     * @param scanner Scanner utilisé pour récupérer l'entrée de l'utilisateur.
     */
    public static void afficherMenu(Scanner scanner) {

        while (true) {
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
     * Affiche la liste des types de travaux disponibles (clés du mapping).
     * Cette méthode sert de guide pour l'utilisateur.
     */
    public static void afficherTypesDeTravaux() {
        System.out.println("Exemples de types de travaux disponibles :");
        reasonCategoryMap.keySet().forEach(type -> System.out.println("- " + type));
    }

    /**
     * Exemple : filtrer par quartier.
     * On réutilise notre méthode unifiée 'afficherTravauxAvecFiltre' (codeFiltre=1).
     */
    private static void afficherTravauxParQuartier(Scanner scanner) {
        System.out.println("Veuillez entrer le quartier souhaité: ");
        String filtreQuartier = scanner.nextLine();

        // On passe 1 pour dire "filtre par quartier"
        afficherTravauxAvecFiltre(1, filtreQuartier);
    }

    /**
     * Exemple : filtrer par type,
     * avec gestion double entre "type local" et "type API".
     */
    private static void afficherTravauxParType(Scanner scanner) {
        // On affiche d'abord les types de travaux possibles
        afficherTypesDeTravaux();
        System.out.println("Veuillez entrer le type souhaité (par ex. 'Travaux routiers'): ");
        String choixTypeUtilisateur = scanner.nextLine();
        // ex. "Travaux routiers"

        // Vérification si ce type existe dans la map
        String reasonCategory = reasonCategoryMap.get(choixTypeUtilisateur);
        // ex. "Réseaux routier - Réfection et travaux corrélatifs"

        if (reasonCategory == null) {
            System.out.println("Le type de travaux n'est pas valide ou n'est pas répertorié dans le mapping.");
        } else {
            // On affiche l'API filtré par la "reasonCategory"
            // + les projets locaux filtrés par le type "local" (choixTypeUtilisateur)
            afficherTravauxParTypeDoubleFiltre(choixTypeUtilisateur, reasonCategory);
        }
    }

    /**
     * Affiche tous les travaux (API + projets prévus/en cours),
     * donc pas de filtre (codeFiltre=0).
     */
    private static void afficherTousLesTravaux() {
        afficherTravauxAvecFiltre(0, null);
    }

    /**
     * Méthode principale (version codeFiltre unique)
     * qui gère l'affichage des travaux (API + locaux) avec un filtre unique.
     *
     * @param codeFiltre    0 => pas de filtre (tous), 1 => par quartier, 2 => par type
     * @param valeurFiltre  la valeur à filtrer (ex: nom du quartier ou "reason_category" pour l'API).
     *                      Peut être null si codeFiltre=0 (tous).
     */
    private static void afficherTravauxAvecFiltre(int codeFiltre, String valeurFiltre) {
        // 1) Récupérer les travaux de l'API
        JSONArray travauxAPI = TravauxController.recupererTravaux(codeFiltre, valeurFiltre);

        // 2) Récupérer et filtrer les projets locaux
        List<Projet> projetsFiltres = filtrerProjetsLocaux(codeFiltre, valeurFiltre);

        // 3) Afficher
        System.out.println("\n===== TRAVAUX DE L'API =====");
        afficherTravauxAPI(travauxAPI);

        System.out.println("\n===== PROJETS LOCAUX =====");
        afficherProjetsLocaux(projetsFiltres);
    }

    /**
     * Méthode spécialisée pour la recherche par type,
     * quand on a un "typeLocal" différent du "typeAPI".
     *
     * @param typeLocal ex: "Travaux routiers" (ce que vous stockez dans p.getTypeTravaux())
     * @param typeAPI   ex: "Réseaux routier - Réfection et travaux corrélatifs" (ce que l'API attend)
     */
    private static void afficherTravauxParTypeDoubleFiltre(String typeLocal, String typeAPI) {
        // -- (1) Récupérer les travaux de l'API avec la valeur typeAPI
        JSONArray travauxAPI = TravauxController.recupererTravaux(2, typeAPI);

        // -- (2) Récupérer et filtrer les projets locaux avec le typeLocal
        List<Projet> projetsFiltres = filtrerProjetsParTypeLocal(typeLocal);

        // -- (3) Afficher
        System.out.println("\n===== TRAVAUX DE L'API =====");
        afficherTravauxAPI(travauxAPI);

        System.out.println("\n===== PROJETS LOCAUX =====");
        afficherProjetsLocaux(projetsFiltres);
    }

    /* *******************************************************************************************
     *  Méthodes de filtrage de projets
     * ******************************************************************************************* */

    /**
     * Filtre unifié : par quartier ou par type (ou pas de filtre),
     * en fonction de codeFiltre.
     * On ne garde que ceux dont le statut est "Prévu" ou "En cours".
     */
    private static List<Projet> filtrerProjetsLocaux(int codeFiltre, String valeurFiltre) {
        List<Projet> tousLesProjets = GestionProjets.chargerProjets();
        List<Projet> resultat = new ArrayList<>();

        for (Projet p : tousLesProjets) {
            // Vérifier le statut
            if (p.getStatut() != null && (
                    p.getStatut().equalsIgnoreCase("Prévu")
                            || p.getStatut().equalsIgnoreCase("En cours"))) {

                switch (codeFiltre) {
                    case 0: // pas de filtre => on prend tout
                        resultat.add(p);
                        break;

                    case 1: // filtre par quartier
                        if (p.getQuartiersAffectes() != null) {
                            for (String q : p.getQuartiersAffectes()) {
                                if (q.equalsIgnoreCase(valeurFiltre)) {
                                    resultat.add(p);
                                    break; // on arrête dès qu'on trouve un match
                                }
                            }
                        }
                        break;

                    case 2: // filtre par type, on compare direct à valeurFiltre
                        if (p.getTypeTravaux() != null
                                && p.getTypeTravaux().equalsIgnoreCase(valeurFiltre)) {
                            resultat.add(p);
                        }
                        break;

                    default:
                        // Si un nouveau codeFiltre arrive un jour
                        break;
                }
            }
        }
        return resultat;
    }

    /**
     * Filtre uniquement par type local (ex: "Travaux routiers") + statut "Prévu" ou "En cours".
     * On ne compare pas à la longue chaîne (e.g. "Réseaux routier - Réfection et travaux corrélatifs"),
     * donc c'est plus flexible.
     */
    private static List<Projet> filtrerProjetsParTypeLocal(String typeRecherche) {
        List<Projet> tousLesProjets = GestionProjets.chargerProjets();
        List<Projet> resultat = new ArrayList<>();

        for (Projet p : tousLesProjets) {
            if (p.getStatut() != null
                    && (p.getStatut().equalsIgnoreCase("Prévu")
                    || p.getStatut().equalsIgnoreCase("En cours"))) {

                if (p.getTypeTravaux() != null
                        && p.getTypeTravaux().equalsIgnoreCase(typeRecherche)) {
                    resultat.add(p);
                }
            }
        }
        return resultat;
    }

    /* *******************************************************************************************
     *  Méthodes d'affichage : Travaux de l'API + Projets
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

        for (int i = 0; i < travaux.length(); i++) {
            JSONObject travail = travaux.getJSONObject(i);

            int id = travail.optInt("_id", -1);
            String quartier = travail.optString("boroughid", "");
            String type = travail.optString("reason_category", "");
            String currentStatus = travail.optString("currentstatus", "");
            String organizationName = travail.optString("organizationname", "");
            String submitterCategory = travail.optString("submittercategory", "");

            System.out.println("\n***********************************");
            System.out.println("Travail ID : " + id);
            System.out.println("Quartier : " + quartier);
            System.out.println("Statut actuel : " + currentStatus);
            System.out.println("Motif (reason_category) : " + type);
            System.out.println("Nom de l'intervenant : " + organizationName);
            System.out.println("Catégorie de l'intervenant : " + submitterCategory);
            System.out.println("*************************************");
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
     * Permet à l'utilisateur de revenir en arrière au menu des travaux
     * ou de retourner au menu des résidents.
     *
     * @param scanner Scanner utilisé pour récupérer l'entrée de l'utilisateur.
     */
    private static void retour(Scanner scanner) {
        System.out.println("\nVoulez-vous revenir au menu des travaux (1) ou retourner au menu des résidents (2)?");
        String reponse = scanner.nextLine();
        if (reponse.equalsIgnoreCase("2")) {
            System.out.println("Retour au menu des résidents!");
            ResidentController.afficherMenuResident();
        }
        // Si c’est "1" ou autre, on ne fait rien,
        // la boucle du menu principal reprendra son cours.
    }
}