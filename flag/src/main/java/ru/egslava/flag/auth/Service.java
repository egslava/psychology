package ru.egslava.flag.auth;

import android.content.Intent;
import android.os.IBinder;

/**
 * Created by egslava on 08/05/15.
 */
public class Service extends android.app.Service {

    @Override public IBinder onBind(Intent intent) {
        return new Authenticator(this).getIBinder();
    }
}
