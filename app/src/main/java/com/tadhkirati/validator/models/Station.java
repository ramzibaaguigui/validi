package com.tadhkirati.validator.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;

public class Station implements Serializable {

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private Long id;

    @SerializedName("distance")
    private double distance;

    @SerializedName("wilaya")
    private String wilaya;

    @SerializedName("arrival_time")
    private String arrivalTime;

    @SerializedName("firstClass_price")
    private BigDecimal firstClassPrice;

    @SerializedName("secondClass_price")
    private BigDecimal secondClassPrice;


    public String getArrivalTime() {
        return this.arrivalTime;
    }


    public String getName() {
        return this.name;
    }

    public String getWilaya() {
        return this.wilaya;
    }

    public BigDecimal getFirstClassCost() {
        return this.firstClassPrice;
    }

    public BigDecimal getSecondClassCost() {
        return this.secondClassPrice;
    }
}
