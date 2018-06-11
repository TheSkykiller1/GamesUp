package borioito.gamesup;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class Event_list_adapter extends BaseAdapter {

	private Context mContext;
	List<Event_list_games> rowGameslist;

	Event_list_adapter(Context context, List<Event_list_games> rowGames) {
		this.mContext = context;
		this.rowGameslist = rowGames;
	}

	@Override
	public int getCount() {
		return rowGameslist.size();
	}

	@Override
	public Object getItem(int position) {
		return rowGameslist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return rowGameslist.indexOf(getItem(position));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext,R.layout.list_gamesevent, null);
        TextView texttitre = (TextView)v.findViewById(R.id.text_titre);
        TextView textplateforme = (TextView)v.findViewById(R.id.text_plateforme);
        TextView textdate = (TextView)v.findViewById(R.id.text_date);

        texttitre.setText(rowGameslist.get(position).getTitre());
        textplateforme.setText(rowGameslist.get(position).getPlateforme());
        textdate.setText(rowGameslist.get(position).getDate());

        v.setTag(rowGameslist.get(position));
        //v.setTag("YOLO");
        return v;
	}

}