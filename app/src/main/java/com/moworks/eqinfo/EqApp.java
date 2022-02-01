package com.moworks.eqinfo;

import android.app.Application;

import timber.log.Timber;

public class EqApp  extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        if(BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }


    }
}
