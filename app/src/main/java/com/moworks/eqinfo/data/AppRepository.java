package com.moworks.eqinfo.data;

import androidx.lifecycle.LiveData;

import com.moworks.eqinfo.data.source.local.EqEntity;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface AppRepository {

    public LiveData<List<EqEntity>> getReport();

    public boolean refreshData() throws IOException, ExecutionException, InterruptedException;

}
