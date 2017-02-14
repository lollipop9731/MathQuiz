package com.example.l.mathquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MenuActivity extends AppCompatActivity  {



    // Variablen

    Button startbutton;
    Button rewardcoinsbutton;
    ImageButton settingsButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);



        // Layout declarations

        startbutton = (Button) findViewById(R.id.startbutton);
        rewardcoinsbutton = (Button) findViewById(R.id.punktebutton);
        settingsButton = (ImageButton) findViewById(R.id.settings);


        // Click Listener
        View.OnClickListener onC = new View.OnClickListener() {

            public void onClick(View v){

                switch (v.getId()){

                    // Startet die Schwierigkeitenactivity, DifficultyActivity
                    case R.id.startbutton:
                        Intent start = new Intent(MenuActivity.this, DifficultyActivity.class);
                        startActivity(start);
                        break;

                    // Startet die Punkteanzeige, PunkteActivity
                    case R.id.punktebutton:
                        Intent start2 = new Intent(MenuActivity.this,PunkteActivity.class);
                        startActivity(start2);
                        break;

                    // Startet die Appeinstellungen, Settings_Activity
                    case R.id.settings:
                        Intent start3 = new Intent(MenuActivity.this,Settings_Activity.class);
                        startActivity(start3);
                        break;


                }
            }
        };


        // Clicklistener aktivieren

        startbutton.setOnClickListener(onC);
        rewardcoinsbutton.setOnClickListener(onC);
        settingsButton.setOnClickListener(onC);

    }


}
