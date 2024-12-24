package Model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe GestionResidents permet de gérer les résidents en permettant de les
 * ajouter, les charger, les sauvegarder et de récupérer
 * des résidents depuis un fichier JSON.
 */
public class GestionResidents {

    // Initialisation d'une liste de résidents
    private static List<Resident> listeResidents = new ArrayList<>();

    private static final String FICHIER_RESIDENTS = "data/residents.json";

    private static Resident residentConnecte;

    /**
     * Retourne la liste des résidents.
     *
     * @return La liste des résidents.
     */
    public static List<Resident> getListeResidents() {
        return listeResidents;
    }

    /**
     * Méthode pour ajouter un résident à la liste des résidents.
     *
     * @param resident Le résident à ajouter.
     */
    public static void ajouterResident(Resident resident) {

        listeResidents.add(resident);
        saveResident();
    }

    public static void supprimerResident(int index) {
        int size = listeResidents.size();

        if (index >= 0 && index < size) {
            Resident residentRetire = listeResidents.get(index);
            listeResidents.remove(residentRetire);

            System.out.println("Résident supprimé avec succès!");

            // Sauvegarder les résidents de la liste dans le JSON
            saveResident();
        }
    }

    /**
     * Cette méthode sert à charger les résidents depuis un fichier JSON
     * et à les ajouter à la liste des résidents.
     *
     * Remarque: c'est une méthode distincte de chargerResidentsDepuisFichier()
     * selon votre design. Vous pouvez fusionner les deux si nécessaire.
     */
    public static void chargeResidents() {
        ObjectMapper obj = new ObjectMapper();
        try {
            File file = new File(FICHIER_RESIDENTS);
            if (file.exists()) {
                listeResidents = obj.readValue(
                        file,
                        obj.getTypeFactory().constructCollectionType(List.class, Resident.class)
                );
            }
        } catch(IOException e) {
            System.out.println("Erreur dans le chargement des résidents. " + e.getMessage());
        }
    }

    /**
     * Méthode servant à sauvegarder les résidents dans un fichier JSON.
     */
    public static void saveResident() {
        ObjectMapper obj = new ObjectMapper();

        // Activer l'indentation pour rendre le fichier JSON lisible
        obj.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            obj.writeValue(new File(FICHIER_RESIDENTS), listeResidents);
        } catch (IOException e) {
            System.out.println("Erreur dans la sauvegarde: " + e.getMessage());
        }
    }

    /**
     * Méthode qui retourne la liste des résidents depuis le fichier JSON.
     * Après lecture, on vérifie la validité de l'adresse de chacun.
     * Si c'est invalide, on corrige par "2350 Édouard Monpetit, Édouard Monpetit".
     *
     * @return La liste des résidents (corrigée si nécessaire), ou null si erreur.
     */
    public static List<Resident> chargerResidentsDepuisFichier() {
        File fichier = new File(FICHIER_RESIDENTS);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Lire le fichier JSON et mapper son contenu à une liste d'objets Resident
            List<Resident> residents = objectMapper.readValue(
                    fichier,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Resident.class)
            );

            // Validation des adresses
            for (Resident r : residents) {
                if (!Valider.validerAdresseResident(r.getAdresse())) {
                    System.out.println("Adresse invalide pour " + r.getAdresseCourriel()
                            + " : \"" + r.getAdresse() + "\"");
                    System.out.println("=> Correction appliquée : \"2350 Édouard Monpetit, Édouard Monpetit\"");

                    // On corrige l'adresse par une défault one
                    r.setAdresse("1234 Rue Sainte-Catherine Ouest, Ville-Marie");
                }
            }

            // On réécrit le fichier JSON avec la liste corrigée
            objectMapper.writeValue(fichier, residents);

            return residents;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}