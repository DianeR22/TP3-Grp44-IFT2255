package Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Preference {
    private String email; // Email du résident
    private List<PreferenceEntry> preferences; // Liste des préférences

    public Preference(String email) {
        this.email = email;
        this.preferences = new ArrayList<>();
        loadPreferences(); // Charger les préférences existantes pour cet email
    }

    public String getEmail() {
        return email;
    }

    public List<PreferenceEntry> getPreferences() {
        return preferences;
    }

    public void ajouterPreference(String jour, LocalTime heureDebut, LocalTime heureFin) {
        PreferenceEntry preferenceEntry = new PreferenceEntry(jour, heureDebut, heureFin);
        preferences.add(preferenceEntry);
        savePreferences(); // Sauvegarder les préférences après ajout
    }

    private void loadPreferences() {
        try {
            Path filePath = Path.of("data/preferences.json");
            if (!Files.exists(filePath)) return; // Pas de fichier existant

            String jsonString = Files.readString(filePath);
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("email").equals(email)) {
                    JSONArray prefArray = jsonObject.getJSONArray("preferences");
                    for (int j = 0; j < prefArray.length(); j++) {
                        JSONObject prefObject = prefArray.getJSONObject(j);
                        String jour = prefObject.getString("jour");
                        LocalTime heureDebut = LocalTime.parse(prefObject.getString("heureDebut"));
                        LocalTime heureFin = LocalTime.parse(prefObject.getString("heureFin"));
                        preferences.add(new PreferenceEntry(jour, heureDebut, heureFin));
                    }
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement des préférences : " + e.getMessage());
        }
    }

    private void savePreferences() {
        JSONArray jsonArray = new JSONArray();

        try {
            // Lire les préférences existantes si le fichier existe
            Path filePath = Path.of("data/preferences.json");
            if (Files.exists(filePath)) {
                String jsonString = Files.readString(filePath);
                jsonArray = new JSONArray(jsonString);
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier JSON : " + e.getMessage());
        }

        boolean emailExists = false;

        // Mettre à jour ou ajouter les préférences pour l'email actuel
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject.getString("email").equals(email)) {
                emailExists = true;

                // Remplacer les préférences existantes
                JSONArray prefArray = new JSONArray();
                for (PreferenceEntry entry : preferences) {
                    JSONObject prefObject = new JSONObject();
                    prefObject.put("jour", entry.getJour());
                    prefObject.put("heureDebut", entry.getHeureDebut().toString());
                    prefObject.put("heureFin", entry.getHeureFin().toString());
                    prefArray.put(prefObject);
                }
                jsonObject.put("preferences", prefArray);
                break;
            }
        }

        // Si l'email n'existe pas, ajouter une nouvelle entrée
        if (!emailExists) {
            JSONObject newEntry = new JSONObject();
            newEntry.put("email", email);

            JSONArray prefArray = new JSONArray();
            for (PreferenceEntry entry : preferences) {
                JSONObject prefObject = new JSONObject();
                prefObject.put("jour", entry.getJour());
                prefObject.put("heureDebut", entry.getHeureDebut().toString());
                prefObject.put("heureFin", entry.getHeureFin().toString());
                prefArray.put(prefObject);
            }
            newEntry.put("preferences", prefArray);
            jsonArray.put(newEntry);
        }

        // Sauvegarder les préférences mises à jour
        try (FileWriter file = new FileWriter("data/preferences.json")) {
            file.write(jsonArray.toString(4)); // Indentation pour la lisibilité
            file.flush();
        } catch (IOException e) {
            System.out.println("Erreur lors de l'écriture dans le fichier JSON : " + e.getMessage());
        }
    }

    public void supprimerPreference(int index) {
        if (index >= 0 && index < preferences.size()) {
            preferences.remove(index); // Supprime l'entrée à l'index donné
            savePreferences(); // Sauvegarde les changements dans le fichier JSON
        } else {
            System.out.println("Index invalide. Aucune préférence supprimée.");
        }
    }

    // Inner class to represent a preference entry
    public static class PreferenceEntry {
        private String jour;
        private LocalTime heureDebut;
        private LocalTime heureFin;

        public PreferenceEntry(String jour, LocalTime heureDebut, LocalTime heureFin) {
            this.jour = jour;
            this.heureDebut = heureDebut;
            this.heureFin = heureFin;
        }

        public String getJour() {
            return jour;
        }

        public LocalTime getHeureDebut() {
            return heureDebut;
        }

        public LocalTime getHeureFin() {
            return heureFin;
        }
    }
}