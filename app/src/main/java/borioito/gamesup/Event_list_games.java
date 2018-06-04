package borioito.gamesup;

public class Event_list_games {

	private String titre;
	private String plateforme;
	private String date;

	public Event_list_games(String titre,String plateforme, String date) {
		this.titre = titre;
		this.plateforme = plateforme;
		this.date = date;
	}

	public String gettitre_name() {
		return titre;
	}

	public void settitre_name(String titre) {
		this.titre = titre;
	}

	public String getplateforme() {
		return plateforme;
	}

	public void setplateforme(String plateforme) {
		this.plateforme = plateforme;
	}

	public String getdate() { return date;	}

	public void setdate(String date) {
		this.date = date;
	}
}