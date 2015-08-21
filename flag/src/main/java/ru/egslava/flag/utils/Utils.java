package ru.egslava.flag.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.CheckBox;
import android.widget.TextView;

import java.security.InvalidParameterException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by egslava on 02/05/15.
 */
public class Utils {

    /** clear values of textviews, checkboxes, etc */
    public static void reset(TextView... views) {
        for(TextView view : views) {
            if (view instanceof CheckBox){
                ((CheckBox) view).setChecked(false);
                continue;
            }

            view.setText("");   // don't change to null - doesn't work on some androids
        }
    }

    public static void confirm(Context context, String msg, DialogInterface.OnClickListener onClickListener) {
        new AlertDialog.Builder( context )
                .setMessage(msg)
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, onClickListener)
                .show();
    }

    public static void confirm_reseti(String msg, TextView... views) {
        if (views.length < 1) return;
        confirm_reset(msg, views);
    }

    public static void confirm_reset(String msg, final TextView... views) {
        if (views.length < 1 ) throw new InvalidParameterException();
        new AlertDialog.Builder(views[0].getContext() )
                .setTitle(android.R.string.dialog_alert_title)
                .setMessage(msg)
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> reset(views))
                .show();
    }

    public static void after( int delayMs, RunnableWithExceptions lambda ) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override public void run() {
                unsafe( () -> lambda.run());
            }
        }, delayMs);
    }

    public static void unsafe(RunnableWithExceptions r){
        try { r.run(); } catch (Exception e) { throw new RuntimeException(e); }
    }

    public interface RunnableWithExceptions {
        void run() throws Exception;
    }
}
