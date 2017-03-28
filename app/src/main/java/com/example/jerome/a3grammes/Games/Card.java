package com.example.jerome.a3grammes.Games;

import com.example.jerome.a3grammes.Interface.Cards;
import com.example.jerome.a3grammes.R;

/**
 * Created by Jerome on 22/02/2017.
 */

public class Card {
    private int valeur = -1;
    private String couleur = null;

    public Card(int valeur, String couleur){
        this.valeur = valeur ;
        this.couleur = couleur ;
    }

    public int getValeur() {
        return valeur;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public void setValeur(int valeur) {

        this.valeur = valeur;
    }

    public int getDrawable(){

        if(valeur!=-1 && couleur!=null) {

            if (couleur == Cards.CLUBS) {

                if (valeur == 1) return R.drawable.ace_of_clubs;
                else if (valeur == 2) return R.drawable.two_of_clubs;
                else if (valeur == 3) return R.drawable.three_of_clubs;
                else if (valeur == 4) return R.drawable.four_of_clubs;
                else if (valeur == 5) return R.drawable.five_of_clubs;
                else if (valeur == 6) return R.drawable.six_of_clubs;
                else if (valeur == 7) return R.drawable.seven_of_clubs;
                else if (valeur == 8) return R.drawable.eight_of_clubs;
                else if (valeur == 9) return R.drawable.nine_of_clubs;
                else if (valeur == 10) return R.drawable.ten_of_clubs;
                else if (valeur == 11) return R.drawable.jack_of_clubs2;
                else if (valeur == 12) return R.drawable.queen_of_clubs2;
                else if (valeur == 13) return R.drawable.king_of_clubs2;
                else return -1;

            } else if (couleur == Cards.SPADES) {

                if (valeur == 1) return R.drawable.ace_of_spades2;
                else if (valeur == 2) return R.drawable.two_of_spades;
                else if (valeur == 3) return R.drawable.three_of_spades;
                else if (valeur == 4) return R.drawable.four_of_spades;
                else if (valeur == 5) return R.drawable.five_of_spades;
                else if (valeur == 6) return R.drawable.six_of_spades;
                else if (valeur == 7) return R.drawable.seven_of_spades;
                else if (valeur == 8) return R.drawable.eight_of_spades;
                else if (valeur == 9) return R.drawable.nine_of_spades;
                else if (valeur == 10) return R.drawable.ten_of_spades;
                else if (valeur == 11) return R.drawable.jack_of_spades2;
                else if (valeur == 12) return R.drawable.queen_of_spades2;
                else if (valeur == 13) return R.drawable.king_of_spades2;
                else return -1;

            } else if (couleur == Cards.DIAMONDS) {

                if (valeur == 1) return R.drawable.ace_of_diamonds;
                else if (valeur == 2) return R.drawable.two_of_diamonds;
                else if (valeur == 3) return R.drawable.three_of_diamonds;
                else if (valeur == 4) return R.drawable.four_of_diamonds;
                else if (valeur == 5) return R.drawable.five_of_diamonds;
                else if (valeur == 6) return R.drawable.six_of_diamonds;
                else if (valeur == 7) return R.drawable.seven_of_diamonds;
                else if (valeur == 8) return R.drawable.eight_of_diamonds;
                else if (valeur == 9) return R.drawable.nine_of_diamonds;
                else if (valeur == 10) return R.drawable.ten_of_diamonds;
                else if (valeur == 11) return R.drawable.jack_of_diamonds2;
                else if (valeur == 12) return R.drawable.queen_of_diamonds2;
                else if (valeur == 13) return R.drawable.king_of_diamonds2;
                else return -1;

            } else if (couleur == Cards.HEARTS) {

                if (valeur == 1) return R.drawable.ace_of_hearts;
                else if (valeur == 2) return R.drawable.two_of_hearts;
                else if (valeur == 3) return R.drawable.three_of_hearts;
                else if (valeur == 4) return R.drawable.four_of_hearts;
                else if (valeur == 5) return R.drawable.five_of_hearts;
                else if (valeur == 6) return R.drawable.six_of_hearts;
                else if (valeur == 7) return R.drawable.seven_of_hearts;
                else if (valeur == 8) return R.drawable.eight_of_hearts;
                else if (valeur == 9) return R.drawable.nine_of_hearts;
                else if (valeur == 10) return R.drawable.ten_of_hearts;
                else if (valeur == 11) return R.drawable.jack_of_hearts2;
                else if (valeur == 12) return R.drawable.queen_of_hearts2;
                else if (valeur == 13) return R.drawable.king_of_hearts2;
                else return -1;

            } else {
                return -1;
            }

        }else
            return -1;
    }

}
