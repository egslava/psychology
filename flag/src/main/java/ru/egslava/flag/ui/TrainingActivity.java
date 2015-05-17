package ru.egslava.flag.ui;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import ru.egslava.flag.Prefs_;
import ru.egslava.flag.R;
import ru.egslava.flag.utils.Images;

@EActivity(R.layout.activity_training)
public class TrainingActivity extends Activity {
    @Pref Prefs_ prefs;
    @ViewById GridView flagsGV;
    int[] flags;
    String[] data = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k"};
    @AfterViews  void init() {
        loadFlags();
//        flagsGV.setAdapter(new ImageAdapter(flags));
        flagsGV.setNumColumns(prefs.t1m().get());
    }

    private void loadFlags(){
        int size = prefs.t1m().get()*prefs.t1n().get();
        flags = new int[size];
        for(int i = 0; i< size; i++){
            flags[i] = Images.imgs[i];
        }
    }
}
