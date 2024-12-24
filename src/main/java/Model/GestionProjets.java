package Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de gestion des projets.
 *
 * Cette classe permet de sauvegarder, charger et filtrer les projets depuis
 * un fichier JSON, ainsi que d'obtenir une liste des projets planifiés pour
 * les trois prochains mois.*/
public class GestionProjets {

    private static final String FILENAME = "data/projets.json";

    /**
     * Sauvegarde un projet dans le fichier JSON des projets.
     *
     * Si le fichier contient déjà des projets, le nouveau projet est ajouté à la liste existante.
     *
     * @param projet L'objet {@link Projet} à sauvegarder. Cet objet contient les informations
     *               telles que le titre, la description, le type de travaux, les quartiers
     *               et rues affectés, les codes postaux, les dates de début et de fin,
     *               les horaires, ainsi que le statut.*/
    public static void sauvegarderProjet(Projet projet) {
        JSONArray projetsArray = new JSONArray();

        try {
            // Read existing projects from the file
            Path filePath = Path.of(FILENAME);
            if (Files.exists(filePath)) {
                String jsonString = Files.readString(filePath);
                projetsArray = new JSONArray(jsonString);
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier projets.json : " + e.getMessage());
        }

        // Convert the project to JSON and add it to the array
        JSONObject projetJson = new JSONObject();
        projetJson.put("titre", projet.getTitre());
        projetJson.put("description", projet.getDescription());
        projetJson.put("typeTravaux", projet.getTypeTravaux());

        // Convert quartiersAffectes and ruesAffectees to JSONArray
        JSONArray quartiersArray = new JSONArray(projet.getQuartiersAffectes());
        JSONArray ruesArray = new JSONArray(projet.getRuesAffectees());
        projetJson.put("quartiersAffectes", quartiersArray);
        projetJson.put("ruesAffectees", ruesArray);
        projetJson.put("codesPostauxAffectes", projet.getCodesPostauxAffectes());
        projetJson.put("dateDebut", projet.getDateDebut().toString());
        projetJson.put("dateFin", projet.getDateFin().toString());
        projetJson.put("heureDebut", projet.getHeureDebut().toString());
        projetJson.put("heureFin", projet.getHeureFin().toString());
        projetJson.put("statut", projet.getStatut());
        projetsArray.put(projetJson);

        // Save the updated projects array back to the file
        try (FileWriter file = new FileWriter(FILENAME)) {
            file.write(projetsArray.toString(4)); // Pretty print JSON
            file.flush();
        } catch (IOException e) {
            System.out.println("Erreur lors de l'écriture dans le fichier projets.json : " + e.getMessage());
        }
    }

    /**
     * Charge les projets depuis un fichier JSON et les convertit en une liste d'objets {@link Projet}.
     *
     * Cette méthode lit les données du fichier JSON spécifié par {@code FILENAME},
     * les parse et les transforme en une liste de projets.
     *
     * @return Une liste d'objets {@link Projet} représentant les projets chargés depuis le fichier JSON.
     *         Retourne une liste vide si le fichier n'existe pas ou en cas d'erreur de lecture.*/
    public static List<Projet> chargerProjets() {
        List<Projet> projets = new ArrayList<>();
        try {
            Path filePath = Path.of(FILENAME);
            if (!Files.exists(filePath)) return projets;

            String jsonString = Files.readString(filePath);
            JSONArray projetsArray = new JSONArray(jsonString);

            for (int i = 0; i < projetsArray.length(); i++) {
                JSONObject projetJson = projetsArray.getJSONObject(i);

                // Convert quartiersAffectes and ruesAffectees from JSONArray to List<String>
                List<String> quartiersAffectes = new ArrayList<>();
                JSONArray quartiersArray = projetJson.getJSONArray("quartiersAffectes");
                for (int j = 0; j < quartiersArray.length(); j++) {
                    quartiersAffectes.add(quartiersArray.getString(j));
                }

                List<String> ruesAffectees = new ArrayList<>();
                JSONArray ruesArray = projetJson.getJSONArray("ruesAffectees");
                for (int j = 0; j < ruesArray.length(); j++) {
                    ruesAffectees.add(ruesArray.getString(j));
                }

                List<String> codesPostaux = new ArrayList<>();
                if (projetJson.has("codesPostauxAffectes")) {
                    JSONArray codesPostauxArray = projetJson.getJSONArray("codesPostauxAffectes");
                    for (int j = 0; j < codesPostauxArray.length(); j++) {
                        codesPostaux.add(codesPostauxArray.getString(j));
                    }
                }

                Projet projet = new Projet(
                        projetJson.getString("titre"),
                        projetJson.getString("description"),
                        projetJson.getString("typeTravaux"),
                        quartiersAffectes,
                        ruesAffectees,
                        codesPostaux,
                        LocalDate.parse(projetJson.getString("dateDebut")),
                        LocalDate.parse(projetJson.getString("dateFin")),
                        LocalTime.parse(projetJson.getString("heureDebut")),
                        LocalTime.parse(projetJson.getString("heureFin"))
                );

                // AJOUT : Lire le champ statut si présent
                if (projetJson.has("statut")) {
                    projet.setStatut(projetJson.getString("statut"));
                }

                projets.add(projet);
            }
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement des projets : " + e.getMessage());
        }
        return projets;
    }

    /**
     * Récupère une liste des projets planifiés pour les trois prochains mois.
     *
     * Cette méthode parcourt les projets chargés depuis le fichier JSON, et filtre
     * ceux dont la date de début est strictement postérieure à aujourd'hui et inférieure
     * ou égale à trois mois à partir d'aujourd'hui.
     *
     * @return Une liste d'objets {@link Projet} représentant les projets à venir dans les trois prochains mois.*/
    public static List<Projet> getProjetsAvenirProchains3Mois() {
        List<Projet> tousLesProjets = chargerProjets();
        List<Projet> projetsAvenir = new ArrayList<>();

        LocalDate aujourdHui = LocalDate.now();
        LocalDate dans3Mois = aujourdHui.plusMonths(3);

        for (Projet p : tousLesProjets) {
            // dateDebut > aujourdHui (strictement futur)
            // ET dateDebut <= dans3Mois
            if (p.getDateDebut().isAfter(aujourdHui) &&
                    (p.getDateDebut().isBefore(dans3Mois) || p.getDateDebut().isEqual(dans3Mois))) {
                projetsAvenir.add(p);
            }
        }
        return projetsAvenir;
    }
}
