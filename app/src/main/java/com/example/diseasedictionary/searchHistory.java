package com.example.diseasedictionary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import java.util.ArrayList;
import java.util.List;

public class searchHistory extends AppCompatActivity {
    AppDataDBHelper dbHelper = null;
    ScoreTableAdapter scoreTableAdapter;
    List<ScoreTableRow> scoreTableRows = new ArrayList<ScoreTableRow>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_history);
        dbHelper = new AppDataDBHelper(getBaseContext());
        createDataRow();
        Button homepage = findViewById(R.id.homepage);
        homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(searchHistory.this,MainActivity.class);
                startActivity(intent);
            }
        });
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvSearchHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        scoreTableAdapter = new ScoreTableAdapter(this, scoreTableRows);
//        scoreTableAdapter.setClickListener((ScoreTableAdapter.ItemClickListener) this);
        recyclerView.setAdapter(scoreTableAdapter);
    }

    private Cursor readFromDB(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                AppDataRepo.SearchEntry.COLUMN_NAME_DISEASENAME
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                AppDataRepo.SearchEntry._ID + " DESC";

        Cursor cursor = db.query(
                AppDataRepo.SearchEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );
        return cursor;
    }

    private void createDataRow(){
        Cursor cursor = readFromDB();
        cursor.moveToFirst();
        do {
            ScoreTableRow scoreTableRow = new ScoreTableRow();
            scoreTableRow.name = cursor.getString(0);
            scoreTableRows.add(scoreTableRow);
        } while (cursor.moveToNext());
        cursor.close();
    }

}