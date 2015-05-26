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
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import ru.egslava.flag.Prefs_;
import ru.egslava.flag.R;
import ru.egslava.flag.ui.training.TrainingActivity_;
import ru.egslava.flag.ui.training.TrainingResultActivity_;
import ru.egslava.flag.ui.views.FitGridLayout;
import ru.egslava.flag.utils.DBHelper;
import ru.egslava.flag.utils.Images;
import ru.egslava.flag.utils.UniqueRandom;
@EActivity(R.layout.activity_experiment)
public class ExperimentPart2Activity extends ActionBarActivity {
    @Extra
    String userName;
    @Extra
    int round;
    @Extra
    int[] oldFlags;
    @ViewById
    FitGridLayout flagsExp2;
    @Pref
    Prefs_ prefs;

    int[] flags;
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    @AfterViews
    void init(){
        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        Cursor c = db.query("marks",null,"`userName`=?",new String[]{userName},null,null,null);
        ArrayList<Integer> allFlags = new ArrayList();
        round++;
        while(c.moveToNext()){
            allFlags.add(c.getInt(1));
        }
        loadFlags(allFlags);
        flagsExp2.init(prefs.e2m().get(), prefs.e2n().get(), flags);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(round < prefs.list5Imgs().get()){
                    ExperimentActivity_.intent(ExperimentPart2Activity.this).round(round).userName(userName).start();
                }
            }
        }, prefs.secs2().get()*1000);
    }

    @Override
    protected void onStop() {
        dbHelper.close();
        super.onStop();
    }

    private void loadFlags(ArrayList<Integer> allFlags) {
        int size = prefs.e2m().get() * prefs.e2n().get();
        ArrayList<Integer> list = new ArrayList();
        for (int i = 0; i < oldFlags.length; i++) {
            list.add(oldFlags[i]);
        }
        UniqueRandom random = new UniqueRandom(0, allFlags.size());
        for (int i = oldFlags.length; i < size; i++) {
            list.add(allFlags.get(random.next()));
        }
        Collections.shuffle(list);
        flags = new int[size];
        for (int i = 0; i < size; i++) {
            flags[i] = list.get(i);
        }
    }
}
