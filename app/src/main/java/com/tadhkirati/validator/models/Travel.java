package com.tadhkirati.validator.models;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class Travel {

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

}
