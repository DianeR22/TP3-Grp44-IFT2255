package Model;

public class Requete {

    // Attributs pour une requete de travail
    private String titreTravail;
    private String description;
    private String typeTravaux;
    private String debut;

    private Intervenant intervenant;
    private Resident resident;

    // Constructeur de Requete
    public Requete(String titreTravail, String description, String typeTravaux, String debut) {
        this.titreTravail = titreTravail;
        this.description = description;
        this.typeTravaux = typeTravaux;
        this.debut = debut;
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


    @Override
    public String toString(){
        return String.format(
                "Requête de travail : %s\nDescription : %s\nType de travail : %s\nDate de début : %s",
                titreTravail, description, typeTravaux, debut
        );
    }

    // SoumettreRequete permet de créer une requête et de l'ajouter à la liste des requêtes
    // Elle prend en paramètres toutes les caractéristiques d'une requête en String
    public static void soumettreRequete(String titreTravail,String description, String typeTravail, String dateDebut){
        Requete requete = new Requete(titreTravail, description, typeTravail, dateDebut);
        GestionRequete.ajouterRequete(requete);
    }
}
