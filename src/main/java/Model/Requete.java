package Model;

public class Requete {

    // Attributs pour une requete de travail
    private String titreTravail;
    private String description;
    private String typeTravaux;
    private String debut;
    private String etat = "Ouverte";

    private Intervenant intervenant;
    private Resident resident;

    // Constructeur de Requete
    public Requete(String titreTravail, String description, String typeTravaux, String debut) {
        this.titreTravail = titreTravail;
        this.description = description;
        this.typeTravaux = typeTravaux;
        this.debut = debut;
    }

    // Constructeur sans argument
    public Requete() {
    }

    // Getter et setter
    public String getTitreTravail() {
        return titreTravail;
    }

    public void setTitreTravail(String titreTravail) {
        this.titreTravail = titreTravail;
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

    public String getDebut() {
        return debut;
    }

    public void setDebut(String debut) {
        this.debut = debut;
    }

    public Intervenant getIntervenant() {
        return intervenant;
    }

    public void setIntervenant(Intervenant intervenant) {
        this.intervenant = intervenant;
    }

    public Resident getResident() {
        return resident;
    }

    public void setResident(Resident resident) {
        this.resident = resident;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String nouvelEtat) {
        this.etat = nouvelEtat;
    }
}
