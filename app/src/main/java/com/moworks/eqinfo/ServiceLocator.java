package com.moworks.eqinfo;

import android.content.Context;

import androidx.room.Room;

import com.moworks.eqinfo.data.EqRepository;
import com.moworks.eqinfo.data.source.local.EqDatabase;
import com.moworks.eqinfo.data.source.local.LocalDataSource;
import com.moworks.eqinfo.data.source.remote.RemoteDataSource;

import kotlin.jvm.Volatile;

public class ServiceLocator {
    private static EqRepository INSTANCE ;
    @Volatile
    private static EqDatabase database ;
    private static final Object LOCK = new Object();

    private ServiceLocator() {
    }

    public static EqRepository getRepository(Context context) {
        if (INSTANCE == null){
            synchronized (LOCK){
                INSTANCE = new EqRepository(RemoteDataSource.getService() , createLocalDatabase(context));
            }

        }
        return INSTANCE;
    }


    private static LocalDataSource createLocalDatabase(Context context){
        if (database == null){
            synchronized (LOCK){
                database = Room.databaseBuilder(context.getApplicationContext() , EqDatabase.class ,"earthquake")
                        .fallbackToDestructiveMigration()
                        .build();
            }

        }
        return new LocalDataSource(database.eqDao());
    }


}
