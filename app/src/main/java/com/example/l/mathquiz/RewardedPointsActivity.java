package com.example.l.mathquiz;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.l.mathquiz.R.id.MathQuestion;


public class RewardedPointsActivity extends AppCompatActivity {

    ListView Rewards;
    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewarded_points);

        Rewards = (ListView)findViewById(R.id.ListViewReward);
        spinner = (Spinner)findViewById(R.id.spinner);

        // Listview mit Datenbank
        MyDatabase myDatabase = new MyDatabase(getApplicationContext());
        List<Rewards> list = myDatabase.getAllFromSecond();
        ArrayAdapter<Rewards> arrayAdapter = new ArrayAdapter<Rewards>(this,android.R.layout.simple_list_item_1,list);
        Rewards.setAdapter(arrayAdapter);

        // Custom Toolbar
        Toolbar toolbar =(Toolbar)findViewById(R.id.my_toolbar);
        toolbar.setTitleTextColor(getColor(R.color.weiß));
        setSupportActionBar(toolbar);

        // Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.spinnerarray,android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);




    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){

            case R.id.settingsaction:
                spinner.performClick();
                return true;
        }
        return true;
    }




}
