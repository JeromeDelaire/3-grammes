package com.example.jerome.a3grammes.Games.TTB;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jerome.a3grammes.Games.Global.Player;
import com.example.jerome.a3grammes.Games.TTB.Database.Helper.TTBDatabaseAccess;
import com.example.jerome.a3grammes.Games.TTB.Database.Model.Question;
import com.example.jerome.a3grammes.Global.Operations;
import com.example.jerome.a3grammes.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

/**
 * Created by Jerome on 04/05/2017.
 *
 */

public class TTB extends AppCompatActivity {

     /*-------------------------------------------------------------------------------*/
    /*----MEMBERS DECLARATION--------------------------------------------------------*/

    /* Widgets, Layouts ... */
    private TextView question_tv, division_tv, level_tv, time_left_tv ;
    private Button answerA, answerB, answerC, answerD ;
    private ProgressBar time_left ;

    /* Game informations */
    private ArrayList<Player> players ;

    /* Game variable */
    private Question actualQuestion ;
    private int actualPlayer ;
    private int sip_count ;
    private CountDownTimer boom ; // At the end of the Timer, actual player must drink sip_count sip
    private int timer ;


     /*-------------------------------------------------------------------------------*/
    /*----GLOBAL VARIABLES-----------------------------------------------------------*/

    TTBDatabaseAccess db ; // For using TTB database
    MediaPlayer good_answer ;
    MediaPlayer wrong_answer ;
    boolean tick = false ;

     /*-------------------------------------------------------------------------------*/
    /*----FUNCTIONS------------------------------------------------------------------*/

    /* When activity is created */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ttb);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // Disable screen lock

        /* Find views */
        question_tv = (TextView) findViewById(R.id.question);
        division_tv = (TextView) findViewById(R.id.division);
        time_left_tv = (TextView) findViewById(R.id.time_left_tv);
        level_tv = (TextView) findViewById(R.id.level);
        time_left = (ProgressBar) findViewById(R.id.time_left);
        answerA = (Button) findViewById(R.id.answerA);
        answerB = (Button) findViewById(R.id.answerB);
        answerC = (Button) findViewById(R.id.answerC);
        answerD = (Button) findViewById(R.id.answerD);

        /* Sounds */

        db = TTBDatabaseAccess.getInstance(this); // Create database
        db.open(); // Open db in writable mode

        /* When game started */
        if (savedInstanceState==null){
            db.initQuestions(); // Set done of questions to 0 or 1 (depending of levels and divisions choice)

            actualPlayer = 0 ; // Actual player is first player
            sip_count = 0 ;

            /* Get informations from parent activity (TTB settings) */
            Bundle b = getIntent().getExtras();
            players = b.getParcelableArrayList("players"); // Get players list

            /* Random duration for boom timer */
            timer = Operations.random_int(30,60);
            time_left.setMax(timer);
            createTimer();

            /* Display first question */
            displayRandomQuestion();
        }

        /* When activity is recreated (on sreen unlocked for example) */
        else{
            players = savedInstanceState.getParcelableArrayList("players");
            actualQuestion = savedInstanceState.getParcelable("question");
            actualPlayer = savedInstanceState.getInt("actualPlayer");
            sip_count = savedInstanceState.getInt("sip");
            timer = savedInstanceState.getInt("timer");
            division_tv.setText(actualQuestion.getQuestion());
            level_tv.setText(actualQuestion.getLevel());
            question_tv.setText(actualQuestion.getQuestion());
            setAnswers();
        }

        answerA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCorrectAnswer(isGoodAnswer(answerA.getText().toString()));
            }
        });

        answerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCorrectAnswer(isGoodAnswer(answerB.getText().toString()));
            }
        });

        answerC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCorrectAnswer(isGoodAnswer(answerC.getText().toString()));
            }
        });

        answerD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCorrectAnswer(isGoodAnswer(answerD.getText().toString()));
            }
        });

    }

    /* Save context of the game (Players, questions etc) */
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList("players", players);
        savedInstanceState.putParcelable("question", actualQuestion);
        savedInstanceState.putInt("actualPlayer", actualPlayer);
        savedInstanceState.putInt("sip", sip_count);
        savedInstanceState.putInt("timer", timer);
    }

    /* When activity is finished */
    public void onStop () {

        if(boom!=null)
            boom.cancel();
        super.onStop();
    }

    /*
     * Display random question with 4 answers
     * Close activity when no question are available
     */
    private void displayRandomQuestion(){

        createTimer();
        boom.start();

        if ((actualQuestion = db.getRandomQuestion()) != null) { // If return question success

            question_tv.setText(actualQuestion.getQuestion()); // Set question text
            division_tv.setText(actualQuestion.getDivision()); // Set division text
            level_tv.setText(String.format(getResources().getString(R.string.level_player), actualQuestion.getLevel(), players.get(actualPlayer).getName())); // Set Level text
            setAnswers(); // Set answers buttons text
        }

        else{ // If no question are available
            finish();
        }
    }

    /* set text of answer buttons */
    private void setAnswers(){
        ArrayList<String> answers = new ArrayList<>();
        answers.add(actualQuestion.getAnswerA());
        answers.add(actualQuestion.getAnswerB());
        answers.add(actualQuestion.getAnswerC());
        answers.add(actualQuestion.getAnswerD());
        Collections.shuffle(answers); // Shuffle answers
        /* Set buttons text */
        answerA.setText(answers.get(0));
        answerB.setText(answers.get(1));
        answerC.setText(answers.get(2));
        answerD.setText(answers.get(3));
    }

    /*
     * Define if the answer click by player is the good answer or not
     * Return true if is good answer, else false
     * Play song indicate if is right or wrond answer
     */
    private boolean isGoodAnswer(String answer){
        boolean correct ;
        if((correct = Objects.equals(actualQuestion.getAnswerA(), answer))){
            good_answer = MediaPlayer.create(getApplicationContext(), R.raw.right_answer);
            good_answer.start();
        }

        else{
            wrong_answer = MediaPlayer.create(getApplicationContext(), R.raw.wrong_answer);
            wrong_answer.start();
        }

        return correct ;
    }

    /* Change actualPlayer = next player */
    private void nextPlayer(){
        if(actualPlayer==players.size()-1)
            actualPlayer=0 ;
        else
            actualPlayer++ ;
    }

    /* Catch the back button */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Si on appuie sur le bouton retour
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /* Open an alert dialog when user touch the back button */
    protected void exitByBackKey() {

        boom.cancel();
        
        new AlertDialog.Builder(this, R.style.TTBAlertDialog)
                .setTitle(R.string.exit)
                .setMessage(getResources().getString(R.string.alert_exit_game))
                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        createTimer();
                        boom.start();
                    }
                }).setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                createTimer();
                boom.start();
            }
        })
                .show();
    }

    /* Display correct answer and results */
    private void showCorrectAnswer(final boolean win){

        boom.cancel();

        int sip_count ;
        String message, title ;

        /* If player choose good answer*/
        if(win){
            title = getResources().getString(R.string.right_answer);

            if(Objects.equals(actualQuestion.getLevel(), "Débutant")) //
                sip_count = 1 ;
            else if(Objects.equals(actualQuestion.getLevel(), "Confirmé"))
                sip_count = 2 ;
            else
                sip_count = 3 ;

            this.sip_count+=sip_count; // Add sip to counter

            message = String.format(getResources().getString(R.string.ttb_win), sip_count);
        }

        /* If user choose bad answer */
        else
        {
            title = getResources().getString(R.string.wrong_answer);

            if(Objects.equals(actualQuestion.getLevel(), "Débutant")) //
                sip_count = 3 ;
            else if(Objects.equals(actualQuestion.getLevel(), "Confirmé"))
                sip_count = 2 ;
            else
                sip_count = 1 ;

            message = String.format(getResources().getString(R.string.ttb_lose), actualQuestion.getAnswerA(), sip_count, players.get(actualPlayer).getName());
        }


        new AlertDialog.Builder(this, R.style.TTBAlertDialog)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(win)
                            nextPlayer();
                        displayRandomQuestion();
                    }
                }).setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if(win)
                    nextPlayer();
                displayRandomQuestion();
            }
        })
                .show();
    }

    /* Show alert dialog at then end of countdown */
    private void boomDialog(){

        new AlertDialog.Builder(this, R.style.TTBAlertDialog)
                .setTitle(R.string.ttb_boom_title)
                .setMessage(String.format(getResources().getString(R.string.ttb_boom), players.get(actualPlayer).getName(), sip_count))
                .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sip_count = 0 ;
                        time_left.setMax(timer);
                        time_left.setProgress(0);
                        displayRandomQuestion();
                    }
                }).setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                sip_count = 0 ;
                time_left.setMax(timer);
                time_left.setProgress(0);
                displayRandomQuestion();
            }
        })
                .show();
    }

    private void createTimer(){

        boom = new CountDownTimer(timer*1000, 500) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(tick){
                    tick = false ;
                }

                else{
                    tick = true ;
                    timer-- ;
                    time_left.setProgress(time_left.getMax()-timer-1);
                    time_left_tv.setText(String.valueOf(timer+1));
                }
            }

            @Override
            public void onFinish() {
                tick = true ;
                timer--;
                time_left.setProgress(time_left.getMax()-timer-1);
                time_left_tv.setText(String.valueOf(timer+1));
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.explosion);
                mp.start();
                timer = Operations.random_int(30, 60);
                boomDialog();
            }
        };
    }

}
