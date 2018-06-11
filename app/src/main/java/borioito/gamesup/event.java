package borioito.gamesup;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import android.view.*;
import java.util.ArrayList;
import java.util.List;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class event extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView listviewGames;
    List<Event_list_games> games_List_items;
    Event_list_adapter adapter;
    int notification_send=0;//On n'envoi que une fois la notif tant que l'application est ouverte
    String filter="None";//Les filtres
    String global_event="Yes";//Yes = tous les event, No = Follow only

    public class Event_list_adapter extends BaseAdapter {

        private Context mContext;

        Event_list_adapter(Context context) {
            this.mContext = context;
        }
        @Override
        public int getCount() {
            return games_List_items.size();
        }
        @Override
        public Object getItem(int position) {
            return games_List_items.get(position);
        }
        @Override
        public long getItemId(int position) {
            return games_List_items.indexOf(getItem(position));
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = View.inflate(mContext,R.layout.list_gamesevent, null);

            //On appelle tous les parametres a remplir
            TextView texttitre = (TextView)v.findViewById(R.id.text_titre);
            TextView textplateforme = (TextView)v.findViewById(R.id.text_plateforme);
            TextView textdate = (TextView)v.findViewById(R.id.text_date);
            Button textbut_follow =(Button)v.findViewById(R.id.text_but_follow);

            //On rempli les parametre
            texttitre.setText(games_List_items.get(position).getTitre());
            textplateforme.setText(games_List_items.get(position).getPlateforme());
            textdate.setText(games_List_items.get(position).getDate());

            //On creer les actions pour les boutons
            final int position_object = position;
            final View viewer=v;
            if(games_List_items.get(position).getIs_follow()==1){
                textbut_follow.setText("Unfollow");
                /**On choisi l'action du bouton unfollow*/
                textbut_follow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Button but_follow_state =(Button)viewer.findViewById(R.id.text_but_follow);
                        but_follow_state.setText("Follow");
                        games_List_items.get(position_object).setIs_follow(0);
                        Log.i("Suivi","Event non suivi "+position_object+" Et donnée "+games_List_items.get(position_object).getIs_follow());
                    }
                });
            }
            else{
                textbut_follow.setText("Follow");
                /**On choisi l'action du bouton follow*/
                textbut_follow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Button but_follow_state =(Button)viewer.findViewById(R.id.text_but_follow);
                        but_follow_state.setText("Unfollow");
                        games_List_items.get(position_object).setIs_follow(1);
                        Log.i("Suivi","Event suivi "+position_object+" Et donnée "+games_List_items.get(position_object).getIs_follow());
                    }
                });
            }
            v.setTag(games_List_items.get(position).getId_component());
            return v;
        }

    }

    public void get_event_from_database()
    {
        //Take all data after a certain date-1
        //SELECT idevent,titre,date FROM table WHERE TheNameOfTimestampColumn > DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 1 DAY)
        /*
        a terminer (notif)
        SELECT  idevent,titre,date
        FROM    event
        WHERE   date >= DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 1 DAY) AND
        date   <= DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 1 DAY)
        */
        ////
        //Todo appliquer les filtres
        //Todo choisir le mode de visualition
        //Todo connection bdd
        //Todo Notif grace a la bdd directement

        games_List_items = new ArrayList<>();
        games_List_items.add(new Event_list_games(1,1,"CSGO","XBOX","23/05/2018",0));
        games_List_items.add(new Event_list_games(2,15,"battlefield","PC","23/05/2018",0));
        games_List_items.add(new Event_list_games(3,85,"overwatch","PC","23/05/2018",1));
        games_List_items.add(new Event_list_games(4,56,"Little pony","PS4","23/05/2018",1));
        games_List_items.add(new Event_list_games(5,441,"Mario","wii","23/05/2018",0));
        games_List_items.add(new Event_list_games(6,57,"Fortnite","PC","23/05/2018",1));
        games_List_items.add(new Event_list_games(7,15,"didisco","XBOX","23/05/2018",0));
        games_List_items.add(new Event_list_games(8,55,"Looss","PS4","23/05/2018",0));


        /*int notification_send=0;
        String filter="None";//Les filtres
        String global_event="Yes";//Yes = tous les event, No = Follow only
        for (Event_list_games item : games_List_items) {
            if (item.getIs_follow()==1) {
                Log.i("Suivi","Article suivi"+item.getId_article());
                if(notification_send==0){

                }
            }
        }*/

    }
    public void actualiser_listview(){
        listviewGames = (ListView)findViewById(R.id.list);
        get_event_from_database();

        //Ajoute les données a la listview
        adapter = new Event_list_adapter(getApplicationContext());
        listviewGames.setAdapter(adapter);
        //listviewGames.setItemsCanFocus(false);
        /**Si l'on clique sur le cadre du jeu on lance un évenement qui affiche un toast*/
        listviewGames.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id){
                Toast.makeText(getApplicationContext(), "Click on=" + view.getTag()+" Position: "+position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        /**ListView des releases*/
        actualiser_listview();

        /**Menu Navigation!*/
        navigation_init();
    }
    public void navigation_init()
    {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    /**Menu Navigation! Commande d'interface*/
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_follow) {
            // Handle the camera action
        } else if (id == R.id.filter_ac) {

        } else if (id == R.id.filter_dc) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
