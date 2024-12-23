package Model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTypeTravaux() {
        return typeTravaux;
    }

    public void setTypeTravaux(String typeTravaux) {
        this.typeTravaux = typeTravaux;
    }

    public List<String> getQuartiersAffectes() {
        return quartiersAffectes;
    }

    public void setQuartiersAffectes(List<String> quartiersAffectes) {
        this.quartiersAffectes = quartiersAffectes;
    }

    public List<String> getRuesAffectees() {
        return ruesAffectees;
    }

    public void setRuesAffectees(List<String> ruesAffectees) {
        this.ruesAffectees = ruesAffectees;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    // Getters et setters pour codesPostauxAffectes
    public List<String> getCodesPostauxAffectes() {
        return codesPostauxAffectes;
    }

    public void setCodesPostauxAffectes(List<String> codesPostauxAffectes) {
        this.codesPostauxAffectes = codesPostauxAffectes;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public LocalTime getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(LocalTime heureDebut) {
        this.heureDebut = heureDebut;
    }

    public LocalTime getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(LocalTime heureFin) {
        this.heureFin = heureFin;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}