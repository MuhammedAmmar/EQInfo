package com.moworks.eqinfo.data.source.local;

import androidx.lifecycle.LiveData;

import com.moworks.eqinfo.Concurrency.Dispatchers;
import com.moworks.eqinfo.data.DataSource;

import java.util.List;
import java.util.concurrent.Executor;


public class LocalDataSource implements DataSource {
    EqDao dao ;
    Executor IoDispatcher = Dispatchers.getInstance().DISK_IO;

    public LocalDataSource(EqDao dao, Executor IoDispatcher) {
        this.dao = dao;
        this.IoDispatcher = IoDispatcher;
    }

    public LocalDataSource(EqDao dao) {
        this.dao = dao;
    }



    @Override
    public LiveData<List<EqEntity>> observeData() {
        return dao.getReport();
    }

    @Override
    public void insertData(List<EqEntity> eqEntity) {
        IoDispatcher.execute(new Runnable() {
            @Override
            public void run() {
                dao.insert(eqEntity);
            }
        });
    }

    @Override
    public void clearDataSource() {
        IoDispatcher.execute(()->{
            dao.clear();
        });
    }


}
