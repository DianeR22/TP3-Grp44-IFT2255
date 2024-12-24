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

/**
 * Gère les préférences horaires des résidents.
 *
 * Cette classe permet de charger, sauvegarder, ajouter, et supprimer des
 * préférences horaires associées à une adresse courriel spécifique.
 * Les préférences sont stockées dans un fichier JSON.
 */
public class Preference {
    private String email; // Email du résident
    private List<PreferenceEntry> preferences; // Liste des préférences

    /**
     * Initialise une instance de préférences pour un résident donné.
     *
     * Le constructeur charge automatiquement les préférences existantes
     * associées à l'adresse courriel fournie.
     *
     * @param email L'adresse courriel du résident.
     */
    public Preference(String email) {
        this.email = email;
        this.preferences = new ArrayList<>();
        loadPreferences(); // Charger les préférences existantes pour cet email
    }

    public String getEmail() {
        return email;
    }

    /**
     * Retourne l'adresse courriel associée aux préférences.
     *
     * @return L'adresse courriel sous forme de chaîne.
     */
    public List<PreferenceEntry> getPreferences() {
        return preferences;
    }

    /**
     * Ajoute une nouvelle préférence horaire pour le résident.
     *
     * Cette méthode ajoute une préférence composée d'un jour,
     * d'une heure de début et d'une heure de fin à la liste,
     * et sauvegarde les préférences mises à jour dans le fichier JSON.
     *
     * @param jour        Le jour de la semaine pour la préférence (ex. "Lundi").
     * @param heureDebut  L'heure de début de la préférence.
     * @param heureFin    L'heure de fin de la préférence.
     */
    public void ajouterPreference(String jour, LocalTime heureDebut, LocalTime heureFin) {
        PreferenceEntry preferenceEntry = new PreferenceEntry(jour, heureDebut, heureFin);
        preferences.add(preferenceEntry);
        savePreferences(); // Sauvegarder les préférences après ajout
    }

    /**
     * Charge les préférences horaires du résident depuis un fichier JSON.
     *
     * Cette méthode lit le fichier `preferences.json`, filtre les préférences
     * associées à l'adresse courriel du résident, et les charge dans l'attribut
     * {@code preferences}.
     */
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

    /**
     * Sauvegarde les préférences horaires du résident dans un fichier JSON.
     *
     * Cette méthode met à jour les préférences existantes dans le fichier
     * ou ajoute une nouvelle entrée si l'adresse courriel n'existe pas encore.
     */
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

    /**
     * Supprime une préférence horaire à un index donné.
     *
     * Cette méthode retire une préférence de la liste en fonction de l'index
     * fourni, et sauvegarde les changements dans le fichier JSON.
     *
     * @param index L'index de la préférence à supprimer (0 pour la première entrée).
     */
    public void supprimerPreference(int index) {
        if (index >= 0 && index < preferences.size()) {
            preferences.remove(index); // Supprime l'entrée à l'index donné
            savePreferences(); // Sauvegarde les changements dans le fichier JSON
        } else {
            System.out.println("Index invalide. Aucune préférence supprimée.");
        }
    }

    /**
     * Représente une entrée de préférence horaire.
     *
     * Chaque préférence est définie par un jour, une heure de début,
     * et une heure de fin.
     */
    // Inner class to represent a preference entry
    public static class PreferenceEntry {
        private String jour;
        private LocalTime heureDebut;
        private LocalTime heureFin;

        /**
         * Initialise une nouvelle entrée de préférence horaire.
         *
         * @param jour       Le jour de la semaine.
         * @param heureDebut L'heure de début.
         * @param heureFin   L'heure de fin.
         */
        public PreferenceEntry(String jour, LocalTime heureDebut, LocalTime heureFin) {
            this.jour = jour;
            this.heureDebut = heureDebut;
            this.heureFin = heureFin;
        }

        /**
         * Retourne le jour de la préférence.
         *
         * @return Le jour sous forme de chaîne.
         */
        public String getJour() {
            return jour;
        }

        /**
         * Retourne l'heure de début de la préférence.
         *
         * @return L'heure de début sous forme d'objet {@link LocalTime}.
         */
        public LocalTime getHeureDebut() {
            return heureDebut;
        }

        /**
         * Retourne l'heure de fin de la préférence.
         *
         * @return L'heure de fin sous forme d'objet {@link LocalTime}.
         */
        public LocalTime getHeureFin() {
            return heureFin;
        }
    }
}