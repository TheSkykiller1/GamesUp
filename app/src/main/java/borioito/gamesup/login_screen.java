package borioito.gamesup;

// Android imports
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
// java.io imports
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

/**
 * Created by Armand ITO on 03/06/2018.
 *
 * Require lib  compile 'com.squareup.okhttp:okhttp:2.4.0'
 *      to add in build.gradle
 *
 */
public class login_screen extends AppCompatActivity {

    private BroadcastReceiver mNetworkReceiver;
    static TextView tv_check_connection;
    private static Context appContext;
    Button b_login, b_signup;
    EditText ed_email,ed_passwd;
    TextView tx_title;

    /**
     * Very first called function to create the Activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        appContext=this;
        b_login = (Button)findViewById(R.id.button);
        //b_contact = (Button)findViewById(R.id.button2);

        ed_email = (EditText)findViewById(R.id.input_email);
        ed_passwd = (EditText)findViewById(R.id.input_password);

        tx_title = (TextView)findViewById(R.id.titleApp);

        b_signup = (Button)findViewById(R.id.createAccount);

        tv_check_connection=(TextView) findViewById(R.id.tv_check_connection);
        mNetworkReceiver = new internet_connection_checker();
        registerNetworkBroadcastForNougat();

        FirstConnectionTest();

        b_login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                final String email = ed_email.getText().toString();
                final String password = ed_passwd.getText().toString();

                // Initialize  AsyncLogin() class with email and password
                new AsyncLogin().execute(email,password);
            }

        });

        b_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(),register.class);
                startActivity(intent);
            }
        });

    }

    /**
     *
     * @param value
     */
    public static void dialog(boolean value){

        if(value){
            tv_check_connection.setText(appContext.getResources().getString(R.string.connectresolve));
            tv_check_connection.setBackgroundColor(Color.parseColor("#11890d"));
            tv_check_connection.setTextColor(Color.WHITE);

            Handler handler = new Handler();
            Runnable delayrunnable = new Runnable() {
                @Override
                public void run() {
                    tv_check_connection.setVisibility(View.GONE);
                }
            };
            // waiting 3 secondes
            handler.postDelayed(delayrunnable, 3000);

        } else {

            tv_check_connection.setVisibility(View.VISIBLE);
            tv_check_connection.setText(appContext.getResources().getString(R.string.cannotconnect));
            tv_check_connection.setBackgroundColor(Color.parseColor("#ac1212"));
            tv_check_connection.setTextColor(Color.WHITE);

        }

    }

    /**
     *
     */
    private void registerNetworkBroadcastForNougat() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        }

    }

    /**
     *
     */
    protected void unregisterNetworkChanges() {

        try {

            unregisterReceiver(mNetworkReceiver);

        } catch (IllegalArgumentException e) {

            e.printStackTrace();

        }

    }

    /**
     *
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterNetworkChanges();
    }

    /**
     *
     */
    public void FirstConnectionTest(){
        try
        {
            if (!isOnline(this)) {
                dialog(false);
                Log.e("INT_DOWN", "Internet Connection Failure !!! ");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param context
     * @return
     */
    private boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *
     */
    private class AsyncLogin extends AsyncTask <String, String, String> {

        ProgressDialog pdLoading = new ProgressDialog(login_screen.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\t"+getText(R.string.authenticating_text));
            pdLoading.setCancelable(false);
            pdLoading.show();

        }
        @Override
        protected String doInBackground(String... params) {

            try {
                // Enter URL address where your php file resides
                url = new URL("http://multiversepurity.ddns.net/connection.php");

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
                        .appendQueryParameter("email", params[0])
                        .appendQueryParameter("password", params[1]);
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

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return(result.toString());

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

            //this method will be running on UI thread

            pdLoading.dismiss();

            if(result.equalsIgnoreCase("true"))
            {
                Intent intent = new Intent(login_screen.this,event.class);
                intent.putExtra("EXTRA_EMAIL", ed_email.getText().toString());
                startActivity(intent);
                login_screen.this.finish();

            }else if (result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
                Toast.makeText(login_screen.this, getText(R.string.invalide_text), Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(login_screen.this, getText(R.string.connexion_error), Toast.LENGTH_LONG).show();

            } else {

                Toast.makeText(login_screen.this, getText(R.string.server_error), Toast.LENGTH_LONG).show();

            }
        }
    }
}

/* TODO
* - [] génération account bundle connection ( stockage email + info bdd)
* (bundle info ++)
* - [] signup (1 email, mp + vérification (min 6 character) , ++test de sécurité )
* - [] changer le mp
* */