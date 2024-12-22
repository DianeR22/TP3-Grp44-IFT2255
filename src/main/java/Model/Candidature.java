package Model;

/**
 * Classe représentant une candidature appartenant à un intervenant
 * pour répondre à une requête d'un des résidents.
 */
public class Candidature {
    // Attributs d'une candidature
    /**
     * La date de début proposée pour la candidature.
     */
    private String dateDebut;

    /**
     * La date de fin proposée pour la candidature.
     */
    private String dateFin;

    /**
     * La requête associée à cette candidature.
     */
    private Requete requete;

    /**
     * L'intervenant qui soumet la candidature.
     */
    private Intervenant intervenant;

    /**
     * Un message optionnel laissé par le résident pour l'intervenant qui propose la candidature.
     */
    private String messageResident;

    /**
     * L'état actuel de la candidature (par défaut : "En attente").
     */
    private String etat = "En attente";

    // Constructeurs

    /**
     * Constructeur de la classe Candidature avec des paramètres.
     *
     * @param dateDebut     La date de début proposée.
     * @param dateFin       La date de fin proposée.
     * @param requete       La requête associée à une candidature.
     * @param intervenant   L'intervenant soumettant la candidature.
     */
    public Candidature(String dateDebut, String dateFin, Requete requete, Intervenant intervenant) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.requete = requete;
        this.intervenant = intervenant;
    }

    /**
     * Constructeur par défaut de la classe Candidature.
     */
    public Candidature() {
    }

    // Getters et setters

    /**
     * Retourne la date de début de la candidature.
     *
     * @return La date de début.
     */
    public String getDateDebut() {
        return dateDebut;
    }

    /**
     * Définit la date de début de la candidature.
     *
     * @param dateDebut La date de début à définir.
     */
    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    /**
     * Retourne la date de fin de la candidature.
     *
     * @return La date de fin.
     */
    public String getDateFin() {
        return dateFin;
    }

    /**
     * Définit la date de fin de la candidature.
     *
     * @param dateFin La date de fin à définir.
     */
    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    /**
     * Retourne la requête associée à la candidature.
     *
     * @return La requête.
     */
    public Requete getRequete() {
        return requete;
    }

    /**
     * Définit la requête associée à la candidature.
     *
     * @param requete La requête à associer.
     */
    public void setRequete(Requete requete) {
        this.requete = requete;
    }

    /**
     * Retourne l'intervenant ayant soumis la candidature.
     *
     * @return L'intervenant.
     */
    public Intervenant getIntervenant() {
        return intervenant;
    }

    /**
     * Définit l'intervenant qui soumet la candidature.
     *
     * @param intervenant L'intervenant à définir.
     */
    public void setIntervenant(Intervenant intervenant) {
        this.intervenant = intervenant;
    }

    /**
     * Retourne le message laissé par le résident pour l'intervenant qui soumet sa candidature.
     *
     * @return Le message du résident.
     */
    public String getMessageResident() {
        return messageResident;
    }

    /**
     * Définit le message laissé par le résident pour cette candidature.
     *
     * @param messageResident Le message à définir.
     */
    public void setMessageResident(String messageResident) {
        this.messageResident = messageResident;
    }

    /**
     * Retourne l'état actuel de la candidature.
     *
     * @return L'état de la candidature.
     */
    public String getEtat() {
        return etat;
    }

    /**
     * Définit un nouvel état pour la candidature.
     *
     * @param nouvelEtat Le nouvel état à définir.
     */
    public void setEtat(String nouvelEtat) {
        this.etat = nouvelEtat;
    }
}