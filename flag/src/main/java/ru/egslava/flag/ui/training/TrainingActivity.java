package ru.egslava.flag.ui.training;

import android.app.Activity;

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
import ru.egslava.flag.utils.Images;
import ru.egslava.flag.utils.UniqueRandom;
import ru.egslava.flag.utils.Utils;

import static ru.egslava.flag.utils.Utils.after;

@EActivity(R.layout.activity_training)
public class TrainingActivity extends Activity {
    @Pref Prefs_ prefs;
    @ViewById   FitGridLayout flagsGV;
    @Extra      ArrayList<Integer> identified;
    @Extra      String userName;

    int[] flags;
    @AfterViews  void init() {
        loadFlags();
        flagsGV.init(prefs.t1m().get(), prefs.t1n().get(), flags);

        after(prefs.secs1().get() * 1000, () -> {
            TrainingEndActivity_.intent(TrainingActivity.this).identified(identified).userName(userName).oldFlags(flags).start();
        });
    }

    private void loadFlags(){
        int size = prefs.t1m().get()*prefs.t1n().get();
        flags = new int[size];
        UniqueRandom random = new UniqueRandom(0, Images.imgs[prefs.imgFolder().get()].length);
        for(int i = 0; i< size; i++){
            //flags[i] = Images.imgs[random.next()][prefs.imgFolder().get()];
            flags[i] = Images.imgs[prefs.imgFolder().get()][random.next()];
        }
    }

    @Override public void onBackPressed() {}
}
