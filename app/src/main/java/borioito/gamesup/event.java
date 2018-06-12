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

    String email_address;
    ListView listviewGames;
    List<Event_list_games> games_List_items;
    Event_list_adapter adapter;
    int notification_send=0;//On n'envoi que une fois la notif tant que l'application est ouverte
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
                textbut_follow.setText(getString(R.string.but_unfollow));
            }
            else{
                textbut_follow.setText(getString(R.string.but_follow));
            }
            //Todo LINK TO DATABSE POUR EDITER LES CHAMPS DE SUIVIS
            /**On choisi l'action du bouton*/
            textbut_follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(games_List_items.get(position_object).getIs_follow()==1){ //Si on suit un jeu alors on arrete de le suivre
                        //ACTION
                        Button but_follow_state =(Button)viewer.findViewById(R.id.text_but_follow);
                        games_List_items.get(position_object).setIs_follow(0);
                        but_follow_state.setText(getString(R.string.but_follow));

                        //Log.i("Suivi","Event non suivi "+position_object+" IS follow? "+games_List_items.get(position_object).getIs_follow());
                    }
                    else { //Si on ne suit pas ce jeu alors on le suit.
                        //ACTION
                        Button but_follow_state =(Button)viewer.findViewById(R.id.text_but_follow);
                        games_List_items.get(position_object).setIs_follow(1);
                        but_follow_state.setText(getString(R.string.but_unfollow));
                        //Log.i("Suivi","Event suivi "+position_object+" IS follow? "+games_List_items.get(position_object).getIs_follow());
                    }
                }
            });
            v.setTag(games_List_items.get(position).getId_component());
            return v;
        }

    }
    public void afficher_notification(){
        //Todo prendre les infos sur les jeux sortant ce mois-ci et qui sont follow par l'utilisateur.
    }

    public void get_event_from_database()
    {
        List<Games_release_object> Games_releases_global = new ArrayList<>();
        List<Games_release_object> Games_releases_followed = new ArrayList<>();
        //Todo connection BDD
        //On récupère toutes les données dans la BDD



        //int id_article, String titre,String plateforme, String date
        Games_releases_global.add(new Games_release_object(18,"Battlefield V","XBOX","25/06/2018"));
        Games_releases_global.add(new Games_release_object(19,"Fornite","PC","30/06/2018"));
        Games_releases_followed.add(new Games_release_object(18,"Battlefield V","XBOX","25/06/2018"));


        //Code fonctionnel
        if(global_event=="Yes"){
            games_List_items = new ArrayList<>();
            int i=0;
            for(Games_release_object item_release: Games_releases_global){
                int followed = 0;
                for(Games_release_object item_followed: Games_releases_followed){
                    if(item_release.getId_article()==item_followed.getId_article()){
                        followed=1;
                        break;
                    }
                }
                games_List_items.add(new Event_list_games(i,item_release.getId_article(),item_release.getTitre(),item_release.getPlateforme(),item_release.getDate(),followed));
                i++;
            }
        }
        else {
            games_List_items = new ArrayList<>();
            int i=0;
            for(Games_release_object item_followed: Games_releases_followed) {
                games_List_items.add(new Event_list_games(i,item_followed.getId_article(),item_followed.getTitre(),item_followed.getPlateforme(),item_followed.getDate(),1));
                i++;
            }
        }

        /*games_List_items = new ArrayList<>();
        games_List_items.add(new Event_list_games(1,1,"CSGO","XBOX","23/05/2018",0));
        games_List_items.add(new Event_list_games(2,15,"battlefield","PC","23/05/2018",0));
        games_List_items.add(new Event_list_games(3,85,"overwatch","PC","23/05/2018",1));
        games_List_items.add(new Event_list_games(4,56,"Little pony","PS4","23/05/2018",1));
        games_List_items.add(new Event_list_games(5,441,"Mario","wii","23/05/2018",0));
        games_List_items.add(new Event_list_games(6,57,"Fortnite","PC","23/05/2018",1));
        games_List_items.add(new Event_list_games(7,15,"didisco","XBOX","23/05/2018",0));
        games_List_items.add(new Event_list_games(8,55,"Looss","PS4","23/05/2018",0));*/

    }
    public void actualiser_listview(){
        listviewGames = (ListView)findViewById(R.id.list);
        get_event_from_database();

        //Ajoute les données a la listview
        adapter = new Event_list_adapter(getApplicationContext());
        listviewGames.setAdapter(adapter);
        //listviewGames.setItemsCanFocus(false);
        /**Si l'on clique sur le cadre du jeu on lance un évenement d'interaction*/
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
        email_address = getIntent().getStringExtra("EXTRA_EMAIL");
        //Log.i("Datatransmise","Emailtransfert:  "+email_address);
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
        int id = item.getItemId();
        if (id == R.id.nav_follow) {
            if(global_event=="Yes"){
                global_event="No";
            }
            else{
                global_event="Yes";
            }
            actualiser_listview();
        } else if (id == R.id.filter_ac) {

        } else if (id == R.id.filter_dc) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
