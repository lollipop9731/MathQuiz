package com.example.l.mathquiz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class  PunkteActivity extends AppCompatActivity {

    RelativeLayout ll;
    View.OnClickListener onC;
    TextView punktestandtv, eingelösttv;
    PlusUndMinusActivity anim;
    TextView pointsanimtetv;
    int wieivielpoints;
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, eingeloestbtn, btn8, newziel, neu, current;
    ArrayList<Integer> buttonid = new ArrayList<>();
    ArrayList<Integer> pointsarray = new ArrayList<>();
    int parsedint;
    String parsedString, name;
    EditText passwortEditText;

    Resources res;


    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punkte);

        ll = (RelativeLayout) findViewById(R.id.activity_punkte);
        punktestandtv = (TextView) findViewById(R.id.punktestand);
        pointsanimtetv = (TextView) findViewById(R.id.pointsanimate);
        anim = new PlusUndMinusActivity();
        eingeloestbtn = (Button) findViewById(R.id.eingeloest);
        newziel = (Button) findViewById(R.id.newgoal);

        //setPoints(2000);


        res = getResources();


        newButtonAfterUserInput();


        punktestandtv.setText(getResources().getString(R.string.points) + " " + Integer.toString(getPoints()));


        View.OnClickListener onC = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.eingeloest:
                        Intent inte = new Intent(PunkteActivity.this, eingeloestActivity.class);
                        //startActivity(inte);
                       // newButtonAfterUserInput();
                        break;

                    case R.id.newgoal:
                        if(getCheckbox()){
                        dialogPassswortZiele();}
                        else {
                            Intent neu1 = new Intent(PunkteActivity.this, NeuesZiel.class);
                            startActivity(neu1);
                        }

                        break;
                }
            }
        };
        eingeloestbtn.setOnClickListener(onC);
        newziel.setOnClickListener(onC);


    }

    protected void onRestart() {
        super.onRestart();
        recreate();
    }

    protected void onPause() {
        super.onPause();
        // newButtonAfterUserInput();

    }


    /**
     * @param name      Text des Buttons
     * @param id        Zugewiesene ID
     * @param margintop Abstand von oben
     * @param points    Wieviel Punkte wert
     * @return
     */
    public Button newButton(String name, int id, int margintop, final int points) {

        wieivielpoints = points;
        final Button newbtn = new Button(getApplicationContext());
        newbtn.setId(id);
        this.name = name;

        buttonid.add(id);
        pointsarray.add(points);


        ll.addView(newbtn);



        newbtn.setText(" " + name + " " + points + " Punkte ");

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/PT_Serif-Web-Italic.ttf");


        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) newbtn.getLayoutParams();
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        lp.setMargins(0, margintop, 0, 0);


        newbtn.setAllCaps(false);
        newbtn.setTypeface(font);
        newbtn.setTextSize(18);
        newbtn.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        if (getPoints() >= points) {
            newbtn.setBackground(getResources().getDrawable(R.drawable.rechteck));
            newbtn.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

        }
        if (getPoints() < points) {
            newbtn.setBackground(getResources().getDrawable(R.drawable.rechteckhell));
            newbtn.setTextColor(getResources().getColor(R.color.grau));


        }


        newbtn.setLayoutParams(lp);

        newbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current = (Button) v;

                if (getPoints() < points) {
                    Toast.makeText(getApplicationContext(), "Zu wenige Punkte zum Einlösen!", Toast.LENGTH_SHORT).show();
                } else {
                    dialogEinlösen(-points);

                }
            }
        });

        newbtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                current = (Button) v;
                if(getCheckbox()) {
                    dialogPassswort();
                }else{
                    dialogLöschen();
                }


                return true;
            }
        });


        return newbtn;


    }

    public int getPoints() {
        SharedPreferences pointsshared = getSharedPreferences("Points", 0);
        if (pointsshared.getInt("Data1", 0) != 0) {
            return pointsshared.getInt("Data1", 0);
        }
        return 0;
    }


    public void setPoints(int points) {
        //Shared Preferences
        SharedPreferences pointsshared = getSharedPreferences("Points", 0);
        SharedPreferences.Editor editor = pointsshared.edit();
        if (points != 0) {
            editor.putInt("Data1", getPoints() + points);
            editor.commit();
        }
    }

    public void dialogEinlösen(final int points) {


        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);


        builder1.setMessage("Wirklich Einlösen?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "JA!",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        setPoints(points);
                        setPunktestand();
                        pointsanimtetv.bringToFront();
                        animatePoints(pointsanimtetv, points, getResources().getColor(R.color.red));
                        for (int i = 0; i < buttonid.size(); i++) {
                            Button btn = (Button) findViewById(buttonid.get(i));

                            if (getPoints() < pointsarray.get(i)) {
                                btn.setBackground(getResources().getDrawable(R.drawable.rechteckhell));
                                btn.setTextColor(getResources().getColor(R.color.grau));
                                //recreate();


                            }


                        }


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

    public void dialogPassswort() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);

        View view = (LayoutInflater.from(PunkteActivity.this)).inflate(R.layout.login_dialog, null);
        builder1.setView(view);
        passwortEditText = (EditText) view.findViewById(R.id.passwordid);


        builder1.setMessage("Bitte Passwort eingeben um fortzufahren.");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {



                        String passwort =  getPasswort();

                        String userpasswort = passwortEditText.getText().toString();




                        if (passwort.equals(userpasswort)) {
                            dialogLöschen();
                        } else{
                            Toast.makeText(getApplicationContext(), "Falsches Passwort", Toast.LENGTH_LONG).show();}


                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "Abbrechen",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void dialogPassswortZiele() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);

        View view = (LayoutInflater.from(PunkteActivity.this)).inflate(R.layout.login_dialog, null);
        builder1.setView(view);
        passwortEditText = (EditText) view.findViewById(R.id.passwordid);


        builder1.setMessage("Bitte Passwort eingeben um Ziele zu bearbeiten!");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {



                        String passwort =  getPasswort();

                        String userpasswort = passwortEditText.getText().toString();




                        if (passwort.equals(userpasswort)) {
                            Intent neu1 = new Intent(PunkteActivity.this, NeuesZiel.class);
                            startActivity(neu1);

                        } else{
                            Toast.makeText(getApplicationContext(), "Falsches Passwort", Toast.LENGTH_LONG).show();}


                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "Abbrechen",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void dialogLöschen() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Das Ziel wirklich löschen ?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "JA!",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        current.getId();
                        NeuesZiel neu1 = new NeuesZiel();


                        neu1.deleteDBEntry(Integer.toString(current.getId()), MyDatabase.FeedEntry._ID, getApplicationContext());

                        ll.removeView(current);


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

    public void setPunktestand() {
        int points = getPoints();
        punktestandtv.setText(getResources().getString(R.string.points) + " " + Integer.toString(getPoints()));
    }


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
        animation.setDuration(4000);
        view.startAnimation(animation);

    }

    public int setMargin(int button) {
        return button * 400 + 600 ;
    }

    public void newButtonAfterUserInput() {


        NeuesZiel neu2 = new NeuesZiel();
        int n = neu2.getAllGoalsFromDB(getApplicationContext()).size();

        for (int i = 0; i < n; i++) {
            neu = newButton(neu2.getGoalName(neu2.getAllGoalsFromDB(getApplicationContext()).get(i), getApplicationContext()), neu2.getAllIDs(getApplicationContext()).get(i), setMargin(i + 1), neu2.getAllPointsFromDB(getApplicationContext()).get(i));

        }


    }

    public void onBackPressed() {
        Intent jau = new Intent(PunkteActivity.this, MenuActivity.class);
        startActivity(jau);

    }

    public String getPasswort(){
        SharedPreferences passwordshared = getSharedPreferences("Password", 0);
        return passwordshared.getString("Password1","");
    }

    /**
     *
     * @return true if checkbox is checked.
     */
    public Boolean getCheckbox(){
        SharedPreferences sharedPreferences = getSharedPreferences("Checkbox",0);
        return  sharedPreferences.getBoolean("CheckboxData",false);
    }




}


