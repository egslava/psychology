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
public class Mark {
    @DatabaseField(generatedId = true)      public int id;      //just for ormlite
    @DatabaseField public String  userName;
    @DatabaseField public int     flagId, mark;

    public Mark(String userName, int flagId, int mark) {
        this.userName = userName;
        this.flagId = flagId;
        this.mark = mark;
    }
}
