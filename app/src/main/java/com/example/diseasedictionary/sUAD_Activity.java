package com.example.diseasedictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class sUAD_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suad);
        Spinner spinner = findViewById(R.id.dropDownMenu);
        Button search = findViewById(R.id.search);
        TextView info = findViewById(R.id.info);
        Button backtohome = findViewById(R.id.backToHomepage);
        EditText searchBox = findViewById(R.id.searchBox);
        String diseaseEntry = searchBox.getText().toString();
        String[] infos = HttpHandler.apiCallMethod(diseaseEntry);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(sUAD_Activity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.items));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);


    }
}