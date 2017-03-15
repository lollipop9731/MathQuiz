package com.example.l.mathquiz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Settings_Activity extends AppCompatActivity {

    CheckBox pwCheckBox;
    EditText pw1, pw2;
    ListView testList;
    ArrayList<String> settingsarray = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_);


        testList = (ListView) findViewById(R.id.listsettings);
        ViewGroup viewGroup = (ViewGroup) getLayoutInflater().inflate(R.layout.header, testList, false);
        testList.addHeaderView(viewGroup);


        String[] values = new String[]{"Passwortschutz", "Zahlenräume anpassen"};

        ListAdapter listAdapter = new ListAdapter(this, values);
        testList.setAdapter(listAdapter);

        testList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getApplicationContext(), Integer.toString(position), Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 1:
                        break;
                    case 2:

                        break;
                }

            }
        });


    }

    protected void onPause(){
        super.onPause();

    }

    public String getPassword() {
        SharedPreferences passwordshared = getSharedPreferences("Password", 0);
        return passwordshared.getString("Password1", "");
    }

    public void setPassword(String password) {
        //Shared Preferences
        SharedPreferences passwordshared = getSharedPreferences("Password", 0);
        SharedPreferences.Editor editor = passwordshared.edit();

        editor.putString("Password1", password);
        editor.commit();

    }

    /**
     * Erstellt den ersten Dialog zum Erstellen eines Passwortes
     */
    public void dialogCreatePasswort() {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // inflate view
        View view = (LayoutInflater.from(Settings_Activity.this)).inflate(R.layout.create_password, null);
        alertDialog.setView(view);
        // Erstellen
        pw1 = (EditText) view.findViewById(R.id.passwordid);
        // Bestätigen
        pw2 = (EditText) view.findViewById(R.id.passwordid1);


        alertDialog.setMessage("Bitte Passwort erstellen und bestätigen.");
        alertDialog.setCancelable(true);

        alertDialog.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {

                        String passwort = "";
                        String passwortconfirm = "";

                        if (pw1.getText().length() > 0 && pw2.getText().length() > 0) {
                            passwort = pw1.getText().toString();
                            passwortconfirm = pw2.getText().toString();

                            if (passwort.equals(passwortconfirm)) {

                                setPassword(passwort);
                                setCheckboxStatus(true);
                                Toast.makeText(getApplicationContext(), "Passwort erstellt!", Toast.LENGTH_LONG).show();
                                dialogInterface.cancel();
                            } else {
                                Toast.makeText(getApplicationContext(), "Passwörter stimmen nicht überein!", Toast.LENGTH_LONG).show();
                                pwCheckBox.setChecked(false);
                            }


                        } else {
                            Toast.makeText(getApplicationContext(), "Bitte Text eingeben", Toast.LENGTH_SHORT).show();
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

    public void dialogPasswortDeaktivieren() {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // inflate view
        View view = (LayoutInflater.from(Settings_Activity.this)).inflate(R.layout.login_dialog, null);
        alertDialog.setView(view);
        // Erstellen
        pw1 = (EditText) view.findViewById(R.id.passwordid);

        alertDialog.setMessage("Bitte Passwort eingeben.");
        alertDialog.setCancelable(true);

        alertDialog.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {

                        String passwort = "";


                        if (pw1.getText().length() > 0) {
                            passwort = pw1.getText().toString();


                            if (passwort.equals(getPassword())) {

                                setPassword(passwort);

                                Toast.makeText(getApplicationContext(), "Passwortschutz deaktiviert!", Toast.LENGTH_SHORT).show();
                                pwCheckBox.setChecked(false);
                                setCheckboxStatus(false);
                                dialogInterface.cancel();
                            } else {
                                Toast.makeText(getApplicationContext(), "Passwörter nicht korrekt!", Toast.LENGTH_LONG).show();
                                pwCheckBox.setChecked(true);
                            }


                        } else {
                            Toast.makeText(getApplicationContext(), "Bitte Text eingeben", Toast.LENGTH_SHORT).show();
                            pwCheckBox.setChecked(true);
                        }


                    }
                });

        alertDialog.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                pwCheckBox.setChecked(true);
                dialogInterface.cancel();
            }
        });

        alertDialog.create();
        alertDialog.show();


    }

    public void OnCheckboxClicked(View view) {

        Boolean isChecked = ((CheckBox) view).isChecked();

        if (isChecked) {
            dialogCreatePasswort();
        } else {
            dialogPasswortDeaktivieren();

        }
    }

    public boolean getCheckboxStatus() {
        SharedPreferences sharedPreferences = getSharedPreferences("Checkbox", 0);
        return sharedPreferences.getBoolean("CheckboxData", false);

    }

    /**
     * @param checkboxStatus true for checked, false for not checked.
     */
    public void setCheckboxStatus(Boolean checkboxStatus) {

        SharedPreferences sharedPreferences = getSharedPreferences("Checkbox", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean("CheckboxData", checkboxStatus);
        editor.commit();
    }

    private class ListAdapter extends ArrayAdapter<String> {


        private final Context context;
        private final String[] values;

        public ListAdapter(Context context, String[] values) {
            super(context, -1, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = convertView;

            if (rowView == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                // inflate Views
                rowView = inflater.inflate(R.layout.rowlayoutrelative, parent, false);

                ViewHolder viewHolder = new ViewHolder();
                viewHolder.imageView = (ImageView) rowView.findViewById(R.id.imageViewrow);
                viewHolder.arrowright = (ImageView) rowView.findViewById(R.id.arrowright);
                viewHolder.textView = (TextView) rowView.findViewById(R.id.textrow);
                viewHolder.settingsCheckbox = (CheckBox) rowView.findViewById(R.id.settings_checkBox);
                rowView.setTag(viewHolder);
            }

            final ViewHolder holder = (ViewHolder) rowView.getTag();
            // set Text from Arraylist
            String textvalues = values[position];
            holder.textView.setText(textvalues);

            if (textvalues.equals("Zahlenräume anpassen")) {
                holder.imageView.setImageResource(R.drawable.ic_plus_one_black_24dp);
                holder.settingsCheckbox.setVisibility(View.GONE);
            }
            if (textvalues.equals("Passwortschutz")) {
                holder.arrowright.setVisibility(View.GONE);
            }

            if (getCheckboxStatus()) {
                holder.settingsCheckbox.setChecked(true);
            } else {
                holder.settingsCheckbox.setChecked(false);
            }

            holder.settingsCheckbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    final Boolean aBoolean = holder.settingsCheckbox.isChecked();
                    setCheckboxStatus(holder.settingsCheckbox.isChecked());

                    final View view;


                    // Passwort aktiviert

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("Passwort eingeben und bestätigen");

                        if(aBoolean) {
                            view = LayoutInflater.from(Settings_Activity.this).inflate(R.layout.create_password, null);
                        }else{
                            view = LayoutInflater.from(Settings_Activity.this).inflate(R.layout.login_dialog, null);
                        }

                        builder.setView(view);

                        builder.setCancelable(true);

                        builder.setPositiveButton(
                                "OK",

                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {




                                        if(aBoolean) {
                                            EditText passwortEditText = (EditText)view.findViewById(R.id.passwordid);
                                            EditText passwortEditTextconfirm = (EditText)view.findViewById(R.id.passwordid1);
                                            String userpasswortconfirm = passwortEditTextconfirm.getText().toString();
                                            String userpasswort = passwortEditText.getText().toString();
                                            if (userpasswort.length() > 0 && userpasswortconfirm.length() > 0) {
                                                if (userpasswortconfirm.equals(userpasswort)) {
                                                    Toast.makeText(getApplicationContext(), "Passwort aktiviert", Toast.LENGTH_LONG).show();
                                                    setPassword(userpasswort);
                                                    holder.settingsCheckbox.setChecked(true);
                                                    setCheckboxStatus(true);


                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Passwörter stimmen nicht überein!", Toast.LENGTH_LONG).show();

                                                }

                                                dialog.cancel();
                                            } else {
                                                Toast.makeText(getContext(), "Bitte Text eingeben.", Toast.LENGTH_SHORT).show();
                                                holder.settingsCheckbox.setChecked(false);
                                                setCheckboxStatus(false);
                                            }

                                        }else {
                                            EditText passwortEditText = (EditText)view.findViewById(R.id.passwordidsingle);
                                            String userpasswort = passwortEditText.getText().toString();
                                            if(userpasswort.equals(getPassword())){
                                                Toast.makeText(getContext(),"Passwort deaktiviert",Toast.LENGTH_SHORT).show();
                                            }else{
                                                Toast.makeText(getApplicationContext(), "Falsches Passwort!", Toast.LENGTH_LONG).show();
                                                holder.settingsCheckbox.setChecked(true);
                                                setCheckboxStatus(true);

                                            }
                                        }



                                    }

                                });

                        builder.setNegativeButton(
                                "Abbrechen",
                                new DialogInterface.OnClickListener(){

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(aBoolean) {

                                            holder.settingsCheckbox.setChecked(false);
                                            setCheckboxStatus(false);
                                            dialog.cancel();
                                        }else{
                                            holder.settingsCheckbox.setChecked(true);
                                            setCheckboxStatus(true);

                                        }
                                    }
                                }
                        );

                        builder.create();
                        builder.show();


                        //Passwort deaktiviert


                }
            });
            return rowView;
        }

        public class ViewHolder {
            public TextView textView;
            public ImageView imageView;
            public ImageView arrowright;
            public CheckBox settingsCheckbox;
        }


    }

}



