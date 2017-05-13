package com.example.jerome.a3grammes.Games.Picolo.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.jerome.a3grammes.Global.Operations;

/**
 * Created by Jerome on 08/04/2017.
 * Contrôleur de la table Content
 */


public class ContentDAO {

    private static final int DB_VERSION = 5;
    private static final String DB_NAME = "picolo.db";

    private static final String TABLE_CONTENT = "round";

    // Attributs
    private static final String COL_KEY = "id";
    private static final int NUM_COL_KEY = 0 ;

    private static final String COL_CAT = "cat";
    private static final int NUM_COL_CAT = 1 ;

    private static final String COL_TYPE = "type" ;
    private static final int NUM_COL_TYPE = 2 ;

    private static final String COL_CONTENT = "content";
    private static final int NUM_COL_CONTENT = 3 ;

    private static final String COL_COMPLEMENT = "complement" ;
    private static final int NUM_COL_COMPLEMENT = 4 ;

    private static final String COL_TTL = "ttl" ;
    private static final int NUM_COL_TTL = 5 ;

    private static final String COL_DONE = "done" ;
    private static final int NUM_COL_DONE = 6 ;

    private static final String COL_NAME1 = "name1" ;
    private static final int NUM_COL_NAME1 = 7 ;

    private static final String COL_NAME2 = "name2" ;
    private static final int NUM_COL_NAME2 = 8 ;

    private SQLiteDatabase db;

    private PicoloDB picoloDB ;

    /*
     * Création de la BDD et de sa table
     */
    public ContentDAO(Context context){
        picoloDB = new PicoloDB(context, DB_NAME, null, DB_VERSION);
    }

    public void openDB(){
        //on ouvre la BDD en écriture
        db = picoloDB.getWritableDatabase();
    }

    public void closeDB(){
        //on ferme l'accès à la BDD
        db.close();
    }

    public SQLiteDatabase getDB(){
        return db;
    }

    /*
     * @param content : content a insérer
     */
    public long insert(Content content){

        ContentValues values = new ContentValues();
        values.put(COL_CAT, content.getCat());
        values.put(COL_TYPE, content.getType());
        values.put(COL_CONTENT, content.getContent());
        values.put(COL_COMPLEMENT, content.getComplement());
        values.put(COL_TTL, content.getTtl());
        values.put(COL_DONE, content.getDone());
        values.put(COL_NAME1, content.getName1());
        values.put(COL_NAME2, content.getName2());
        return db.insert(TABLE_CONTENT, null, values);
    }

    /*
     * Méthode appelé lorsque l'on tire au sort un content
     *
     * @param id identifiant du tuple à modifier
     * @param content nouveau content
     */
    public int update(long id, Content content){
        ContentValues values = new ContentValues();
        values.put(COL_CAT, content.getCat());
        values.put(COL_TYPE, content.getType());
        values.put(COL_CONTENT, content.getContent());
        values.put(COL_COMPLEMENT, content.getComplement());
        values.put(COL_TTL, content.getTtl());
        values.put(COL_DONE, content.getDone());
        values.put(COL_NAME1, content.getName1());
        values.put(COL_NAME2, content.getName2());
        return db.update(TABLE_CONTENT, values, COL_KEY + " = " + id, null);
    }

    public int updateNames(long id, String[] names){
        ContentValues values = new ContentValues();
        Content content = select(id);
        values.put(COL_CAT, content.getCat());
        values.put(COL_TYPE, content.getType());
        values.put(COL_CONTENT, content.getContent());
        values.put(COL_COMPLEMENT, content.getComplement());
        values.put(COL_TTL, content.getTtl());
        values.put(COL_DONE, content.getDone());
        values.put(COL_NAME1, names[0]);
        values.put(COL_NAME2, names[1]);
        return db.update(TABLE_CONTENT, values, COL_KEY + " = " + id, null);
    }
    /*
     * Repasse tous les dones à 0 et les TTL à -1
     */
    public void init(boolean dumb, boolean sexy, boolean hard){
        db.execSQL("UPDATE " + TABLE_CONTENT + " SET " + COL_DONE + "='0' ;" );
        if(!dumb)db.execSQL("UPDATE " + TABLE_CONTENT + " SET " + COL_DONE + "='1' WHERE " + COL_CAT + " LIKE 'dumb' ;" );
        if(!sexy)db.execSQL("UPDATE " + TABLE_CONTENT + " SET " + COL_DONE + "='1' WHERE " + COL_CAT + " LIKE 'sexy' ;" );
        if(!hard)db.execSQL("UPDATE " + TABLE_CONTENT + " SET " + COL_DONE + "='1' WHERE " + COL_CAT + " LIKE 'hard' ;" );
        db.execSQL("UPDATE " + TABLE_CONTENT + " SET " + COL_TTL+ "='-1' ;" );
    }

    /*
     * @param id identifiant du Content à retourner
     */
    public Content select(long id){
        Cursor cursor = db.rawQuery("select * from " + TABLE_CONTENT + " where " + COL_KEY + " like ?", new  String[]{Long.toString(id)});
        Content content = cursorToContent(cursor);
        cursor.close();
        return content;
    }

    public Content selectRandom(){
        Cursor cursor = db.rawQuery("select * from " + TABLE_CONTENT + " where " + COL_DONE + " like ?", new  String[]{Integer.toString(0)});
        cursor.move(Operations.random_int(1, cursor.getCount()+1));
        Content content = new Content(
                cursor.getString(NUM_COL_CAT),
                cursor.getString(NUM_COL_TYPE),
                cursor.getString(NUM_COL_CONTENT),
                cursor.getString(NUM_COL_COMPLEMENT),
                cursor.getInt(NUM_COL_TTL),
                1,
                new String[]{cursor.getString(NUM_COL_NAME1), cursor.getString(NUM_COL_NAME2)}
        );
        content.setId(cursor.getLong(NUM_COL_KEY));
        update(cursor.getLong(NUM_COL_KEY), content);
        cursor.close();
        return content;
    }

    private Content cursorToContent(Cursor cursor){

        // Si aucun élément n'a été retourné dans la requête on renvoi null
        if (cursor.getCount() == 0)
            return null ;

        // Sinon on se place sur le premier élément
        cursor.moveToFirst();
        // On crée un Content
        Content content = new Content(
                cursor.getString(NUM_COL_CAT),
                cursor.getString(NUM_COL_TYPE),
                cursor.getString(NUM_COL_CONTENT),
                cursor.getString(NUM_COL_COMPLEMENT),
                cursor.getInt(NUM_COL_TTL),
                cursor.getInt(NUM_COL_DONE),
                new String[]{cursor.getString(NUM_COL_NAME1), cursor.getString(NUM_COL_NAME2)}
        );

        content.setId(cursor.getLong(NUM_COL_KEY));
        content.setName1(cursor.getString(NUM_COL_NAME1));
        content.setName2(cursor.getString(NUM_COL_NAME2));

        cursor.close();

        return content;
    }

    /*
     * Retourne la taille de la base de donnée
     */
    public int getSize(){
        Cursor cursor = db.rawQuery("select * from " + TABLE_CONTENT, new  String[]{});
        int count = cursor.getCount()+1;
        cursor.close();
        return count ;
    }

    /*
     * Retourne le nombre de contenus qui n'ont pas encore été passés
     */
    public int getAvailableContents(){
        Cursor cursor = db.rawQuery("select * from " + TABLE_CONTENT + " where " + COL_DONE + "=0", new  String[]{});
        int count = cursor.getCount() ;
        cursor.close();
        return count;
    }

    /*
     * Indique le nombre de ttl à 0
     */
    public int getNbComplementReady(){
        Cursor cursor = db.rawQuery("select * from " + TABLE_CONTENT + " where " + COL_TTL + "=0", new  String[]{});
        int count ;
        if(!cursor.moveToFirst())
            count = 0 ;
        else
            count = cursor.getCount();
        cursor.close();
        return count;
    }

    /*
     * Retourne un complément aléatoirement
     */
    public Content getRandomComplement(){

        // Selection de tous les content avec un ttl=0
        Cursor cursor = db.rawQuery("select * from " + TABLE_CONTENT + " where " + COL_TTL + "=0", new  String[]{});
        cursor.moveToPosition(Operations.random_int(1, cursor.getCount()));

        // Mise à jour du TTL
        Content content = new Content(
                cursor.getString(NUM_COL_CAT),
                cursor.getString(NUM_COL_TYPE),
                cursor.getString(NUM_COL_CONTENT),
                cursor.getString(NUM_COL_COMPLEMENT),
                cursor.getInt(NUM_COL_TTL)-1,
                cursor.getInt(NUM_COL_DONE),
                new String[]{cursor.getString(NUM_COL_NAME1), cursor.getString(NUM_COL_NAME2)}
        );

        content.setId(cursor.getLong(NUM_COL_KEY));
        content.setName1(cursor.getString(NUM_COL_NAME1));
        content.setName2(cursor.getString(NUM_COL_NAME2));

        update(cursor.getLong(NUM_COL_KEY), content);

        cursor.close();
        return content ;
    }

    /*
     * Décrémente tous les TTL > 0
     */
    public void decrementTTL(){
        String query = "UPDATE " + TABLE_CONTENT +
                " SET " + COL_TTL + " = " + COL_TTL + " - 1" +
                " WHERE " + COL_TTL + " > 0 ;" ;
        db.execSQL(query);
    }

    /*
     * Met done à 1
     */
    public void setDone(long id){
        Cursor cursor = db.rawQuery("select * from " + TABLE_CONTENT + " where " + COL_KEY + " like ?", new  String[]{Long.toString(id)});
        cursor.moveToFirst();
        Content content = new Content(
                cursor.getString(NUM_COL_CAT),
                cursor.getString(NUM_COL_TYPE),
                cursor.getString(NUM_COL_CONTENT),
                cursor.getString(NUM_COL_COMPLEMENT),
                cursor.getInt(NUM_COL_TTL),
                1,
                new String[]{cursor.getString(NUM_COL_NAME1), cursor.getString(NUM_COL_NAME2)}
        );

        content.setId(cursor.getLong(NUM_COL_KEY));
        content.setName1(cursor.getString(NUM_COL_NAME1));
        content.setName2(cursor.getString(NUM_COL_NAME2));

        cursor.close();
        update(id, content);
    }


    public void setTTL(long id, int ttl){
        Cursor cursor = db.rawQuery("select * from " + TABLE_CONTENT + " where " + COL_KEY + " like ?", new  String[]{Long.toString(id)});
        cursor.moveToFirst();
        Content content = new Content(
                cursor.getString(NUM_COL_CAT),
                cursor.getString(NUM_COL_TYPE),
                cursor.getString(NUM_COL_CONTENT),
                cursor.getString(NUM_COL_COMPLEMENT),
                ttl,
                cursor.getInt(NUM_COL_DONE),
                new String[]{cursor.getString(NUM_COL_NAME1), cursor.getString(NUM_COL_NAME2)}
        );

        content.setId(cursor.getLong(NUM_COL_KEY));
        content.setName1(cursor.getString(NUM_COL_NAME1));
        content.setName2(cursor.getString(NUM_COL_NAME2));

        cursor.close();
        update(id, content);
    }
}