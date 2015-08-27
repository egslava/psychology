package ru.egslava.flag.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.tojc.ormlite.android.annotation.AdditionalAnnotation;
import com.tojc.ormlite.android.annotation.AdditionalAnnotation.Contract;

/**
 * Created by egslava on 03/06/15.
 */

@Contract
@DatabaseTable
public class FlagExp {
    @DatabaseField public String  userName;
    @DatabaseField public int     flagId, round, state;

    public FlagExp(){}

    public FlagExp(String userName, int flagId, int round, int state) {
        this.userName = userName;
        this.flagId = flagId;
        this.round = round;
        this.state = state;
    }
}
