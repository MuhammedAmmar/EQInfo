package com.moworks.eqinfo.data.source.local;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.moworks.eqinfo.pojo.EqModel;

import java.util.ArrayList;
import java.util.List;


@Entity(tableName = "eq_report")
public class EqEntity {
    @PrimaryKey(autoGenerate = true)
    public Long id;
    public String place;
    @ColumnInfo(name = "time")
    public long time;
    @ColumnInfo(name = "magnitude")
    public double magnitude ;
    @ColumnInfo(name = "map_uri")
    public String mapUrl;


    public EqEntity( Long id ,String place, long time, double magnitude , String mapUrl) {
        this.id = id;
        this.place = place;
        this.time = time;
        this.magnitude  = magnitude ;
        this.mapUrl = mapUrl;
    }



    @Ignore
    public EqEntity(String place, long time, double magnitude , String mapUrl) {
        this.place = place;
        this.time = time;
        this.magnitude  = magnitude ;
        this.mapUrl = mapUrl;
    }


    public static List<EqModel> asEqModel(List<EqEntity> entityList){
        List<EqModel> eqModel = new ArrayList<>();
        for (EqEntity entity : entityList){
            eqModel.add( new  EqModel(entity.place , entity.time , entity.magnitude , entity.mapUrl));
        }

        return eqModel;
    }
}
