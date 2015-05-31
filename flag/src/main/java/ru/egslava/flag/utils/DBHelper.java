package ru.egslava.flag.utils;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "flagsDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("LOG_TAG", "--- onCreate database ---");
        // создаем таблицу с полями
        try {
            db.execSQL("create table marks ("
                    + "userName text,"
                    + "flagId integer,"
                    + "mark text" + ");");
            db.execSQL("create table flag_exp ("
                    + "userName text ,"
                    + "flagId integer,"
                    + "state text,"
                    + "round integer" + ");");
            db.execSQL("create table train_result ("
                    + "userName text ,"
                    + "flagId integer);");

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}