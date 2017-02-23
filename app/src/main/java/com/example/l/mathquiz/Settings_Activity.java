package com.example.l.mathquiz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class Settings_Activity extends AppCompatActivity {

    CheckBox pwCheckBox;
    EditText pw1,pw2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_);

        pwCheckBox = (CheckBox)findViewById(R.id.pw_checkbox);

        if(getCheckboxStatus()){
            pwCheckBox.setChecked(true);
        }else{
            pwCheckBox.setChecked(false);
        }
    }





    public void setPassword(String password) {
        //Shared Preferences
        SharedPreferences passwordshared = getSharedPreferences("Password", 0);
        SharedPreferences.Editor editor = passwordshared.edit();

        editor.putString("Password1", password);
        editor.commit();

    }

    public String getPassword() {
        SharedPreferences passwordshared = getSharedPreferences("Password", 0);
        return passwordshared.getString("Password1", "");
    }


    /**
     * Erstellt den ersten Dialog zum Erstellen eines Passwortes
     */
    public void dialogCreatePasswort(){

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // inflate view
        View view = (LayoutInflater.from(Settings_Activity.this)).inflate(R.layout.create_password,null);
        alertDialog.setView(view);
        // Erstellen
        pw1 = (EditText)view.findViewById(R.id.passwordid);
        // Bestätigen
        pw2 = (EditText)view.findViewById(R.id.passwordid1);




        alertDialog.setMessage("Bitte Passwort erstellen und bestätigen.");
        alertDialog.setCancelable(true);

        alertDialog.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {

                        String passwort="";
                        String passwortconfirm="";

                        if(pw1.getText().length()>0 && pw2.getText().length()>0) {
                            passwort = pw1.getText().toString();
                            passwortconfirm = pw2.getText().toString();

                            if(passwort.equals(passwortconfirm)){

                                setPassword(passwort);
                                setCheckboxStatus(true);
                                Toast.makeText(getApplicationContext(),"Passwort erstellt!",Toast.LENGTH_LONG).show();
                                dialogInterface.cancel();
                            }else{
                                Toast.makeText(getApplicationContext(),"Passwörter stimmen nicht überein!",Toast.LENGTH_LONG).show();
                                pwCheckBox.setChecked(false);
                            }


                        }else{
                            Toast.makeText(getApplicationContext(),"Bitte Text eingeben",Toast.LENGTH_SHORT).show();
                            pwCheckBox.setChecked(false);
                        }






                    }
                });

        alertDialog.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                pwCheckBox.setChecked(false);
                dialogInterface.cancel();
            }
        });

        alertDialog.create();
        alertDialog.show();


    }

    public void dialogPasswortDeaktivieren(){

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // inflate view
        View view = (LayoutInflater.from(Settings_Activity.this)).inflate(R.layout.login_dialog,null);
        alertDialog.setView(view);
        // Erstellen
        pw1 = (EditText)view.findViewById(R.id.passwordid);

        alertDialog.setMessage("Bitte Passwort eingeben.");
        alertDialog.setCancelable(true);

        alertDialog.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {

                        String passwort="";


                        if(pw1.getText().length()>0) {
                            passwort = pw1.getText().toString();


                            if(passwort.equals(getPassword())){

                                setPassword(passwort);

                                Toast.makeText(getApplicationContext(),"Passwortschutz deaktiviert!",Toast.LENGTH_SHORT).show();
                                pwCheckBox.setChecked(false);
                                setCheckboxStatus(false);
                                dialogInterface.cancel();
                            }else{
                                Toast.makeText(getApplicationContext(),"Passwörter nicht korrekt!",Toast.LENGTH_LONG).show();
                                pwCheckBox.setChecked(true);
                            }


                        }else{
                            Toast.makeText(getApplicationContext(),"Bitte Text eingeben",Toast.LENGTH_SHORT).show();
                            pwCheckBox.setChecked(true);
                        }






                    }
                });

        alertDialog.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                pwCheckBox.setChecked(false);
                dialogInterface.cancel();
            }
        });

        alertDialog.create();
        alertDialog.show();


    }

    public void OnCheckboxClicked(View view){

        Boolean isChecked = ((CheckBox)view).isChecked();

        if(isChecked){
            dialogCreatePasswort();
        }else{
            dialogPasswortDeaktivieren();

        }
    }


    /**
     *
     *
     * @param checkboxStatus true for checked, false for not checked.
     */
    public void setCheckboxStatus(Boolean checkboxStatus){

        SharedPreferences sharedPreferences = getSharedPreferences("Checkbox",0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean("CheckboxData",checkboxStatus);
        editor.commit();
    }

    public boolean getCheckboxStatus(){
        SharedPreferences sharedPreferences = getSharedPreferences("Checkbox",0);
       return  sharedPreferences.getBoolean("CheckboxData",false);

    }
}

