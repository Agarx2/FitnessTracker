package com.example.nadirferlin.fitnesstracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME ="FitnessTracker.db";
    public static final String TABLE_NAME = "User_Table";
    public static final String COL_1 = "UserID";
    public static final String COL_2 = "Name";
    public static final String COL_3 = "Age";
    public static final String COL_4 = "Geschlecht";
    public static final String COL_5 = "Gewicht";
    public static final String COL_6 = "Taetigkeit";
    public static final String COL_7 = "Aktivitaet";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (UserID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Age DATETIME, Geschlecht TEXT, Gewicht DECIMAL, Taetigkeit TEXT, Aktivitaet TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String Name, String Age, String Geschlecht, Double Gewicht, String Taetigkeit, String Aktivitaet){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, Name);
        contentValues.put(COL_3, Age);
        contentValues.put(COL_4, Geschlecht);
        contentValues.put(COL_5, Gewicht);
        contentValues.put(COL_6, Taetigkeit);
        contentValues.put(COL_7, Aktivitaet);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1) {
            return false;
        }else{
            return true;
        }
    }
}