package com.example.jerome.a3grammes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.jerome.a3grammes.Rules.TTBRules;
import com.example.jerome.a3grammes.Rules.PicoloRules;
import com.example.jerome.a3grammes.Rules.RedOrBlackRules;
import com.example.jerome.a3grammes.Settings.TTBSettings;
import com.example.jerome.a3grammes.Settings.PicoloSettings;
import com.example.jerome.a3grammes.Settings.RedOrBlackSettings;

public class GamesMenu extends AppCompatActivity implements View.OnClickListener{

    FrameLayout picolo_rules, more_or_less_rules, red_or_black_rules ;
    LinearLayout picolo, more_or_less, red_or_black ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_menu);

        //Boutons pour choisir le jeu
        picolo = (LinearLayout) findViewById(R.id.picolo_button);
        picolo_rules = (FrameLayout) findViewById(R.id.picolo_rules_button);
        more_or_less = (LinearLayout) findViewById(R.id.ttb_button);
        more_or_less_rules = (FrameLayout) findViewById(R.id.ttb_rules_button);
        red_or_black = (LinearLayout) findViewById(R.id.red_or_black_button);
        red_or_black_rules = (FrameLayout) findViewById(R.id.red_or_black_rules_button);

        //Ajout des listeners aux boutons
        picolo.setOnClickListener(this);
        picolo_rules.setOnClickListener(this);
        more_or_less.setOnClickListener(this);
        more_or_less_rules.setOnClickListener(this);
        red_or_black.setOnClickListener(this);
        red_or_black_rules.setOnClickListener(this);
    }

    // OnClickListeners
    @Override
    public void onClick(View view) {
        //Si on clique sur start picolo
        if(view==picolo){
            Intent myIntent = new Intent(this, PicoloSettings.class);
            startActivityForResult(myIntent, 0);
        }

        // Si on clique sur les règles du picolo
        else if(view==picolo_rules){
            Intent myIntent = new Intent(this, PicoloRules.class);
            startActivityForResult(myIntent, 0);
        }

        //Si on clique sur start more or less
        else if(view==more_or_less){
            Intent myIntent = new Intent(this, TTBSettings.class);
            startActivityForResult(myIntent, 0);
        }

        // Si on clique sur les règles du more or less
        else if(view==more_or_less_rules){
            Intent myIntent = new Intent(this, TTBRules.class);
            startActivityForResult(myIntent, 0);
        }

        //Si on clique sur start red or black
        else if(view==red_or_black){
            Intent myIntent = new Intent(this, RedOrBlackSettings.class);
            startActivityForResult(myIntent, 0);
        }

        // Si on clique sur les règles du red or black
        else if(view==red_or_black_rules){
            Intent myIntent = new Intent(this, RedOrBlackRules.class);
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
