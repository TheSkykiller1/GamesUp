package borioito.gamesup;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class login_screen extends AppCompatActivity {

    Button b_login, b_contact;
    EditText ed_email,ed_passwd;

    TextView tx_title,tx_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        b_login = (Button)findViewById(R.id.button);
        b_contact = (Button)findViewById(R.id.button2);

        ed_email = (EditText)findViewById(R.id.input_email);
        ed_passwd = (EditText)findViewById(R.id.input_password);

        tx_title = (TextView)findViewById(R.id.titleApp);
        tx_signup = (TextView)findViewById(R.id.signup);

        b_login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                if(ed_email.getText().toString().equals("user") && ed_passwd.getText().toString().equals("user1")  ){
                    Toast.makeText(getApplicationContext(),
                            "Welcome Back!",Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(login_screen.this, register.class));
                }else {
                    Toast.makeText(getApplicationContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    protected void mysignup(View v){
        startActivity(new Intent(login_screen.this, register.class));
    }

    protected void passEvent(View v){
////        startActivity(new Intent(login_screen.this, Activity_event.class));
   }

}

/* TODO
* - Check internet connection
* - Label if error (id/pwd) [email ou mp incorrecte]
* - connection bdd authentification port 3306
* - génération account bundle connection ( stockage email + info bdd)
* (bundle info ++)
* - ajout de bouton contacte
* - mettre les textes res/string
* - signup (1 email, mp + vérification (min 6 character) , ++test de sécurité )
* - changer le mp
* - sha256 mp
* */