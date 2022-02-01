package com.moworks.eqinfo.Concurrency;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class Dispatchers {
    public final   Executor DISK_IO ;
    public final   Executor NETWORK_IO ;
    public final   Executor MAIN ;
    private static final Object LOCK = new Object();
    private static Dispatchers dispatchers;

    private Dispatchers(Executor DISK_IO , Executor NETWORK_IO , Executor MAIN) {
        this.DISK_IO =DISK_IO;
        this.NETWORK_IO = NETWORK_IO;
        this.MAIN = MAIN;
    }

    public static Dispatchers  getInstance(){
        if (dispatchers == null ){
            synchronized (LOCK){
                dispatchers = new Dispatchers(Executors.newSingleThreadExecutor() , Executors.newFixedThreadPool(6) , new MainDispatcher());
            }
        }
        return dispatchers;
    }


    private static class MainDispatcher implements Executor{
         private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());


        @Override
        public void execute(Runnable command) {
            mainThreadHandler.post(command);
        }
    }

}
