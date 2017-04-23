package com.example.jerome.a3grammes.Games.RedOrBlack;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.jerome.a3grammes.Games.Cards.Card;
import com.example.jerome.a3grammes.Games.Cards.CardSet;
import com.example.jerome.a3grammes.Games.Global.Player;
import com.example.jerome.a3grammes.Global.Screen;
import com.example.jerome.a3grammes.R;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Jerome on 30/03/2017.
 */

public class Pyramid extends AppCompatActivity {
    private ImageView[][] cards = new ImageView[4][];
    private RelativeLayout[] row ;
    private CardSet cardSet ;
    private ArrayList<Player> players ;
    private LinearLayout linearLayout ;
    private int cards_flipped = 0 ;

    int size_cards_factor = 5;
    int flipping_speed = 250 ;
    boolean give_or_take = false ;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pyramid);

        // Au lacement de la partie
        if(savedInstanceState==null){
            Bundle b = getIntent().getExtras();
            // Récupération des joueurs
            players = b.getParcelableArrayList("players");
            // Récupération du ou des jeux de carte
            cardSet = b.getParcelable("cardSet");
        }

        // Après dévérouillage du téléphone on récupère les éléments sauvegardés
        else{
            players = savedInstanceState.getParcelableArrayList("players");
            cardSet = savedInstanceState.getParcelable("cards");
        }

        // Création d'un tableau en forme de pyramide
        for(int i=0 ; i<4 ; i++){
            cards[i] = new ImageView[i+1];
        }

        // Lignes de la pyramide
        row = new RelativeLayout[4];
        row[0] = (RelativeLayout) findViewById(R.id.pyramid_row1);
        row[1] = (RelativeLayout) findViewById(R.id.pyramid_row2);
        row[2] = (RelativeLayout) findViewById(R.id.pyramid_row3);
        row[3] = (RelativeLayout) findViewById(R.id.pyramid_row4);

        linearLayout = (LinearLayout) findViewById(R.id.pyramid_linear_layout);

        createPyramid();

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seeCard();
            }
        });

    }

    private void seeCard() {
        cards_flipped++;

        if(cards_flipped==1) flippingCard(3, 0, cardSet.take());
        if(cards_flipped==2) flippingCard(3, 1, cardSet.take());
        if(cards_flipped==3) flippingCard(3, 2, cardSet.take());
        if(cards_flipped==4) flippingCard(3, 3, cardSet.take());
        if(cards_flipped==5) flippingCard(2, 0, cardSet.take());
        if(cards_flipped==6) flippingCard(2, 1, cardSet.take());
        if(cards_flipped==7) flippingCard(2, 2, cardSet.take());
        if(cards_flipped==8) flippingCard(1, 0, cardSet.take());
        if(cards_flipped==9) flippingCard(1, 1, cardSet.take());
        if(cards_flipped==10) flippingCard(0, 0, cardSet.take());
    }

    /*
    * Sauvegarde l'état du jeu quand on vérouille le téléphone
    */
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList("players", players);
        savedInstanceState.putParcelable("cards", cardSet);
    }

    /*
     * Crée la pyramide
     */
    private void createPyramid(){
        int id = 0 ;
        // Récupère la taille de l'écran
        int max_size = Screen.getScreenWidth(this);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER ;

        for(int i=0 ; i<row.length ; i++){
            row[i].setLayoutParams(layoutParams);

            for(int j=0 ; j<cards[i].length ; j++){

                cards[i][j] = new ImageView(this);
                cards[i][j].setId(id);
                id ++ ;
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(max_size/size_cards_factor, (int) ((max_size/size_cards_factor)*1.52));

                if(j!=0)
                    params.addRule(RelativeLayout.RIGHT_OF, cards[i][j-1].getId());

                int margin = (Screen.getScreenHeight(this) - ((int) ((max_size/size_cards_factor)*1.52)*row.length))/(2*2*row.length) ;
                int sideMargin = (Screen.getScreenWidth(this) - ((max_size/size_cards_factor)*cards[cards.length-1].length))/(2*cards[cards.length-1].length);
                params.setMargins(sideMargin, margin, sideMargin, margin);
                cards[i][j].setLayoutParams(params);
                cards[i][j].setBackgroundResource(R.drawable.back_card);
                row[i].addView(cards[i][j]);

            }

        }

    }

    private void flippingCard(final int row, final int column, final Card card){

        final Drawable d = getResources().getDrawable(card.getDrawable());
        final ObjectAnimator animation = ObjectAnimator.ofFloat(cards[row][column], "rotationY", 90f);  // HERE 360 IS THE ANGLE OF ROTATE, YOU CAN USE 90, 180 IN PLACE OF IT,  ACCORDING TO YOURS REQUIREMENT
        animation.setDuration(flipping_speed);
        animation.setInterpolator(new AccelerateInterpolator());

        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

                cards[row][column].setBackground(d);

                ObjectAnimator animation2 = ObjectAnimator.ofFloat(cards[row][column], "rotationY", -90f);  // HERE 360 IS THE ANGLE OF ROTATE, YOU CAN USE 90, 180 IN PLACE OF IT,  ACCORDING TO YOURS REQUIREMENT
                animation2.setDuration(0);
                animation2.start();
                ObjectAnimator animation = ObjectAnimator.ofFloat(cards[row][column], "rotationY", 0f);  // HERE 360 IS THE ANGLE OF ROTATE, YOU CAN USE 90, 180 IN PLACE OF IT,  ACCORDING TO YOURS REQUIREMENT
                animation.setDuration(flipping_speed);
                animation.setInterpolator(new DecelerateInterpolator());

                animation.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    // A la fin de l'animation on annonce les résultats
                    @Override
                    public void onAnimationEnd(Animator animator) {
                        openDialog(give_or_take, row, card);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                animation.start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        animation.start();
    }

    /*
     * Ouvre une boîte de dialogue qui annonce les gorgées
     */
    private void openDialog(boolean give, int row, Card card) {

        String give_take = "";
        String result = "";
        int number = 0;

        // Si on doit donner
        if (give)
            give_take = getResources().getString(R.string.give);
            // Si on doit prendre
        else
            give_take = getResources().getString(R.string.take);

        // Pour chaque joueur, on regarde combien de fois ils ont la carte
        for (int i = 0; i < players.size(); i++) {
            if ((number = getNumberOfCard(players.get(i), card)) > 0)
                result += String.format(getResources().getString(R.string.pyramid_result),
                        players.get(i).getName(),
                        number,
                        getResources().getString(card.getName()),
                        give_take,
                        (4-row) * number) + "\n\n";
        }

        if (!Objects.equals(result, "")) {
            new AlertDialog.Builder(this, R.style.MyAlertDialogTheme)
                    .setMessage(result)
                    .setCancelable(false)
                    .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        // Si on clique sur OK
                        public void onClick(DialogInterface arg0, int arg1) {
                            if (cards_flipped == 10){
                                setResult(1);
                                finish();
                            }

                            give_or_take = !give_or_take;
                        }
                    }).show();
        }else{
            if(cards_flipped == 10){
                setResult(1);
                finish();
            }

        }
    }

    /*
     * Retourne le nombre de carte que le joueur possède dont la valeur équivaut à la valeur de card
     */
    private int getNumberOfCard(Player player, Card card){
        int number = 0 ;

        for (int i=0 ; i<player.getCards().size() ; i++){
            if(player.getCards().get(i).getValeur() == card.getValeur())
                number ++ ;
        }
        return number ;
    }
}
