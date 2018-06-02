package borioito.gamesup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;

import android.view.View;

public class login_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
    }

    public void passevent(View v){
        Intent myIntent = new Intent(login_screen.this, event.class);
        login_screen.this.startActivity(myIntent);
   }
}