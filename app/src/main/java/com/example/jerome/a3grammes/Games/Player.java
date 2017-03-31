package com.example.jerome.a3grammes.Games;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Jerome on 22/02/2017.*/

public class Player implements Parcelable {
    private String name ;
    private ArrayList<Card> cards ;

    public Player(String name){
        this.name = name ;
        cards = new ArrayList<>();
    }

    /*
     * Retourne la liste des cartes du joueur
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /*
     * Ajoute une carte dans le jeu du joueur
     */
    public void addCard(Card card){
        cards.add(card);
    }

    /*
     * Retourne la valeur de la carte la plus haute
     */
    public int getMax(){
        int max = -1 ;
        for(int i=0 ; i<cards.size()-1; i++){
            if(cards.get(i).getValeur()>max)
                max = cards.get(i).getValeur() ;
        }
        return max ;
    }

    /*
     * Retourne la valeur de la carte la plus basse
     */
    public int getMin(){
        int min = 14 ;
        for(int i=0 ; i<cards.size()-1; i++){
            if(cards.get(i).getValeur()<min)
                min = cards.get(i).getValeur() ;
        }
        return min ;
    }

    /*
     * Parcelable implements methods
     */

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("cards", cards);
        parcel.writeBundle(bundle);
    }

    protected Player(Parcel in) {
        name = in.readString();
        Bundle bundle = in.readBundle(getClass().getClassLoader());
        cards = bundle.getParcelableArrayList("cards");
    }
}
