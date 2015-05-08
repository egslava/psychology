package ru.egslava.flag;

import android.accounts.AccountManager;
import android.app.ListActivity;
import android.content.Intent;
import android.preference.PreferenceActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.res.StringArrayRes;

@EActivity
public class MenuActivity extends ListActivity {

    @StringArrayRes             String[]                menu;
    public                      ArrayAdapter<String>    adapter;

    @AfterInject void init() {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, menu);
        setListAdapter(adapter);
    }

    @ItemClick void list(int position) {
        PrefsActivity_.intent(this).start();
    }
}
