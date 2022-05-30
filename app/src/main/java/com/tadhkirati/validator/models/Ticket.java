package com.tadhkirati.validator.models;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class Ticket {

    @SerializedName("travel_id")
    Long travelId;

    @SerializedName("passenger_name")
    String passengerName;

    @SerializedName("travel_class")
    String travelClass;

    @SerializedName("payment_method")
    String paymentMethod;


    String paymentToken;

    @SerializedName("validated")
    private Integer isValidated;

    @SerializedName("boarding_station")
    Station boardingStation;

    @SerializedName("landing_station")
    Station landingStation;

    @SerializedName("price")
    Double price;

    @SerializedName("payer")
    String payerName;

    @SerializedName("qrcode_token")
    private String qrCodeToken;

    @SerializedName("id")
    private Long id;



    public String getPassengerName() {
        return this.passengerName;
    }

    public String getBoardingStationName() {
        return this.boardingStation.getName();
    }

    public String getLandingStationName() {
        return this.landingStation.getName();
    }

    public String getToken() {
        return this.qrCodeToken;
    }

    public boolean isValidated() {
        return isValidated == 1;
    }

    public String paymentMethod() {
        return this.paymentMethod;
    }

    public String travelClass() {
        return this.travelClass;
    }

    public Double getPrice() {
        return this.price;
    }

    public String getQrCodeToken() {
        return this.qrCodeToken;
    }
}
