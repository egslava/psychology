package ru.egslava.flag.ui.userlist;

import android.accounts.Account;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

class AccountsAdapter extends ArrayAdapter<Account> {
    public AccountsAdapter(Context context, Account[] objects) {
        super(context, android.R.layout.simple_list_item_1, android.R.id.text1, objects);
    }

    @Override public View getView(int position, View itemView, ViewGroup parent) {
        if (itemView == null) itemView = AccountItem_.build(getContext());
        AccountItem view = (AccountItem) itemView;
        view.bind(getItem(position));
        return view;
    }
}