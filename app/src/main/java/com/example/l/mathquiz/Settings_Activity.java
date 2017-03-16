package com.example.l.mathquiz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
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


        String[] values = new String[]{"Passwortschutz","Geschütze Bereiche", "Zahlenräume anpassen"};

        ListAdapter listAdapter = new ListAdapter(this, values);
        testList.setAdapter(listAdapter);

        testList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                switch (position) {
                    case 1:
                        break;
                    case 2:
                        if(!getGeschützteBereiche()){
                            Toast.makeText(getApplicationContext(),"Zuerst Passwortschutz aktivieren",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"Bereiche wählen",Toast.LENGTH_SHORT).show();
                        }


                        break;
                }

            }
        });


    }

    protected void onPause() {
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
     * Wenn Bereiche deaktiviert sind false
     * @param geschützteBereich
     */
    public void setGeschützteBereich(Boolean geschützteBereich){
        SharedPreferences sharedPreferences = getSharedPreferences("Bereiche",0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean("BereicheData",geschützteBereich);
        editor.commit();
    }

    public boolean getGeschützteBereiche(){
        SharedPreferences sharedPreferences = getSharedPreferences("Bereiche",0);
        return  sharedPreferences.getBoolean("BereicheData",false);
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
        public final String[] values;


        public ListAdapter(Context context, String[] values) {
            super(context, -1, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
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
            final String textvalues = values[position];
            holder.textView.setText(textvalues);

            if (textvalues.equals("Zahlenräume anpassen")) {
                holder.imageView.setImageResource(R.drawable.ic_plus_one_black_24dp);
                holder.settingsCheckbox.setVisibility(View.GONE);
            }
            if (textvalues.equals("Passwortschutz")) {
                holder.arrowright.setVisibility(View.GONE);
            }
            if(textvalues.equals("Geschütze Bereiche")){
                holder.imageView.setVisibility(View.INVISIBLE);
                holder.settingsCheckbox.setVisibility(View.GONE);
            }

            if(holder.settingsCheckbox.isChecked()){
                if(textvalues.equals("Geschütze Bereiche")){
                    holder.textView.setTypeface(null,Typeface.NORMAL);
                    holder.textView.setTextColor(getResources().getColor(R.color.schwarz));
                    setGeschützteBereich(true);
                }
            }else {
                if(textvalues.equals("Geschütze Bereiche")){
                    holder.textView.setTextColor(getResources().getColor(R.color.grau));
                    holder.textView.setTypeface(null, Typeface.ITALIC);
                    setGeschützteBereich(false);


                }
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

                    if (aBoolean) {
                        view = LayoutInflater.from(Settings_Activity.this).inflate(R.layout.create_password, null);
                    } else {
                        view = LayoutInflater.from(Settings_Activity.this).inflate(R.layout.login_dialog, null);
                    }

                    builder.setView(view);

                    builder.setCancelable(true);

                    builder.setPositiveButton(
                            "OK",

                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {


                                    if (aBoolean) {
                                        EditText passwortEditText = (EditText) view.findViewById(R.id.passwordid);
                                        EditText passwortEditTextconfirm = (EditText) view.findViewById(R.id.passwordid1);
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

                                    } else {
                                        EditText passwortEditText = (EditText) view.findViewById(R.id.passwordidsingle);
                                        String userpasswort = passwortEditText.getText().toString();
                                        if (userpasswort.equals(getPassword())) {
                                            Toast.makeText(getContext(), "Passwort deaktiviert", Toast.LENGTH_SHORT).show();
                                            // Geschütze Bereiche ausblenden


                                        } else {
                                            Toast.makeText(getApplicationContext(), "Falsches Passwort!", Toast.LENGTH_LONG).show();
                                            holder.settingsCheckbox.setChecked(true);
                                            setCheckboxStatus(true);

                                        }
                                    }


                                }

                            });

                    builder.setNegativeButton(
                            "Abbrechen",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (aBoolean) {

                                        holder.settingsCheckbox.setChecked(false);
                                        setCheckboxStatus(false);
                                        dialog.cancel();
                                    } else {
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

            if(holder.settingsCheckbox.isChecked()){
                if(textvalues.equals("Geschütze Bereiche")){
                    holder.textView.setTextColor(getResources().getColor(R.color.schwarz));
                    holder.textView.setTypeface(null,Typeface.NORMAL);
                    setGeschützteBereich(true);

                }
            }else {
                if(textvalues.equals("Geschütze Bereiche")){
                    holder.textView.setTextColor(getResources().getColor(R.color.grau));
                    holder.textView.setTypeface(null, Typeface.ITALIC);
                    setGeschützteBereich(false);


                }
            }

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



