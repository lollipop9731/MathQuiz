package com.example.l.mathquiz;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * The type Main activity.
 */
public class PlusUndMinusActivity extends AppCompatActivity {

    static Boolean w;
    // Elements variables
    TextView mathquestion;
    TextView mathquiz;
    EditText result;
    Button confirm;
    TextView answer;
    TextView punktestand;
    TextView pointsanimatetv;
    // ints, doubles and String
    int operand1;
    int operand2;
    int operatorboolean;
    int resultint;
    int correctresult;
    String[] operators;
    String[] correctresulttext;
    String[] correctresulttextsecond;
    String[] wrongresultfirst;
    String[] wrongresultsecond;

    int points = 0;

    int alle = 0;


    // other stuff
    Random rgen = new Random();
    int counter = 0;
    Boolean p; // für allevier
    String catstr;
    String cat1str;
    String cat2str;
    String diffstr1;
    String diffstr2;
    String diffstr3;
    String diffmalstr1;
    String diffmalstr2;
    String diffmalstr3;
    String diffallestr1;
    String diffallestr2;
    String diffallestr3;

    // set font
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plusundminus);


        // corret result string array first try
        Resources res = getResources();
        correctresulttext = res.getStringArray(R.array.richtigesergebnis);

        // correct result string array second try
        correctresulttextsecond = res.getStringArray(R.array.richtigesergebniszweiterversuch);

        // wrong result string array first try
        wrongresultfirst = res.getStringArray(R.array.falschesergbnisfirst);

        // wrong result string array second try
        wrongresultsecond = res.getStringArray(R.array.falschesergbnissecond);

        // rechenaufgabe 2+3
        mathquestion = (TextView) findViewById(R.id.MathQuestion);

        // Feld zum Ergebnis eintragen
        result = (EditText) findViewById(R.id.result);

        // Button zum bestätigen
        confirm = (Button) findViewById(R.id.confirmnumber);

        // Text zum Einfügen des Antworttextes
        answer = (TextView) findViewById(R.id.rightorwrong);

        // Punktestand
        punktestand = (TextView) findViewById(R.id.points);

        // Points animated
        pointsanimatetv = (TextView) findViewById(R.id.pointsanimate);

        // Erste Aufgabe
        switch (getCategory()) {
            case 1:
                mathquestion.setText(newRandomtermPlusMinusNEU(getDifficulty()));
                break;
            case 2:
                mathquestion.setText(newRandomtermMalGeteilt(getDifficulty()));
                break;
            case 3:
                mathquestion.setText("Kategorie 3");
                break;
        }


        punktestand.setText(getResources().getString(R.string.points) + " " + Integer.toString(getPoints()));

        // New clicklistener
        View.OnClickListener onC = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {

                    // der Button zum Eingeben des Ergebnisses
                    case R.id.confirmnumber:


                        points = getPoints();
                        //  result must be not empty
                        if (result.getText().length() > 0) {


                            // result, entered by user
                            resultint = Integer.parseInt(result.getText().toString());

                            Animation fadeinandout = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein_out);
                            answer.startAnimation(fadeinandout);


                            if (catstr != null) {
                                if (getCorrectresultplusminus() == resultint) {


                                    if (counter == 0) {
                                        // FIRST TRY PLUS MINUS

                                        //leicht poitns
                                        if (diffstr1 != null) {

                                            // points + 2 animate
                                            animatePoints(pointsanimatetv, 2, getResources().getColor(R.color.green));
                                            setPoints(getPoints() + 2);

                                        }

                                        //mittel points
                                        if (diffstr2 != null) {
                                            setPoints(getPoints() + 4);

                                            // points + 4 animate
                                            animatePoints(pointsanimatetv, 4, getResources().getColor(R.color.green));
                                        }
                                        // schwer points
                                        if (diffstr3 != null) {
                                            setPoints(getPoints() + 6);

                                            // points + 6 animate
                                            animatePoints(pointsanimatetv, 6, getResources().getColor(R.color.green));
                                        }


                                        // random answertext
                                        answer.setText(correctresulttext[rgen.nextInt(correctresulttext.length)]);
                                        result.setText("");


                                    }
                                    // SECOND TRY PLUS MINUS
                                    if (counter == 1) {
                                        //leicht poitns
                                        if (diffstr1 != null) {
                                            setPoints(getPoints() + 1);
                                            // points + 2 animate
                                            animatePoints(pointsanimatetv, 1, getResources().getColor(R.color.green));
                                        }

                                        //mittel points
                                        if (diffstr2 != null) {
                                            setPoints(getPoints() + 2);
                                            // points + 4 animate
                                            animatePoints(pointsanimatetv, 2, getResources().getColor(R.color.green));
                                        }
                                        // schwer points
                                        if (diffstr3 != null) {
                                            setPoints(getPoints() + 3);

                                            // points + 6 animate
                                            animatePoints(pointsanimatetv, 3, getResources().getColor(R.color.green));
                                        }

                                        answer.setText(correctresulttextsecond[rgen.nextInt(correctresulttextsecond.length)]);
                                    }

                                    counter = 0;
                                    //leicht
                                    if (diffstr1 != null) {
                                        mathquestion.setText(newRandomtermPlusMinusNEU(getDifficulty()));
                                    }
                                    //mittel
                                    if (diffstr2 != null) {
                                        mathquestion.setText(newRandomtermPlusMinusNEU(getDifficulty()));
                                    }
                                    // schwer
                                    if (diffstr3 != null) {
                                        mathquestion.setText(newRandomtermPlusMinusNEU(getDifficulty()));
                                    }


                                    // reset input
                                    result.setText("");


                                    // WRONG PLUS MINUS
                                } else {


                                    counter++;

                                    if (diffstr1 != null) {
                                        setPoints(getPoints() - 1);
                                        animatePoints(pointsanimatetv, -1, getResources().getColor(R.color.red));
                                        answer.setText(wrongresultfirst[rgen.nextInt(wrongresultfirst.length)]);
                                    }
                                    //mittel
                                    if (diffstr2 != null) {
                                        setPoints(getPoints() - 2);
                                        animatePoints(pointsanimatetv, -2, getResources().getColor(R.color.red));
                                        answer.setText(wrongresultfirst[rgen.nextInt(wrongresultfirst.length)]);
                                    }
                                    // schwer
                                    if (diffstr3 != null) {
                                        setPoints(getPoints() - 3);
                                        animatePoints(pointsanimatetv, -3, getResources().getColor(R.color.red));
                                        answer.setText(wrongresultfirst[rgen.nextInt(wrongresultfirst.length)]);
                                    }


                                    if (counter % 2 == 0) {

                                        if (diffstr1 != null) {
                                            mathquestion.setText(newRandomtermPlusMinusNEU(getDifficulty()));
                                            setPoints(getPoints() - 1);
                                            animatePoints(pointsanimatetv, -1, getResources().getColor(R.color.red));
                                            answer.setText(wrongresultsecond[rgen.nextInt(wrongresultsecond.length)]);
                                        }
                                        //mittel
                                        if (diffstr2 != null) {
                                            mathquestion.setText(newRandomtermPlusMinusNEU(getDifficulty()));
                                            setPoints(getPoints() - 2);
                                            animatePoints(pointsanimatetv, -2, getResources().getColor(R.color.red));
                                            answer.setText(wrongresultsecond[rgen.nextInt(wrongresultsecond.length)]);
                                        }
                                        // schwer
                                        if (diffstr3 != null) {
                                            mathquestion.setText(newRandomtermPlusMinusNEU(getDifficulty()));
                                            setPoints(getPoints() - 3);
                                            animatePoints(pointsanimatetv, -3, getResources().getColor(R.color.red));
                                            answer.setText(wrongresultsecond[rgen.nextInt(wrongresultsecond.length)]);
                                        }
                                        counter = 0;
                                    }
                                }
                            }

                            // MAL UND GETEILT
                            if (cat1str != null) {

                                if (getCorrectresultmalgeteilt() == resultint) {

                                    // FIRST TRY MAL GETEILT
                                    if (counter == 0) {

                                        if (diffmalstr1 != null) {
                                            setPoints(getPoints() + 2);
                                            // points + 2 animate
                                            animatePoints(pointsanimatetv, 2, getResources().getColor(R.color.green));
                                        }

                                        //mittel points
                                        if (diffmalstr2 != null) {
                                            setPoints(getPoints() + 4);
                                            // points + 4 animate
                                            animatePoints(pointsanimatetv, 4, getResources().getColor(R.color.green));
                                        }
                                        // schwer points
                                        if (diffmalstr3 != null) {
                                            setPoints(getPoints() + 6);
                                            // points + 6 animate
                                            animatePoints(pointsanimatetv, 6, getResources().getColor(R.color.green));
                                        }

                                        // random answertext
                                        answer.setText(correctresulttext[rgen.nextInt(correctresulttext.length)]);
                                        result.setText("");
                                    }

                                    // SECOND TRY MAL GETEILT
                                    if (counter == 1) {

                                        if (diffmalstr1 != null) {
                                            setPoints(getPoints() + 1);
                                            // points + 2 animate
                                            animatePoints(pointsanimatetv, 1, getResources().getColor(R.color.green));
                                        }

                                        //mittel points
                                        if (diffmalstr2 != null) {
                                            setPoints(getPoints() + 2);
                                            // points + 4 animate
                                            animatePoints(pointsanimatetv, 2, getResources().getColor(R.color.green));
                                        }
                                        // schwer points
                                        if (diffmalstr3 != null) {
                                            setPoints(getPoints() + 3);
                                            // points + 6 animate
                                            animatePoints(pointsanimatetv, 3, getResources().getColor(R.color.green));
                                        }

                                        answer.setText(correctresulttextsecond[rgen.nextInt(correctresulttextsecond.length)]);
                                    }

                                    counter = 0;

                                    // LEICHT
                                    if (diffmalstr1 != null) {
                                        mathquestion.setText(newRandomtermMalGeteilt(getDifficulty()));
                                    }

                                    //MITTEL
                                    if (diffmalstr2 != null) {
                                        mathquestion.setText(newRandomtermMalGeteilt(getDifficulty()));
                                    }
                                    //MITTEL
                                    if (diffmalstr3 != null) {
                                        mathquestion.setText(newRandomtermMalGeteilt(getDifficulty()));
                                    }

                                    // reset input
                                    result.setText("");

                                    // if the userresult is wrong

                                } else {

                                    answer.setText(wrongresultfirst[rgen.nextInt(wrongresultfirst.length)]);

                                    counter++;
                                    if (diffmalstr1 != null) {
                                        animatePoints(pointsanimatetv, -1, getResources().getColor(R.color.red));
                                        setPoints(getPoints() - 1);
                                    }

                                    //MITTEL
                                    if (diffmalstr2 != null) {
                                        animatePoints(pointsanimatetv, -2, getResources().getColor(R.color.red));
                                        setPoints(getPoints() - 2);
                                    }
                                    //MITTEL
                                    if (diffmalstr3 != null) {
                                        animatePoints(pointsanimatetv, -3, getResources().getColor(R.color.red));
                                        setPoints(getPoints() - 3);
                                    }


                                    if (counter % 2 == 0) {

                                        // LEICHT
                                        if (diffmalstr1 != null) {
                                            mathquestion.setText(newRandomtermMalGeteilt(getDifficulty()));
                                        }

                                        //MITTEL
                                        if (diffmalstr2 != null) {
                                            mathquestion.setText(newRandomtermMalGeteilt(getDifficulty()));
                                        }
                                        //MITTEL
                                        if (diffmalstr3 != null) {
                                            mathquestion.setText(newRandomtermMalGeteilt(getDifficulty()));
                                        }


                                        answer.setText(wrongresultsecond[rgen.nextInt(wrongresultsecond.length)]);

                                        counter = 0;
                                    }
                                }
                            }

                            // ALLE VIER
                            if (cat2str != null) {

                                // wenn malgeteilt

                                if (getCorrectresultalle() == resultint) {
                                    // FIRST TRY
                                    if (counter == 0) {
                                        if (diffallestr1 != null) {
                                            setPoints(getPoints() + 2);
                                            // points + 2 animate
                                            animatePoints(pointsanimatetv, 2, getResources().getColor(R.color.green));
                                        }

                                        //mittel points
                                        if (diffallestr2 != null) {
                                            setPoints(getPoints() + 4);
                                            // points + 4 animate
                                            animatePoints(pointsanimatetv, 4, getResources().getColor(R.color.green));
                                        }
                                        // schwer points
                                        if (diffallestr3 != null) {
                                            setPoints(getPoints() + 6);
                                            // points + 6 animate
                                            animatePoints(pointsanimatetv, 6, getResources().getColor(R.color.green));
                                        }

                                        // random answertext
                                        answer.setText(correctresulttext[rgen.nextInt(correctresulttext.length)]);
                                        result.setText("");
                                    }
                                    if (counter == 1) {

                                        if (diffallestr1 != null) {
                                            setPoints(getPoints() + 1);
                                            // points + 2 animate
                                            animatePoints(pointsanimatetv, 1, getResources().getColor(R.color.green));
                                        }

                                        //mittel points
                                        if (diffallestr2 != null) {
                                            setPoints(getPoints() + 2);
                                            // points + 4 animate
                                            animatePoints(pointsanimatetv, 2, getResources().getColor(R.color.green));
                                        }
                                        // schwer points
                                        if (diffallestr3 != null) {
                                            points = points + 3;
                                            // points + 6 animate
                                            animatePoints(pointsanimatetv, 3, getResources().getColor(R.color.green));
                                        }

                                        answer.setText(correctresulttextsecond[rgen.nextInt(correctresulttextsecond.length)]);
                                    }


                                    counter = 0;


                                    if (diffallestr1 != null) {
                                        mathquestion.setText(newRandomtermAlleVier(1));
                                    }
                                    // mittel
                                    if (diffallestr2 != null) {
                                        mathquestion.setText(newRandomtermAlleVier(2));
                                    }
                                    // schwer
                                    if (diffallestr3 != null) {
                                        mathquestion.setText(newRandomtermAlleVier(2));
                                    }

                                    // reset input
                                    result.setText("");
                                } else {

                                    answer.setText(wrongresultfirst[rgen.nextInt(wrongresultfirst.length)]);

                                    counter++;
                                    // LEICHT
                                    if (diffallestr1 != null) {
                                        animatePoints(pointsanimatetv, -1, getResources().getColor(R.color.red));
                                        setPoints(getPoints() - 1);
                                    }

                                    //MITTEL
                                    if (diffallestr2 != null) {
                                        animatePoints(pointsanimatetv, -2, getResources().getColor(R.color.red));
                                        setPoints(getPoints() - 2);
                                    }
                                    //MITTEL
                                    if (diffallestr3 != null) {
                                        animatePoints(pointsanimatetv, -3, getResources().getColor(R.color.red));
                                        setPoints(getPoints() - 3);
                                    }


                                    if (counter % 2 == 0) {

                                        // LEICHT
                                        if (diffallestr1 != null) {
                                            mathquestion.setText(newRandomtermAlleVier(1));
                                        }

                                        //MITTEL
                                        if (diffallestr2 != null) {
                                            mathquestion.setText(newRandomtermAlleVier(2));
                                        }
                                        //MITTEL
                                        if (diffallestr3 != null) {
                                            mathquestion.setText(newRandomtermAlleVier(3));
                                        }


                                        answer.setText(wrongresultsecond[rgen.nextInt(wrongresultsecond.length)]);

                                        counter = 0;
                                    }
                                }


                            }


                            // if result is empty, show toast
                        } else {

                            Context context = getApplicationContext();
                            CharSequence empty = "Bitte eine Zahl eingeben " + getResources().getString(R.string.nameplayer) + " !";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, empty, duration);

                            toast.show();
                        }
                        // set points


                        punktestand.setText(getResources().getString(R.string.points) + " " + Integer.toString(getPoints()));


                        break;


                }


            }

        };

        // set on clicklistener
        confirm.setOnClickListener(onC);


    }

    public int getCorrectresultplusminus() {
        if (this.operatorboolean == 0) {
            correctresult = this.operand1 + this.operand2;
            return correctresult;
        }
        if (this.operatorboolean == 1) {
            correctresult = this.operand1 - this.operand2;
            return correctresult;
        }

        return 0;
    }

    public int getCorrectresultmalgeteilt() {
        if (this.operatorboolean == 0) {
            correctresult = this.operand1 * this.operand2;
            return correctresult;
        }
        if (this.operatorboolean == 1) {
            correctresult = this.operand1 / this.operand2;
            return correctresult;
        }

        return 0;
    }

    public int getCorrectresultalle() {
        if (this.alle == 0) {
            return getCorrectresultmalgeteilt();
        }
        if (this.alle == 1) {
            return getCorrectresultplusminus();
        }
        return 0;
    }


    public String newRandomtermMalGeteilt(int difficulty) {
        Random newRandom = new Random();
        operators = new String[]{"*", "÷"};

        operatorboolean = newRandom.nextInt(operators.length);


        // bei mal
        if (this.operatorboolean == 0) {
            switch (difficulty) {
                case 1:
                    // random number between 2-10
                    operand1 = newRandom.nextInt(9) + 2;

                    // generate number, so the result is below 50
                    operand2 = newRandom.nextInt(50 / operand1) + 1;
                    break;

                case 2:
                    // random numbers between 5-15
                    operand1 = newRandom.nextInt(10) + 5;

                    // generate number, so the result is between 50-100
                    operand2 = newRandom.nextInt(50 / operand1) + 50 / operand1;

                    break;

                case 3:
                    // random numbers between 10-20
                    operand1 = newRandom.nextInt(20) + 10;

                    // generate number, so the result is between 100-150
                    operand2 = newRandom.nextInt(100 / operand1) + 100 / operand1;
                    break;

            }
        }

        // bei geteilt
        if (this.operatorboolean == 1) {
            // Alle Teiler
            ArrayList<Integer> teiler = new ArrayList<Integer>();

            // ArrayList mit ausgewählten Teiler, mit Ergebnis unter X
            ArrayList<Integer> finalteiler = new ArrayList<>();

            switch (getDifficulty()) {
                case 1:

                    // Erste Schleife, sucht alle Teiler, außer 1 und die Zahl selbst
                    while (true) {
                        operand1 = newRandom.nextInt(49) + 2;
                        for (int i = 1; i <= operand1; i++) {
                            if (operand1 % i == 0 && i != operand1 && i != 1) {
                                teiler.add(i);
                            }
                        }
                        // Break, wenn Zahl mindestens einen Teiler hat
                        if (teiler.size() > 0)
                            break;
                    }

                    //Checked nach Teiler, dass das Ergebnis unter 10 ist.
                    for (int k = 0; k < teiler.size(); k++) {
                        int l = teiler.get(k);
                        if (operand1 / l < 10) {

                            finalteiler.add(l);
                        }

                    }

                    operand2 = finalteiler.get(newRandom.nextInt(finalteiler.size()));

                    break;

                case 2:

                    // erste Zahl zwischen 50-150


                    // Alle Teiler der zufälligen Zahl zu Array hinzufügen.
                    while (true) {
                        operand1 = newRandom.nextInt(150) + 20;
                        for (int i = 1; i <= operand1; i++) {
                            if (operand1 % i == 0 && i != operand1 && i != 1) {
                                teiler.add(i);
                            }
                        }
                        if (teiler.size() > 0)
                            break;
                    }

                    while (true) {
                        for (int k = 0; k < teiler.size(); k++) {
                            int l = teiler.get(k);
                            if (operand1 / l > 5 && operand1 / l < 20) {

                                finalteiler.add(l);
                            }

                        }
                        if (finalteiler.size() > 0) {
                            operand2 = finalteiler.get(newRandom.nextInt(finalteiler.size()));
                            break;
                        } else {
                            operand1 = newRandom.nextInt(150) + 20;
                        }
                    }


                    break;

                case 3:

                    // erste Zahl zwischen 100-200
                    while (true) {
                        operand1 = newRandom.nextInt(250) + 50;
                        for (int i = 1; i <= operand1; i++) {
                            if (operand1 % i == 0 && i != operand1 && i != 1) {
                                teiler.add(i);
                            }
                        }
                        if (teiler.size() > 0)
                            break;
                    }
                    while (true) {
                        for (int k = 0; k < teiler.size(); k++) {
                            int l = teiler.get(k);
                            if (operand1 / l > 15 && operand1 / l < 30) {

                                finalteiler.add(l);
                            }

                        }
                        if (finalteiler.size() > 0) {
                            operand2 = finalteiler.get(newRandom.nextInt(finalteiler.size()));
                            break;
                        } else {
                            operand1 = newRandom.nextInt(250) + 50;
                        }
                    }


                    break;


            }

        }


        // return string
        String term = "Was ergibt " + Integer.toString(this.operand1) + " " +
                this.operators[this.operatorboolean] + " " + Integer.toString(this.operand2) +
                " ? ";
        return term;

    }

    public String newRandomtermAlleVier(int difficult) {
        String term = "";
        this.alle = rgen.nextInt(2);
        if (this.alle == 0) {
            switch (difficult) {
                case 1:
                    term = newRandomtermMalGeteilt(getDifficulty());
                    break;
                case 2:
                    term = newRandomtermMalGeteilt(getDifficulty());
                    break;
                case 3:
                    term = newRandomtermMalGeteilt(getDifficulty());

            }
            return term;

        }
        if (this.alle == 1) {
            switch (difficult) {
                case 1:
                    term = newRandomtermPlusMinusNEU(getDifficulty());
                    break;
                case 2:
                    term = newRandomtermPlusMinusNEU(getDifficulty());
                    break;
                case 3:
                    term = newRandomtermPlusMinusNEU(getDifficulty());
            }
        }
        return term;
    }


    /**
     * Animate points
     *
     * @param view   Textview
     * @param points Number of points
     * @param color  Color as an int
     */
    public void animatePoints(TextView view, int points, int color) {

        String pointstr = "";
        if (points > 0) {
            pointstr = "+ " + Integer.toString(points);
        }
        if (points < 0) {
            pointstr = "" + Integer.toString(points);
        }
        view.setText(pointstr);
        view.setTextColor(color);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.points_animation);

        view.startAnimation(animation);

    }

    public int getPoints() {

        SharedPreferences pointsshared = getSharedPreferences("Points", 0);

        return pointsshared.getInt("Data1", 0);


    }

    public void setPoints(int points) {
        //Shared Preferences
        SharedPreferences pointsshared = getSharedPreferences("Points", 0);
        SharedPreferences.Editor editor = pointsshared.edit();

        editor.putInt("Data1", points);
        editor.commit();

    }

    /**
     * @return Aktuell gewählte Kategorie.
     * 1 für Plus und Minus
     * 2 für Mal und Geteilt
     * 3 für alle vier Rechenarten
     */
    public int getCategory() {
        // erstellt die SharedPreferences category
        SharedPreferences categoryShared = getSharedPreferences("category", 0);

        if (categoryShared.getInt("categoryData", 0) != 0) {
            return categoryShared.getInt("categoryData", 0);
        }
        return 0;
    }

    /**
     * Returned die aktuelle Schwierigkeitsstufe.
     *
     * @return 1 für leicht, 2 für mittel, 3 für schwer.
     * <p>
     * Wenn null, dann 0.
     */
    public int getDifficulty() {

        SharedPreferences difficulty = getSharedPreferences("difficulty", 0);
        if (difficulty.getInt("difficultyData", 0) != 0) {
            return difficulty.getInt("difficultyData", 0);
        }

        return 0;
    }

    /**
     * Erzeugt einen neuen zufälligen Term.
     *
     * @param difficulty Die verschiedenen Schwierigkeitsstufen.
     *                   1 für 0-20, 2 für 20-75, 3 für 75 -150.
     * @return String Term
     */
    public String newRandomtermPlusMinusNEU(int difficulty) {

        Random newRandom = new Random();
        operators = new String[]{"+", "-"};
        operatorboolean = newRandom.nextInt(operators.length);
        // bei plus
        switch (difficulty) {
            case 1:
                operand1 = newRandom.nextInt(20);
                operand2 = newRandom.nextInt(20);
                break;
            case 2:
                operand1 = newRandom.nextInt(55) + 20;
                operand2 = newRandom.nextInt(55) + 20;
                break;
            case 3:
                operand1 = newRandom.nextInt(75) + 75;
                operand2 = newRandom.nextInt(75) + 75;
                break;
        }


        // no result <0
        if (operatorboolean == 1) {
            operand2 = newRandom.nextInt(this.operand1 + 1);
        }


        // return string
        String term = "Was ergibt " + Integer.toString(this.operand1) + " " +
                this.operators[this.operatorboolean] + " " + Integer.toString(this.operand2) +
                " ? ";
        return term;


    }


}







