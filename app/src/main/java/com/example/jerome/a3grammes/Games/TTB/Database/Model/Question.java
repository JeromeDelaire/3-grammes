package com.example.jerome.a3grammes.Games.TTB.Database.Model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jerome on 04/05/2017.
 * Questions/réponses (4 réponses par question)
 */

public class Question implements Parcelable{

    /* Members */
    private long id ;
    private String question ;
    private String answerA ;
    private String answerB ;
    private String answerC ;
    private String answerD ;
    private String level ;
    private String division ;
    private int done ; // 0 if question never used, 1 when question used

    /* Constructors */
    public Question(String question, String answerA, String answerB, String answerC, String answerD, String level, String division){
        this.question = question ;
        this.answerA = answerA ;
        this.answerB = answerB ;
        this.answerC = answerC ;
        this.answerD = answerD ;
        this.level = level ;
        this.division = division ;
    }

    public Question(int id, String question, String answerA, String answerB, String answerC, String answerD, String level, String division){
        this.id = id ;
        this.question = question ;
        this.answerA = answerA ;
        this.answerB = answerB ;
        this.answerC = answerC ;
        this.answerD = answerD ;
        this.level = level ;
        this.division = division ;
    }

    /* Getters */
    public long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswerA() {
        return answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public String getLevel() {
        return level;
    }

    public String getDivision() {
        return division;
    }

    public int getDone() {
        return done;
    }

    /* Setters */
    public void setId(long id) {
        this.id = id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public void setAnswerD(String answerD) {
        this.answerD = answerD;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public void setDone(int done) {
        this.done = done;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        bundle.putString("question", question);
        bundle.putString("answerA", answerA);
        bundle.putString("answerB", answerB);
        bundle.putString("answerC", answerC);
        bundle.putString("answerD", answerD);
        bundle.putInt("done", done);
        dest.writeBundle(bundle);
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    protected Question(Parcel in) {
        Bundle bundle = in.readBundle(getClass().getClassLoader());
        id = bundle.getInt("id");
        question = bundle.getString("question");
        answerA = bundle.getString("answerA");
        answerB = bundle.getString("answerB");
        answerC = bundle.getString("answerC");
        answerD = bundle.getString("answerD");
        done = bundle.getInt("done");
    }
}
