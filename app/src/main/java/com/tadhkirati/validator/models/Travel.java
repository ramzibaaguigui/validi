package com.tadhkirati.validator.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class Travel implements Serializable {

    @SerializedName("departure_station")
    String departureStation;

    @SerializedName("arrival_station")
    String larrivalStation;

    @SerializedName("distance")
    BigDecimal distance;

    @SerializedName("estimated_duration")
    BigDecimal estimatedDuration;

    @SerializedName("description")
    String description;

    @SerializedName("status")
    TravelStatus status;

    @SerializedName("stations")
    List<Station> stations;

    @SerializedName("id")
    Long id;





    public String getArrivalStation() {
        return this.stations.get(stations.size() - 1).getName();
    }

    public String getDepartureStation() {
        return this.stations.get(0).getName();
    }

    public static Travel createTravel() {
        return new Travel();
    }

    public Travel withDepartureStation(String departureStation) {
        this.departureStation = departureStation;
        return this;
    }

    public Travel withArrivalStation(String arrivalStation) {
        return this;
    }


    enum TravelStatus {
        pending, completed, underway
    }
}
