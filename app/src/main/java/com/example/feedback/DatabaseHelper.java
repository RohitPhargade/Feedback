package com.example.feedback;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.SharedPreferences;
import static android.content.SharedPreferences.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import static com.example.feedback.FormActivity.*;
import androidx.annotation.Nullable;

import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "CUSTOMER_FEEDBACK";
//    private static final String DATABASE_NAME = FormActivity.dbname.toUpperCase();
    private static final String TABLE_NAME = "CUSTOMER_DATA";
//    private static final String TABLE_NAME = "CUSTOMER_DATA1";
//    private static final String TABLE_NAME = FormActivity.dbname.toUpperCase();
    private static final String COLUMN_1 = "ID";
    private static final String COLUMN_2 = "NAME";
    private static final String COLUMN_3 = "PHONE_NUMBER";
    private static final String COLUMN_4 = "RATING";
    private static final String COLUMN_5 = "DESCRIPTION";



    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1 );
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " +TABLE_NAME+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT , NAME TEXT , PHONE_NUMBER INTEGER , RATING TEXT , DESCRIPTION TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData(String NAME, String PHONE_NUMBER, String RATING, String DESCRIPTION) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_2, NAME);
        values.put(COLUMN_3, PHONE_NUMBER);
        values.put(COLUMN_4, RATING);
        values.put(COLUMN_5, DESCRIPTION);
        long var = db.insert(TABLE_NAME, null, values);
        if (var == -1) {
            return false;
        }
        else {
            return true;
        }
    }
    public Cursor getData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " +TABLE_NAME+ " WHERE ID='" +id+"'";
        Cursor cursor = db.rawQuery(query , null);
        return cursor;
    }
    public boolean updateData(String id , String NAME ,String PHONE_NUMBER, String RATING, String DESCRIPTION){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_1 , id);
        contentValues.put(COLUMN_2 , NAME);
        contentValues.put(COLUMN_3 , PHONE_NUMBER);
        contentValues.put(COLUMN_4 , RATING);
        contentValues.put(COLUMN_5 , DESCRIPTION);
        db.update(TABLE_NAME , contentValues , "ID=?" , new  String[]{id});
        return true;
    }
    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME , "ID=?" , new String[]{id});
    }
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return cursor;
    }
    public Integer deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME , null , null);
    }

}
