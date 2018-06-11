package borioito.gamesup;

import android.content.res.TypedArray;

public class Event_list_games {
/**Class objet "Games Event" Contient toutes les informations sur le jeu*/
	//private int pics_id;
	private String titre;
	private String plateforme;
	private String date;
	private int id;

	public Event_list_games(int id, String titre/*,int pics_id*/,String plateforme, String date) {
        //this.pics_id = pics_id;
        this.id = id;
	    this.titre = titre;
		this.plateforme = plateforme;
		this.date = date;
	}
	//Setter, getter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
}