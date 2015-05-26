package ru.egslava.flag.ui.training;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
//import ru.egslava.flag.utils.;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import ru.egslava.flag.R;
import ru.egslava.flag.ui.MenuActivity_;
import ru.egslava.flag.ui.expirement.ExperimentActivity_;
import ru.egslava.flag.utils.DBHelper;

@EActivity(R.layout.activity_training_result)
public class TrainingResultActivity extends ActionBarActivity {
    @Extra
    ArrayList<Integer> identified;
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
        dbHelper.onCreate(db);
        expButton.setVisibility(View.INVISIBLE);
        db.delete("marks", "`userName`=?", new String[]{userName});
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
        ExperimentActivity_.intent(this).userName(userName).round(0).start();
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
        if (current >= max) {
            return;
        }
        ContentValues cv = new ContentValues();
        cv.put("userName", userName);
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

}