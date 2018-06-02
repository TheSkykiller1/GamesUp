package borioito.gamesup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;

public class login_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
    }

    protected void passEvent(View v){
        startActivity(new Intent(login_screen.this, activity_event.class));
   }

}