package com.example.jerome.a3grammes.Games.MoreOrLess;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.jerome.a3grammes.Games.Cards.CardSet;
import com.example.jerome.a3grammes.Games.Global.Player;
import com.example.jerome.a3grammes.Global.Screen;
import com.example.jerome.a3grammes.R;

import java.util.ArrayList;

/**
 * Created by Jerome on 23/04/2017.
 */

public class MoreOrLess extends AppCompatActivity {

    private ArrayList<Player> players ;

    private FrameLayout cardSetLayout ;
    private LinearLayout root ;
    private int screenWidth, cardWidth ;
    private CardSet cardSet ;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_or_less);

        cardSetLayout = (FrameLayout) findViewById(R.id.card_set);
        root = (LinearLayout) findViewById(R.id.main_layout);

        cardSet = new CardSet(1);
        cardSet.shuffle();

        // Au lancement de la partie
        if(savedInstanceState==null){
            screenWidth = Screen.getScreenWidth(this);
            cardWidth = screenWidth/13 ;

            ImageView cardSetIV = new ImageView(this);
            cardSetIV.setBackgroundResource(R.drawable.back_card);
            cardSetIV.setLayoutParams(new FrameLayout.LayoutParams(cardWidth, (int) (1.52*cardWidth)));
            cardSetLayout.addView(cardSetIV);

            Bundle b = getIntent().getExtras();
            // Récupération des joueurs
            players = b.getParcelableArrayList("players");
        }

        // Après dévérouillage du téléphone on récupère les éléments sauvegardés
        else{
            players = savedInstanceState.getParcelableArrayList("players");
        }

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }

    /*
    * Sauvegarde l'état du jeu quand on vérouille le téléphone
    */
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList("players", players);
    }

}
