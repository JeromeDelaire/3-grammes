package com.example.jerome.a3grammes.Games.TTB.Database.Helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jerome.a3grammes.Games.TTB.Database.Model.Question;
import com.example.jerome.a3grammes.Global.Operations;

/**
 * Created by Jerome on 13/05/2017.
 *
 */

public class TTBDatabaseAccess {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static TTBDatabaseAccess instance;

    // Tables
    private static final String TABLE_QUESTIONS = "questions" ;

    // Columns
    private static final String KEY_ID = "id" ;
    private static final String KEY_QUESTION = "question" ;
    private static final String KEY_ANSWER_A = "answerA";
    private static final String KEY_ANSWER_B = "answerB";
    private static final String KEY_ANSWER_C = "answerC";
    private static final String KEY_ANSWER_D = "answerD";
    private static final String KEY_LEVEL = "level" ;
    private static final String KEY_DIVISION = "division" ;
    private static final String KEY_DONE = "done" ;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context the context
     */
    private TTBDatabaseAccess(Context context) {
        this.openHelper = new TTBDatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static TTBDatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new TTBDatabaseAccess(context);
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

    /* Get a random question where done = 0 and set done to 1*/
    public Question getRandomQuestion(){

        String selectQuery = "SELECT * FROM " + TABLE_QUESTIONS +
                " WHERE " + KEY_DONE + " LIKE ? ;";

        Cursor cursor = database.rawQuery(selectQuery, new  String[]{Integer.toString(0)});

        if(cursor != null && cursor.getCount()!=0)
            cursor.moveToPosition(Operations.random_int(0, cursor.getCount()));
        else
            return null ;

        setDone(cursor.getLong(cursor.getColumnIndex(KEY_ID)));

        Question question = new Question(
                cursor.getString(cursor.getColumnIndex(KEY_QUESTION)),
                cursor.getString(cursor.getColumnIndex(KEY_ANSWER_A)),
                cursor.getString(cursor.getColumnIndex(KEY_ANSWER_B)),
                cursor.getString(cursor.getColumnIndex(KEY_ANSWER_C)),
                cursor.getString(cursor.getColumnIndex(KEY_ANSWER_D)),
                cursor.getString(cursor.getColumnIndex(KEY_LEVEL)),
                cursor.getString(cursor.getColumnIndex(KEY_DIVISION))
        );

        question.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
        question.setDone(1);

        cursor.close();
        return question ;
    }

    /* Set done to 1 where id = question_id */
    private void setDone(long question_id){

        String updateQuery = "UPDATE " + TABLE_QUESTIONS +
                " SET " + KEY_DONE + " = 1 " +
                "WHERE " + KEY_ID + "=" + question_id;

        database.execSQL(updateQuery);
    }

    /*
    * Init done of question
    * ==> 0 if level and division is selected
    * ==> else 1
    */
    public void initQuestions() {

        String updateQuery = "UPDATE " + TABLE_QUESTIONS +
                " SET " + KEY_DONE + "=0;";

        database.execSQL(updateQuery);

    }


}
