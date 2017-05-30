package com.example.l.mathquiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DifficultyActivity extends AppCompatActivity {

    //  Variablen
    TextView difficultytv;
    Button leichtbtn;
    Button mittelbtn;
    Button schwerbtn;


    // Setzen der Schriftart
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty);

        //CustomActionbar
        // Custom Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar1);
        toolbar.setTitleTextColor(getColor(R.color.weiß));
        toolbar.setTitle("Schwierigkeitsgrad");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        // Layouts

        difficultytv = (TextView) findViewById(R.id.difficulty);
        leichtbtn = (Button) findViewById(R.id.leicht);
        mittelbtn = (Button) findViewById(R.id.mittel);
        schwerbtn = (Button) findViewById(R.id.schwer);

        //Intents zum Starten von CategoriesActivity
        final Intent categories = new Intent(DifficultyActivity.this, CategoriesActivity.class);

        // Click Listener

        View.OnClickListener onCa = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {


                    case R.id.leicht:

                        setDifficulty(1);
                        startActivity(categories);
                        break;


                    case R.id.mittel:

                        setDifficulty(2);
                        startActivity(categories);
                        break;

                    case R.id.schwer:

                        setDifficulty(3);
                        startActivity(categories);
                        break;


                }

            }
        };

        // Clicklistener aktivieren
        leichtbtn.setOnClickListener(onCa);
        mittelbtn.setOnClickListener(onCa);
        schwerbtn.setOnClickListener(onCa);


    }

    /**
     * Setzt die Schwierigkeitsstufe fest.
     *
     * @param difficulty Zahl von 1-3,
     *                      1 für leicht, 2 für Mittel, 3 für schwer.
     */
    public void setDifficulty(int difficulty) {

        SharedPreferences difficultyShared = getSharedPreferences("difficulty", 0);
        SharedPreferences.Editor editor = difficultyShared.edit();

        // Schreibt die Schwierigkeitsstufe als SharedPreference in difficulty.
        editor.putInt("difficultyData", difficulty);
        editor.commit();

    }


}
