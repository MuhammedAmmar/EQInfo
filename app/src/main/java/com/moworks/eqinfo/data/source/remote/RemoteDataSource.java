package com.moworks.eqinfo.data.source.remote;


import com.moworks.eqinfo.pojo.EqModel;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import kotlin.jvm.Volatile;

public  class RemoteDataSource extends NetworkService {
    @Volatile
    private  static RemoteDataSource INSTANCE ;
    private static final Object LOCK = new Object();

    public static RemoteDataSource getService(){
        if (INSTANCE == null){
            synchronized (LOCK){
                INSTANCE = new RemoteDataSource();
            }
        }
        return INSTANCE;
    }


    private RemoteDataSource() {
    }

    @Override
    public List<EqModel> service() throws IOException {
        String rawJson = makeHttpRequest(bailUrl());
        try {
            return parseJsonResponse(rawJson);

        } catch (JSONException e) {

            e.printStackTrace();
        }
        return null;
    }

}
