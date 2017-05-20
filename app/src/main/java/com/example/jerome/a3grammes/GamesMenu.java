package com.example.jerome.a3grammes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.example.jerome.a3grammes.Rules.PicoloRules;
import com.example.jerome.a3grammes.Rules.RedOrBlackRules;
import com.example.jerome.a3grammes.Rules.TTBRules;
import com.example.jerome.a3grammes.Settings.PicoloSettings;
import com.example.jerome.a3grammes.Settings.RedOrBlackSettings;
import com.example.jerome.a3grammes.Settings.TTBSettings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class GamesMenu extends AppCompatActivity implements View.OnClickListener{

    private ImageButton picolo_rules, more_or_less_rules, red_or_black_rules ;
    private RelativeLayout picolo, more_or_less, red_or_black, comming_soon ;
    private RelativeLayout oldAnimatedLayout = null ;

    int rotationAngle = 3, rotation_speed = 75, animation_count = 0 ;
    Timer timer ;
    TimerTask timerTask ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_menu);

        //Boutons pour choisir le jeu
        picolo = (RelativeLayout) findViewById(R.id.picolo_button);
        picolo_rules = (ImageButton) findViewById(R.id.picolo_rules_button);
        more_or_less = (RelativeLayout) findViewById(R.id.ttb_button);
        more_or_less_rules = (ImageButton) findViewById(R.id.ttb_rules_button);
        red_or_black = (RelativeLayout) findViewById(R.id.red_or_black_button);
        red_or_black_rules = (ImageButton) findViewById(R.id.red_or_black_rules_button);
        comming_soon = (RelativeLayout) findViewById(R.id.comming_soon_button);

        //Ajout des listeners aux boutons
        picolo.setOnClickListener(this);
        picolo_rules.setOnClickListener(this);
        more_or_less.setOnClickListener(this);
        more_or_less_rules.setOnClickListener(this);
        red_or_black.setOnClickListener(this);
        red_or_black_rules.setOnClickListener(this);
    }

    @Override
    protected void onResume(){

        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<RelativeLayout> relativeLayouts = new ArrayList<>();
                        if(oldAnimatedLayout!=picolo) relativeLayouts.add(picolo);
                        if(oldAnimatedLayout!=more_or_less) relativeLayouts.add(more_or_less);
                        if(oldAnimatedLayout!=red_or_black) relativeLayouts.add(red_or_black);
                        if(oldAnimatedLayout!=comming_soon) relativeLayouts.add(comming_soon);
                        Collections.shuffle(relativeLayouts);
                        oldAnimatedLayout=relativeLayouts.get(0);
                        animateButton(relativeLayouts.get(0));
                    }
                });
            }
        };

        timer = new Timer();
        timer.schedule(timerTask, 1000, 3000);
        super.onResume();
    }
    // OnClickListeners
    @Override
    public void onClick(View view) {
        timer.cancel();

        //Si on clique sur start picolo
        if(view==picolo){
            Intent myIntent = new Intent(this, PicoloSettings.class);
            startActivityForResult(myIntent, 0);
        }

        // Si on clique sur les règles du picolo
        else if(view==picolo_rules){
            Intent myIntent = new Intent(this, PicoloRules.class);
            startActivityForResult(myIntent, 0);
        }

        //Si on clique sur start more or less
        else if(view==more_or_less){
            Intent myIntent = new Intent(this, TTBSettings.class);
            startActivityForResult(myIntent, 0);
        }

        // Si on clique sur les règles du more or less
        else if(view==more_or_less_rules){
            Intent myIntent = new Intent(this, TTBRules.class);
            startActivityForResult(myIntent, 0);
        }

        //Si on clique sur start red or black
        else if(view==red_or_black){
            Intent myIntent = new Intent(this, RedOrBlackSettings.class);
            startActivityForResult(myIntent, 0);
        }

        // Si on clique sur les règles du red or black
        else if(view==red_or_black_rules){
            Intent myIntent = new Intent(this, RedOrBlackRules.class);
            startActivityForResult(myIntent, 0);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
            // A propos
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void animateButton(final View view){
        RotateAnimation rotate = new RotateAnimation(0, rotationAngle, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(rotation_speed);
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setFillAfter(true);

        rotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                RotateAnimation rotate = new RotateAnimation(rotationAngle, -rotationAngle, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(rotation_speed);
                rotate.setInterpolator(new LinearInterpolator());
                rotate.setFillAfter(true);

                rotate.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        RotateAnimation rotate = new RotateAnimation(-rotationAngle, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        rotate.setDuration(rotation_speed);
                        rotate.setInterpolator(new LinearInterpolator());
                        rotate.setFillAfter(true);
                        rotate.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                if(animation_count>=2){
                                    animation_count = 0 ;
                                }

                                else{
                                    animation_count++ ;
                                    animateButton(view);
                                }
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        view.startAnimation(rotate);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                view.startAnimation(rotate);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        view.startAnimation(rotate);
    }
}
