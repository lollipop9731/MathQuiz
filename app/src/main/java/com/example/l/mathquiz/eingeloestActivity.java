package com.example.l.mathquiz;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class eingeloestActivity extends AppCompatActivity {


    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    TextView Datum1tv,Datum2tv,Datum3tv,Datum4tv,Datum5tv,Datum6tv,Datum7tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eingeloest);



        Datum1tv = (TextView) findViewById(R.id.datum1);
        Datum2tv = (TextView) findViewById(R.id.datum2);
        Datum3tv = (TextView) findViewById(R.id.datum3);
        Datum4tv = (TextView) findViewById(R.id.datum4);
        Datum5tv = (TextView) findViewById(R.id.datum5);
        Datum6tv = (TextView) findViewById(R.id.datum6);
        Datum7tv = (TextView) findViewById(R.id.datum7);


        Datum1tv.setText(geteingelöstString("Datum1"));
        Datum2tv.setText(geteingelöstString("Datum2"));
        Datum3tv.setText(geteingelöstString("Datum3"));
        Datum4tv.setText(geteingelöstString("Datum4"));
        Datum5tv.setText(geteingelöstString("Datum5"));
        Datum6tv.setText(geteingelöstString("Datum6"));
        Datum7tv.setText(geteingelöstString("Datum7"));

    }



    public String geteingelöstString(String key){
        SharedPreferences stringsshared = getSharedPreferences("Eingeloest", 0);
        if (stringsshared.getString(key,null) != null) {
            return stringsshared.getString(key, null);

        }
        return "";
    }

}
