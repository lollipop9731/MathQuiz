package com.example.l.mathquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity  {



    // Variablen

    Button startbutton;
    Button rewardcoinsbutton;
    ImageButton settingsButton;
    int backButtonCount=0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        backButtonCount=0;

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

    /**
     * Back button listener.
     * Will close the application if the back button pressed twice.
     */
    @Override
    public void onBackPressed()
    {
        if(backButtonCount >= 1)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this,R.string.appbeenden, Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }

}
