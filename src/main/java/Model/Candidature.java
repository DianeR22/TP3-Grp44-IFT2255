package Model;

public class Candidature {
    // Attributs d'une candidature
    private String dateDebut;
    private String dateFin;
    private Requete requete;
    private Intervenant intervenant;
    private String messageResident;
    private String etat = "En attente";

    // Constructeur
    public Candidature(String dateDebut, String dateFin, Requete requete, Intervenant intervenant) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.requete = requete;
        this.intervenant = intervenant;
    }

    // Constructeur sans arguement
    public Candidature(){

    }

    // Getter et setter
    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public Requete getRequete() {
        return requete;
    }

    public void setRequete(Requete requete) {
        this.requete = requete;
    }

    public Intervenant getIntervenant() {
        return intervenant;
    }

    public void setIntervenant(Intervenant intervenant) {
        this.intervenant = intervenant;
    }

    public String getMessageResident() {
        return messageResident;
    }

    public void setMessageResident(String messageResident) {
        this.messageResident = messageResident;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String nouvelEtat) {
        this.etat = nouvelEtat;
    }
}
