package ru.egslava.flag.ui.userlist;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.ViewById;
import org.joda.time.LocalDate;
import org.joda.time.Years;

import ru.egslava.flag.R;

import static ru.egslava.flag.Fixes.*;

/**
 * Created by egslava on 08/05/15.
 */

@EViewGroup(R.layout.item_account)
public class AccountItem extends FrameLayout {
    public AccountItem(Context context) { super(context); }
    public AccountItem(Context context, AttributeSet attrs) { super(context, attrs); }

    @ViewById TextView login, name, ages;

    @SystemService AccountManager   accountManager;

    public void bind( Account account) {
        map(login,  account.name);
        map(name,   accountManager.getUserData(account, "name") );
        mapBirth(   account );
    }

    void mapBirth( Account account ) {
        final String birthString = accountManager.getUserData(account, "birth");

        if ( birthString == null ) {
            map(this.ages, null);
            return;
        }

        final Long      birth   = Long.parseLong(birthString);
        final long      ages    = Years.yearsBetween(new LocalDate(birth), new LocalDate()).getYears();
        map(this.ages, String.format("(%d)", ages) );
    }

}

