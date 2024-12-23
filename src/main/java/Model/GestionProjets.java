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

public class GestionProjets {

    private static final String FILENAME = "data/projets.json";

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
                projets.add(projet);
            }
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement des projets : " + e.getMessage());
        }

        return projets;
    }
}
