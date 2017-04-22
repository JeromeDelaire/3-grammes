package com.example.jerome.a3grammes.Games.RedOrBlack;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jerome.a3grammes.Games.Cards.CardSet;
import com.example.jerome.a3grammes.Games.Global.Player;
import com.example.jerome.a3grammes.Interface.Cards;
import com.example.jerome.a3grammes.Interface.Constants;
import com.example.jerome.a3grammes.R;

import java.util.ArrayList;

/**
 * Created by Jerome on 22/02/2017.*/

public class RedOrBlack extends AppCompatActivity{
    private ImageView card[] ;
    private FrameLayout frame[];
    private TextView question ;
    private ArrayList<Player> players ;
    private CardSet cards ;
    private int actualRound = 1 ;
    private int actualPlayer = 0;
    private LinearLayout buttonLayout ;
    private int animationSpeed = 500 ;
    private boolean pyramid ;

    private Context context ;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_or_black);

        context = this.getApplicationContext() ;

        question = (TextView) findViewById(R.id.question_rob);
        buttonLayout = (LinearLayout) findViewById(R.id.button_layout_rob);

        // Au lacement de la partie
        if(savedInstanceState==null){
            Bundle b = getIntent().getExtras();
            // Récupération des joueurs
            players = b.getParcelableArrayList("data");
            // Création et mélange des cartes
            int setNumber = 1 ;
            while ((10 + 5*players.size())>(52*setNumber))
                setNumber ++ ;
            if(setNumber>1) Toast.makeText(this, String.format(getResources().getString(R.string.alert_number_of_set), setNumber), Toast.LENGTH_LONG).show();
            cards = new CardSet(setNumber);
            cards.shuffle();
            pyramid = b.getBoolean("pyramid");
        }

        // Après dévérouillage du téléphone on récupère les éléments sauvegardés
        else{
            players = savedInstanceState.getParcelableArrayList("players");
            cards = savedInstanceState.getParcelable("card_set");
            actualRound = savedInstanceState.getInt("actualRound");
            actualPlayer = savedInstanceState.getInt("actualPlayer");
            pyramid = savedInstanceState.getBoolean("pyramid");
        }

        // Emplacements pours les 5 cartes
        card = new ImageView[5];
        card[0] = (ImageView) findViewById(R.id.rob_card1);
        card[1] = (ImageView) findViewById(R.id.rob_card2);
        card[2] = (ImageView) findViewById(R.id.rob_card3);
        card[3] = (ImageView) findViewById(R.id.rob_card4);
        card[4] = (ImageView) findViewById(R.id.rob_card5);

        // Frames contenant les cartes
        frame = new FrameLayout[5];
        frame[0] = (FrameLayout) findViewById(R.id.rob_frame_card1);
        frame[1] = (FrameLayout) findViewById(R.id.rob_frame_card2);
        frame[2] = (FrameLayout) findViewById(R.id.rob_frame_card3);
        frame[3] = (FrameLayout) findViewById(R.id.rob_frame_card4);
        frame[4] = (FrameLayout) findViewById(R.id.rob_frame_card5);

        if(players!=null)
            runRound();
        /*Intent myIntent = new Intent(this, Pyramid.class);
        startActivityForResult(myIntent, 0);*/
    }

    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        // on récupère le statut de retour de l'activité 2 c'est à dire l'activité numéro 1000
        if(requestCode==Constants.PYRAMID_CODE){
            // si le code de retour est égal à 1 on stoppe l'activité 1
            if(resultCode==1){
                finish();
            }
        }
        super.onActivityResult (requestCode, resultCode, data);
    }

    /*
     * Sauvegarde l'état du jeu quand on vérouille le téléphone
     */
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList("players", players);
        savedInstanceState.putParcelable("card_set", cards);
        savedInstanceState.putInt("actualRound", actualRound);
        savedInstanceState.putInt("actualPlayer", actualPlayer);
        savedInstanceState.putBoolean("pyramid", pyramid);
    }

    /*
     * Première question : Rouge ou Noir ?
     */
    private void round1(final Player player){
        buttonLayout.removeAllViews();
        // Affichage des cartes
        displayCards(player);

        question.setText(players.get(actualPlayer).getName() + getResources().getString(R.string.rob_question1));

        ArrayList<Button> buttons = new ArrayList<>();

        // Création du bouton Rouge
        final Button red = new Button(this);
        red.setText(R.string.rob_red);
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.addCard(cards.take());
                displayLastCard(winRound1(player, red), player);
            }
        });

        //Création du bouton noir
        final Button black = new Button(this);
        black.setText(R.string.rob_black);
        black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.addCard(cards.take());
                displayLastCard(winRound1(player, black), player);

            }
        });

        //Ajout des boutons dans le layout
        buttons.add(red);
        buttons.add(black);
        createButtonLayout(buttons);

    }

    /*
     * 2ème question : En-dessous ou Au-dessus ?
     */
    private void round2(final Player player){
        buttonLayout.removeAllViews();
        // Affichage des cartes
        displayCards(player);

        ArrayList<Button> buttons = new ArrayList<>();

        // Création du bouton Au-dessus
        final Button upper = new Button(this);
        upper.setText(R.string.rob_upper);
        upper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.addCard(cards.take());
                displayLastCard(winRound2(player, upper), player);
            }
        });

        //Création du bouton En-dessous
        final Button under = new Button(this);
        under.setText(R.string.rob_under);
        under.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.addCard(cards.take());
                displayLastCard(winRound2(player, under), player);
            }
        });

        //Ajout des boutons dans le layout
        buttons.add(under);
        buttons.add(upper);
        createButtonLayout(buttons);

        question.setText(players.get(actualPlayer).getName() + getResources().getString(R.string.rob_question2));
    }

    /*
     * 3ème question :  Intérieur ou Extérieur ?
     */
    private void round3(final Player player){
        buttonLayout.removeAllViews();
        // Affichage des cartes
        displayCards(player);

        ArrayList<Button> buttons = new ArrayList<>();

        // Création du bouton Intérieur
        final Button between = new Button(this);
        between.setText(R.string.rob_between);
        between.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.addCard(cards.take());
                displayLastCard(winRound3(player, between), player);
            }
        });

        //Création du bouton Extérieur
        final Button outside = new Button(this);
        outside.setText(R.string.rob_outside);
        outside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.addCard(cards.take());
                displayLastCard(winRound3(player, outside), player);
            }
        });

        //Ajout des boutons dans le layout
        buttons.add(outside);
        buttons.add(between);
        createButtonLayout(buttons);

        question.setText(players.get(actualPlayer).getName() + getResources().getString(R.string.rob_question3));
    }

    /*
     * 4ème question : Trèfle, Carreau, Coeur ou Pique ?
     */
    private void round4(final Player player){
        buttonLayout.removeAllViews();
        // Affichage des cartes
        displayCards(player);

        ArrayList<Button> buttons = new ArrayList<>();

        // Création du bouton Trèfle
        final Button clubs = new Button(this);
        clubs.setText(R.string.rob_clubs);
        clubs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.addCard(cards.take());
                displayLastCard(winRound4(player, clubs), player);
            }
        });

        //Création du bouton carreau
        final Button diamonds = new Button(this);
        diamonds.setText(R.string.rob_diamonds);
        diamonds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.addCard(cards.take());
                displayLastCard(winRound4(player, diamonds),player);
            }
        });

        //Création du bouton Coeur
        final Button hearts = new Button(this);
        hearts.setText(R.string.rob_hearts);
        hearts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.addCard(cards.take());
                displayLastCard(winRound4(player, hearts),player);
            }
        });

        //Création du bouton Pique
        final Button spades = new Button(this);
        spades.setText(R.string.rob_spades);
        spades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.addCard(cards.take());
                displayLastCard(winRound4(player, spades),player);
            }
        });

        //Ajout des boutons dans le layout
        buttons.add(clubs);
        buttons.add(hearts);
        buttons.add(diamonds);
        buttons.add(spades);
        createButtonLayout(buttons);

        question.setText(players.get(actualPlayer).getName() + getResources().getString(R.string.rob_question4));
    }

    /*
     * 5ème question : Paire, Impaire, Ou Tête ?
     */
    private void round5(final Player player){
        buttonLayout.removeAllViews();
        // Affichage des cartes
        displayCards(player);

        ArrayList<Button> buttons = new ArrayList<>();

        // Création du bouton Paire
        final Button pair = new Button(this);
        pair.setText(R.string.rob_pair);
        pair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.addCard(cards.take());
                displayLastCard(winRound5(player, pair), player);
            }
        });

        //Création du bouton Impaire
        final Button odd = new Button(this);
        odd.setText(R.string.rob_odd);
        odd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.addCard(cards.take());
                displayLastCard(winRound5(player, odd), player);
            }
        });

        //Création du bouton Tête
        final Button face = new Button(this);
        face.setText(R.string.rob_face);
        face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.addCard(cards.take());
                displayLastCard(winRound5(player, face),player);
            }
        });

        //Ajout des boutons dans le layout
        buttons.add(pair);
        buttons.add(face);
        buttons.add(odd);
        createButtonLayout(buttons);

        question.setText(players.get(actualPlayer).getName() + getResources().getString(R.string.rob_question5));
    }

    // Met en forme le layout des boutons
    private void createButtonLayout(ArrayList<Button> buttons){

        for(int i=0 ; i<buttons.size() ; i++){

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    1f
            );
            int dim = (int) getResources().getDimension(R.dimen.primary_margin);
            layoutParams.setMargins(dim, dim, dim, dim);
            buttonLayout.addView(buttons.get(i), layoutParams);
        }
    }

    // Met en forme le layout des cartes
    private void createCardLayout(Player player){
        for(int i=0 ; i<player.getCards().size() ; i++){

        }
    }

    /*
     * Capture le bouton retour
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Si on appuie sur le bouton retour
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /*
      * Ouvre une fenêtre d'alerte lorsqu'on appuis sur le bouton retour
      */
    protected void exitByBackKey() {
        new AlertDialog.Builder(this, R.style.MyAlertDialogTheme)
                .setMessage(getResources().getString(R.string.alert_exit_game))
                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();
    }

    /*
     * Affiche les cartes du joueur passé en paramètre avec une petite animation.
     * Annonce le résultat à la fin de l'animation
     */
    private void displayCards(final Player player){

        for (int i=4 ; i>=0 ; i--){
            int drawable ;

            // Change les backgrnouds pas les bonnes cartes
            if(player.getCards().size()>i && (drawable=player.getCards().get(i).getDrawable())!=-1)
                card[i].setBackgroundResource(drawable);
            else{
                card[i].setBackgroundResource(R.color.primary_red_or_black);
            }

            // Lance les animations
            TranslateAnimation animation = new TranslateAnimation(-(frame[i].getX()+frame[i].getWidth()) , 0, 0, 0);
            animation.setDuration(animationSpeed);
            animation.setFillAfter(true);
            frame[i].startAnimation(animation);
        }
    }

    /*
     * Affiche la dernière carte tirée avec une animation
     * Affiche le résultat d'une alerte Dialog
     */
    private void displayLastCard(final boolean winRound, final Player player){
        enableButtons(false);

        int nbCards = player.getCards().size()-1;

        // Change le backgrnoud pas la bonne carte
        card[nbCards].setBackgroundResource(player.getCards().get(nbCards).getDrawable());

        // Lance l'animation
        TranslateAnimation animation = new TranslateAnimation(-(frame[nbCards].getX()+frame[nbCards].getWidth()) , 0, 0, 0);
        animation.setDuration(animationSpeed);
        animation.setFillAfter(true);

        // Listener sur l'animation
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            // Quand l'animation se termine, on affiche le résultat
            @Override
            public void onAnimationEnd(Animation animation) {
                openDialog(winRound, player);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        frame[nbCards].startAnimation(animation);
    }

    /*
    * Retire les cartes du joueur passé en paramètre avec une petite animation.
    */
    private void removeCards(Player player){

        enableButtons(false);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        for(int i=0 ; i<5 ; i++){
            TranslateAnimation animation = new TranslateAnimation(0, -(frame[i].getX()+frame[i].getWidth()), 0, 0);
            animation.setDuration(animationSpeed);
            animation.setFillAfter(true);

            if(i==4){
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        runRound();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }

            frame[i].startAnimation(animation);
        }
    }

    /*
     * Affiche une boîte de dialogue qui indique le nombre de gorgés à boire ou à donner
     */
    public void openDialog(boolean win, final Player player){

        // Si le joueur a gagné
        if(win){

            new AlertDialog.Builder(this, R.style.MyAlertDialogTheme)
                    .setMessage(String.format(getResources().getString(R.string.rob_win), player.getName(), actualRound))
                    .setCancelable(false)
                    .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            // Si c'est le dernier joueur du round
                            if(actualPlayer==players.size()-1){
                                actualPlayer=0;
                                // Si c'étais le dernier round
                                if(actualRound==5) {
                                    // Pas de pyramide
                                    if (!pyramid)
                                        finish();
                                        // On lance une pyramide
                                    else {
                                        Intent myIntent = new Intent(context, Pyramid.class);
                                        myIntent.putParcelableArrayListExtra("players", players);
                                        myIntent.putExtra("cardSet", cards);
                                        startActivityForResult(myIntent, Constants.PYRAMID_CODE);
                                    }
                                }
                                else{
                                    actualRound++ ;
                                    removeCards(player);
                                }

                            }
                            // Sinon
                            else{
                                actualPlayer++;
                                removeCards(player);
                            }

                        }
                    }).show();
        }

        // Si le joueur a perdu
        else{

            new AlertDialog.Builder(this, R.style.MyAlertDialogTheme)
                    .setMessage(String.format(getResources().getString(R.string.rob_lose), player.getName(), actualRound))
                    .setCancelable(false)
                    .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {

                            // Si c'est le dernier joueur du round on lance le round suivant
                            if(actualPlayer==players.size()-1){
                                actualPlayer=0;
                                // Si c'étais le dernier round
                                if(actualRound==5){
                                    // Pas de pyramide
                                    if(!pyramid)
                                        finish();
                                        // On lance une pyramide
                                    else{
                                        Intent myIntent = new Intent(context, Pyramid.class);
                                        myIntent.putParcelableArrayListExtra("players", players);
                                        myIntent.putExtra("cardSet", cards);
                                        startActivityForResult(myIntent, Constants.PYRAMID_CODE);
                                    }
                                }
                                else{
                                    actualRound++ ;
                                    removeCards(player);
                                }

                            }
                            // Sinon
                            else{
                                actualPlayer++;
                                removeCards(player);
                            }

                        }
                    }).show();
        }
    }

    /*
     * Détermine quelle round lancer
     */
    public void runRound(){
        for(int i=0 ; i<players.size() ; i++)
            removeCards(players.get(i));
        if(actualRound==1)round1(players.get(actualPlayer));
        else if(actualRound==2)round2(players.get(actualPlayer));
        else if(actualRound==3)round3(players.get(actualPlayer));
        else if(actualRound==4)round4(players.get(actualPlayer));
        else if(actualRound==5)round5(players.get(actualPlayer));
    }

    /*
     * Retourne vrai si le joueur a gangé le round 1, faux sinon
     */
    public boolean winRound1(Player player, Button button){
        // Si le joueur a cliqué sur rouge
        if(button.getText()==getResources().getString(R.string.rob_red)){
            return (player.getCards().get(0).getCouleur()== Cards.DIAMONDS || player.getCards().get(0).getCouleur() == Cards.HEARTS) ;
        }
        // Si le joueur a cliqué sur noir
        else{
            return (player.getCards().get(0).getCouleur()== Cards.CLUBS || player.getCards().get(0).getCouleur() == Cards.SPADES) ;
        }
    }

    /*
    * Retourne vrai si le joueur a gangé le round 2, faux sinon
    */
    public boolean winRound2(Player player, Button button){
        // Si le joueur a cliqué sur au-dessus
        if(button.getText()==getResources().getString(R.string.rob_upper)){
            return (player.getCards().get(1).getValeur()>player.getCards().get(0).getValeur()) ;
        }
        // Si le joueur a cliqué sur en-dessous
        else{
            return (player.getCards().get(1).getValeur()<player.getCards().get(0).getValeur()) ;
        }
    }

    /*
    * Retourne vrai si le joueur a gangé le round 3, faux sinon
    */
    public boolean winRound3(Player player, Button button){
        // Si le joueur a cliqué sur Interieur
        if(button.getText()==getResources().getString(R.string.rob_between)){
            return (player.getCards().get(2).getValeur()<player.getMax() && player.getCards().get(2).getValeur()>player.getMin());
        }
        // Si le joueur a cliqué sur Exterieur
        else{
            return (player.getCards().get(2).getValeur()>player.getMax() || player.getCards().get(2).getValeur()<player.getMin());
        }
    }

    /*
    * Retourne vrai si le joueur a gangé le round 4, faux sinon
    */
    public boolean winRound4(Player player, Button button){
        // Si le joueur a cliqué sur trèfle
        if(button.getText()==getResources().getString(R.string.rob_clubs)){
            return (player.getCards().get(3).getCouleur()==Cards.CLUBS);
        }
        // Si le joueur a cliqué sur Carreau
        else if(button.getText()==getResources().getString(R.string.rob_diamonds)){
            return (player.getCards().get(3).getCouleur()==Cards.DIAMONDS);
        }
        // Si le joueur a cliqué sur Coeur
        else if(button.getText()==getResources().getString(R.string.rob_hearts)){
            return (player.getCards().get(3).getCouleur()==Cards.HEARTS);
        }
        // Si le joueur a cliqué sur Pique
        else{
            return (player.getCards().get(3).getCouleur()==Cards.SPADES);
        }

    }

    /*
* Retourne vrai si le joueur a gangé le round 5, faux sinon
*/
    public boolean winRound5(Player player, Button button){
        // Si le joueur a cliqué sur Tête
        if(button.getText()==getResources().getString(R.string.rob_face)){
            return (player.getCards().get(4).getValeur()>10);
        }
        // Si le joueur a cliqué sur pair
        else if(button.getText()==getResources().getString(R.string.rob_pair)){
            return (player.getCards().get(4).getValeur()%2==0 && player.getCards().get(4).getValeur()<=10);
        }
        // Si le joueur a cliqué sur impair
        else {
            return (player.getCards().get(4).getValeur()%2==1 && player.getCards().get(4).getValeur()<=10);
        }
    }

    /*
     * Déactive/active les boutons de jeux
     */
    private void enableButtons(boolean enable){
        for (int i=0 ; i<buttonLayout.getChildCount() ; i++){
            buttonLayout.getChildAt(i).setEnabled(enable);
        }
    }
}