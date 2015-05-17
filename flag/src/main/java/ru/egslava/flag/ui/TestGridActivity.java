package ru.egslava.flag.ui;

import android.app.Activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import ru.egslava.flag.R;
import ru.egslava.flag.ui.views.FitGridLayout;
import ru.egslava.flag.utils.Images;

/**
 * Created by egslava on 18/05/15.
 */
@EActivity(R.layout.activity_test_grid)
public class TestGridActivity extends Activity{

    @ViewById FitGridLayout grid;

    @AfterViews void init() {
        grid.init(2, 2, Images.imgs[0], Images.imgs[1], Images.imgs[2], Images.imgs[3]);
    }

}
