package com.example.jerome.a3grammes.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.jerome.a3grammes.GamesMenu;
import com.example.jerome.a3grammes.R;
import com.example.jerome.a3grammes.Rules.PicoloRules;

/**
 * Created by Jerome on 22/02/2017. */

public class PicoloSettings extends AppCompatActivity {
    LinearLayout root ;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.main_layout_settings);
        rl.setBackgroundResource(R.color.primary_picolo);

        TextView title = (TextView) findViewById(R.id.tv_title_settings);
        title.setText(R.string.picolo);

        root = (LinearLayout) findViewById(R.id.names_layout);

        // Ajout de deux joueurs de départ
        myEditText();
        myEditText();

        // Bouton pour ajouter des joueurs
        ImageButton addPlayer = (ImageButton) findViewById(R.id.ib_add_player);
        addPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myEditText();
                ScrollView scroll = (ScrollView) findViewById(R.id.sv_name);
                scroll.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });

        // Bouton pour retirer des joueurs
        ImageButton removePlayer = (ImageButton) findViewById(R.id.ib_remove_player);
        removePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(root.getChildCount()>2)
                    root.removeViewAt(root.getChildCount()-1);
                ScrollView scroll = (ScrollView) findViewById(R.id.sv_name);
                scroll.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_rules:
                // Règles du picolo
                Intent myIntent = new Intent(this, PicoloRules.class);
                startActivityForResult(myIntent, 0);
                return true;

            case R.id.action_home:
                // Retour au menu principal
                Intent homeIntent = new Intent(this, GamesMenu.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public EditText myEditText(){
        EditText editText = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        int dim = (int) getResources().getDimension(R.dimen.edit_text_name_margin);
        lp.setMargins(dim, 0, dim, 0);
        editText.setHint(getResources().getString(R.string.player) + " " + (root.getChildCount()+1));
        root.addView(editText, root.getChildCount(), lp);
        return editText ;
    }
}
