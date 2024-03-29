package com.tadhkirati.validator.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class Travel implements Serializable, Parcelable, Parcelable.Creator<Travel> {

    // TODO: work on the parcelable interface so that you can received data from the other side without
    // todo: any problems,
    public static final Creator<Travel> CREATOR = new Creator<Travel>() {
        @Override
        public Travel createFromParcel(Parcel in) {
            return new Travel(in);
        }

        @Override
        public Travel[] newArray(int size) {
            return new Travel[size];
        }
    };

    @SerializedName("departure_station")
    private String departureStation;
    @SerializedName("arrival_station")
    private String arrivalStation;

    @SerializedName("distance")
//    BigDecimal distance;
    Integer distance;

    @SerializedName("description")
    String description;
    @SerializedName("status")
    String status;

    @SerializedName("stations")
    List<Station> stations;
    @SerializedName("id")
    Long id;
    @SerializedName("firstClass_limitPlaces")
    Integer firstClassLimitPlaces;
    @SerializedName("secondClass_limitPlaces")
    Integer secondClassLimitPlaces;
    @SerializedName("estimated_duration")
    private Integer duration;
//    private BigDecimal duration;

    protected Travel(Parcel in) {

    }

    public Travel(){}

    public static Travel createTravel() {
        return new Travel();
    }

    public String getArrivalStationName() {
        return this.stations.get(stations.size() - 1).getName();
    }

    public String getDepartureStationName() {
        return this.stations.get(0).getName();
    }

    public Travel withDepartureStation(String departureStation) {
        this.departureStation = departureStation;
        return this;
    }

    public int getFirstClassLimitPlaces() {
        return this.firstClassLimitPlaces;
    }

    public int getSecondClassLimitPlaces() {
        return this.secondClassLimitPlaces;
    }

    public Travel withArrivalStation(String arrivalStation) {
        return this;
    }

    public String status() {
        return this.status;
    }


    public String getDepartureTime() {
        return stations.get(0).getArrivalTime();
    }

    public String getArrivalTime() {
        return stations.get(stations.size() - 1).getArrivalTime();
    }

    public Integer getDuration() {
        return duration;
    }

    public Integer getDistance() {
        return this.distance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        // writing
        parcel.writeString(new Gson().toJson(this));
    }


    @Override
    public Travel createFromParcel(Parcel parcel) {
        return new Gson().fromJson(parcel.readString(), Travel.class);
    }

    @Override
    public Travel[] newArray(int i) {
        return new Travel[0];
    }

    public List<Station> getStations() {
        return this.stations;
    }

    public Long getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Travel{" +
                "departureStation='" + departureStation + '\'' +
                ", arrivalStation='" + arrivalStation + '\'' +
                ", distance=" + distance +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", stations=" + stations +
                ", id=" + id +
                ", firstClassLimitPlaces=" + firstClassLimitPlaces +
                ", secondClassLimitPlaces=" + secondClassLimitPlaces +
                ", duration=" + duration +
                '}';
    }
}
