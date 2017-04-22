package com.example.jerome.a3grammes.Games.Picolo.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jerome on 08/04/2017.
 * Crée la base de données
 */

public class PicoloDB extends SQLiteOpenHelper{

    public static boolean updated = false ;
    private static final String TABLE_NAME = "round";

    // Attributs
    private static final String COL_KEY = "id";
    private static final String COL_CAT = "cat" ;
    private static final String COL_TYPE = "type" ;
    private static final String COL_CONTENT = "content";
    private static final String COL_COMPLEMENT = "complement" ;
    private static final String COL_TTL = "ttl" ;
    private static final String COL_DONE = "done" ;
    private static final String COL_NAME1 = "name1" ;
    private static final String COL_NAME2 = "name2" ;

    // Requête de création de la table ROUND
    private static final String CREATE_TABLE_ROUND = "CREATE TABLE " + TABLE_NAME + " (" +
            COL_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_CAT + " TEXT, " +
            COL_TYPE + " TEXT, " +
            COL_CONTENT + " TEXT, " +
            COL_COMPLEMENT + " TEXT, " +
            COL_TTL + " INTEGER, " +
            COL_DONE + " INTEGER DEFAULT 0, " +
            COL_NAME1 + " TEXT, " +
            COL_NAME2 + " TEXT " +
            // Checks
            /*"CHECK (" + COL_TYPE + " IN (" + GAME + ", " + CHALLENGE + ", " + NORMAL + ", " + MALEDICTION + "))" +*/
            ");" ;

    // Requêt pour supprimer les tables avant une mise à jour de la BDD
    private static final String TABLE_DROP_ROUND =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public PicoloDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ROUND);
        updated = true ;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TABLE_DROP_ROUND);
        db.execSQL(CREATE_TABLE_ROUND);
        updated = true;
    }
}
