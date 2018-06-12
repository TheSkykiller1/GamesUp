package borioito.gamesup;

import android.content.res.TypedArray;

public class Event_list_games {
/**Class objet "Games Event" Contient toutes les informations sur le jeu*/
	//private int pics_id;
	private String titre;
	private String plateforme;
	private String date;
	private int id_component;
	private int id_article;
	private int is_follow;

	public Event_list_games(int id_component, int id_article, String titre/*,int pics_id*/,String plateforme, String date, int is_follow) {
        //this.pics_id = pics_id;
        this.id_component = id_component;
        this.id_article = id_article;
	    this.titre = titre;
		this.plateforme = plateforme;
		this.date = date;
		this.is_follow= is_follow;
	}
	//Setter, getter

    public int getIs_follow() {
        return is_follow;
    }

    public void setIs_follow(int is_follow) {
        this.is_follow = is_follow;
    }

    public int getId_component() {
        return id_component;
    }

    public int getId_article() {
        return id_article;
    }


    public String getTitre() {
        return titre;
    }


    public String getPlateforme() {
        return plateforme;
    }

    public String getDate() {
        return date;
    }

}