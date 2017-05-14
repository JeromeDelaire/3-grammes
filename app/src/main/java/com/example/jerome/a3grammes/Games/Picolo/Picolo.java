package com.example.jerome.a3grammes.Games.Picolo;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jerome.a3grammes.Games.Global.Player;
import com.example.jerome.a3grammes.Games.Picolo.Database.Content;
import com.example.jerome.a3grammes.Games.Picolo.Database.Helper.PicoloDatabaseAccess;
import com.example.jerome.a3grammes.Global.Operations;
import com.example.jerome.a3grammes.R;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 * Created by Jerome on 08/04/2017.
 * Jeu Picolo
 */

public class Picolo extends AppCompatActivity {
    private static final int TTL_MIN = 5 ;
    private static final int TTL_MAX = 20 ;
    private ArrayList<Player> players ;
    private TextView type, contentTV;
    private RelativeLayout root ;
    private String actualContent = "" ;
    private String actualType = "" ;
    private PicoloDatabaseAccess database ;

    /*
     * Création de l'activité
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picolo);

        // Désactive le verrouillage automatique
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        type = (TextView) findViewById(R.id.type_of_content_text_view);
        contentTV = (TextView) findViewById(R.id.content_text_view);
        root = (RelativeLayout) findViewById(R.id.picolo_main_layout);

        database = PicoloDatabaseAccess.getInstance(this);
        database.open();

        // Au lancement de la partie
        if(savedInstanceState==null){
            Bundle b = getIntent().getExtras();
            // Récupération des joueurs
            players = b.getParcelableArrayList("players");
            database.init(b.getBoolean("dumb"), b.getBoolean("sexy"), b.getBoolean("hard"));
        }

        // Après dévérouillage du téléphone on récupère les éléments sauvegardés
        else{
            players = savedInstanceState.getParcelableArrayList("players");
            actualType = savedInstanceState.getString("actualType");
            actualContent = savedInstanceState.getString("actualContent");
            contentTV.setText(actualContent);
            type.setText(actualType);
        }

        for(int i = 1 ; i<database.getSize() ; i++)
            database.updateNames(i, getRandomPlayers());

        if(savedInstanceState==null) {
            Bundle b = getIntent().getExtras();
            database.init(b.getBoolean("dumb"), b.getBoolean("sexy"), b.getBoolean("hard"));
            selectRandomContent();
        }

        // Si on clique sur l'écran
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(database.getAvailableContents()==0 && database.getNbComplementReady()==0)
                    finish();
                else
                    selectRandomContent();
            }
        });

    }

    /*
     * Sauvegarde l'état du jeu quand on vérouille le téléphone
     */
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList("players", players);
        savedInstanceState.putString("actualType", actualType);
        savedInstanceState.putString("actualContent", actualContent);
    }

    /*
     * Renvoi un contentTV aléatoire qui n'a pas déjà été pris
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void selectRandomContent(){

        Content content ;

        // On sélectionne un complément aléatoirement parmis ceux disponibles
        content = selectRandomComplement() ;

        // Si il n'y avait pas de complément disponible, on prend un contentTV
        if(content==null) {

            // On décrément les ttl > 0
            database.decrementTTL();

            content = database.selectRandom();

            long id = content.getId();

            // Si c'est une malédiction on choisit un ttl
            if (Objects.equals(content.getType(), getResources().getString(R.string.malediction)))
                database.setTTL(id, getRandomTtl());


            content = formatContent(content);
            actualContent = content.getContent();
            actualContent = actualContent.replace("[RandomLetter]", randomLetter());
            actualContent = actualContent.replace("[RandomMonth]", randomMonth());
            actualContent = actualContent.replaceFirst("Random100", String.valueOf(Operations.random_int(1, 100)));
            actualContent = actualContent.replaceFirst("Random100", String.valueOf(Operations.random_int(1, 100)));
            actualContent = actualContent.replaceFirst("Random100", String.valueOf(Operations.random_int(1, 100)));
            contentTV.setText(actualContent);
        }else{
            content = formatContent(content);
            actualContent = content.getComplement();
            contentTV.setText(actualContent);
        }
        if(!Objects.equals(content.getType(), getResources().getString(R.string.normal))){
            type.setVisibility(View.VISIBLE);
        }
        else{
            type.setVisibility(View.GONE);
        }

        Window window = this.getWindow();
        // Normal cat
        if(Objects.equals(content.getCat(), "Normal")){
            root.setBackgroundResource(R.color.picolo_normal);
            window.setStatusBarColor(getResources().getColor(R.color.picolo_normal));
        }
        // Dumb cat
        else if(Objects.equals(content.getCat(), "Débiles")){
            root.setBackgroundResource(R.color.picolo_dumb);
            window.setStatusBarColor(getResources().getColor(R.color.picolo_dumb));
        }

        // sexy cat
        else if(Objects.equals(content.getCat(), "Sexy")){
            root.setBackgroundResource(R.color.picolo_sexy);
            window.setStatusBarColor(getResources().getColor(R.color.picolo_sexy));
        }

        // Hard cat
        else if(Objects.equals(content.getCat(), "Hard")){
            root.setBackgroundResource(R.color.picolo_hard);
            window.setStatusBarColor(getResources().getColor(R.color.picolo_hard));
        }
        actualType = content.getType();
        type.setText(actualType);
    }

    private Content selectRandomComplement(){
        // Si il n'y a pas de compléments prêt on retourne null
        if((database.getNbComplementReady())==0)
            return null ;
        else{
            return database.getRandomComplement();
        }
    }

    /*
     * Retourne un ttl aléatoire
     */
    private int getRandomTtl(){
        // Si le nombre de contenu restant est supérieur au ttlMax
        int availableContents = database.getAvailableContents();
        if(availableContents>=TTL_MAX)
            return Operations.random_int(TTL_MIN, TTL_MAX);
            // Si le nombre de contenu restant est compris entre le ttlmax et le ttlmin
        else if(availableContents<TTL_MAX && availableContents>=TTL_MIN)
            return Operations.random_int(TTL_MIN, availableContents);
            // Si le nombre de contenus restant est inférieur au ttlmin
        else
            return availableContents ;
    }

    /*
     * Remplace les champs name1 et name2 dans les textes
     */
    private Content formatContent(Content content){
        String name1 = content.getName1();
        String name2 = content.getName2();
        content.setContent(content.getContent().replace("[Name1]", name1));
        content.setContent(content.getContent().replace("[Name2]", name2));
        if(content.getComplement()!=null){
            content.setComplement(content.getComplement().replace("[Name1]", name1));
            content.setComplement(content.getComplement().replace("[Name2]", name2));
        }
        return content ;
    }

    /*
     * Tire deux joueurs au hasard
     */
    private String[] getRandomPlayers(){
        ArrayList<Player> playersTemp = (ArrayList<Player>) players.clone();
        String[] names = new String[2];

        int random = Operations.random_int(0, playersTemp.size());
        names[0] = playersTemp.get(random).getName();
        playersTemp.remove(random);

        random = Operations.random_int(0, playersTemp.size());
        names[1] = playersTemp.get(random).getName();

        return names;
    }

    /*
    * Capture le bouton retour
    */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Si on appuie sur le bouton retour
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /*
      * Ouvre une fenêtre d'alerte lorsqu'on appuis sur le bouton retour
      */
    protected void exitByBackKey() {
        new AlertDialog.Builder(this, R.style.MyAlertDialogTheme)
                .setMessage(getResources().getString(R.string.alert_exit_game))
                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();
    }

    private String randomLetter(){
        Random r = new Random();
        char c = (char)(r.nextInt(26) + 'a');
        return String.valueOf(c);
    }

    private String randomMonth(){
        final String[] months = getResources().getStringArray(R.array.Months);
        int rand = (int) (Math.random() * 12);
        return (months[rand]);
    }
}
