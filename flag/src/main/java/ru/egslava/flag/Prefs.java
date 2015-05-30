package ru.egslava.flag;

import org.androidannotations.annotations.sharedpreferences.DefaultInt;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by egslava on 07/05/15.
 */

@SharedPref(SharedPref.Scope.UNIQUE)
public interface Prefs {

    @DefaultInt(4) int t1n();
    @DefaultInt(4) int t1m();
    @DefaultInt(6) int t2n();
    @DefaultInt(6) int t2m();

    @DefaultInt(4) int e1n();
    @DefaultInt(4) int e1m();
    @DefaultInt(6) int e2n();
    @DefaultInt(6) int e2m();

    @DefaultInt(3) int secs1();     // time for first table
    @DefaultInt(7) int secs2();     // time for second table

    // how much flags from first table aren't shown in the second one (and will be added to five seria)
    @DefaultInt(2) int list5Imgs();

    @DefaultInt(0) int imgFolder();
}
