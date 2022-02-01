package com.moworks.eqinfo.data;

import androidx.lifecycle.LiveData;

import com.moworks.eqinfo.data.source.local.EqEntity;

import java.util.List;

public interface DataSource{

    public LiveData<List<EqEntity>> observeData();


     public void insertData(List<EqEntity> eqEntity);


     public void clearDataSource();

}
