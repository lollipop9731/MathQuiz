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

    // entered result by user
    int resultint;

    // right result of the term
    int correctresult;

    String[] operators;
    String[] correctresulttext;
    String[] correctresulttextsecond;
    String[] wrongresultfirst;
    String[] wrongresultsecond;

    int points = 0;

    // other stuff
    Random rgen = new Random();
    int counter = 0;


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
        mathquestion.setText(NewTerm());

        punktestand.setText(getResources().getString(R.string.points) + " " + Integer.toString(getPoints()));

        // New clicklistener
        View.OnClickListener onC = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {

                    // der Button zum Eingeben des Ergebnisses
                    case R.id.confirmnumber:


                        punktestand.setText(Integer.toString(correctresult));

                        // aktuellen Punktestand
                        points = getPoints();

                        //  result must be not empty
                        if (result.getText().length() > 0) {

                            // result, entered by user
                            resultint = Integer.parseInt(result.getText().toString());

                            // Animation des Antworttextes
                            Animation fadeinandout = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein_out);
                            answer.startAnimation(fadeinandout);


                            // Wenn das Ergebnis korrekt ist.
                            if (correctresult == resultint) {

                                switch (getDifficulty()) {

                                    // Leichte Schwierigkeitsstufe
                                    case 1:
                                        // points + 2 und passende Animation beim ersten Versuch
                                        if (counter == 0) {
                                            PointsAllocation(2);

                                        }
                                        // Beim zweiten Versuch
                                        if (counter == 1) {
                                            PointsAllocation(1);
                                        }

                                        break;

                                    // Mittlere Schwierigkeitsstufe
                                    case 2:

                                        if (counter == 0) {
                                            PointsAllocation(4);
                                        }
                                        // Beim zweiten Versuch
                                        if (counter == 1) {
                                            PointsAllocation(2);
                                        }

                                        break;
                                    // Schwerste Schwierigkeitsstufe
                                    case 3:
                                        // points + 6 und passende Animation
                                        if (counter == 0) {
                                            PointsAllocation(6);
                                        }
                                        // Beim zweiten Versuch, Counter auf 1
                                        if (counter == 1) {
                                            PointsAllocation(3);
                                        }
                                        break;
                                }
                                // Counter resetten
                                counter = 0;

                                // Neue Aufgabe
                                mathquestion.setText(NewTerm());

                                // if user input is NOT correct
                            } else {

                                switch (getDifficulty()) {
                                    // Leicht
                                    case 1:
                                        PointsAllocation(-1);
                                        break;

                                    // Mittel
                                    case 2:
                                        PointsAllocation(-2);
                                        break;

                                    // Schwer
                                    case 3:
                                        PointsAllocation(-3);
                                        break;
                                }
                                counter++;

                                // New Term at every second try.
                                if (counter % 2 == 0)
                                    mathquestion.setText(NewTerm());
                            }
                            // if result is empty, show toast
                        } else {
                            mathquestion.setText(NewTerm());
                            //Toast.makeText(getApplicationContext(),"Bitte eine Zahl eingeben",Toast.LENGTH_LONG).show();
                        }
                        // set points
                        punktestand.setText(getResources().getString(R.string.points) + " " + Integer.toString(getPoints()));
                        break;
                }

                result.setText("");
            }


        };

        // set on clicklistener
        confirm.setOnClickListener(onC);


    }

    /**
     * Erstellte eine neue zufällige Mal und Geteilt Aufgabe.
     *
     * @param difficulty Kann 1-3 sein. 1 für leicht, 3 für schwer.
     * @return String mit dem neuen kompletten Term.
     */
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

            this.correctresult = operand1 * operand2;
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
                        teiler.clear();
                        operand1 = newRandom.nextInt(49) + 2;
                        for (int i = 2; i < operand1; i++) {
                            if (operand1 % i == 0 ) {
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
                        teiler.clear();
                        operand1 = newRandom.nextInt(150) + 20;
                        for (int i = 2; i < operand1; i++) {
                            if (operand1 % i == 0) {
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

                    // erste Zahl zwischen 50-300
                    while (true) {
                        teiler.clear();
                        operand1 = newRandom.nextInt(250) + 50;
                        for (int i = 2; i < operand1; i++) {
                            if (operand1 % i == 0) {
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

            this.correctresult = operand1 / operand2;
        }


        // return string
        String term = "Was ergibt " + Integer.toString(this.operand1) + " " +
                this.operators[this.operatorboolean] + " " + Integer.toString(this.operand2) +
                " ? ";
        return term;

    }

    /**
     * Zufällige Auswahl von allen vier Rechenarten.
     *
     * @return
     */
    public String newRandomtermAlleVier() {
        String term = "";
        int alle = rgen.nextInt(2);
        if (alle == 0) {
            return newRandomtermMalGeteilt(getDifficulty());
        } else {
            return newRandomtermPlusMinusNEU(getDifficulty());
        }

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

    /**
     * Aktuelle Anzahl an Punkten.
     *
     * @return Int Punktzahl
     */
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
        int plusmin,plusmax;

        ZahlenraumActivity zahlenraumActivity = new ZahlenraumActivity();

        Random newRandom = new Random();
        operators = new String[]{"+", "-"};
        operatorboolean = newRandom.nextInt(operators.length);
        // bei plus
        switch (difficulty) {
            case 1:

                plusmin = getZahlenraum(ZahlenraumActivity.PLUS,ZahlenraumActivity.LEICHT,ZahlenraumActivity.MINIMUM);
                plusmax = getZahlenraum(ZahlenraumActivity.PLUS,ZahlenraumActivity.LEICHT,ZahlenraumActivity.MAXIMUM);

                operand1 = newRandom.nextInt(plusmax-plusmin+1)+plusmin;
                operand2 = newRandom.nextInt(plusmax-plusmin+1)+plusmin;
                break;

            case 2:

                plusmin = getZahlenraum(ZahlenraumActivity.PLUS,ZahlenraumActivity.MITTEL,ZahlenraumActivity.MINIMUM);
                plusmax = getZahlenraum(ZahlenraumActivity.PLUS,ZahlenraumActivity.MITTEL,ZahlenraumActivity.MAXIMUM);
                operand1 = newRandom.nextInt(plusmax-plusmin+1)+plusmin;
                operand2 = newRandom.nextInt(plusmax-plusmin+1)+plusmin;
                break;
            case 3:
                plusmin = getZahlenraum(ZahlenraumActivity.PLUS,ZahlenraumActivity.SCHWER,ZahlenraumActivity.MINIMUM);
                plusmax = getZahlenraum(ZahlenraumActivity.PLUS,ZahlenraumActivity.SCHWER,ZahlenraumActivity.MAXIMUM);
                operand1 = newRandom.nextInt(plusmax-plusmin+1)+plusmin;
                operand2 = newRandom.nextInt(plusmax-plusmin+1)+plusmin;
                break;
        }


        // no result <0
        if (operatorboolean == 1) {
            operand2 = newRandom.nextInt(this.operand1 + 1);
            this.correctresult = operand1 - operand2;
        } else {
            this.correctresult = operand1 + operand2;
        }


        // return string
        String term = "Was ergibt " + Integer.toString(this.operand1) + " " +
                this.operators[this.operatorboolean] + " " + Integer.toString(this.operand2) +
                " ? ";
        return term;


    }

    public void PointsAllocation(int points) {
        if (points > 0) {
            animatePoints(pointsanimatetv, points, getResources().getColor(R.color.green));
            setPoints(getPoints() + points);
        } else {
            animatePoints(pointsanimatetv, points, getResources().getColor(R.color.red));
            setPoints(getPoints() + points);
        }
    }

    /**
     * Creates new term, depending on category.
     *
     * @param category
     */
    public String NewTerm() {

        Random newRandom = new Random();
        int whichone = newRandom.nextInt(2);

        switch (getCategory()) {
            case 1:
                return newRandomtermPlusMinusNEU(getDifficulty());

            case 2:

                switch (whichone){
                    case 0:
                       return NewMalTerm();
                    case 1:
                       return NewGeteiltTerm();
                }


            case 3:
                return newRandomtermAlleVier();

        }
        return "";
    }

    public String NewGeteiltTerm(){

        Random newRandom = new Random();
        // Alle Teiler
        ArrayList<Integer> teiler = new ArrayList<Integer>();

        // ArrayList mit ausgewählten Teiler, mit Ergebnis unter X
        ArrayList<Integer> finalteiler = new ArrayList<>();

        switch (getDifficulty()) {
            case 1:

                // Erste Schleife, sucht alle Teiler, außer 1 und die Zahl selbst
                while (true) {
                    teiler.clear();
                    operand1 = newRandom.nextInt(50)+2;
                    for (int i = 2; i < operand1; i++) {
                        if (operand1 % i == 0) {
                            teiler.add(i);
                        }
                    }
                    // Wenn größer als null, sonst wieder nach oben und neue Zahl generieren
                    if (teiler.size() > 0) {
                        operand2 = teiler.get(newRandom.nextInt(teiler.size()));
                        if(operand1/operand2>5 && operand1/operand2<10){
                            break;
                        }

                    }
                }

                break;

            case 2:

                // erste Zahl zwischen 50-150


                // Alle Teiler der zufälligen Zahl zu Array hinzufügen.
                while (true) {
                    teiler.clear();
                    operand1 = newRandom.nextInt(150) + 50;
                    for (int i = 2; i < operand1; i++) {
                        if (operand1 % i == 0) {
                            teiler.add(i);
                        }
                    }
                    // Wenn größer als null, sonst wieder nach oben und neue Zahl generieren
                    if (teiler.size() > 0) {
                        operand2 = teiler.get(newRandom.nextInt(teiler.size()));
                        if(operand1/operand2>5 && operand1/operand2<10){
                            break;
                        }

                    }
                }


                break;

            case 3:

                // erste Zahl zwischen 50-300
                while (true) {
                    teiler.clear();
                    operand1 = newRandom.nextInt(250) + 50;
                    for (int i = 2; i < operand1; i++) {
                        if (operand1 % i == 0) {
                            teiler.add(i);
                        }
                    }
                    // Wenn größer als null, sonst wieder nach oben und neue Zahl generieren
                    if (teiler.size() > 0) {
                      operand2 = teiler.get(newRandom.nextInt(teiler.size()));
                        if(operand1/operand2>5 && operand1/operand2<10){
                            break;
                        }

                    }
                }
                break;
        }

        this.correctresult = operand1 / operand2;



    // return string
    String term = "Was ergibt " + Integer.toString(this.operand1) + " ÷ " + Integer.toString(this.operand2) +
            " ? ";
    return term;


}

    public String NewMalTerm(){

        Random newRandom = new Random();
        switch (getDifficulty()) {
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

        this.correctresult = operand1 * operand2;

        String term = "Was ergibt " + Integer.toString(this.operand1) + " * " + Integer.toString(this.operand2) +
                " ? ";
        return term;
    }

    public int getZahlenraum(String Category, String Difficulty, String minormax){
        SharedPreferences sharedPreferences = getSharedPreferences(Category,0);
        return sharedPreferences.getInt(Difficulty+minormax,0);
    }
}







