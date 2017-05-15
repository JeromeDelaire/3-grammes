package com.example.jerome.a3grammes.Games.Picolo.Database.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jerome.a3grammes.Games.Picolo.Database.Model.Content;
import com.example.jerome.a3grammes.Global.Operations;

/**
 * Created by Jerome on 14/05/2017.
 *
 */

public class PicoloDatabaseAccess {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static PicoloDatabaseAccess instance;

    private static final String TABLE_CONTENT = "round";

    // Columns
    private static final String COL_KEY = "id";
    private static final String COL_CAT = "division";
    private static final String COL_TYPE = "type" ;
    private static final String COL_CONTENT = "content";
    private static final String COL_COMPLEMENT = "complement" ;
    private static final String COL_TTL = "ttl" ;
    private static final String COL_DONE = "done" ;
    private static final String COL_NAME1 = "name1" ;
    private static final String COL_NAME2 = "name2" ;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context the context
     */
    private PicoloDatabaseAccess(Context context) {
        this.openHelper = new PicoloDatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static PicoloDatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new PicoloDatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /*
     * Méthode appelé lorsque l'on tire au sort un content
     *
     * @param id identifiant du tuple à modifier
     * @param content nouveau content
     */
    private int update(long id, Content content){
        ContentValues values = new ContentValues();
        values.put(COL_CAT, content.getCat());
        values.put(COL_TYPE, content.getType());
        values.put(COL_CONTENT, content.getContent());
        values.put(COL_COMPLEMENT, content.getComplement());
        values.put(COL_TTL, content.getTtl());
        values.put(COL_DONE, content.getDone());
        values.put(COL_NAME1, content.getName1());
        values.put(COL_NAME2, content.getName2());
        return database.update(TABLE_CONTENT, values, COL_KEY + " = " + id, null);
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
        return database.update(TABLE_CONTENT, values, COL_KEY + " = " + id, null);
    }

    /*
    * Repasse tous les dones à 0 et les TTL à -1
    */
    public void init(boolean dumb, boolean sexy, boolean hard){
        database.execSQL("UPDATE " + TABLE_CONTENT + " SET " + COL_DONE + "='0' ;" );
        if(!dumb)database.execSQL("UPDATE " + TABLE_CONTENT + " SET " + COL_DONE + "='1' WHERE " + COL_CAT + " LIKE 'Débiles' ;" );
        if(!sexy)database.execSQL("UPDATE " + TABLE_CONTENT + " SET " + COL_DONE + "='1' WHERE " + COL_CAT + " LIKE 'Sexy' ;" );
        if(!hard)database.execSQL("UPDATE " + TABLE_CONTENT + " SET " + COL_DONE + "='1' WHERE " + COL_CAT + " LIKE 'Hard' ;" );
        database.execSQL("UPDATE " + TABLE_CONTENT + " SET " + COL_TTL+ "='-1' ;" );
    }

    /*
   * @param id identifiant du Content à retourner
   */
    private Content select(long id){
        Cursor cursor = database.rawQuery("select * from " + TABLE_CONTENT + " where " + COL_KEY + " like ?", new  String[]{Long.toString(id)});
        Content content = cursorToContent(cursor);
        cursor.close();
        return content;
    }

    public Content selectRandom(){
        Cursor cursor = database.rawQuery("select * from " + TABLE_CONTENT + " where " + COL_DONE + " like ?", new  String[]{Integer.toString(0)});
        cursor.moveToPosition(Operations.random_int(0, cursor.getCount()));
        Content content = new Content(
                cursor.getString(cursor.getColumnIndex(COL_CAT)),
                cursor.getString(cursor.getColumnIndex(COL_TYPE)),
                cursor.getString(cursor.getColumnIndex(COL_CONTENT)),
                cursor.getString(cursor.getColumnIndex(COL_COMPLEMENT)),
                cursor.getInt(cursor.getColumnIndex(COL_TTL)),
                1,
                new String[]{cursor.getString(cursor.getColumnIndex(COL_NAME1)), cursor.getString(cursor.getColumnIndex(COL_NAME2))}
        );
        content.setId(cursor.getLong(cursor.getColumnIndex(COL_KEY)));
        update(cursor.getLong(cursor.getColumnIndex(COL_KEY)), content);
        cursor.close();
        return content;
    }

    private Content cursorToContent(Cursor cursor){

        // Si aucun élément n'a été retourné dans la requête on renvoi null
        if (cursor.getCount() <= 0)
            return null ;

        // Sinon on se place sur le premier élément
        cursor.moveToFirst();
        // On crée un Content
        Content content = new Content(
                cursor.getString(cursor.getColumnIndex(COL_CAT)),
                cursor.getString(cursor.getColumnIndex(COL_TYPE)),
                cursor.getString(cursor.getColumnIndex(COL_CONTENT)),
                cursor.getString(cursor.getColumnIndex(COL_COMPLEMENT)),
                cursor.getInt(cursor.getColumnIndex(COL_TTL)),
                cursor.getInt(cursor.getColumnIndex(COL_DONE)),
                new String[]{cursor.getString(cursor.getColumnIndex(COL_NAME1)), cursor.getString(cursor.getColumnIndex(COL_NAME2))}
        );

        content.setId(cursor.getLong(cursor.getColumnIndex(COL_KEY)));
        content.setName1(cursor.getString(cursor.getColumnIndex(COL_NAME1)));
        content.setName2(cursor.getString(cursor.getColumnIndex(COL_NAME2)));

        cursor.close();

        return content;
    }

    /*
    * Retourne la taille de la base de donnée
    */
    public int getSize(){
        Cursor cursor = database.rawQuery("select * from " + TABLE_CONTENT, new  String[]{});
        int count = cursor.getCount()+1;
        cursor.close();
        return count ;
    }

    /*
     * Retourne le nombre de contenus qui n'ont pas encore été passés
     */
    public int getAvailableContents(){
        Cursor cursor = database.rawQuery("select * from " + TABLE_CONTENT + " where " + COL_DONE + "=0", new  String[]{});
        int count = cursor.getCount() ;
        cursor.close();
        return count;
    }

    /*
     * Indique le nombre de ttl à 0
     */
    public int getNbComplementReady(){
        Cursor cursor = database.rawQuery("select * from " + TABLE_CONTENT + " where " + COL_TTL + "=0", new  String[]{});
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
        Cursor cursor = database.rawQuery("select * from " + TABLE_CONTENT + " where " + COL_TTL + "=0", new  String[]{});
        cursor.move(Operations.random_int(1, cursor.getCount()));

        // Mise à jour du TTL
        Content content = new Content(
                cursor.getString(cursor.getColumnIndex(COL_CAT)),
                cursor.getString(cursor.getColumnIndex(COL_TYPE)),
                cursor.getString(cursor.getColumnIndex(COL_CONTENT)),
                cursor.getString(cursor.getColumnIndex(COL_COMPLEMENT)),
                cursor.getInt(cursor.getColumnIndex(COL_TTL))-1,
                cursor.getInt(cursor.getColumnIndex(COL_DONE)),
                new String[]{cursor.getString(cursor.getColumnIndex(COL_NAME1)), cursor.getString(cursor.getColumnIndex(COL_NAME2))}
        );

        content.setId(cursor.getLong(cursor.getColumnIndex(COL_KEY)));
        content.setName1(cursor.getString(cursor.getColumnIndex(COL_NAME1)));
        content.setName2(cursor.getString(cursor.getColumnIndex(COL_NAME2)));

        update(cursor.getLong(cursor.getColumnIndex(COL_KEY)), content);

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
        database.execSQL(query);
    }

    /*
     * Met done à 1
     */
    public void setDone(long id){
        Cursor cursor = database.rawQuery("select * from " + TABLE_CONTENT + " where " + COL_KEY + " like ?", new  String[]{Long.toString(id)});
        cursor.moveToFirst();
        Content content = new Content(
                cursor.getString(cursor.getColumnIndex(COL_CAT)),
                cursor.getString(cursor.getColumnIndex(COL_TYPE)),
                cursor.getString(cursor.getColumnIndex(COL_CONTENT)),
                cursor.getString(cursor.getColumnIndex(COL_COMPLEMENT)),
                cursor.getInt(cursor.getColumnIndex(COL_TTL)),
                1,
                new String[]{cursor.getString(cursor.getColumnIndex(COL_NAME1)), cursor.getString(cursor.getColumnIndex(COL_NAME2))}
        );

        content.setId(cursor.getLong(cursor.getColumnIndex(COL_KEY)));
        content.setName1(cursor.getString(cursor.getColumnIndex(COL_NAME1)));
        content.setName2(cursor.getString(cursor.getColumnIndex(COL_NAME2)));

        cursor.close();
        update(id, content);
    }


    public void setTTL(long id, int ttl){
        Cursor cursor = database.rawQuery("select * from " + TABLE_CONTENT + " where " + COL_KEY + " like ?", new  String[]{Long.toString(id)});
        cursor.moveToFirst();
        Content content = new Content(
                cursor.getString(cursor.getColumnIndex(COL_CAT)),
                cursor.getString(cursor.getColumnIndex(COL_TYPE)),
                cursor.getString(cursor.getColumnIndex(COL_CONTENT)),
                cursor.getString(cursor.getColumnIndex(COL_COMPLEMENT)),
                ttl,
                1,
                new String[]{cursor.getString(cursor.getColumnIndex(COL_NAME1)), cursor.getString(cursor.getColumnIndex(COL_NAME2))}
        );

        content.setId(cursor.getLong(cursor.getColumnIndex(COL_KEY)));
        content.setName1(cursor.getString(cursor.getColumnIndex(COL_NAME1)));
        content.setName2(cursor.getString(cursor.getColumnIndex(COL_NAME2)));


        cursor.close();
        update(id, content);
    }

}
