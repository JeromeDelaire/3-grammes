package com.example.jerome.a3grammes.Games.Picolo.Database.Model;

/**
 * Created by Jerome on 08/04/2017.
 * Table round
 *
 *  id  type    content complement  ttl done
 *  int string  string  string      int int
 */

public class Content {

    private long id ;
    // Catégories Normal, Sexy, Débiles, Hard
    private String cat;
    // Jeux, Défis, Questions, Règle...
    private String type ;
    // Contenu
    private String content ;
    // Complément (fin du Jeu,défi,règle)
    private String complement;
    // Time To Live : Nombre de tours avant d'annoncer la fin du défi, règle ou jeu
    private int ttl ;
    // Content déjà passé done = 1, sinon done = 0
    private int done ;

    private String name1 ;
    private String name2 ;

    public Content(String cat, String type, String content, String complement, int ttl, int done, String names[]){
        super();
        this.cat = cat ;
        this.type = type ;
        this.content = content ;
        this.complement = complement ;
        this.ttl = ttl ;
        this.done = done ;
        this.name1 = names[0] ;
        this.name2 = names[1] ;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }
}
