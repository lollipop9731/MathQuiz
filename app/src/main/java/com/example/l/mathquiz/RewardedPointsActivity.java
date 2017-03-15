package com.example.l.mathquiz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.example.l.mathquiz.R.id.MathQuestion;
import static com.example.l.mathquiz.R.id.settingsaction;


public class RewardedPointsActivity extends AppCompatActivity {

    ListView Rewards;
    MyDatabase myDatabase;


    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewarded_points);

        Rewards = (ListView) findViewById(R.id.ListViewReward);


        // Listview mit Datenbank
        myDatabase = new MyDatabase(getApplicationContext());
        List<Rewards> list = myDatabase.getAllFromSecond();
        ArrayAdapter<Rewards> arrayAdapter = new ArrayAdapter<Rewards>(this, android.R.layout.simple_list_item_1, list);
        Rewards.setAdapter(arrayAdapter);

        // Custom Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setTitleTextColor(getColor(R.color.weiß));
        setSupportActionBar(toolbar);

    }


        // Popup Menü
    public void showPopUp(View v){


        MenuBuilder menuBuilder = new MenuBuilder(this);
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.optionssettings,menuBuilder);

        MenuPopupHelper menuPopupHelper = new MenuPopupHelper(this,menuBuilder,v);
        menuPopupHelper.setForceShowIcon(true);
        menuPopupHelper.show();

            menuBuilder.setCallback(new MenuBuilder.Callback(){
                @Override
                public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.resetten:
                            DialogReset();
                            return true;
                        case R.id.zeitraum:
                            Toast.makeText(getApplicationContext(),"Zeitraum",Toast.LENGTH_SHORT).show();
                            return true;
                        default:
                            return false;
                    }
                }

                @Override
                public void onMenuModeChange(MenuBuilder menu) {}
            });

    }



    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){

            case R.id.settingsaction:

                showPopUp(findViewById(R.id.settingsaction));


                return true;
        }
        return true;
    }

    public void updateListview(){
        myDatabase = new MyDatabase(getApplicationContext());
        List<Rewards> list = myDatabase.getAllFromSecond();
        ArrayAdapter<Rewards> arrayAdapter = new ArrayAdapter<Rewards>(this, android.R.layout.simple_list_item_1, list);
        Rewards.setAdapter(arrayAdapter);
    }

    public void DialogReset(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);


        builder1.setMessage("Alle Felder zurücksetzen?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "JA!",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        myDatabase = new MyDatabase(getApplicationContext());
                        myDatabase.deleteAllEntriesonSecond();
                        updateListview();

                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "Nein, doch nicht.",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }



}
