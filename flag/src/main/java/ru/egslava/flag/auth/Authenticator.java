package ru.egslava.flag.auth;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import java.security.InvalidParameterException;

/**
 * Created by egslava on 08/05/15.
 */
public class Authenticator extends AbstractAccountAuthenticator {

    public final Context context;

    public Authenticator(Context context) {
        super(context);
        this.context = context;
    }
//    public Authenticator(Context context) {
//        super(context);
//    }

    @Override public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
        return null;
    }

    @Override public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {
        if ( ! accountType.equals("ru.egslava.flag")) throw new InvalidParameterException();

        final Intent addNewUserActivity = AddNewUserActivity_.intent(context).response(response).get();

        final Bundle bundle = new Bundle( options );
        bundle.putParcelable(AccountManager.KEY_INTENT, addNewUserActivity);
        return bundle;
    }

    @Override public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) throws NetworkErrorException {
        Log.e("AUTH", "confirmCredentials");
        return null;
    }

    @Override public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        Log.e("AUTH", "getAuthToken");
        return null;
    }

    @Override public String getAuthTokenLabel(String authTokenType) {
        Log.e("AUTH", "getAuthTokenLabel");
        return null;
    }

    @Override public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        Log.e("AUTH", "updateCredentials");
        return null;
    }

    @Override public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException {
        Log.e("AUTH", "hasFeatures");
        return null;
    }

    @NonNull @Override public Bundle getAccountRemovalAllowed(AccountAuthenticatorResponse response, Account account) throws NetworkErrorException {
        Log.e("AUTH", "getAccountRemovalAllowed");
        return super.getAccountRemovalAllowed(response, account);
    }
}
