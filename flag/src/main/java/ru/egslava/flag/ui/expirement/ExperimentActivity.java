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

import ru.egslava.flag.Prefs_;
import ru.egslava.flag.R;
import ru.egslava.flag.ui.views.FitGridLayout;
import ru.egslava.flag.utils.DBHelper;

@EActivity(R.layout.activity_experiment)
public class ExperimentActivity extends ActionBarActivity {
    @Extra
    String userName;
    @ViewById
    FitGridLayout flagsExp;
    @Pref
    Prefs_ prefs;

    private SQLiteDatabase db;
    private DBHelper dbHelper;


    @AfterViews void init(){
        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        Cursor c = db.query("marks",null,"`userName`=?",new String[]{userName},null,null,null);
        //flagsExp.init(prefs.e1m(), prefs.e1n(), );
    }

    @Override
    protected void onStop() {
        dbHelper.close();
        super.onStop();
    }
}
