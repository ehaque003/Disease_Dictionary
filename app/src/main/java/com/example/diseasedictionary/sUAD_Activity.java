package com.example.diseasedictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class sUAD_Activity extends AppCompatActivity {
    String[] infos = new String[7];
    String[] options = {"Symptoms", "Facts", "Transmission", "Treatment", "Diagnosis", "Prevention"};
    TextView info;
    HttpHandler handler = new HttpHandler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suad);
        Spinner spinner = findViewById(R.id.dropDownMenu);
        Button search = findViewById(R.id.search);
        info = findViewById(R.id.info);
        Button backtohome = findViewById(R.id.backToHomepage);
        EditText searchBox = findViewById(R.id.searchBox);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(sUAD_Activity.this, android.R.layout.simple_list_item_1, options);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String diseaseEntry = searchBox.getText().toString();
                try {
                    handler.jsonFormatter(diseaseEntry);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(handler.response23.isEmpty()){
                    Log.d("Warning", "Empty");
                }
                try{
                    JSONObject jsonObject = new JSONObject(handler.response23);
                    infos[0] = jsonObject.getJSONObject("disease").getString("symptoms");
                    infos[1] = jsonObject.getJSONObject("disease").getString("transmission");
                    infos[2] = jsonObject.getJSONObject("disease").getString("treatment");
                    infos[4] = jsonObject.getJSONObject("disease").getString("diagnosis");
                    infos[5] = jsonObject.getJSONObject("disease").getString("prevention");


                    infos[3] = jsonObject.getJSONObject("disease").getJSONArray("facts").getString(1);
                } catch (JSONException jsonException){
                    jsonException.printStackTrace();
                    Log.d("Waring", "You dead");
                }


                searchBox.setVisibility(View.INVISIBLE);
                search.setVisibility(View.INVISIBLE);
                info.setVisibility(View.VISIBLE);
                backtohome.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.VISIBLE);
            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String clicked = options[i];
                if(clicked.equals("Symptoms")){
                    if(infos[0]=="null"){
                        info.setText("No Information On This 1");
                    }
                    else{
                        info.setText(infos[0]);
                    }
                }
                else if(clicked.equals("Transmission")){
                    if(infos[1]=="null"){
                        info.setText("No Information On This 2");
                    }
                    else{
                        info.setText(infos[1]);
                    }
                }
                else if(clicked.equals("Treatment")){
                    if(infos[2]=="null"){
                        info.setText("No Information On This 3");
                    }
                    else{
                        info.setText(infos[2]);
                    }
                }
                else if(clicked.equals("Diagnosis")){
                    if(infos[4]=="null"){
                        info.setText("No Information On This 4");
                    }
                    else{
                        info.setText(infos[4]);
                    }
                }
                else if(clicked.equals("Prevention")){
                    if(infos[5]=="null"){
                        info.setText("No Information On This 5");
                    }
                    else{
                        info.setText(infos[5]);
                    }
                }
                else if(clicked.equals("Facts")){
                    if(infos[3]=="null"){
                        info.setText("No Information On This 6");
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



    }


}