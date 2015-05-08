package ru.egslava.flag.auth;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.ViewById;

import ru.egslava.flag.R;
import ru.egslava.flag.views.ChooseBirthdayClickListener;
import ru.egslava.flag.views.GenderView;

/**
 * Created by egslava on 08/05/15.
 */

@EActivity(R.layout.activity_add_new_user)
public class AddNewUserActivity extends AccountAuthenticatorActivity{

    @ViewById   EditText birth;
    @ViewById   AutoCompleteTextView login, name;
    @ViewById   GenderView    sex;

    @Extra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE) AccountAuthenticatorResponse    response;

    @SystemService  AccountManager  manager;

    Long birthdate;
    public Account[] accounts;

    @AfterViews void init() {

        birth.setOnClickListener(new ChooseBirthdayClickListener(0, (time, age) -> {
            birth.setText(String.valueOf(age));
            birthdate = (long) age;
        }));
        Log.e("Account", "AddNewUserActivity");

        accounts = manager.getAccountsByType("ru.egslava.flag");


        setAutocompletes();
    }

    private void setAutocompletes() {
        String[]    logins = new String[accounts.length],
                    names = new String[accounts.length];
        for ( int i = 0; i < accounts.length ; i++ ){
            final Account account = accounts[i];
            logins[i] = account.name;
            names[i] = manager.getUserData(account, "name");
        }

        login.setAdapter( new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, android.R.id.text1, logins) );
        name.setAdapter( new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, android.R.id.text1, names) );
    }

    @Click void register() {
        final String    login = this.login.getText().toString(),
                        name = this.name.getText().toString();

        if ( TextUtils.isEmpty(login) ) { this.login.setError("Can not be empty"); return; }
        if ( TextUtils.isEmpty(name) ) { this.name.setError("Can not be empty"); return; }
        if ( birthdate == null ) { dialog("Please, enter birthdate"); return; }

        for (Account account : accounts) {
            if ( TextUtils.equals( account.name, login) ) {
                this.login.setError("This login already exists. Please, choose another (if you aren't owner of that login) or just log in.");
                this.login.requestFocus();
                return;
            }
        }

        final Account account = new Account(login, "ru.egslava.flag");
        final Bundle userdata = new Bundle();
        userdata.putString("name", name);
        userdata.putLong("birth", birthdate);
        userdata.putBoolean("sex", sex.isChecked() );

        if ( ! manager.addAccountExplicitly(account, null, userdata) ){
            dialog("Can not register new account");
            return;
        }

        final Bundle result = new Bundle();
        result.putString(AccountManager.KEY_ACCOUNT_NAME, login);
        result.putString(AccountManager.KEY_ACCOUNT_TYPE, "ru.egslava.flag");

        setAccountAuthenticatorResult( result );
        setResult(RESULT_OK);
        finish();
    }

    private void dialog(String message) {
        new AlertDialog.Builder( this )
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }
}
