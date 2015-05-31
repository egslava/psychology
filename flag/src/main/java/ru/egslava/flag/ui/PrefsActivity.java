package ru.egslava.flag.ui;

import android.app.Activity;
import android.util.Range;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.api.sharedpreferences.IntPrefEditorField;
import org.androidannotations.api.sharedpreferences.IntPrefField;

import java.util.Arrays;

import ru.egslava.flag.Prefs_;
import ru.egslava.flag.R;
import ru.egslava.flag.utils.Images;

/**
 * Created by egslava on 07/05/15.
 */

@EActivity(R.layout.activity_prefs)
public class PrefsActivity extends Activity {

    @Pref Prefs_ prefs;

    @ViewById   public EditText t1n, t1m, t2n, t2m, e1n, e1m, e2n, e2m, secs1, secs2, list5Imgs;
    @ViewById   public Spinner  imgFolder;
    public ArrayAdapter<Integer> adapter;

    @Override   protected void onResume() {
        super.onResume();

        adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, range(0, Images.imgs.length));
        imgFolder.setAdapter(adapter);
        load(t1n, prefs.t1n());
        load(t1m, prefs.t1m());
        load(t2n, prefs.t2n());
        load(t2m, prefs.t2m());
        load(e1n, prefs.e1n());
        load(e1m, prefs.e1m());
        load(e2n, prefs.e2n());
        load(e2m, prefs.e2m());
        load(secs1, prefs.secs1());
        load(secs2, prefs.secs2());
        load(list5Imgs, prefs.list5Imgs());
        imgFolder.setSelection( prefs.imgFolder().get() );
    }

    @Override   protected void onPause() {
        super.onPause();
        final Prefs_.PrefsEditor_ e = prefs.edit();

        save(t1n, e.t1n());
        save(t1m, e.t1m());
        save(t2n, e.t2n());
        save(t2m, e.t2m());
        save(e1n, e.e1n());
        save(e1m, e.e1m());
        save(e2n, e.e2n());
        save(e2m, e.e2m());
        save(secs1, e.secs1());
        save(secs2, e.secs2());
        save(list5Imgs, e.list5Imgs());
        e.imgFolder().put( (int) imgFolder.getSelectedItem() );

        e.apply();
    }

    void save(TextView text, IntPrefEditorField i) { i.put(Integer.parseInt(text.getText().toString())); }
    void load(TextView text, IntPrefField i) { text.setText( String.valueOf( i.get( ) ) ); }

    Integer[] range(int lower, int upper) {
        Integer[] result = new Integer[upper - lower + 1];
        for (int i = lower; i <= upper; i++) {
            result[i - lower] = i;
        }
        return result;
    }

}