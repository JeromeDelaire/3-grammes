package com.example.jerome.a3grammes.Global;

/**
 * Created by Jerome on 08/04/2017.
 */

public class Operations {

    public static int random_int(int Min, int Max)
    {
        return (int) (Math.random()*(Max-Min))+Min;
    }
}
