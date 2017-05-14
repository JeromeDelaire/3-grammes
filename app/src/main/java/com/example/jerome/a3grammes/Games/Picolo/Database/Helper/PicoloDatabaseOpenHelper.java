package com.example.jerome.a3grammes.Games.Picolo.Database.Helper;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Jerome on 14/05/2017.
 */

public class PicoloDatabaseOpenHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "picolo_database.db";
    private static final int DATABASE_VERSION = 1;

    public PicoloDatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
