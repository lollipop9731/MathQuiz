package com.example.l.mathquiz;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class NeuesZielActivity extends AppCompatActivity {


    Button add, Dbbtn;
    EditText name, points;

    String parsedstring;
    TextView test;
    int counter = 0;
    int parsedpoints = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neues_ziel);

        add = (Button) findViewById(R.id.add_neuesziel);
        name = (EditText) findViewById(R.id.name_neuesziel);
        points = (EditText) findViewById(R.id.punkte_neuesziel);
        test = (TextView) findViewById(R.id.testtv1);


        View.OnClickListener onL = new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                switch (v.getId()) {

                    case R.id.add_neuesziel:
                        if (!checkIfGoalExists(name.getText().toString())) {
                            Toast.makeText(getApplicationContext(), "Ziel hinzugefügt!", Toast.LENGTH_SHORT).show();
                            writeOnDB(name.getText().toString(), Integer.parseInt(points.getText().toString()), getApplicationContext());


                        } else
                            Toast.makeText(getApplicationContext(), "Ziel existiert bereits!", Toast.LENGTH_SHORT).show();


                        break;



                }

            }
        };

        add.setOnClickListener(onL);



    }

    /**
     * Writes text and points of a goal in db.
     *
     * @param text
     * @param points
     */
    public void writeOnDB(String text, int points, Context context) {
        MyDatabase db = new MyDatabase(context);
        db.insert(text, points);
    }

    /**
     * Returns all Goals of DB.
     *
     * @return String Arraylist
     */
    public List<String> getAllGoalsFromDB(Context context) {
        return new MyDatabase(context).getAllGoals();
    }

    /**
     * Return all Points of DB.
     *
     * @return Integer Arraylist.
     */
    public List<Integer> getAllPointsFromDB(Context context) {
        return new MyDatabase(context).getAllPoints();
    }


    /**
     * Deletes whole DB.
     */
    public void deleteDB() {
        new MyDatabase(getApplicationContext()).deleteWholeDB();
    }

    public void createDB() {
        new MyDatabase(getApplicationContext()).createDB();
    }

    /**
     * Updates everything which is equal to name in a given column.
     *
     * @param name   The name of the String should be updated.
     * @param update The updated String.
     * @param column The coulum in which name is.
     */
    public void updateDB(String name, String update, String column) {
        new MyDatabase(getApplicationContext()).updateDB(name, update, column);
    }

    /**
     * Deletes everything in a row which equals name.
     *
     * @param name   the String of what should be deleted.
     * @param column In which column of DB {@code name} is.
     */
    public void deleteDBEntry(String name, String column, Context context) {
        new MyDatabase(context).deleteEntryOnDB(name, column);
    }

    public List<Integer> getPointbyGoal(String name, Context context) {
        return new MyDatabase(context).getPointbyGoals(name);
    }

    /**
     * Checks if Goals already exist in DB.
     *
     * @param goal
     * @return true, if it exists, false if it doesnt exist.
     */
    public boolean checkIfGoalExists(String goal) {
        if (getPointbyGoal(goal, getApplicationContext()).size() == 0) {
            return false;
        } else return true;
    }

    public String getGoalName(String goal, Context context) {
        return new MyDatabase(context).getGoalName(goal).get(0);
    }

    public int getIDbyGoal(String goal) {
        return new MyDatabase(getApplicationContext()).getIDbyName(goal).get(0);
    }

    public List<Integer> getAllIDs(Context context) {
        return new MyDatabase(context).getAllIDs();

    }
}
