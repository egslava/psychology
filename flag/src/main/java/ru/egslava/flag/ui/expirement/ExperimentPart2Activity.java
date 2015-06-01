package ru.egslava.flag.ui.expirement;

import android.content.ContentValues;
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
@EActivity(R.layout.activity_experiment_part2)
public class ExperimentPart2Activity extends ActionBarActivity {
    @Extra
    String userName;
    @Extra
    int round;
    @Extra
    int[] oldFlags;
    @ViewById FitGridLayout flagsExp2;
    @Pref
    Prefs_ prefs;

    int[] flags;
    private SQLiteDatabase db;
    private DBHelper dbHelper;
    private ArrayList<Integer> allFlags;
    @AfterViews
    void init(){
        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        Cursor c = db.query("train_result",null,"`userName`=?",new String[]{userName},null,null,null);
        allFlags = new ArrayList();
        round++;
        while(c.moveToNext()){
            allFlags.add(c.getInt(1));
        }
        loadFlags();
        flagsExp2.init(prefs.e2m().get(), prefs.e2n().get(), flags);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                saveResults();
                if (round < prefs.list5Imgs().get()) {
                    ExperimentActivity_.intent(ExperimentPart2Activity.this).round(round + 1).userName(userName).start();
                } else {
                    Cursor c = db.query("flag_exp", null, "`userName`=?", new String[]{userName}, null, null, null);
                    ArrayList<Integer> expRsults = new ArrayList();
                    while (c.moveToNext()) {
                        expRsults.add(c.getInt(1));
                        for (Integer flag : allFlags) {
                            if (!isInList(expRsults, flag)) {
                                ContentValues cv = new ContentValues();
                                cv.put("userName", userName);
                                cv.put("flagId", flag);
                                cv.put("state", 2);
                                cv.put("round", round);
                                db.insert("flag_exp", null, cv);
                            }
                        }
                    }
                    ExperimentResultActivity_.intent(ExperimentPart2Activity.this).start();
                }
            }
        }, prefs.secs2().get() * 1000);
    }

    @Override
    protected void onStop() {
        dbHelper.close();
        super.onStop();
    }

    private void loadFlags() {
        int size = prefs.e2m().get() * prefs.e2n().get();
        ArrayList<Integer> list = new ArrayList();
        for (int i = 0; i < oldFlags.length - prefs.list5Imgs().get(); i++) {
            list.add(oldFlags[i]);
        }
        for(int i = oldFlags.length - prefs.list5Imgs().get(); i < oldFlags.length; i++) {
            ContentValues cv = new ContentValues();
            cv.put("userName", userName);
            cv.put("flagId", oldFlags[i]);
            cv.put("round",round);
            cv.put("state", 2);
            db.insert("flag_exp", null, cv);
        }
        UniqueRandom random = new UniqueRandom(0, allFlags.size());
        for (int i = oldFlags.length - prefs.list5Imgs().get(); i < size; i++) {
            list.add(allFlags.get(random.next()));
            db.rawQuery("delete from train_result where flagId=?", new String[]{"" + flags[i]});
        }
        Collections.shuffle(list);
        flags = new int[size];
        for (int i = 0; i < size; i++) {
            flags[i] = list.get(i);
        }
    }

    private void saveResults(){
        ArrayList<Integer> list = flagsExp2.getSelectedIds();
        ArrayList<Integer> old = castToIntegerList(oldFlags);
        for(Integer flag : list){
            ContentValues cv = new ContentValues();
            cv.put("userName", userName);
            cv.put("flagId", flag);
            cv.put("round",round);
            if(isInList(old, flag)){
                cv.put("state", 0);
            } else {
                cv.put("state", 4);
            }
            db.insert("flag_exp", null, cv);
        }
        for(int i = 0; i < oldFlags.length - prefs.list5Imgs().get(); i++){
            if(!isInList(list, oldFlags[i])){
                ContentValues cv = new ContentValues();
                cv.put("userName", userName);
                cv.put("flagId", oldFlags[i]);
                cv.put("round",round);
                cv.put("state", 1);
                db.insert("flag_exp", null, cv);
            }
        }
        for(int i=0;i<flags.length;i++){
            if(!isInList(old, flags[i]) && !isInList(list, flags[i])){
                ContentValues cv = new ContentValues();
                cv.put("userName", userName);
                cv.put("flagId", oldFlags[i]);
                cv.put("round",round);
                cv.put("state", 3);
                db.insert("flag_exp", null, cv);
            }
        }
    }

    private boolean isInList(ArrayList<Integer> list, Integer val){
        for(Integer flag : list){
            if(flag.equals(val)) return true;
        }
        return false;
    }

    private ArrayList<Integer> castToIntegerList(int[] a){
        ArrayList<Integer> list = new ArrayList<>();
        for(int i=0; i<a.length;i++){
            list.add(a[i]);
        }
        return list;
    }
}
