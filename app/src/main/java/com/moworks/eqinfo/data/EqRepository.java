package com.moworks.eqinfo.data;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.moworks.eqinfo.Concurrency.Dispatchers;
import com.moworks.eqinfo.data.source.local.EqEntity;
import com.moworks.eqinfo.data.source.remote.Service;
import com.moworks.eqinfo.pojo.EqModel;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;


public class EqRepository implements AppRepository {

    private Service remoteDataSource ;
    private DataSource  localDataSource ;
    private Executor NioDispatcher = Dispatchers.getInstance().NETWORK_IO;

    public EqRepository(Service remoteDataSource, DataSource localDataSource , Executor NioDispatcher) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
        this.NioDispatcher = NioDispatcher;
    }

    public EqRepository(Service remoteDataSource, DataSource localDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }





    @Override
    public LiveData<List<EqEntity>> getReport() {
        return localDataSource.observeData();
    }

    // return true if submit result is completed else return false
    @Override
    public boolean refreshData() {
        ExecutorService executorService = (ExecutorService) NioDispatcher;

        boolean isSuccess = false;
        try {
            List<EqModel> result  = executorService.submit(new Callable<List<EqModel>>() {
                @Override
                public List<EqModel> call() throws Exception {
                    return remoteDataSource.service();
                }
            }).get();

            Log.d("EqReport" , "before if"+ result);

            if (result != null ){
                Log.d("EqReport" , ""+  result);
                localDataSource.clearDataSource();
                localDataSource.insertData(EqModel.asEqEntity(result));
                isSuccess = true;
            }

        }catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return isSuccess;
    }


}
