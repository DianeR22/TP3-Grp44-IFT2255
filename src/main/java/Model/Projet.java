package Model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Représente un projet de travaux avec ses détails.
 *
 * Cette classe contient des informations sur un projet, telles que
 * le titre, la description, le type de travaux, les quartiers et rues
 * affectés, les dates et horaires, ainsi que le statut du projet.
 */
public class Projet {
    private String titre;
    private String description;
    private String typeTravaux;
    private List<String> quartiersAffectes; // Corrigé : List<String>
    private List<String> ruesAffectees;
    private List<String> codesPostauxAffectes;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private String statut; // Prévu, En cours, Terminé

    /**
     * Initialise un nouveau projet avec les informations fournies.
     *
     * @param titre               Le titre du projet.
     * @param description         La description du projet.
     * @param typeTravaux         Le type de travaux effectués.
     * @param quartiersAffectes   La liste des quartiers affectés.
     * @param ruesAffectees       La liste des rues affectées.
     * @param codesPostauxAffectes La liste des codes postaux affectés.
     * @param dateDebut           La date de début des travaux.
     * @param dateFin             La date de fin des travaux.
     * @param heureDebut          L'heure de début des travaux chaque jour.
     * @param heureFin            L'heure de fin des travaux chaque jour.
     */
    public Projet(String titre, String description, String typeTravaux, List<String> quartiersAffectes,
                  List<String> ruesAffectees, List<String> codesPostauxAffectes,
                  LocalDate dateDebut, LocalDate dateFin,
                  LocalTime heureDebut, LocalTime heureFin) {
        this.titre = titre;
        this.description = description;
        this.typeTravaux = typeTravaux;
        this.quartiersAffectes = quartiersAffectes;
        this.ruesAffectees = ruesAffectees;
        this.codesPostauxAffectes = codesPostauxAffectes;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.statut = "Prévu"; // Par défaut
    }

    // Getters et setters

    /**
     * Retourne le titre du projet.
     *
     * @return Le titre du projet.
     */
    public String getTitre() {
        return titre;
    }

    /**
     * Modifie le titre du projet.
     *
     * @param titre Le nouveau titre du projet.
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * Retourne la description du projet.
     *
     * @return La description du projet.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Modifie la description du projet.
     *
     * @param description La nouvelle description du projet.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retourne le type de travaux effectués.
     *
     * @return Le type de travaux.
     */
    public String getTypeTravaux() {
        return typeTravaux;
    }

    /**
     * Modifie le type de travaux effectués.
     *
     * @param typeTravaux Le nouveau type de travaux.
     */
    public void setTypeTravaux(String typeTravaux) {
        this.typeTravaux = typeTravaux;
    }

    /**
     * Retourne la liste des quartiers affectés par le projet.
     *
     * @return Une liste de quartiers affectés.
     */
    public List<String> getQuartiersAffectes() {
        return quartiersAffectes;
    }

    /**
     * Modifie la liste des quartiers affectés par le projet.
     *
     * @param quartiersAffectes La nouvelle liste de quartiers affectés.
     */
    public void setQuartiersAffectes(List<String> quartiersAffectes) {
        this.quartiersAffectes = quartiersAffectes;
    }

    /**
     * Retourne la liste des rues affectées par le projet.
     *
     * @return Une liste de rues affectées.
     */
    public List<String> getRuesAffectees() {
        return ruesAffectees;
    }

    /**
     * Modifie la liste des rues affectées par le projet.
     *
     * @param ruesAffectees La nouvelle liste de rues affectées.
     */
    public void setRuesAffectees(List<String> ruesAffectees) {
        this.ruesAffectees = ruesAffectees;
    }

    /**
     * Retourne la liste des codes postaux affectés par le projet.
     *
     * @return Une liste de codes postaux affectés.
     */
    public List<String> getCodesPostauxAffectes() {
        return codesPostauxAffectes;
    }

    /**
     * Modifie la liste des codes postaux affectés par le projet.
     *
     * @param codesPostauxAffectes La nouvelle liste de codes postaux affectés.
     */
    public void setCodesPostauxAffectes(List<String> codesPostauxAffectes) {
        this.codesPostauxAffectes = codesPostauxAffectes;
    }

    /**
     * Retourne la date de début des travaux.
     *
     * @return La date de début.
     */
    public LocalDate getDateDebut() {
        return dateDebut;
    }

    /**
     * Modifie la date de début des travaux.
     *
     * @param dateDebut La nouvelle date de début.
     */
    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    /**
     * Retourne la date de fin des travaux.
     *
     * @return La date de fin.
     */
    public LocalDate getDateFin() {
        return dateFin;
    }

    /**
     * Modifie la date de fin des travaux.
     *
     * @param dateFin La nouvelle date de fin.
     */
    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    /**
     * Retourne l'heure de début des travaux chaque jour.
     *
     * @return L'heure de début.
     */
    public LocalTime getHeureDebut() {
        return heureDebut;
    }

    /**
     * Modifie l'heure de début des travaux chaque jour.
     *
     * @param heureDebut La nouvelle heure de début.
     */
    public void setHeureDebut(LocalTime heureDebut) {
        this.heureDebut = heureDebut;
    }

    /**
     * Retourne l'heure de fin des travaux chaque jour.
     *
     * @return L'heure de fin.
     */
    public LocalTime getHeureFin() {
        return heureFin;
    }

    /**
     * Modifie l'heure de fin des travaux chaque jour.
     *
     * @param heureFin La nouvelle heure de fin.
     */
    public void setHeureFin(LocalTime heureFin) {
        this.heureFin = heureFin;
    }

    /**
     * Retourne le statut du projet.
     *
     * @return Le statut du projet.
     */
    public String getStatut() {
        return statut;
    }

    /**
     * Modifie le statut du projet.
     *
     * @param statut Le nouveau statut du projet.
     */
    public void setStatut(String statut) {
        this.statut = statut;
    }
}