package borioito.gamesup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
 * Created by Armand ITO on 10/06/2018.
 */
public class register extends AppCompatActivity {

    Button b_signup;
    EditText ed_email,ed_passwd,ed_passwd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        b_signup = (Button)findViewById(R.id.signup);

        ed_email = (EditText)findViewById(R.id.input_email);
        ed_passwd = (EditText)findViewById(R.id.input_password);
        ed_passwd2 = (EditText)findViewById(R.id.input_password2);

        b_signup.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                final String email = ed_email.getText().toString();
                final String password = ed_passwd.getText().toString();
                final String password2 = ed_passwd2.getText().toString();

                if(password.equals(password2)){
                    if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){

                        new AsyncSignup().execute(email,password);
                    }
                    else {
                        Toast.makeText(register.this, "Please set a real email", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(register.this, "password not the same", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private class AsyncSignup extends AsyncTask<String, String, String> {
            ProgressDialog pdLoading = new ProgressDialog(register.this);
            HttpURLConnection conn;
            URL url = null;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                //this method will be running on UI thread
                pdLoading.setMessage("\tLoading...");
                pdLoading.setCancelable(false);
                pdLoading.show();

            }

            @Override
            protected String doInBackground(String... params) {
                try {
                    // Enter URL address where your php file resides
                    url = new URL("http://multiversepurity.ddns.net/signup.php");

                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
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
                    // TODO Auto-generated catch block
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

                if(result.equalsIgnoreCase("true")) {

                    Toast.makeText(register.this, "Account created", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(register.this,login_screen.class);
                        startActivity(intent);
                    register.this.finish();

                }else if (result.equalsIgnoreCase("false")){

                    // If username and password does not match display a error message
                    Toast.makeText(register.this, "Existing user", Toast.LENGTH_LONG).show();

                } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                    Toast.makeText(register.this, "Something went wrong! Connection Problem.", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(register.this, "Lost connection with server", Toast.LENGTH_LONG).show();

                }
            }
        }
}
