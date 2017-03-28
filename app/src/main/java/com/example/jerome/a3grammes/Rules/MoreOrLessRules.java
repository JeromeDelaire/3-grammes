package com.example.jerome.a3grammes.Rules;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.jerome.a3grammes.GamesMenu;
import com.example.jerome.a3grammes.R;
import com.example.jerome.a3grammes.Settings.MoreOrLessSettings;

/**
 * Created by Jerome on 22/02/2017.
 */

public class MoreOrLessRules extends AppCompatActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_or_less_rules);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_rules, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                // Retour au menu principal
                Intent homeIntent = new Intent(this, GamesMenu.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                return true;

            case R.id.action_go_back:
                // Retour en arrière
                Intent goBack = new Intent(this, MoreOrLessSettings.class);
                goBack.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(goBack);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
