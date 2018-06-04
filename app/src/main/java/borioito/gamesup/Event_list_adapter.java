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
		TextView text_titre = (TextView)v.findViewById(R.id.text_titre);
		TextView text_plateforme = (TextView)v.findViewById(R.id.text_plateforme);
		TextView text_date = (TextView)v.findViewById(R.id.text_date);

		return convertView;
	}

}