package com.tadhkirati.validator.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;

public class Travel implements Serializable {

    @SerializedName("departure_station")
    String departureStation;

    @SerializedName("arrival_station")
    String arrivalStation;

    @SerializedName("distance")
    BigDecimal distance;

    @SerializedName("estimated_duration")
    BigDecimal estimatedDuration;

    @SerializedName("description")
    String description;

    @SerializedName("status")
    TravelStatus status;

    public String getArrivalStation() {
        return this.arrivalStation;
    }

    public String getDepartureStation() {
        return this.departureStation;
    }

    public static Travel createTravel() {
        return new Travel();
    }

    public Travel withDepartureStation(String departureStation) {
        this.departureStation = departureStation;
        return this;
    }

    public Travel withArrivalStation(String arrivalStation) {
        this.arrivalStation = arrivalStation;
        return this;
    }

}
