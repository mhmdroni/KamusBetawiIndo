package com.example.kamusbetawiindo.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.kamusbetawiindo.Model.Kamus;
import com.example.kamusbetawiindo.R;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import au.com.bytecode.opencsv.CSVReader;

public class DBHelper extends SQLiteOpenHelper {
    private static Context mContext;
    private static final String DATABASE_NAME = "kamus.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_DATA_BETAWI = "data_betawi";
    private static final String TABLE_DATA_INDO = "data_indo";
    private static final String KEY_ID = "id";
    private static final String KEY_BETAWI = "betawi";
    private static final String KEY_INDO = "indo";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_DATA = "CREATE TABLE " + TABLE_DATA_BETAWI + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_BETAWI + " TEXT," + KEY_INDO + " TEXT" + ")";
        String CREATE_TABLE_DATA_INDO = "CREATE TABLE " + TABLE_DATA_INDO + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_BETAWI  + " TEXT," + KEY_INDO+ " TEXT" + ")";
        db.execSQL(CREATE_TABLE_DATA);
        db.execSQL(CREATE_TABLE_DATA_INDO);
        importBetawi(db);
        importIndo(db);

    }

    private void importIndo(SQLiteDatabase db) {
        List<String[]> recordIndo = readDataFromFileResource(R.raw.indonesia);
        String[] recordData;
        for (int i = 0; i < recordIndo.size(); i++) {
            Log.d("listData", recordIndo.get(i).toString());
            recordData = recordIndo.get(i);
            ContentValues values = new ContentValues();
            values.put(KEY_ID, recordData[0]);
            values.put(KEY_INDO, recordData[1]);
            values.put(KEY_BETAWI, recordData[2]);

            db.insert(TABLE_DATA_INDO, null, values);
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA_BETAWI);
        onCreate(db);
    }

    public Kamus getbetawi(String betawi) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_DATA_BETAWI, new String[]{KEY_ID,
                        KEY_BETAWI, KEY_INDO}, KEY_BETAWI + "=?",
                new String[]{betawi}, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Kamus kamus = null;
        try {
            kamus = new Kamus();
            kamus.setId(cursor.getInt(0));
            kamus.setBetawi(cursor.getString(1));
            kamus.setIndo(cursor.getString(2));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // return contact
        if (kamus != null) {
            return kamus;
        } else {
            return null;
        }
    }
    public Kamus getIndo(String jawa) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_DATA_INDO, new String[]{KEY_ID,
                        KEY_BETAWI, KEY_INDO}, KEY_INDO + "=?",
                new String[]{jawa}, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Kamus kamus = null;
        try {
            kamus = new Kamus();
            kamus.setId(cursor.getInt(0));
            kamus.setBetawi(cursor.getString(1));
            kamus.setIndo(cursor.getString(2));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // return contact
        if (kamus != null) {
            return kamus;
        } else {
            return null;
        }
    }

        private List<String[]> readDataFromFileResource(int resourceId) {
            List<String[]> records = null;

            try {
                CSVReader reader = new CSVReader(new InputStreamReader(mContext.getResources().openRawResource(resourceId)));
                records = reader.readAll();
                reader.close();
            } catch (IOException e) {
                Log.e("error", "Failed to read data from file: " + e.getMessage());
                e.printStackTrace();
            }

            return records;
        }

    public List<Kamus> getAllKamus() {
        List<Kamus> kamusList = new ArrayList<Kamus>();

        String selectQuery = "SELECT * FROM " + TABLE_DATA_BETAWI;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Kamus kamus = new Kamus();
                kamus.setId(Integer.parseInt(cursor.getString(0)));
                kamus.setBetawi(cursor.getString(1));
                kamus.setIndo(cursor.getString(2));
                // Adding contact to list
                kamusList.add(kamus);
            } while (cursor.moveToNext());
        }
        return kamusList;
    }

    public List<Kamus> getAllIndo() {
        List<Kamus> kamusList = new ArrayList<Kamus>();

        String selectQuery = "SELECT  * FROM " + TABLE_DATA_INDO;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()){
            do {
                Kamus kamus = new Kamus();
                kamus.setId(Integer.parseInt(cursor.getString(0)));
                kamus.setIndo(cursor.getString(1));
                kamus.setBetawi(cursor.getString(2));
                // Adding contact to list
                kamusList.add(kamus);
            } while (cursor.moveToNext());
        }
        return kamusList;
    }

    public List<Kamus> getAllKamusjawa() {
        List<Kamus> kamusList = new ArrayList<Kamus>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DATA_BETAWI;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Kamus kamus = new Kamus();
                kamus.setId(Integer.parseInt(cursor.getString(0)));
                kamus.setBetawi(cursor.getString(1));
                kamus.setIndo(cursor.getString(2));
                // Adding contact to list
                kamusList.add(kamus);
            } while (cursor.moveToNext());
        }
        return kamusList;
    }

    public void addBetawi(List<Kamus> jawaList) {
        SQLiteDatabase db = this.getWritableDatabase();

        String[] recordData;

        for (int i = 0; i < jawaList.size(); i++) {

            ContentValues values = new ContentValues();

            values.put(KEY_ID, jawaList.get(i).getId());
            values.put(KEY_BETAWI, jawaList.get(i).getBetawi());
            values.put(KEY_INDO, jawaList.get(i).getIndo());

            db.insert(TABLE_DATA_BETAWI, null, values);
        }
    }

    public void addIndo(List<Kamus> indoList) {
        SQLiteDatabase db = this.getWritableDatabase();

        String[] recordData;

        for (int i = 0; i < indoList.size(); i++) {

            ContentValues values = new ContentValues();

            values.put(KEY_ID, indoList.get(i).getId());
            values.put(KEY_BETAWI, indoList.get(i).getBetawi());
            values.put(KEY_INDO, indoList.get(i).getIndo());

            db.insert(TABLE_DATA_INDO, null, values);
        }
    }

    public void deletebetawi() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DATA_BETAWI, null, null);
    }

    public void deleteIndo() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DATA_INDO, null, null);
    }

    private void importBetawi(SQLiteDatabase db) {
        List<String[]> records = readDataFromFileResource(R.raw.betawi);
        String[] recordData;
        for (int i = 0; i < records.size(); i++) {
            Log.d("listData", records.get(i).toString());
            recordData = records.get(i);
            ContentValues values = new ContentValues();
            values.put(KEY_ID, recordData[0]);
            values.put(KEY_BETAWI, recordData[1]);
            values.put(KEY_INDO, recordData[2]);

            db.insert(TABLE_DATA_BETAWI, null, values);
        }
    }
}
