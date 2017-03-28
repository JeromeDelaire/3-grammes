package com.example.jerome.a3grammes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.jerome.a3grammes.Settings.MoreOrLessSettings;
import com.example.jerome.a3grammes.Settings.PicoloSettings;
import com.example.jerome.a3grammes.Settings.RedOrBlackSettings;

public class GamesMenu extends AppCompatActivity implements View.OnClickListener{

    private Button picolo, more_or_less, red_or_black ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_menu);

        //Boutons pour choisir le jeu
        picolo = (Button) findViewById(R.id.picolo_button);
        more_or_less = (Button) findViewById(R.id.more_or_less_button);
        red_or_black = (Button) findViewById(R.id.red_or_black_button);

        //Ajout des listeners aux boutons
        picolo.setOnClickListener(this);
        more_or_less.setOnClickListener(this);
        red_or_black.setOnClickListener(this);
    }

    // OnClickListeners
    @Override
    public void onClick(View view) {
        //Si on clique sur picolo
        if(view==picolo){
            Intent myIntent = new Intent(this, PicoloSettings.class);
            startActivityForResult(myIntent, 0);
        }

        //Si on clique sur more or less
        else if(view==more_or_less){
            Intent myIntent = new Intent(this, MoreOrLessSettings.class);
            startActivityForResult(myIntent, 0);
        }

        //Si on clique sur red or black
        else if(view==red_or_black){
            Intent myIntent = new Intent(this, RedOrBlackSettings.class);
            startActivityForResult(myIntent, 0);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
            // A propos
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
