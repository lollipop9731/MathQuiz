package com.example.l.mathquiz;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ZahlenraumActivity extends AppCompatActivity {

    EditText PlusVon,PlusBis,MinusVon,MinusBis,MalVon,MalBis,GeteiltVon,GeteiltBis;
    Button Save,Load;
    Spinner difficulty;

    // Konstanten Category
    private static String PLUS = "PLUS";
    private static String MINUS = "MINUS";
    private static String MAL = "MAL";
    private static String GETEILT = "GETEILT";

    // Konstanten Difficulty
    private static String LEICHT = "LEICHT";
    private static String MITTEL = "MITTEL";
    private static String SCHWER = "SCHWER";

    // Konstanten Minimum or Maximum
    private static String MINIMUM = "MINIMUM";
    private static String MAXIMUM = "MAXIMUM";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zahlenraum);

        PlusVon = (EditText)findViewById(R.id.zahlenraum_plus_minimum);
        PlusBis = (EditText)findViewById(R.id.zahlenraum_plus_maximum);
        MinusBis = (EditText)findViewById(R.id.zahlenraum_minus_minimum);
        MinusVon = (EditText)findViewById(R.id.zahlenraum_minus_maximum);
        MalVon = (EditText)findViewById(R.id.zahlenraum_mal_minimum);
        MalBis = (EditText)findViewById(R.id.zahlenraum_mal_maximum);
        GeteiltVon = (EditText)findViewById(R.id.zahlenraum_geteilt_minimum);
        GeteiltBis = (EditText)findViewById(R.id.zahlenraum_geteilt_maximum);
        Save =(Button)findViewById(R.id.saveButton);
        Load = (Button)findViewById(R.id.zahlenraum_load);
        difficulty = (Spinner)findViewById(R.id.schwierigkeitsgradspinner);


    }

    /**
     * Legt den gewünschten Zahlenraum fest. Erstellt SharedPreferences mit dem Namen der Kategorie.
     * PutInt mit dem Keyword Difficulty+minormax
     * @param Category Plus, Minus, Mal oder Geteilt. Schreibweise ist entscheident
     * @param Difficulty Leicht, Mittel oder Schwer
     * @param minormax Minimum oder Maximum
     * @param number gewünschte Zahl
     */
    public void SetZahlenRaum(String Category,String Difficulty,String minormax,int number){

        SharedPreferences sharedPreferences = getSharedPreferences(Category,0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(Difficulty+minormax,number);
        editor.commit();

    }

    public int getZahlenraum(String Category, String Difficulty, String minormax){
        SharedPreferences sharedPreferences = getSharedPreferences(Category,0);
        return sharedPreferences.getInt(Difficulty+minormax,0);
    }

    public void onSave(View v){

      writetoShared(PlusVon,PLUS,getSpinnerStatus(),MINIMUM);
        writetoShared(PlusBis,PLUS,getSpinnerStatus(),MAXIMUM);
        writetoShared(MinusVon,MINUS,getSpinnerStatus(),MINIMUM);
        writetoShared(MinusBis,MINUS,getSpinnerStatus(),MAXIMUM);
        writetoShared(MalVon,MAL,getSpinnerStatus(),MINIMUM);
        writetoShared(MalBis,MAL,getSpinnerStatus(),MAXIMUM);
        writetoShared(GeteiltVon,GETEILT,getSpinnerStatus(),MINIMUM);
        writetoShared(GeteiltBis,GETEILT,getSpinnerStatus(),MAXIMUM);
        Toast.makeText(getApplicationContext(),"Zahlenräume gespeichert",Toast.LENGTH_LONG).show();

    }

    public void onLoad(View v){

        PlusVon.setText(Integer.toString(getZahlenraum(PLUS,getSpinnerStatus(),MINIMUM)));
        PlusBis.setText(Integer.toString(getZahlenraum(PLUS,getSpinnerStatus(),MAXIMUM)));
        MinusVon.setText(Integer.toString(getZahlenraum(MINUS,getSpinnerStatus(),MINIMUM)));
        MinusBis.setText(Integer.toString(getZahlenraum(MINUS,getSpinnerStatus(),MAXIMUM)));
        MalVon.setText(Integer.toString(getZahlenraum(MAL,getSpinnerStatus(),MINIMUM)));
        MalBis.setText(Integer.toString(getZahlenraum(MAL,getSpinnerStatus(),MAXIMUM)));
        GeteiltVon.setText(Integer.toString(getZahlenraum(GETEILT,getSpinnerStatus(),MINIMUM)));
        GeteiltBis.setText(Integer.toString(getZahlenraum(GETEILT,getSpinnerStatus(),MAXIMUM)));

    }

    public void writetoShared(EditText editText,String Category, String Difficulty,String MinorMax){
        if(editText.getText().length()>0){
            int parse = Integer.parseInt(editText.getText().toString());
            SetZahlenRaum(Category,Difficulty,MinorMax,parse);
        }
    }

    public String getSpinnerStatus(){
        if(difficulty.getSelectedItem().toString().equals("Leicht")){
            return LEICHT;
        }
        if(difficulty.getSelectedItem().toString().equals("Mittel")){
            return MITTEL;
        }
        if(difficulty.getSelectedItem().toString().equals("Schwer")){
            return SCHWER;
        }
        return "";
    }
}
