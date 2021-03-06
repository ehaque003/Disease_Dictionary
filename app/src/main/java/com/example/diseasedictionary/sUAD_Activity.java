package com.example.diseasedictionary;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class sUAD_Activity extends AppCompatActivity {
    static AppDataDBHelper dbHelper = null;
    String[] infos = new String[6];
    String[] options = {"Symptoms", "Facts", "Transmission", "Treatment", "Diagnosis", "Prevention"};
    TextView info;
    HttpHandler handler = new HttpHandler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suad);
        dbHelper = new AppDataDBHelper(getBaseContext());
        Spinner spinner = findViewById(R.id.dropDownMenu);
        Button search = findViewById(R.id.search);
        info = findViewById(R.id.info);
        Button backtohome = findViewById(R.id.backToHomepage);
        EditText searchBox = findViewById(R.id.searchBox);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(sUAD_Activity.this, android.R.layout.simple_list_item_1, options);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);


        search.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                String diseaseEntry = searchBox.getText().toString().toLowerCase(Locale.ROOT);
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                String temp = "1"+diseaseEntry+"1";
                temp = temp.replace('1', (char)34);
                String query = "SELECT "+AppDataRepo.SearchEntry.COLUMN_NAME_DIAGNOSIS+", "+AppDataRepo.SearchEntry.COLUMN_NAME_TRANSMISSIONS+", "+AppDataRepo.SearchEntry.COLUMN_NAME_TREATMENT+", "+AppDataRepo.SearchEntry.COLUMN_NAME_PREVENTION+", "+AppDataRepo.SearchEntry.COLUMN_NAME_FACTS+", "+AppDataRepo.SearchEntry.COLUMN_NAME_SYMPTOMS+" FROM " + AppDataRepo.SearchEntry.TABLE_NAME + " WHERE " + AppDataRepo.SearchEntry.COLUMN_NAME_DISEASENAME + " = " + temp;
                Cursor c = db.rawQuery(query, null);
                boolean result = c.getCount() > 0;
                if(result){
                    c.moveToFirst();
                    infos[0] = c.getString(5);
                    infos[1] = c.getString(1);
                    infos[2] = c.getString(2);
                    infos[3] = c.getString(4);
                    infos[4] = c.getString(0);
                    infos[5] = c.getString(3);
                    c.close();
                    db.close();
                }
                else{
                    try {
                        handler.jsonFormatter(diseaseEntry);
                        infos = new String[]{"null", "null", "null", "null", "null", "null"};
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    try{
                        JSONObject jsonObject = new JSONObject(handler.response23);
                        infos[0] = jsonObject.getJSONObject("disease").getString("symptoms");
                        infos[1] = jsonObject.getJSONObject("disease").getString("transmission");
                        infos[2] = jsonObject.getJSONObject("disease").getString("treatment");
                        infos[4] = jsonObject.getJSONObject("disease").getString("diagnosis");
                        infos[5] = jsonObject.getJSONObject("disease").getString("prevention");
                        infos[3] = jsonObject.getJSONObject("disease").getJSONArray("facts").getString(1);
                        writeToDB(diseaseEntry);
                    } catch (JSONException jsonException){
                        infos = new String[]{"null", "null", "null", "null", "null", "null"};
                        jsonException.printStackTrace();
                        Log.d("Warning", "You dead");
                    }


                }
                searchBox.setVisibility(View.INVISIBLE);
                search.setVisibility(View.INVISIBLE);
                info.setVisibility(View.VISIBLE);
                backtohome.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.VISIBLE);

                if(infos[0]==null||infos[0].equals("null")){
                    info.setText("No Information On This");
                }
                else{
                    info.setText(infos[0]);
                }
            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String clicked = options[i];
                if(clicked.equals("Symptoms")){
                    if(infos[0]==null||infos[0].equals("null")){
                        info.setText("No Information On This");
                    }
                    else{
                        info.setText(infos[0]);
                    }
                }
                else if(clicked.equals("Transmission")){
                    if(infos[1]==null||infos[1].equals("null")){
                        info.setText("No Information On This");
                    }
                    else{
                        info.setText(infos[1]);
                    }
                }
                else if(clicked.equals("Treatment")){
                    if(infos[2]==null||infos[2].equals("null")){
                        info.setText("No Information On This");
                    }
                    else{
                        info.setText(infos[2]);
                    }
                }
                else if(clicked.equals("Diagnosis")){
                    if(infos[4]==null||infos[4].equals("null")){
                        info.setText("No Information On This");
                    }
                    else{
                        info.setText(infos[4]);
                    }
                }
                else if(clicked.equals("Prevention")){
                    if(infos[5]==null||infos[5].equals("null")){
                        info.setText("No Information On This");
                    }
                    else{
                        info.setText(infos[5]);
                    }
                }
                else if(clicked.equals("Facts")){
                    if(infos[3]==null||infos[3].equals("null")){
                        info.setText("No Information On This");
                    }
                    else{
                        info.setText(infos[3]);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        backtohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(sUAD_Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });




    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void writeToDB(String name) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AppDataRepo.SearchEntry.COLUMN_NAME_DISEASENAME, name);
        values.put(AppDataRepo.SearchEntry.COLUMN_NAME_DIAGNOSIS, infos[4]);
        values.put(AppDataRepo.SearchEntry.COLUMN_NAME_PREVENTION, infos[5]);
        values.put(AppDataRepo.SearchEntry.COLUMN_NAME_TREATMENT, infos[2]);
        values.put(AppDataRepo.SearchEntry.COLUMN_NAME_FACTS, infos[3]);
        values.put(AppDataRepo.SearchEntry.COLUMN_NAME_TRANSMISSIONS, infos[1]);
        values.put(AppDataRepo.SearchEntry.COLUMN_NAME_SYMPTOMS, infos[0]);
        values.put(AppDataRepo.SearchEntry.COLUMN_NAME_DIAGNOSIS, infos[4]);
        db.insert(AppDataRepo.SearchEntry.TABLE_NAME, null, values);


    }


}