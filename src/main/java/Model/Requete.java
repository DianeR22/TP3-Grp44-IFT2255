package Model;

/**
 * La classe Requete représente une requête de travail d'un résident
 * et possède un type de travail, une description et une date de début.
 * Elle contient des informations sur l'intervenant et le résident
 * concernés par la requête.
 */
public class Requete {

    // Attributs pour une requete de travail
    private String titreTravail;
    private String description;
    private String typeTravaux;
    private String debut;
    private String etat = "Ouverte";

    private Intervenant intervenant;
    private Resident resident;

    /**
     * Constructeur permettant d'instancier une requête avec ses attributs
     * @param titreTravail titre du travail de la requête
     * @param description description de la requête
     * @param typeTravaux type de travail de la requête
     * @param debut date de début de la requête
     */
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
    /**
     * Retourne le titre du travail associé à la requête.
     *
     * @return Le titre du travail.
     */
    public String getTitreTravail() {
        return titreTravail;
    }

    /**
     * Définit le titre du travail associé à la requête.
     *
     * @param titreTravail Le titre à attribuer à la requête.
     */
    public void setTitreTravail(String titreTravail) {
        this.titreTravail = titreTravail;
    }

    /**
     * Retourne la description associée à la requête.
     *
     * @return La description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Définit la description associé à la requête.
     *
     * @param description description de la requête.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retourne le type du travail associé à la requête.
     *
     * @return Le type du travail.
     */
    public String getTypeTravaux() {
        return typeTravaux;
    }

    /**
     * Définit le type du travail associé à la requête.
     *
     * @param typeTravaux Le type de travail de la requête.
     */
    public void setTypeTravaux(String typeTravaux) {
        this.typeTravaux = typeTravaux;
    }

    /**
     * Retourne la date de début associée à la requête.
     *
     * @return Le date de début du travail.
     */
    public String getDebut() {
        return debut;
    }

    /**
     * Définit la date de début associée à la requête.
     *
     * @param debut Le date de début à attribuer à la requête.
     */
    public void setDebut(String debut) {
        this.debut = debut;
    }

    /**
     * Retourne l'intervenant associé à la requête.
     *
     * @return L'intervenant
     */
    public Intervenant getIntervenant() {
        return intervenant;
    }

    /**
     * Définit l'intervenant associé à la requête.
     *
     * @param intervenant L'intervenant à attribuer à la requête.
     */
    public void setIntervenant(Intervenant intervenant) {
        this.intervenant = intervenant;
    }

    /**
     * Retourne le résident associé à la requête.
     *
     * @return Le résident.
     */
    public Resident getResident() {
        return resident;
    }

    /**
     * Définit le resident associé à la requête.
     *
     * @param resident Le résident à attribuer à la requête.
     */
    public void setResident(Resident resident) {
        this.resident = resident;
    }

    /**
     * Retourne l'état associé à la requête.
     *
     * @return L'état de la requête
     */
    public String getEtat() {
        return etat;
    }

    /**
     * Définit l'état associé à la requête.
     *
     * @param nouvelEtat L'état à attribuer à la requête.
     */
    public void setEtat(String nouvelEtat) {
        this.etat = nouvelEtat;
    }
}
