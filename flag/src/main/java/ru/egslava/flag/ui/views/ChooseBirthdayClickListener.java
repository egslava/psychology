package ru.egslava.flag.ui.views;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;

import ru.egslava.flag.commons.CC;


/**
 * Created by egslava on 09/04/15.
 */
public class ChooseBirthdayClickListener implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    public CC<Long, Integer> callback;
    public long initTime;

    public ChooseBirthdayClickListener( long initTime, CC<Long, Integer> callback) {
        this.callback = callback;
        this.initTime = initTime;
    }
    public ChooseBirthdayClickListener() { this.initTime = System.currentTimeMillis(); }

    @Override public void onClick(View v) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(initTime);

        int year    = cal.get(Calendar.YEAR);
        int month   = cal.get(Calendar.MONTH);
        int day     = cal.get(Calendar.DAY_OF_MONTH);

        new DatePickerDialog( v.getContext(), this, year, month, day ).show();
    }

    @Override public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar now = Calendar.getInstance();
        int currentYear     = now.get( Calendar.YEAR );
        int currentMonth    = now.get(Calendar.MONTH);
        int currentDay      = now.get(Calendar.DAY_OF_MONTH);

        int yearsOld = currentYear - year;

        if ( currentMonth < monthOfYear) {
            yearsOld -= 1;
        } else if ( currentMonth == monthOfYear ) {
            if ( currentDay < dayOfMonth ) yearsOld -= 1;
        }

        Calendar birthCal = Calendar.getInstance();
        birthCal.set( year, monthOfYear, dayOfMonth );

        initTime = birthCal.getTimeInMillis();   // next time we shoud start from chosed date
        if (callback != null) callback.on(birthCal.getTimeInMillis(), yearsOld );

    }
}
