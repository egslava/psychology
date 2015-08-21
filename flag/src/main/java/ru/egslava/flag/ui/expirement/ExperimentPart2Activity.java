package ru.egslava.flag.ui.expirement;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.j256.ormlite.dao.Dao;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OrmLiteDao;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ru.egslava.flag.Prefs_;
import ru.egslava.flag.R;
import ru.egslava.flag.model.FlagExp;
import ru.egslava.flag.model.FlagExpContract;
import ru.egslava.flag.model.TrainResult;
import ru.egslava.flag.model.TrainResultContract;
import ru.egslava.flag.ui.training.TrainingActivity_;
import ru.egslava.flag.ui.training.TrainingResultActivity_;
import ru.egslava.flag.ui.views.FitGridLayout;
import ru.egslava.flag.utils.DBHelper;
import ru.egslava.flag.utils.Images;
import ru.egslava.flag.utils.UniqueRandom;
import ru.egslava.flag.utils.Utils;

import static ru.egslava.flag.utils.Utils.*;

@EActivity(R.layout.activity_experiment_part2)
public class ExperimentPart2Activity extends ActionBarActivity {
    @Extra  String userName;
    @Extra  int round;
    @Extra  int[] oldFlags;

    @ViewById   FitGridLayout flagsExp2;
    @Pref       Prefs_ prefs;

    int[] flags;
    private ArrayList<Integer> allFlags;

    @OrmLiteDao(helper = DBHelper.class)
    Dao<TrainResult, Integer>   trainsDao;

    @OrmLiteDao(helper = DBHelper.class)
    Dao<FlagExp, Integer>   flagExpDao;

    @AfterViews void init(){    unsafe(() -> {

        final List<TrainResult> trainResults = trainsDao.queryForEq(TrainResultContract.USERNAME, userName);
        for (TrainResult result : trainResults) {
            allFlags.add(result.flagId);
        }

        //        c = db.query("train_result",null,"`userName`=?",new String[]{userName},null,null,null);
        //        allFlags = new ArrayList();
        round++;
        //        while(c.moveToNext()){
        //            allFlags.add(c.getInt(1));
        //        }
        loadFlags();

        flagsExp2.init(prefs.e2m().get(), prefs.e2n().get(), flags);

        after(prefs.secs2().get() * 1000, () -> {

            saveResults();
            if (round < prefs.list5Imgs().get()) {
                ExperimentActivity_.intent(this).round(round + 1).userName(userName).start();
            } else {
                //                        Cursor c = db.query("flag_exp", null, "`userName`=?", new String[]{userName}, null, null, null);
                final List<FlagExp> flagExps = flagExpDao.queryForEq(FlagExpContract.USERNAME, userName);
                ArrayList<Integer> expRsults = new ArrayList();
                //                        while (c.moveToNext()) {

                for (FlagExp flagExp : flagExps) {
                    //                            expRsults.add(c.getInt(1));
                    expRsults.add(flagExp.flagId);
                    for (Integer flag : allFlags) {
                        if (!isInList(expRsults, flag)) {
                            flagExpDao.create(new FlagExp(userName, flag, 2, round));
                            //                                ContentValues cv = new ContentValues();
                            //                                cv.put("userName", userName);
                            //                                cv.put("flagId", flag);
                            //                                cv.put("state", 2);
                            //                                cv.put("round", round);
                            //                                db.insert("flag_exp", null, cv);
                        }
                    }
                }
                ExperimentResultActivity_.intent(this).start();
            }
        });

    });}

//    @Override
//    protected void onStop() {
//        dbHelper.close();
//        super.onStop();
//    }

    private void loadFlags() throws SQLException {
        int size = prefs.e2m().get() * prefs.e2n().get();
        ArrayList<Integer> list = new ArrayList();
        for (int i = 0; i < oldFlags.length - prefs.list5Imgs().get(); i++) {
            list.add(oldFlags[i]);
        }
        for(int i = oldFlags.length - prefs.list5Imgs().get(); i < oldFlags.length; i++) {
            final FlagExp flagExp = new FlagExp(userName, oldFlags[i], round, 2);
            flagExpDao.create(flagExp);
//            db.insert("flag_exp", null, cv);
        }
        UniqueRandom random = new UniqueRandom(0, allFlags.size());
        for (int i = oldFlags.length - prefs.list5Imgs().get(); i < size; i++) {
            list.add(allFlags.get(random.next()));
            trainsDao.deleteById(flags[i]);
//            flagExpDao.executeRaw(String.format("delete from %s where flagId=?", TrainResultContract.TABLE_NAME), ""+flags[i]);
//                    db.rawQuery("delete from train_result where flagId=?", new String[]{
//                            "" + flags[i]});
        }
        Collections.shuffle(list);
        flags = new int[size];
        for (int i = 0; i < size; i++) {
            flags[i] = list.get(i);
        }
    }

    private void saveResults() throws SQLException {
        ArrayList<Integer> list = flagsExp2.getSelectedIds();
        ArrayList<Integer> old = castToIntegerList(oldFlags);
        for(Integer flag : list){
            flagExpDao.create(new FlagExp(userName, flag, round, isInList(old, flag)? 0: 4));
//            ContentValues cv = new ContentValues();
//            cv.put("userName", userName);
//            cv.put("flagId", flag);
//            cv.put("round",round);
//            if(isInList(old, flag)){
//                cv.put("state", 0);
//            } else {
//                cv.put("state", 4);
//            }
//            db.insert("flag_exp", null, cv);
        }
        for(int i = 0; i < oldFlags.length - prefs.list5Imgs().get(); i++){
            if(!isInList(list, oldFlags[i])){
                flagExpDao.create( new FlagExp(userName, oldFlags[i], round, 1) );
//                ContentValues cv = new ContentValues();
//                cv.put("userName", userName);
//                cv.put("flagId", oldFlags[i]);
//                cv.put("round",round);
//                cv.put("state", 1);
//                db.insert("flag_exp", null, cv);
            }
        }
        for(int i=0;i<flags.length;i++){
            if(!isInList(old, flags[i]) && !isInList(list, flags[i])){
                flagExpDao.create( new FlagExp(userName, oldFlags[i], round, 3) );
//                ContentValues cv = new ContentValues();
//                cv.put("userName", userName);
//                cv.put("flagId", oldFlags[i]);
//                cv.put("round",round);
//                cv.put("state", 3);
//                db.insert("flag_exp", null, cv);
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
