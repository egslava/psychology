package ru.egslava.flag.ui.views;

import android.content.Context;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.security.InvalidParameterException;
import java.util.ArrayList;

/**
 * Created by egslava on 17/05/15.
 */
public class FitGridLayout extends LinearLayout {

    public FitGridLayout(Context context) { super(context); }
    public FitGridLayout(Context context, AttributeSet attrs) { super(context, attrs); }

    ArrayList<FlagView> flagViews = new ArrayList<>();

    public void init (int rows, int cols, FlagView... views){
        setOrientation(VERTICAL);

        if (views.length != rows * cols) throw new InvalidParameterException();
        if (rows <= 0 || cols <= 0) throw new InvalidParameterException();

        for (int row = 0; row < rows; row++){
            final LinearLayout linlay = new LinearLayout(getContext());
            linlay.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1));
            linlay.setOrientation(HORIZONTAL);

            for (int col = 0; col < cols; col++){
                final FlagView view = views[rows * row + col];
                view.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1));
                linlay.addView(view);

                flagViews.add(view);
            }

            addView(linlay);
        }
    }

    public void init(int rows, int cols, int... imageIds) {
        FlagView[] views = new FlagView[imageIds.length];

        for (int i = 0; i < imageIds.length; i++){
            final FlagView image = new FlagView(getContext());
            image.setScaleType(ImageView.ScaleType.FIT_CENTER);
            image.setImageResource(imageIds[i]);
            views[i] = image;
        }

        init (rows, cols, views);
    }

    public ArrayList<Integer> getSelectedIds() {
        final ArrayList<Integer> result = new ArrayList<>();
        for( FlagView flag: flagViews ){
            if (flag.isChecked()) {
                result.add(flag.resId);
            }
        }

        return result;
    }
}
