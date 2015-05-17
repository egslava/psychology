package ru.egslava.flag.ui.training;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.TreeSet;

import ru.egslava.flag.R;
import ru.egslava.flag.ui.MenuActivity_;

@EActivity(R.layout.activity_training_result)
public class TrainingResultActivity extends ActionBarActivity {
    @Extra
    ArrayList<Integer> identified;
    @Extra
    Integer userId;
    @Extra
    String userName;
    @ViewById
    ImageView imageView;
    @ViewById
    Button expButton, toMenu;

    private int current;
    private int max;
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    @AfterViews
    void init() {
        imageView.setImageResource(identified.get(current));
        current++;
        max = identified.size();
        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        expButton.setVisibility(View.INVISIBLE);
        saveMark(1);
    }

    @Click
    void mark1() {
        saveMark(1);
        nextImage();
    }

    @Click
    void mark2() {
        saveMark(2);
        nextImage();
    }

    @Click
    void mark3() {
        saveMark(3);
        nextImage();
    }

    @Click
    void mark4() {
        saveMark(4);
        nextImage();
    }

    @Click
    void expButton() {

    }

    @Click
    void toMenu() {
        MenuActivity_.intent(this).start();
    }

    @Override
    protected void onStop() {
        dbHelper.close();
        super.onStop();
    }

    private void saveMark(int mark) {
        ContentValues cv = new ContentValues();
        cv.put("userName", userName);
        cv.put("userId", userId);
        cv.put("flagId", imageView.getId());
        cv.put("mark", mark);
        db.insert("marks", null, cv);
    }

    private void nextImage() {
        if (current >= max) {
            expButton.setVisibility(View.VISIBLE);
            return;
        }
        imageView.setImageResource(identified.get(current));
        current++;
    }

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            // конструктор суперкласса
            super(context, "myDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d("LOG_TAG", "--- onCreate database ---");
            // создаем таблицу с полями
            db.execSQL("create table mytable ("
                    + "id integer primary key autoincrement,"
                    + "name text,"
                    + "email text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}