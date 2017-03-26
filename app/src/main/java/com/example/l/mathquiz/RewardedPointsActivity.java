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
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Calendar;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class RewardedPointsActivity extends AppCompatActivity {

    ListView Rewards;
    MyDatabase myDatabase;
    ArrayAdapter<Rewards> arrayAdapter;
    List<Rewards> list;
    public AlertDialog dialog;

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
        toolbar.setTitle("Eingelöste Felder");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


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

        dialog = new AlertDialog.Builder(this)


        .setView(R.layout.timerange)
        .setMessage("Zeitraum wählen")
        .setCancelable(true)
        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDatabase myDatabase = new MyDatabase(getApplicationContext());
                DateTest Anfangsdatum = (DateTest)getDate("DateFirst");
                DateTest Enddatum = (DateTest)getDate("DateSecond");
                list = myDatabase.getInTimespan(Anfangsdatum.toMillis(),Enddatum.toMillis());
                arrayAdapter = new ArrayAdapter<Rewards>(RewardedPointsActivity.this, android.R.layout.simple_list_item_1, list);
                Rewards.setAdapter(arrayAdapter);
                dialog.cancel();
            }
        })
        .setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        })

        .create();



        dialog.show();







        final Button Anfang = (Button) dialog.findViewById(R.id.anfangid);
        final Button Ende = (Button) dialog.findViewById(R.id.endeid);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch(v.getId()){
                    case R.id.anfangid:
                        DatePickerDialog(0);
                        break;

                    case R.id.endeid:
                        DatePickerDialog(1);
                        break;
                }
            }
        };

        Anfang.setOnClickListener(onClickListener);
        Ende.setOnClickListener(onClickListener);



    }

    /**
     *
     * @param modus 0 for startdate 1 for enddate
     */
    public void DatePickerDialog(final int modus) {

        Calendar calendar = Calendar.getInstance();


        final DatePickerDialog datePickerDialog = new DatePickerDialog(RewardedPointsActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                DateTest dateTest = new DateTest();
                dateTest.setDay(dayOfMonth);
                dateTest.setMonth(month);
                dateTest.setYear(year);

                if(modus==0) {
                    setDate(dateTest, "DateFirst");
                    final Button Anfang = (Button)dialog.findViewById(R.id.anfangid);
                    Anfang.setText(getDate("DateFirst").toString());
                }
                if(modus==1){
                    setDate(dateTest,"DateSecond");
                    final Button Ende = (Button)dialog.findViewById(R.id.endeid);
                    Ende.setText(getDate("DateSecond").toString());
                }








            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));


        datePickerDialog.show();
    }

    /**
     *
     * @param key DateFirst for Anfang, DateSecond for Ende des Zeitraums.
     * @return
     */
    public Object getDate(String key) {
        SharedPreferences sharedPreferences = getSharedPreferences("Date", 0);

        Gson gson = new Gson();
        String json = sharedPreferences.getString(key, "");
        return gson.fromJson(json, DateTest.class);


    }

    /**
     *
     * @param date
     * @param key DateFirst vor Anfangsdatum. DateSecond for Enddatum
     */
    public void setDate(DateTest date,String key) {

        SharedPreferences sharedPreferences = getSharedPreferences("Date", 0);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();

        String json = gson.toJson(date);

        editor.putString(key, json);
        editor.commit();


    }


}
