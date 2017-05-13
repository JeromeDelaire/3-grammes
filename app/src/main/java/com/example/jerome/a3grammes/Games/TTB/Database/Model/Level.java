package com.example.jerome.a3grammes.Games.TTB.Database.Model;

/**
 * Created by Jerome on 04/05/2017.
 * Classe pour indiquer le niveau de la question : Facile, Moyen, Difficile
 */

public class Level {

    /* Attributs */
    private long id ;
    private String level ;

    /* Constructeurs */
    public Level(String level){
        this.level = level ;
    }

    public Level(long id, String level){
        this.id = id ;
        this.level = level ;
    }

    /* Getters */
    public long getId() {
        return id;
    }

    public String getLevel() {
        return level;
    }

    /* Setters */
    public void setId(long id) {
        this.id = id;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
