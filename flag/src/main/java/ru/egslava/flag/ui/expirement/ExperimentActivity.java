package ru.egslava.flag.ui.expirement;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import ru.egslava.flag.Prefs_;
import ru.egslava.flag.R;
import ru.egslava.flag.ui.views.FitGridLayout;
import ru.egslava.flag.utils.DBHelper;
import ru.egslava.flag.utils.Images;
import ru.egslava.flag.utils.UniqueRandom;

@EActivity(R.layout.activity_experiment)
public class ExperimentActivity extends ActionBarActivity {
    @Extra
    String userName;
    @Extra
    int round;
    @ViewById
    FitGridLayout flagsExp;
    @Pref
    Prefs_ prefs;

    int[] flags;
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    @AfterViews void init(){
        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("select flagId from train_result where userName=?",new String[]{userName});
        ArrayList<Integer> allFlags = new ArrayList();
        while(c.moveToNext()){
            allFlags.add(c.getInt(0));
        }
        loadFlags(allFlags);
        flagsExp.init(prefs.e1m().get(), prefs.e1n().get(), flags);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ExperimentPart2Activity_.intent(ExperimentActivity.this).userName(userName).round(round).oldFlags(flags).start();
            }
        }, prefs.secs1().get()*1000);
    }

    @Override
    protected void onStop() {
        dbHelper.close();
        super.onStop();
    }

    private void loadFlags(ArrayList<Integer> allFlags){
        int size = prefs.e1m().get()*prefs.e1n().get();
        flags = new int[size];
        UniqueRandom random = new UniqueRandom(0, allFlags.size()-1);
        for(int i = 0; i< size; i++){
            flags[i] = allFlags.get(random.next());
            db.rawQuery("delete from train_result where flagId=?",new String[]{""+flags[i]});
        }
    }
}
