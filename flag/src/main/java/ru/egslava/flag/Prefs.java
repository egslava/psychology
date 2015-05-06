package ru.egslava.flag;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by egslava on 07/05/15.
 */

@SharedPref(SharedPref.Scope.UNIQUE)
public interface Prefs {

    int t1n(); int t1m();
    int t2n(); int t2m();

    int time1(); int time2();
    int list5Flags();
}
