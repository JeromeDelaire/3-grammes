package com.example.jerome.a3grammes.Games.TTB.Database.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.jerome.a3grammes.Games.TTB.Database.Model.Division;
import com.example.jerome.a3grammes.Games.TTB.Database.Model.Level;
import com.example.jerome.a3grammes.Games.TTB.Database.Model.Question;
import com.example.jerome.a3grammes.Global.Operations;

/**
 * Created by Jerome on 04/05/2017.
 */

public class TTBDatabaseHelper extends SQLiteOpenHelper {

     /*---------------------------------------------------------------------------------*/
    /*---CONSTANTS---------------------------------------------------------------------*/

    // Version
    public static final int DATABASE_VERSION = 1 ;

    // Name
    public static final String DATABASE_NAME = "TTBQuestions" ;

    // Tables
    public static final String TABLE_QUESTIONS = "questions" ;
    public static final String TABLE_DIVISIONS = "divisions" ;
    public static final String TABLE_LEVELS = "levels" ;
    public static final String TABLE_QUESTIONS_INFOS = "questions_infos" ;

    // Common columns
    public static final String KEY_ID = "id" ;

    // Questions columns
    public static final String KEY_QUESTION = "question" ;
    public static final String KEY_ANSWER_A = "answer_a";
    public static final String KEY_ANSWER_B = "answer_b";
    public static final String KEY_ANSWER_C = "answer_c";
    public static final String KEY_ANSWER_D = "answer_d";
    public static final String KEY_DONE = "done" ;

    // Division columns
    public static final String KEY_DIVISION = "division" ;

    // Levels columns
    public static final String KEY_LEVEL = "level" ;

    // Questions info columns
    public static final String KEY_QUESTION_ID = "question_id" ;
    public static final String KEY_DIVISION_ID = "division_id" ;
    public static final String KEY_LEVEL_ID = "level_id" ;

    /* Table Create Statements */
    // Question table create statement
    private static final String CREATE_TABLE_QUESTIONS = "CREATE TABLE "
            + TABLE_QUESTIONS + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_QUESTION + " TEXT,"
            + KEY_ANSWER_A + " TEXT," +
            KEY_ANSWER_B + " TEXT," +
            KEY_ANSWER_C + " TEXT," +
            KEY_ANSWER_D + " TEXT," +
            KEY_DONE + " INTEGER DEFAULT 1);";

    // Division table create statement
    private static final String CREATE_TABLE_DIVISIONS = "CREATE TABLE "
            + TABLE_DIVISIONS + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_DIVISION + " TEXT);";

    // Levels table create statement
    private static final String CREATE_TABLE_LEVELS = "CREATE TABLE "
            + TABLE_LEVELS + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_LEVEL + " TEXT);" ;

    // Questions infos table create statement
    private static final String CREATE_TABLE_QUESTIONS_INFOS = "CREATE TABLE "
            + TABLE_QUESTIONS_INFOS + "(" +
            KEY_QUESTION_ID + " INTEGER PRIMARY KEY," +
            KEY_LEVEL_ID + " INTEGER," +
            KEY_DIVISION_ID + " INTEGER);" ;

    private SQLiteDatabase db ;

     /*---------------------------------------------------------------------------------*/
    /*---DATABASE FUNCTIONS------------------------------------------------------------*/


    public TTBDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        this.db = db ;

        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIVISIONS + " ;");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS + " ;");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LEVELS + " ;");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS_INFOS + " ;");

        // Create tables
        db.execSQL(CREATE_TABLE_DIVISIONS);
        db.execSQL(CREATE_TABLE_LEVELS);
        db.execSQL(CREATE_TABLE_QUESTIONS);
        db.execSQL(CREATE_TABLE_QUESTIONS_INFOS);

        /* INSERT IN TABLES */
        /* Insert Divisions */
        insertDivision(new Division("débile"));
        insertDivision(new Division("coquine"));
        insertDivision(new Division("sport"));
        insertDivision(new Division("technologie"));
        insertDivision(new Division("cinéma"));
        insertDivision(new Division("histoire"));
        insertDivision(new Division("musique"));
        insertDivision(new Division("géographie"));
        insertDivision(new Division("télévision"));
        insertDivision(new Division("humour"));
        insertDivision(new Division("science"));

        /* Insert levels */
        insertLevel(new Level("facile"));
        insertLevel(new Level("moyen"));
        insertLevel(new Level("difficile"));

        /* Insert questions */
        /* Dumb */
        insertQuestion(new Question("En moyenne, combien de fois un homme pète par jour ?", "14", "10", "22", "26"), getLevelId("moyen"), getDivisionId("débile"));

        /* Sex */
        insertQuestion(new Question("En France, quelle est la taille moyenne du pénis chez l'homme ?", "13.5cm", "13cm", "14cm", "14.5cm"), getLevelId("moyen"), getDivisionId("coquine"));

        /* Sport */
        insertQuestion(new Question("Qui détient actuellement le plus grand nombre de titres au Roland-Garros ? ", "Raphaël Nadal", "Henri Cochet", "René Lacoste", "Roger Federer"), getLevelId("facile"), getDivisionId("sport"));

        /* Technologie */
        insertQuestion(new Question("En informatique, à quoi correspond 1Ko ? ", "8192 bits", "1000 bits", "1024 bits", "4096 bits"), getLevelId("moyen"), getDivisionId("technologie"));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Create tables
        onCreate(db);
    }

    public void openDB(){
        db = this.getWritableDatabase();
    }

     /*---------------------------------------------------------------------------------*/
    /*---DIVISIONS QUERIES-------------------------------------------------------------*/

    /* Insert a division */
    private long insertDivision(Division division){

        ContentValues values = new ContentValues();
        values.put(KEY_DIVISION, division.getDivision());

        long division_id = db.insert(TABLE_DIVISIONS, null, values);
        division.setId(division_id);

        return division_id ;
    }

    /* Return id of a division */
    private long getDivisionId(String division){

        String selectQuery = "SELECT " + KEY_ID +
                " FROM " + TABLE_DIVISIONS +
                " WHERE " + KEY_DIVISION + " LIKE \"" + division + "\"" ;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor != null){
            cursor.moveToFirst();
            long division_id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
            cursor.close();
            return division_id ;
        }else{
            return -1 ;
        }
    }

     /*---------------------------------------------------------------------------------*/
    /*---LEVELS QUERIES--------------------------------------- ------------------------*/

    /* Insert a level */
    private long insertLevel(Level level){

        ContentValues values = new ContentValues();
        values.put(KEY_LEVEL, level.getLevel());

        long level_id = db.insert(TABLE_LEVELS, null, values);
        level.setId(level_id);

        return level_id ;
    }

    /* Return id of a level */
    private long getLevelId(String level){

        String selectQuery = "SELECT " + KEY_ID +
                " FROM " + TABLE_LEVELS +
                " WHERE " + KEY_LEVEL + " LIKE \"" + level + "\"" ;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor != null && cursor.getCount()!=0){
            cursor.moveToFirst();
            long level_id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
            cursor.close();
            return level_id ;
        }

        else{
            if(cursor!=null)cursor.close();
            return -1 ;
        }
    }


     /*---------------------------------------------------------------------------------*/
    /*---QUESTIONS QUERIES-------------------------------------------------------------*/

    /* Insert a question with four answers (A is the great answer) */
    private long insertQuestion(Question question, long level_id, long division_id){

        ContentValues values = new ContentValues();
        values.put(KEY_QUESTION, question.getQuestion());
        values.put(KEY_ANSWER_A, question.getAnswerA());
        values.put(KEY_ANSWER_B, question.getAnswerB());
        values.put(KEY_ANSWER_C, question.getAnswerC());
        values.put(KEY_ANSWER_D, question.getAnswerD());
        values.put(KEY_DONE, question.getDone());

        long question_id = db.insert(TABLE_QUESTIONS, null, values);
        question.setId(question_id);

        /* Assign level and division of the question */
        insertQuestionInfos(question_id, level_id, division_id);

        return question_id ;
    }

    /* Get a random question where done = 0 and set done to 1*/
    public Question getRandomQuestion(){

        String selectQuery = "SELECT * FROM " + TABLE_QUESTIONS +
                " WHERE " + KEY_DONE + " LIKE ? ;";

        Cursor cursor = db.rawQuery(selectQuery, new  String[]{Integer.toString(0)});

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
                cursor.getString(cursor.getColumnIndex(KEY_ANSWER_D))
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

        db.execSQL(updateQuery);
    }

    /*
     * Init done of question
     * ==> 0 if level and division is selected
     * ==> else 1
     */
    public void initQuestions(){

        String updateQuery = "UPDATE " + TABLE_QUESTIONS +
                " SET " + KEY_DONE + "=0;" ;

        db.execSQL(updateQuery);
    }
    
     /*---------------------------------------------------------------------------------*/
    /*---QUESTIONS INFOS QUERIES-------------------------------------------------------*/

    /* Insert questions infos */
    private void insertQuestionInfos(long question_id, long level_id, long division_id){

        ContentValues values = new ContentValues();
        values.put(KEY_QUESTION_ID, question_id);
        values.put(KEY_LEVEL_ID, level_id);
        values.put(KEY_DIVISION_ID, division_id);

        db.insert(TABLE_QUESTIONS_INFOS, null, values);
    }

    /* Get The level of a question */
    public String getQuestionLevel(Question question){

        /* Get the level_id in question infos table in row where question_id = question.id */
        String selectQuery1 = "SELECT " + KEY_LEVEL_ID + " FROM " + TABLE_QUESTIONS_INFOS +
                " WHERE " + KEY_QUESTION_ID + "=" + question.getId();

        Cursor cursor1 = db.rawQuery(selectQuery1, null);

        if(cursor1 != null && cursor1.getCount() != 0){

            cursor1.moveToFirst();
            long level_id = cursor1.getLong(cursor1.getColumnIndex(KEY_LEVEL_ID));
            cursor1.close();

            /* Select the level name corresponding to level_id */
            String selectQuery2 = " SELECT " + KEY_LEVEL + " FROM " + TABLE_LEVELS +
                    " WHERE " + KEY_ID +  "=" + level_id ;

            Cursor cursor2 = db.rawQuery(selectQuery2, null);

            if(cursor2 != null && cursor2.getCount()!=0){

                cursor2.moveToFirst();
                String level = cursor2.getString(cursor2.getColumnIndex(KEY_LEVEL));
                cursor2.close();
                return level ;

            }

            else{
                return "ERROR: NO LEVEL FOUND" ;
            }
        }

        else{
            return "ERROR: NO LEVEL FOUND" ;
        }
    }

    /* Get the division of a question  */
    public String getQuestionDivision(Question question){

         /* Get the division_id in question infos table in row where question_id = question.id */
        String selectQuery1 = "SELECT " + KEY_DIVISION_ID + " FROM " + TABLE_QUESTIONS_INFOS +
                " WHERE " + KEY_QUESTION_ID + "=" + question.getId();

        Cursor cursor1 = db.rawQuery(selectQuery1, null);

        if(cursor1 != null && cursor1.getCount() != 0){

            cursor1.moveToFirst();
            long division_id = cursor1.getLong(cursor1.getColumnIndex(KEY_DIVISION_ID));
            cursor1.close();

            /* Select the division name corresponding to division_id */
            String selectQuery2 = " SELECT " + KEY_DIVISION + " FROM " + TABLE_DIVISIONS +
                    " WHERE " + KEY_ID +  "=" + division_id ;

            Cursor cursor2 = db.rawQuery(selectQuery2, null);

            if(cursor2 != null && cursor2.getCount()!=0){

                cursor2.moveToFirst();
                String level = cursor2.getString(cursor2.getColumnIndex(KEY_DIVISION));
                cursor2.close();
                return level ;

            }

            else{
                return "ERROR: NO DIVISION FOUND" ;
            }
        }

        else{
            return "ERROR: NO DIVISION FOUND" ;
        }
    }

}
