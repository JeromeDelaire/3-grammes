package com.example.jerome.a3grammes.Games.TTB.Database.Model;

/**
 * Created by Jerome on 04/05/2017.
 * Classe pour les catégories (Sex, Debile, etc)
 */

public class Division {

    /* Attributs */
    private long id ;
    private String division ;

    /* Constructeurs */
    public Division(String division){
        this.division = division ;
    }

    public Division(long id, String division){
        this.id = id ;
        this.division = division ;
    }

    /* Getters */
    public long getId() {
        return id;
    }

    public String getDivision() {
        return division;
    }

    /* Setters */
    public void setId(long id) {
        this.id = id;
    }

    public void setDivision(String division) {
        this.division = division;
    }
}
