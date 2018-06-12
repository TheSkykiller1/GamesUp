package borioito.gamesup;

public class Games_release_object {
    /**Class objet "Games Event" Contient toutes les informations sur le jeu*/
    //private int pics_id;
    private String titre;
    private String plateforme;
    private String date;
    private int id_article;

    public Games_release_object(int id_article, String titre,String plateforme, String date) {
        this.id_article = id_article;
        this.titre = titre;
        this.plateforme = plateforme;
        this.date = date;
    }
    //Setter, getter
    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getPlateforme() {
        return plateforme;
    }

    public void setPlateforme(String plateforme) {
        this.plateforme = plateforme;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId_article() {
        return id_article;
    }

    public void setId_article(int id_article) {
        this.id_article = id_article;
    }
}