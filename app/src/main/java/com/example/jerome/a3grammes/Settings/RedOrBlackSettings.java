package com.example.jerome.a3grammes.Settings;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jerome.a3grammes.Games.Player;
import com.example.jerome.a3grammes.Games.RedOrBlack;
import com.example.jerome.a3grammes.GamesMenu;
import com.example.jerome.a3grammes.R;
import com.example.jerome.a3grammes.Rules.RedOrBlackRules;

import java.util.ArrayList;

/**
 * Created by Jerome on 22/02/2017. */

public class RedOrBlackSettings extends AppCompatActivity {
    LinearLayout root ;
    RelativeLayout mainLayout ;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        TextView title = (TextView) findViewById(R.id.tv_title_settings);
        title.setText(R.string.red_or_black);

        root = (LinearLayout) findViewById(R.id.names_layout);
        mainLayout = (RelativeLayout) findViewById(R.id.main_layout_settings);

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
        final ImageButton removePlayer = (ImageButton) findViewById(R.id.ib_remove_player);
        removePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(root.getChildCount()>2)
                    root.removeViewAt(root.getChildCount()-1);
                ScrollView scroll = (ScrollView) findViewById(R.id.sv_name);
                scroll.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });

        // Bouton pour lancer la partie
        final Button runGame = (Button) findViewById(R.id.but_run_game);
        runGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean fill = true ;
                // On vérifie que tous les noms on étés rentrés
                for(int i=0 ; i<root.getChildCount();i++){
                    EditText editText = (EditText) root.getChildAt(i);
                    if(editText.getText().length()==0)
                        fill = false ;
                }

                // On lance une partie si les noms sont tous là
                if(fill){
                    ArrayList<Player> players = new ArrayList<>();

                    // Création des joueurs
                    for(int i=0 ; i<root.getChildCount() ; i++){
                        EditText name = (EditText) root.getChildAt(i);
                        players.add(new Player(name.getText().toString()));
                    }

                    // Lancement de l'activité
                    //Bundle b = new Bundle();
                   // b.putParcelableArrayList(Constants.KEY_BUNDLE_PLAYER, players);
                    Intent myIntent = new Intent(view.getContext(), RedOrBlack.class);
                    myIntent.putParcelableArrayListExtra("data", players);
                    //myIntent.putExtra(Constants.KEY_LIST_PLAYER, b);
                    startActivityForResult(myIntent, 0);
                }else
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_empty_tags), Toast.LENGTH_SHORT).show();
            }
        });

        mainLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

                boolean someHasFocus = false;

                for(int i=0 ;i<root.getChildCount() ; i++){
                    EditText edit = (EditText) root.getChildAt(i);
                    if(edit.hasFocus())
                        someHasFocus = true ;
                }

                if(someHasFocus){
                    if(bottom>oldBottom){
                        // Keyboard Close
                        runGame.setVisibility(View.VISIBLE);
                        ScrollView scroll = (ScrollView) findViewById(R.id.sv_name);
                        scroll.fullScroll(ScrollView.FOCUS_DOWN);

                    }else if(bottom<oldBottom){
                        // Keyboard Open
                        runGame.setVisibility(View.GONE);
                        ScrollView scroll = (ScrollView) findViewById(R.id.sv_name);
                        scroll.fullScroll(ScrollView.FOCUS_DOWN);
                    }

                }else{
                    // show
                    runGame.setVisibility(View.VISIBLE);
                }
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
                // Règles du red or black
                Intent myIntent = new Intent(this.getApplicationContext(), RedOrBlackRules.class);
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
