package ru.egslava.flag.ui.expirement;

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
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ru.egslava.flag.Prefs_;
import ru.egslava.flag.R;
import ru.egslava.flag.model.TrainResult;
import ru.egslava.flag.model.TrainResultContract;
import ru.egslava.flag.ui.views.FitGridLayout;
import ru.egslava.flag.utils.DBHelper;
import ru.egslava.flag.utils.Images;
import ru.egslava.flag.utils.UniqueRandom;
import ru.egslava.flag.utils.Utils;

import static ru.egslava.flag.model.TrainResultContract.FLAGID;
import static ru.egslava.flag.model.TrainResultContract.USERNAME;

@EActivity(R.layout.activity_experiment)
public class ExperimentActivity extends ActionBarActivity {
    @Extra  String userName;
    @Extra  int round;
    @ViewById   FitGridLayout flagsExp;

    @Pref   Prefs_ prefs;

    int[] flags;

    @OrmLiteDao(helper = DBHelper.class)
    Dao<TrainResult, Void>  dao;

    public List<TrainResult> trainResults;

    @AfterViews void init(){    Utils.unsafe( () -> {
        ArrayList<Integer> allFlags = new ArrayList<>();

        trainResults = dao.queryBuilder().where().eq(USERNAME, userName).query();
        for(TrainResult trainResult : trainResults){
            allFlags.add( trainResult.flagId );
        }
        loadFlags(allFlags);

        flagsExp.init(prefs.e1m().get(), prefs.e1n().get(), flags);
        Utils.after( prefs.secs1().get() * 1000, () -> {
            ExperimentPart2Activity_.intent(this).userName(userName).round(round).oldFlags(flags).start();
        });
    });}

    private void loadFlags(ArrayList<Integer> allFlags) throws SQLException {
        int size = prefs.e1m().get()*prefs.e1n().get();
        flags = new int[size];
        UniqueRandom random = new UniqueRandom(0, allFlags.size()-1);
        for(int i = 0; i< size; i++){
            flags[i] = allFlags.get(random.next());
            dao.deleteBuilder().where().eq(FLAGID, flags[i]).query();
//            db.rawQuery("delete from train_result where flagId=?",new String[]{""+flags[i]});
        }
    }
}
