package com.example.l.mathquiz;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.Calendar;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class RewardedPointsActivity extends AppCompatActivity {

    ListView Rewards;
    MyDatabase myDatabase;
    ArrayAdapter<Rewards> arrayAdapter;
    List<Rewards> list;


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
        list = myDatabase.getAllFromSecond();
        arrayAdapter = new ArrayAdapter<Rewards>(this, android.R.layout.simple_list_item_1, list);
        Rewards.setAdapter(arrayAdapter);

        // Custom Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setTitleTextColor(getColor(R.color.weiß));
        setSupportActionBar(toolbar);

    }


    // Popup Menü
    public void showPopUp(View v) {


        MenuBuilder menuBuilder = new MenuBuilder(this);
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.optionssettings, menuBuilder);

        MenuPopupHelper menuPopupHelper = new MenuPopupHelper(this, menuBuilder, v);
        menuPopupHelper.setForceShowIcon(true);
        menuPopupHelper.show();

        menuBuilder.setCallback(new MenuBuilder.Callback() {
            @Override
            public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.resetten:
                        DialogReset();
                        return true;
                    case R.id.zeitraum:


                        List<Rewards> list2 = myDatabase.getInTimespan(1489618800000L, 1489932768548L);
                        ArrayAdapter arrayAdapter = new ArrayAdapter<Rewards>(RewardedPointsActivity.this, android.R.layout.simple_list_item_1, list2);
                        Rewards.setAdapter(arrayAdapter);

                        TimeRangeDialog();


                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onMenuModeChange(MenuBuilder menu) {
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.settingsaction:

                showPopUp(findViewById(R.id.settingsaction));


                return true;
        }
        return true;
    }

    public void updateListview() {
        myDatabase = new MyDatabase(getApplicationContext());
        List<Rewards> list = myDatabase.getAllFromSecond();
        ArrayAdapter<Rewards> arrayAdapter = new ArrayAdapter<Rewards>(this, android.R.layout.simple_list_item_1, list);
        Rewards.setAdapter(arrayAdapter);
    }

    public void DialogReset() {
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

    public void TimeRangeDialog() {

        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Zeitspanne wählen")
                .setView(R.layout.timerange)
                .create();

        dialog.show();

        final Button Anfang = (Button) dialog.findViewById(R.id.anfangid);
        final Button Ende = (Button) dialog.findViewById(R.id.endeid);
        Anfang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog();
                Anfang.setText(getDate().toString());
            }
        });
    }


    public void DatePickerDialog() {

        Calendar calendar = Calendar.getInstance();


        DatePickerDialog datePickerDialog = new DatePickerDialog(RewardedPointsActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                DateTest dateTest = new DateTest();
                dateTest.setDay(dayOfMonth);
                dateTest.setMonth(month);
                dateTest.setYear(year);

                setDate(dateTest);


            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));


        datePickerDialog.show();
    }

    public Object getDate() {
        SharedPreferences sharedPreferences = getSharedPreferences("Date", 0);

        Gson gson = new Gson();
        String json = sharedPreferences.getString("DateData", "");
        return gson.fromJson(json, DateTest.class);


    }

    public void setDate(DateTest date) {

        SharedPreferences sharedPreferences = getSharedPreferences("Date", 0);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();

        String json = gson.toJson(date);

        editor.putString("DateData", json);
        editor.commit();


    }


}
