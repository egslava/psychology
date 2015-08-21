package ru.egslava.flag.utils;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import ru.egslava.flag.model.FlagExp;
import ru.egslava.flag.model.Mark;
import ru.egslava.flag.model.TrainResult;

public class DBHelper extends OrmLiteSqliteOpenHelper {

    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "flagsDB", null, 1);
    }

    @Override public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        Log.d("LOG_TAG", "--- onCreate database ---");
        // создаем таблицу с полями
        try {
            TableUtils.createTableIfNotExists(connectionSource, Mark.class);
            TableUtils.createTableIfNotExists(connectionSource, FlagExp.class);
            TableUtils.createTableIfNotExists(connectionSource, TrainResult.class);

        } catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource,
                                    int oldVersion, int newVersion) {}

}