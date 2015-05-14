package ru.egslava.flag;

import android.view.MotionEvent;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by egslava on 01/05/15.
 */
public class Fixes {

    /**
     * This method should be used instead of textView.setText(cs).
     * Not all android phones understand textView.setText(null). Some phones just keep previous
     * text. So if you're working with list views you should use this method.
     */
    public static <T> void map (TextView view, T cs) {
        view.setText( String.valueOf(cs) );
        if (cs == null) { view.setText(""); };
    }

    /** This method works like as just map, but don't throw NPE if view is null */
    public static <T> boolean mapi(TextView view, T cs) {
        if (view == null) return false;
        map(view, cs);
        return true;
    }

    public static <T> void listViewInsideScrollView(final ListView list) {
        list.setOnTouchListener((v, event) -> {
            list.requestDisallowInterceptTouchEvent(true);
            int action = event.getActionMasked();
            switch (action) {
                case MotionEvent.ACTION_UP:
                    list.requestDisallowInterceptTouchEvent(false);
                    break;
            }
            return false;
        });
    }

}
