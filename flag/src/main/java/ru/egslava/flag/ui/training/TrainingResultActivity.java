package ru.egslava.flag.ui.training;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.TreeSet;

import ru.egslava.flag.R;
import ru.egslava.flag.ui.MenuActivity_;

@EActivity(R.layout.activity_training_result)
public class TrainingResultActivity extends ActionBarActivity {
    @Extra
    TreeSet<Integer> identified;
    @Extra
    Integer round;
    @ViewById
    TextView textView;
    @ViewById
    Button toMenu;

    @AfterViews
    void init() {
        textView.setText("За " + round + " раунда было опознано" + identified.size() + "флагов");
    }

    @Click void toMenu(){
        MenuActivity_.intent(this).start();
    }
}