package ru.egslava.flag.ui.training;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
//import ru.egslava.flag.utils.;

import com.j256.ormlite.dao.Dao;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OrmLiteDao;
import org.androidannotations.annotations.ViewById;

import java.sql.SQLException;
import java.util.ArrayList;

import ru.egslava.flag.R;
import ru.egslava.flag.model.Mark;
import ru.egslava.flag.model.MarkContract;
import ru.egslava.flag.model.TrainResult;
import ru.egslava.flag.ui.MenuActivity_;
import ru.egslava.flag.ui.expirement.ExperimentActivity_;
import ru.egslava.flag.utils.DBHelper;
import ru.egslava.flag.utils.Utils;

@EActivity(R.layout.activity_training_result)
public class TrainingResultActivity extends ActionBarActivity {
    @Extra          ArrayList<Integer> identified;
    @Extra          String userName;
    @ViewById       ImageView imageView;
    @ViewById       Button expButton, toMenu;

    private int current;
    private int max;
//    private SQLiteDatabase db;
//    private DBHelper dbHelper;

    @OrmLiteDao(helper = DBHelper.class)
    Dao<Mark, Integer>              marksDao;

    @OrmLiteDao(helper = DBHelper.class)
    Dao<TrainResult, Integer>      trainsDao;

    @AfterViews
    void init() {
        imageView.setImageResource(identified.get(current));
        current++;
        max = identified.size();
//        dbHelper = new DBHelper(this);
//        db = dbHelper.getWritableDatabase();
//        dbHelper.onCreate(db);
        expButton.setVisibility(View.INVISIBLE);

//        db.delete("marks", "`userName`=?", new String[]{userName});
        try {
            marksDao.deleteBuilder().where().eq(MarkContract.USERNAME, userName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Click void mark1() {
        saveMark(1);
        nextImage();
    }

    @Click void mark2() {
        saveMark(2);
        nextImage();
    }

    @Click void mark3() {
        saveMark(3);
        nextImage();
    }

    @Click void mark4() {
        saveMark(4);
        nextImage();
    }

    @Click void expButton() { ExperimentActivity_.intent(this).userName(userName).round(0).start(); }

    @Click void toMenu() { MenuActivity_.intent(this).start(); }

//    @Override
//    protected void onStop() {
//        dbHelper.close();
//        super.onStop();
//    }

    private void saveMark(int mark) { Utils.unsafe( () -> {
        if (current >= max) {
            return;
        }

        marksDao.create(new Mark(userName, identified.get(current) - 1, mark));
        trainsDao.create( new TrainResult(userName, identified.get(current) - 1) );
//        ContentValues cv = new ContentValues();
//        cv.put("userName", userName);
//        cv.put("flagId", identified.get(current) - 1);
//        cv.put("mark", mark);
//        db.insert("marks", null, cv);
//        ContentValues cv1 = new ContentValues();
//        cv1.put("userName", userName);
//        cv1.put("flagId", identified.get(current) - 1);
//        db.insert("train_result", null, cv1);

    }); }

    private void nextImage() {
        if (current >= max) {
            expButton.setVisibility(View.VISIBLE);
            return;
        }
        imageView.setImageResource(identified.get(current));
        current++;
    }

}