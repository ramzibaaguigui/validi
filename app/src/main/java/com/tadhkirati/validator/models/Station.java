package com.tadhkirati.validator.models;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

public class Station {
    private String name;

    @SerializedName("id")
    private Long id;

    @SerializedName("distance")
    private BigDecimal distance;


    @SerializedName("wilaya")
    private String wilaya;

    @SerializedName("arrival_time")
    private String arrivalTime;

    @SerializedName("firstClass_price")
    private BigDecimal firstClassPrice;

    @SerializedName("secondClass_price")
    private BigDecimal secondClassPrice;




    public String getName() {
        return this.name;
    }

}
