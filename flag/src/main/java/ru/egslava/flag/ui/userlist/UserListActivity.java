package ru.egslava.flag.ui.userlist;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.ViewById;
import org.joda.time.LocalDate;
import org.joda.time.Years;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ru.egslava.flag.R;

/**
 * Created by egslava on 08/05/15.
 */

@EActivity(R.layout.activity_user_list)
@OptionsMenu( R.menu.menu_userlist )
public class UserListActivity extends ActionBarActivity {

    @SystemService  AccountManager accountManager;
    @ViewById       ListView  list;

    public SearchView searchView;
    public AccountsAdapter adapter;

    @AfterViews void init() {
        final Account[] accounts = accountManager.getAccountsByType(getPackageName());
        adapter = new AccountsAdapter(this, accounts);
        list.setAdapter(adapter);
        list.setTextFilterEnabled(true);
    }

    @Click void add() { accountManager.addAccount(getPackageName(), null, null, null, this, null, null); }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        searchView = (SearchView) MenuItemCompat.getActionView ( searchBar );
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String s) {
                Log.e("Filter", "Submit");
                return false;
            }

            @Override public boolean onQueryTextChange(String s) {
//                list.setFilterText( s );
                list.setFilterText(s);
                Log.e("Filter", "Change");
                return false;
            }
        });
        return true;
    }

    @OptionsMenuItem MenuItem searchBar;
}
