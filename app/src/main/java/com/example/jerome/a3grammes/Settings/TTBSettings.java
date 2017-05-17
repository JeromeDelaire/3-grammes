package com.example.jerome.a3grammes.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jerome.a3grammes.Games.Global.Player;
import com.example.jerome.a3grammes.Games.TTB.TTB;
import com.example.jerome.a3grammes.GamesMenu;
import com.example.jerome.a3grammes.R;
import com.example.jerome.a3grammes.Rules.TTBRules;

import java.util.ArrayList;

/**
 * Created by Jerome on 22/02/2017.
 */

public class TTBSettings extends AppCompatActivity {
    private LinearLayout root ;
    private RelativeLayout mainLayout ;
    private  TextView nb_players ;
    private LinearLayout toHide ;
    private int animationSpeed = 100;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ttb_settings);

        root = (LinearLayout) findViewById(R.id.names_layout);
        mainLayout = (RelativeLayout) findViewById(R.id.main_layout_settings);
        nb_players = (TextView) findViewById(R.id.nb_players_text_view);
        toHide = (LinearLayout) findViewById(R.id.menu_to_hide);

        // Ajout de deux joueurs de départ
        myEditText();
        myEditText();

        // Indique le nombre de joueurs
        nb_players.setText(String.format(getResources().getString(R.string.nb_players), root.getChildCount()));

        // Bouton pour ajouter des joueurs
        final ImageButton addPlayer = (ImageButton) findViewById(R.id.ib_add_player);
        addPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myEditText();
                nb_players.setText(String.format(getResources().getString(R.string.nb_players), root.getChildCount()));
            }
        });

        // Bouton pour retirer des joueurs
        final ImageButton removePlayer = (ImageButton) findViewById(R.id.ib_remove_player);
        removePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(root.getChildCount()>2)
                    root.removeViewAt(root.getChildCount()-1);
                nb_players.setText(String.format(getResources().getString(R.string.nb_players), root.getChildCount()));
            }
        });

        // Bouton pour lancer la partie
        final LinearLayout runGame = (LinearLayout) findViewById(R.id.but_run_game);
        runGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // On vérifie que tous les noms on étés rentrés
                boolean fill = allChildComplete() ;

                // On lance une partie si les noms sont tous là
                if(fill){
                    runGame(view);
                }else
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_empty_tags), Toast.LENGTH_SHORT).show();
            }
        });

        // Listener sur le clavier
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
                        show();
                    }

                    else if(bottom<oldBottom){
                        // Keyboard Open
                        hide();
                    }

                }else{
                    // show
                }
            }
        });


    }

    /*
     * Crée les joueur et Lance une partie
     */
    private void runGame(View view) {
        ArrayList<Player> players = new ArrayList<>();

        // Création des joueurs
        for(int i=0 ; i<root.getChildCount() ; i++){
            EditText name = (EditText) root.getChildAt(i);
            players.add(new Player(name.getText().toString()));
        }

        // Lancement de l'activité
        Intent myIntent = new Intent(view.getContext(), TTB.class);
        myIntent.putParcelableArrayListExtra("players", players);
        startActivityForResult(myIntent, 0);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    // Listener sur les boutons de la barre d'action
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_rules:
                // Règles du red or black
                Intent myIntent = new Intent(this.getApplicationContext(), TTBRules.class);
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

    // Crée un champ pour un prénom de joueur
    public EditText myEditText(){
        EditText editText = new EditText(this);
        editText.setSingleLine(true);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                // Si on clique sur "Done" (sur le clavier)
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {
                    ScrollView sv = (ScrollView) findViewById(R.id.sv_name);

                    if(!allChildComplete()){
                        int child = indexOfNextEmptyChild();
                        sv.scrollBy(0, root.getChildAt(child).getHeight()*child);
                        root.getChildAt(child).requestFocus();
                    }
                    else runGame(view);

                    return true;
                }
                return false;
            }
        });

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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

    // Cache une partie de l'activité quand le clavier s'ouvre
    public void hide(){
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0 , 0, -toHide.getHeight());
        translateAnimation.setDuration(animationSpeed);
        translateAnimation.setFillAfter(true);
        TranslateAnimation translateAnimation2 = new TranslateAnimation(0, 0 , 0, -toHide.getHeight());
        translateAnimation2.setDuration(animationSpeed);
        translateAnimation2.setFillAfter(false);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                toHide.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        toHide.startAnimation(translateAnimation);
        ScrollView scrollView = (ScrollView) findViewById(R.id.sv_name);
        scrollView.startAnimation(translateAnimation2);
        scrollView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
    }

    public void show(){
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0 , -toHide.getHeight(), 0);
        translateAnimation.setDuration(animationSpeed);
        translateAnimation.setFillAfter(true);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                toHide.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ScrollView scrollView = (ScrollView) findViewById(R.id.sv_name);
        scrollView.startAnimation(translateAnimation);
        toHide.startAnimation(translateAnimation);
    }


    private int indexOfNextEmptyChild() {
        int i = -1 ;
        EditText editText ;
        do {
            i++;
            editText = (EditText) root.getChildAt(i);
        }while (editText.getText().length() != 0);
        return i;
    }

    private boolean allChildComplete(){
        boolean fill = true ;
        for(int i=0 ; i<root.getChildCount();i++){
            EditText editText = (EditText) root.getChildAt(i);
            if(editText.getText().length()==0)
                fill = false ;
        }
        return fill ;
    }

}
