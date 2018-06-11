package borioito.gamesup;

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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import android.view.*;
import java.util.ArrayList;
import java.util.List;
import android.widget.AdapterView.OnItemClickListener;


public class event extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView listviewGames;
    private List<Event_list_games> games_List_items;
    private Event_list_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);


        /**ListView des releases*/

        listviewGames = (ListView)findViewById(R.id.list);
        games_List_items = new ArrayList<>();

        games_List_items.add(new Event_list_games(1,"CSGO","XBOX","23/05/2018"));
        games_List_items.add(new Event_list_games(2,"battlefield","PC","23/05/2018"));
        games_List_items.add(new Event_list_games(3,"overwatch","PC","23/05/2018"));
        games_List_items.add(new Event_list_games(4,"Little pony","PS4","23/05/2018"));
        games_List_items.add(new Event_list_games(5,"Mario","wii","23/05/2018"));
        games_List_items.add(new Event_list_games(6,"Fortnite","PC","23/05/2018"));
        games_List_items.add(new Event_list_games(7,"didisco","XBOX","23/05/2018"));
        games_List_items.add(new Event_list_games(8,"Looss","PS4","23/05/2018"));

        adapter = new Event_list_adapter(getApplicationContext(),games_List_items);
        listviewGames.setAdapter(adapter);
        listviewGames.setItemsCanFocus(false);
        listviewGames.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id){
                Toast.makeText(getApplicationContext(), "Click on=" + view.getTag(), Toast.LENGTH_SHORT).show();
            }
        });


        /**Menu Navigation!*/
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
