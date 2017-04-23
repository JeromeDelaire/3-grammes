package com.example.jerome.a3grammes.Games.Cards;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.jerome.a3grammes.Interface.Cards;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Jerome on 23/02/2017.
 */

public class CardSet implements Parcelable{
    private ArrayList<Card> cards ;

    public CardSet(int setNumber){
        cards = new ArrayList<>() ;
        for(int j=0 ; j<setNumber ; j++) {
            for (int i = 0; i < 52; i += 4) {
                cards.add(new Card((i / 4) + 1, Cards.CLUBS));
                cards.add(new Card((i / 4) + 1, Cards.SPADES));
                cards.add(new Card((i / 4) + 1, Cards.HEARTS));
                cards.add(new Card((i / 4) + 1, Cards.DIAMONDS));
            }
        }
    }

    public void shuffle(){
        ArrayList<Card> shuffle = new ArrayList<>(cards);
        Collections.shuffle(shuffle);
        cards = shuffle ;
    }

    public Card take(){
        Card temp = cards.get(cards.size()-1);
        cards.remove(cards.size()-1);
        return temp ;
    }

    public int getSize(){
        return cards.size();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CardSet> CREATOR = new Creator<CardSet>() {
        @Override
        public CardSet createFromParcel(Parcel in) {
            return new CardSet(in);
        }

        @Override
        public CardSet[] newArray(int size) {
            return new CardSet[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("cards", cards);
        parcel.writeBundle(bundle);
    }

    protected CardSet(Parcel in) {
        Bundle bundle = in.readBundle(getClass().getClassLoader());
        cards = bundle.getParcelableArrayList("cards");
    }

}
