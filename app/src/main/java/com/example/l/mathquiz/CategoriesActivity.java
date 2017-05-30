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

public class CategoriesActivity extends AppCompatActivity {

     // Elements of layout
    TextView categoriestv;
    Button plusminusbtn;
    Button malgeteiltbtn;
    Button allevierbtn;


    // font setzen
    @Override
    protected void attachBaseContext(Context newBase){
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        //set Actionbar
        // Custom Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar1);
        toolbar.setTitleTextColor(getColor(R.color.weiß));
        toolbar.setTitle("Eingelöste Felder");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Intent to start PlusUndMinusActivity
        final Intent plusminus = new Intent(CategoriesActivity.this, PlusUndMinusActivity.class);


        // Categories Textview
        categoriestv = (TextView) findViewById(R.id.categories);

        // Buttons
        plusminusbtn = (Button) findViewById(R.id.plusminus);
        malgeteiltbtn = (Button) findViewById(R.id.malgeteilt);
        allevierbtn = (Button) findViewById(R.id.allevier);

        // new Clicklistener
 
        View.OnClickListener onC = new View.OnClickListener(){

            public void onClick(View v){

                switch(v.getId()){

                    case R.id.plusminus:

                        setCategory(1);
                        startActivity(plusminus);
                        break;

                    case R.id.malgeteilt:

                        setCategory(2);
                        startActivity(plusminus);
                        break;

                    case R.id.allevier:

                        setCategory(3);
                        startActivity(plusminus);
                        break;
                }
            }
        };

        // activate ClickListener
        plusminusbtn.setOnClickListener(onC);
        malgeteiltbtn.setOnClickListener(onC);
        allevierbtn.setOnClickListener(onC);
    }

    /**
     * Setzt die Kategorie fest.
     * @param category 1 für Plus und Minus,
     *                 2 für Mal und Geteilt,
     *                 3 für alle vier Rechenarten
     */
    public void setCategory(int category){

        // erstellt die SharedPreferences category
        SharedPreferences categoryShared = getSharedPreferences("category",0);
        SharedPreferences.Editor editor = categoryShared.edit();

        // schreibt die gewählte Art (1-3)
        editor.putInt("categoryData",category);
        editor.commit();
    }


}
