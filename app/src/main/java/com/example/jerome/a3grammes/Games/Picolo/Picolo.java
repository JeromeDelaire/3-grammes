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
import com.example.jerome.a3grammes.Games.Picolo.Database.ContentDAO;
import com.example.jerome.a3grammes.Games.Picolo.Database.PicoloDB;
import com.example.jerome.a3grammes.Global.Operations;
import com.example.jerome.a3grammes.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created by Jerome on 08/04/2017.
 * Jeu Picolo
 */

public class Picolo extends AppCompatActivity {
    private static final int TTL_MIN = 5 ;
    private static final int TTL_MAX = 20 ;
    private ArrayList<Player> players ;
    private TextView type, contentTV;
    private ContentDAO database ;
    private RelativeLayout root ;
    private String actualContent = "" ;
    private String actualType = "" ;

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

        database = new ContentDAO(this);
        database.openDB();

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
        if(PicoloDB.updated) {

            // Normal cat contents
            List<String> normalContents = Arrays.asList(getResources().getStringArray(R.array.picolo_normals));
            List<String> gamesContents = Arrays.asList(getResources().getStringArray(R.array.picolo_games));
            List<String> maledictionsContents = Arrays.asList(getResources().getStringArray(R.array.picolo_maledictions));
            List<String> endMaledictionsContents = Arrays.asList(getResources().getStringArray(R.array.picolo_end_maledictions));
            List<String> youPreferContents = Arrays.asList(getResources().getStringArray(R.array.picolo_prefers));

            // Dumb cat contents
            List<String> normalContentsDumb = Arrays.asList(getResources().getStringArray(R.array.picolo_normals_dumb));
            List<String> gamesContentsDumb = Arrays.asList(getResources().getStringArray(R.array.picolo_games_dumb));
            List<String> maledictionsContentsDumb = Arrays.asList(getResources().getStringArray(R.array.picolo_maledictions_dumb));
            List<String> endMaledictionsContentsDumb = Arrays.asList(getResources().getStringArray(R.array.picolo_end_maledictions_dumb));
            List<String> youPreferContentsDumb = Arrays.asList(getResources().getStringArray(R.array.picolo_prefers_dumb));

            // Sexy cat contents
            List<String> normalContentsSexy = Arrays.asList(getResources().getStringArray(R.array.picolo_normals_sexy));
            List<String> gamesContentsSexy = Arrays.asList(getResources().getStringArray(R.array.picolo_games_sexy));
            List<String> maledictionsContentsSexy = Arrays.asList(getResources().getStringArray(R.array.picolo_maledictions_sexy));
            List<String> endMaledictionsContentsSexy = Arrays.asList(getResources().getStringArray(R.array.picolo_end_maledictions_sexy));
            List<String> youPreferContentsSexy = Arrays.asList(getResources().getStringArray(R.array.picolo_prefers_sexy));

            // Hard cat contents
            List<String> normalContentsHard = Arrays.asList(getResources().getStringArray(R.array.picolo_normals_hard));
            List<String> gamesContentsHard = Arrays.asList(getResources().getStringArray(R.array.picolo_games_hard));
            List<String> maledictionsContentsHard = Arrays.asList(getResources().getStringArray(R.array.picolo_maledictions_hard));
            List<String> endMaledictionsContentsHard = Arrays.asList(getResources().getStringArray(R.array.picolo_end_maledictions_hard));
            List<String> youPreferContentsHard = Arrays.asList(getResources().getStringArray(R.array.picolo_prefers_hard));

            ArrayList<Content> contents = new ArrayList<>();

            /*
             * Normal cat contents
             */
            //Création des contents normaux
            for (int i = 0; i < normalContents.size(); i++)
                contents.add(new Content("normal", getResources().getString(R.string.normal), normalContents.get(i), null, -1, 0, getRandomPlayers()));
            // Création des contenus "Jeux"
            for (int i = 0; i < gamesContents.size(); i++)
                contents.add(new Content("normal", getResources().getString(R.string.game), gamesContents.get(i), null, -1, 0, getRandomPlayers()));
            // Création des contenus "Malédictions"
            for (int i = 0; i < maledictionsContents.size(); i++)
                contents.add(new Content("normal", getResources().getString(R.string.malediction), maledictionsContents.get(i), endMaledictionsContents.get(i), -1, 0, getRandomPlayers()));
            // Création des contenus "tu préfères"
            for(int i=0 ; i<youPreferContents.size() ; i++)
                contents.add(new Content("normal", getResources().getString(R.string.you_prefer), youPreferContents.get(i), null, -1, 0, getRandomPlayers()));

             /*
             * Dumb cat contents
             */
            //Création des contents normaux
            for (int i = 0; i < normalContentsDumb.size(); i++)
                contents.add(new Content("dumb", getResources().getString(R.string.normal), normalContentsDumb.get(i), null, -1, 0, getRandomPlayers()));
            // Création des contenus "Jeux"
            for (int i = 0; i < gamesContentsDumb.size(); i++)
                contents.add(new Content("dumb", getResources().getString(R.string.game), gamesContentsDumb.get(i), null, -1, 0, getRandomPlayers()));
            // Création des contenus "Malédictions"
            for (int i = 0; i < maledictionsContentsDumb.size(); i++)
                contents.add(new Content("dumb", getResources().getString(R.string.malediction), maledictionsContentsDumb.get(i), endMaledictionsContentsDumb.get(i), -1, 0, getRandomPlayers()));
            // Création des contenus "tu préfères"
            for(int i=0 ; i<youPreferContentsDumb.size() ; i++)
                contents.add(new Content("dumb", getResources().getString(R.string.you_prefer), youPreferContentsDumb.get(i), null, -1, 0, getRandomPlayers()));

            /*
             * Sexy cat contents
             */
            //Création des contents normaux
            for (int i = 0; i < normalContentsSexy.size(); i++)
                contents.add(new Content("sexy", getResources().getString(R.string.normal), normalContentsSexy.get(i), null, -1, 0, getRandomPlayers()));
            // Création des contenus "Jeux"
            for (int i = 0; i < gamesContentsSexy.size(); i++)
                contents.add(new Content("sexy", getResources().getString(R.string.game), gamesContentsSexy.get(i), null, -1, 0, getRandomPlayers()));
            // Création des contenus "Malédictions"
            for (int i = 0; i < maledictionsContentsSexy.size(); i++)
                contents.add(new Content("sexy", getResources().getString(R.string.malediction), maledictionsContentsSexy.get(i), endMaledictionsContentsSexy.get(i), -1, 0, getRandomPlayers()));
            // Création des contenus "tu préfères"
            for(int i=0 ; i<youPreferContentsSexy.size() ; i++)
                contents.add(new Content("sexy", getResources().getString(R.string.you_prefer), youPreferContentsSexy.get(i), null, -1, 0, getRandomPlayers()));

            /*
             * Hard cat contents
             */
            //Création des contents normaux
            for (int i = 0; i < normalContentsHard.size(); i++)
                contents.add(new Content("hard", getResources().getString(R.string.normal), normalContentsHard.get(i), null, -1, 0, getRandomPlayers()));
            // Création des contenus "Jeux"
            for (int i = 0; i < gamesContentsHard.size(); i++)
                contents.add(new Content("hard", getResources().getString(R.string.game), gamesContentsHard.get(i), null, -1, 0, getRandomPlayers()));
            // Création des contenus "Malédictions"
            for (int i = 0; i < maledictionsContentsHard.size(); i++)
                contents.add(new Content("hard", getResources().getString(R.string.malediction), maledictionsContentsHard.get(i), endMaledictionsContentsHard.get(i), -1, 0, getRandomPlayers()));
            // Création des contenus "tu préfères"
            for(int i=0 ; i<youPreferContentsHard.size() ; i++)
                contents.add(new Content("hard", getResources().getString(R.string.you_prefer), youPreferContentsHard.get(i), null, -1, 0, getRandomPlayers()));

            for (int i = 0; i < contents.size(); i++)
                database.insert(contents.get(i));

        }

        if(savedInstanceState==null)
            selectRandomContent();

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(database.getAvailableContents()==0 && database.getNbComplementReady()==0)
                    exitByBackKey();
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

        Content content = null;

        // On sélectionne un complément aléatoirement parmis ceux disponibles
        content = selectRandomComplement() ;

        // Si il n'y avait pas de complément disponible, on prend un contentTV
        if(content==null) {

            // On décrément les ttl > 0
            database.decrementTTL();

            content = database.selectRandom();

            long id = content.getId();

            // On passe done à 1 pour ne pas le retirer
            database.setDone(id);

            // Si c'est une malédiction on choisit un ttl
            if (Objects.equals(content.getType(), getResources().getString(R.string.malediction)))
                database.setTTL(id, getRandomTtl());


            content = formatContent(content);
            actualContent = content.getContent();
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
        if(Objects.equals(content.getCat(), "normal")){
            root.setBackgroundResource(R.color.picolo_normal);
            window.setStatusBarColor(getResources().getColor(R.color.picolo_normal));
        }
        // Dumb cat
        else if(Objects.equals(content.getCat(), "dumb")){
            root.setBackgroundResource(R.color.picolo_dumb);
            window.setStatusBarColor(getResources().getColor(R.color.picolo_dumb));
        }

        // sexy cat
        else if(Objects.equals(content.getCat(), "sexy")){
            root.setBackgroundResource(R.color.picolo_sexy);
            window.setStatusBarColor(getResources().getColor(R.color.picolo_sexy));
        }

        // Hard cat
        else if(Objects.equals(content.getCat(), "hard")){
            root.setBackgroundResource(R.color.picolo_hard);
            window.setStatusBarColor(getResources().getColor(R.color.picolo_hard));
        }
        actualType = content.getType();
        type.setText(actualType);
    }

    private Content selectRandomComplement(){
        int nbComlementReady = 0 ;
        // Si il n'y a pas de compléments prêt on retourne null
        if((nbComlementReady=database.getNbComplementReady())==0)
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
}
