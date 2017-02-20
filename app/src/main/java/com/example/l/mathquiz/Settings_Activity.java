package com.example.l.mathquiz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class Settings_Activity extends AppCompatActivity {

    Switch passwordSwitch;
    EditText pwtypeinTextView, pwconfirmTextView;
    Button confirmpwButton;
    EditText passwortEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_);

        passwordSwitch = (Switch) findViewById(R.id.passwordonoff);
        pwtypeinTextView = (EditText) findViewById(R.id.typeinpw);
        pwconfirmTextView = (EditText) findViewById(R.id.typeinpwconfirm);
        confirmpwButton = (Button) findViewById(R.id.confirmpwbtn);

        pwtypeinTextView.setVisibility(View.INVISIBLE);
        pwconfirmTextView.setVisibility(View.INVISIBLE);
        confirmpwButton.setVisibility(View.INVISIBLE);




        if(getSwitchStatus()){
            passwordSwitch.setChecked(true);
        }else{
            passwordSwitch.setChecked(false);
        }

        if(getSwitchStatus()){
            pwtypeinTextView.setVisibility(View.VISIBLE);
            pwconfirmTextView.setVisibility(View.VISIBLE);
            confirmpwButton.setVisibility(View.VISIBLE);
        }

        passwordSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            dialogCreatePasswort();

            }
        });

        confirmpwButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pwtypeinTextView.getText().toString().equals(pwconfirmTextView.getText().toString())) {
                    dialogPassswort();
                } else {
                    Toast.makeText(getApplicationContext(), "Passwörter stimmen nicht überein!", Toast.LENGTH_LONG).show();
                }


            }
        });


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

    public void dialogPassswort() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);

        View view = (LayoutInflater.from(Settings_Activity.this)).inflate(R.layout.login_dialog, null);
        builder1.setView(view);
        passwortEditText = (EditText) view.findViewById(R.id.passwordid);


        builder1.setMessage("Bitte aktuelles Passwort eingeben um Passwort zu ändern.");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        String passwort = getPassword();

                        String userpasswort = passwortEditText.getText().toString();


                        if (passwort.equals(userpasswort)) {
                            setPassword(pwconfirmTextView.getText().toString());
                            Toast.makeText(getApplicationContext(), "Passwort aktualisiert!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Falsches Passwort!", Toast.LENGTH_LONG).show();
                        }


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

    public void dialogPassswortStatus() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);

        View view = (LayoutInflater.from(Settings_Activity.this)).inflate(R.layout.login_dialog, null);
        builder1.setView(view);
        passwortEditText = (EditText) view.findViewById(R.id.passwordid);


        builder1.setMessage("Bitte aktuelles Passwort eingeben um Passwort zu deaktivieren.");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        String passwort = getPassword();

                        String userpasswort = passwortEditText.getText().toString();

                        // Test für Anzeige des aktuellen Passwortes
                        Toast.makeText(getApplicationContext(),getPassword(),Toast.LENGTH_LONG).show();


                        if (passwort.equals(userpasswort)) {
                            setPassword(pwconfirmTextView.getText().toString());
                            Toast.makeText(getApplicationContext(), "Passwort deaktiviert!", Toast.LENGTH_LONG).show();
                            setSwitchStatus(false);
                            passwordSwitch.setChecked(false);

                        } else {
                            Toast.makeText(getApplicationContext(), "Falsches Passwort!", Toast.LENGTH_LONG).show();
                            setSwitchStatus(true);
                            passwordSwitch.setChecked(true);

                        }


                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "Abbrechen",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        setSwitchStatus(true);
                        passwordSwitch.setChecked(true);
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void setSwitchStatus(boolean status){
        SharedPreferences switchstatus = getSharedPreferences("Switch",0);
        SharedPreferences.Editor editor = switchstatus.edit();

        editor.putBoolean("Switch1",status);
        editor.commit();

    }

    public Boolean getSwitchStatus(){
        SharedPreferences switchstatus = getSharedPreferences("Switch",0);
        return switchstatus.getBoolean("Switch1",true);
    }

    public void dialogCreatePasswort(){

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        View view = (LayoutInflater.from(Settings_Activity.this)).inflate(R.layout.create_password,null);

        alertDialog.setView(view);
        // Erstellen
        final EditText pw1 = (EditText)findViewById(R.id.passwordid);
        // Bestätigen
        final EditText pw2 = (EditText)findViewById(R.id.passwordid1);

        alertDialog.setMessage("Bitte Passwort erstellen und bestätigen.");
        alertDialog.setCancelable(true);

        alertDialog.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {

                        String passwort = pw1.getText().toString();

                        String passwortconfirm = pw2.getText().toString();

                        if(passwort.equals(passwortconfirm)){

                            setPassword(passwort);
                            Toast.makeText(getApplicationContext(),"Passwort erstellt!",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"Passwörter stimmen nicht überein!",Toast.LENGTH_LONG).show();

                        }

                        dialogInterface.cancel();
                    }
                });

        alertDialog.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();
            }
        });

        alertDialog.create();
        alertDialog.show();


    }
}
