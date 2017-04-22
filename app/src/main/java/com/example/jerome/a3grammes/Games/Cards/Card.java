package com.example.jerome.a3grammes.Games.Cards;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.jerome.a3grammes.Interface.Cards;
import com.example.jerome.a3grammes.R;

import java.util.Objects;

/**
 * Created by Jerome on 22/02/2017.
 */

public class Card implements Parcelable{
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

    public int getDrawable(){

        if(valeur!=-1 && couleur!=null) {

            if (Objects.equals(couleur, Cards.CLUBS)) {

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

            } else if (Objects.equals(couleur, Cards.SPADES)) {

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

            } else if (Objects.equals(couleur, Cards.DIAMONDS)) {

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

            } else if (Objects.equals(couleur, Cards.HEARTS)) {

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

    /*
     * Retourne le nom de la carte
     */
    public int getName(){

        if(valeur==1) return R.string.as ;
        else if(valeur==2) return R.string.two ;
        else if(valeur==3) return R.string.three ;
        else if(valeur==4) return R.string.four ;
        else if(valeur==5) return R.string.five ;
        else if(valeur==6) return R.string.six ;
        else if(valeur==7) return R.string.seven ;
        else if(valeur==8) return R.string.eight ;
        else if(valeur==9) return R.string.nine ;
        else if(valeur==10) return R.string.ten ;
        else if(valeur==11) return R.string.jack ;
        else if(valeur==12) return R.string.queen ;
        else if(valeur==13) return R.string.king ;
        else return -1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(valeur);
        parcel.writeString(couleur);
    }

    protected Card(Parcel in) {
        valeur = in.readInt();
        couleur = in.readString();
    }

    public static final Creator<Card> CREATOR = new Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel in) {
            return new Card(in);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };
}
