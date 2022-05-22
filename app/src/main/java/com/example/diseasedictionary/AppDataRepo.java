package com.example.diseasedictionary;

import android.provider.BaseColumns;

public class AppDataRepo {
    private AppDataRepo() {}

    public static class SearchEntry implements BaseColumns {
        public static final String TABLE_NAME = "SEARCHHISTORY";
        public static final String COLUMN_NAME_SYMPTOMS = "Symptoms";
        public static final String COLUMN_NAME_TRANSMISSIONS = "Transmissions";
        public static final String COLUMN_NAME_FACTS = "Facts";
        public static final String COLUMN_NAME_TREATMENT = "Treatments";
        public static final String COLUMN_NAME_PREVENTION = "Prevention";
        public static final String COLUMN_NAME_DIAGNOSIS = "Diagnosis";
        public static final String COLUMN_NAME_DISEASENAME = "Name";
    }


    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + SearchEntry.TABLE_NAME + " (" +
                    SearchEntry._ID + " INTEGER PRIMARY KEY, " +
                    SearchEntry.COLUMN_NAME_DISEASENAME + " TEXT, " +
                    SearchEntry.COLUMN_NAME_SYMPTOMS + " TEXT, " +
                    SearchEntry.COLUMN_NAME_TRANSMISSIONS + " TEXT, " +
                    SearchEntry.COLUMN_NAME_FACTS + " TEXT, " +
                    SearchEntry.COLUMN_NAME_TREATMENT + " TEXT, " +
                    SearchEntry.COLUMN_NAME_PREVENTION + " TEXT, " +
                    SearchEntry.COLUMN_NAME_DIAGNOSIS + " TEXT " +
                    ")";


    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + SearchEntry.TABLE_NAME;




}
