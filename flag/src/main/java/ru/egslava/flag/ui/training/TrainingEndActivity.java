package ru.egslava.flag.ui.training;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ImageView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;

import ru.egslava.flag.Prefs_;
import ru.egslava.flag.R;
import ru.egslava.flag.ui.*;
import ru.egslava.flag.utils.Images;
import ru.egslava.flag.utils.UniqueRandom;

@EActivity(R.layout.activity_training_end)
public class TrainingEndActivity extends ActionBarActivity {
    @Pref Prefs_ prefs;
    @ViewById GridView flagsSGV;
    @Extra Integer[] oldFlags;
    @Extra int round;
    @Extra TreeSet<Integer> identified;

    Integer[] flags;
    TreeSet<Integer> selected = new TreeSet<>();

    @AfterViews void init() {
        loadFlags();
        flagsSGV.setAdapter(new ImageAdapter(this, flags));
        flagsSGV.setNumColumns(prefs.t2m().get());
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for(Integer element : selected){
                    if(checkSelectedElement(flags[element])){
                        identified.add(flags[element]);
                    }
                }
                if(identified.size() > prefs.t2m().get() * prefs.t2n().get()){
                    //TrainingResultActivity_.intent(TrainingEndActivity.this).identified(identified).round(round);
                } else {
                    TrainingActivity_.intent(TrainingEndActivity.this).round(round+1).identified(identified).start();
                }
            }
        }, prefs.secs2().get()*1000);
    }

    private void loadFlags(){
        int size = prefs.t2m().get()*prefs.t2n().get();
        ArrayList<Integer> list = new ArrayList();
        for(int i = 0; i< oldFlags.length; i++){
            list.add(oldFlags[i]);
        }
        UniqueRandom random = new UniqueRandom(0, Images.imgs.length);
        for(int i = oldFlags.length; i< size; i++){
            list.add(Images.imgs[random.next()]);
        }
        Collections.shuffle(list);
        flags = new Integer[size];
        flags = list.toArray(flags);
    }

    private boolean checkSelectedElement(Integer element){
        for(int i =0; i < oldFlags.length; i++){
            if(oldFlags[i].equals(element)) return true;
        }
        return false;
    }

    @ItemClick void flagsSGV(int position){
        selected.add(position);
    }

    @Override
    public void onBackPressed() {
    }
}
