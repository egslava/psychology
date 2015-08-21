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
public class TrainResult {

    @DatabaseField                          public String      userName;
    @DatabaseField                          public int         flagId;
    @DatabaseField(generatedId = true)      public int          id;

    public TrainResult(String userName, int flagId) {
        this.userName = userName;
        this.flagId = flagId;
    }
}
