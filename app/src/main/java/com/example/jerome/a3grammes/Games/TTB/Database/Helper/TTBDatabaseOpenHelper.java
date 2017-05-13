package com.example.jerome.a3grammes.Games.TTB.Database.Helper;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Jerome on 13/05/2017.
 */

public class TTBDatabaseOpenHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "ttb_database.db";
    private static final int DATABASE_VERSION = 1;

    public TTBDatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
