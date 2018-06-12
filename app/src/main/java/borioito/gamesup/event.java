package borioito.gamesup;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.AsyncTask;
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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.app.*;
import android.support.v4.app.*;
import android.os.Build;
import android.content.*;

public class event extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String email_address;
    ListView listviewGames;
    List<Event_list_games> games_List_items;
    Event_list_adapter adapter;
    int notification_send=0;//On n'envoi que une fois la notif tant que l'application est ouverte
    String global_event="Yes";//Yes = tous les event, No = Follow only
    String filter="ASC";
    int Notification_id=10;
    String CHANNEL_ID;


    /**Tous les requetes HTTP pour la BDD*/
    /*private class AsyncFollow_interact extends AsyncTask<String, String, String> {

        ProgressDialog pdLoading = new ProgressDialog( event.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //this method will be running on UI thread
            pdLoading.setMessage("\t"+getString(R.string.loading_text));
            pdLoading.setCancelable(false);
            pdLoading.show();
        }
        @Override
        protected String doInBackground(String... params) {

            try {
                url = new URL("http://multiversepurity.ddns.net/add_delete_follow.php");

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "exception";
            }

            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("action", params[0])
                        .appendQueryParameter("EMAIL", params[1])
                        .appendQueryParameter("ID", params[2]);
                String query = builder.build().getEncodedQuery();

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                e1.printStackTrace();
                return "exception";
            }

            try {
                int response_code = conn.getResponseCode();
                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {
                    return("Data success");
                }else{
                    return("unsuccessful");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }
        }
        @Override
        protected void onPostExecute(String result) {
            pdLoading.dismiss();
        }
    }*/



    /**Adaptateur pour la listview*/
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

                        final String action = "DEL";
                        final String email = email_address;
                        final String id = ""+games_List_items.get(position_object).getId_article();
                       // new event.AsyncFollow_interact().execute(action,email,id);

                        games_List_items.get(position_object).setIs_follow(0);
                        but_follow_state.setText(getString(R.string.but_follow));
                        //Log.i("Suivi","Event non suivi "+position_object+" IS follow? "+games_List_items.get(position_object).getIs_follow());
                    }
                    else { //Si on ne suit pas ce jeu alors on le suit.
                        //ACTION
                        Button but_follow_state =(Button)viewer.findViewById(R.id.text_but_follow);

                        final String action = "ADD";
                        final String email = email_address;
                        final String id = ""+games_List_items.get(position_object).getId_article();
                       // new event.AsyncFollow_interact().execute(action,email,id);

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
   private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.notif_channel_title);
            String description = getString(R.string.notif_channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    private void setOnTap(){
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, login_screen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_menu_send)
                .setContentTitle(getString(R.string.notif_title))
                .setContentText("Liste des jeux qui vont sortir que vous suivez: \n Much longer text that cannot fit one line...\n Again and Again \n TEST")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Liste des jeux qui vont sortir que vous suivez: \n Much longer text that cannot fit one line...\n Again and Again \n TEST"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(Notification_id, mBuilder.build());
    }
    private final void createNotification(){
        createNotificationChannel(); //Execute seulement une fois pour android 8+
        setOnTap();
    }

    public void afficher_notification(){
        //Todo prendre les infos sur les jeux sortant ce mois-ci et qui sont follow par l'utilisateur.
        List<Games_release_object> Games_followed_release_this_month = new ArrayList<>();
        if(Games_followed_release_this_month.size()>0){
            String notification_text="";
            for(Games_release_object item_followed: Games_followed_release_this_month){
                notification_text += item_followed.getTitre()+ " ";
            }
        }

    }

    public void get_event_from_database()
    {
        createNotificationChannel(); //Execute seulement une fois.
        createNotification();

        List<Games_release_object> Games_releases_global = new ArrayList<>();
        List<Games_release_object> Games_releases_followed = new ArrayList<>();
        //Todo connection BDD
        //On récupère toutes les données dans la BDD



        //int id_article, String titre,String plateforme, String date
        Games_releases_global.add(new Games_release_object(18,"Battlefield V","XBOX","25/06/2018"));
        Games_releases_global.add(new Games_release_object(19,"Fornite","PC","30/06/2018"));

        Games_releases_global.add(new Games_release_object(20,"Overwatch","PC","15/06/2018"));
        Games_releases_global.add(new Games_release_object(21,"Mario world","PC,XBOX,PS4,PS VITA, WII, SWITCH","10/08/2018"));

        Games_releases_global.add(new Games_release_object(22,"AC:O","XBOX,PC,PS4","22/10/2018"));
        Games_releases_global.add(new Games_release_object(23,"Avenger the game","PC","30/11/2018"));

        Games_releases_followed.add(new Games_release_object(18,"Battlefield V","XBOX","25/06/2018"));
        Games_releases_followed.add(new Games_release_object(22,"AC:O","XBOX,PC,PS4","22/10/2018"));
        Games_releases_followed.add(new Games_release_object(20,"Overwatch","PC","15/06/2018"));


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
        //Récuperation des info de l'activité précédente
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
                this.setTitle(getResources().getText(R.string.title_followed));
            }
            else{
                global_event="Yes";
                this.setTitle(getResources().getText(R.string.title_activity_event));
            }
            filter="ASC";
            actualiser_listview();
        } else if (id == R.id.filter_ac) {
            filter="ASC";

        } else if (id == R.id.filter_dc) {
            filter="DESC";
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
