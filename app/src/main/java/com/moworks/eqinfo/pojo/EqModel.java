package com.moworks.eqinfo.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.moworks.eqinfo.data.source.local.EqEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EqModel implements Parcelable  {
    private  Long id ;
    private  String place ;
    private  long time;
    private  double magnitude ;
    private String  mapUrl ;



    public EqModel(Long id , String place, long time, double magnitude, String mapUrl) {
        this.id = id;
        this.place = place;
        this.time = time;
        this.magnitude = magnitude;
        this.mapUrl = mapUrl;
    }

    public EqModel( String place, long time, double magnitude, String mapUrl) {
        this.place = place;
        this.time = time;
        this.magnitude = magnitude;
        this.mapUrl = mapUrl;
    }

    protected EqModel(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        place = in.readString();
        time = in.readLong();
        magnitude = in.readDouble();
        mapUrl = in.readString();
    }

    public static final Creator<EqModel> CREATOR = new Creator<EqModel>() {
        @Override
        public EqModel createFromParcel(Parcel in) {
            return new EqModel(in);
        }

        @Override
        public EqModel[] newArray(int size) {
            return new EqModel[size];
        }
    };

    public String getPlace() {
        return place;
    }

    public long getTime() {
        return time;
    }

    public double getMag() {
        return magnitude;
    }

    public String getMapUrl() {
        return mapUrl;
    }

    public Long getId() {
        return id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EqModel eqModel = (EqModel) o;
        return time == eqModel.time &&
                Double.compare(eqModel.magnitude, magnitude) == 0 &&
                Objects.equals(id, eqModel.id) &&
                Objects.equals(place, eqModel.place) &&
                Objects.equals(mapUrl, eqModel.mapUrl);
    }


    public static List<EqEntity> asEqEntity(List<EqModel> modelList){
        List<EqEntity> eqEntities = new ArrayList<>();
        for (EqModel model : modelList){
            eqEntities.add( new  EqEntity(model.place , model.time , model.magnitude , model.mapUrl));
        }

        return eqEntities;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(place);
        dest.writeLong(time);
        dest.writeDouble(magnitude);
        dest.writeString(mapUrl);
    }
}
