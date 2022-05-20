package com.tadhkirati.validator.models;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class Ticket {


    Travel travel;
    String passengerName;
    String travelClass;
    String paymentMethod;
    String paymentToken;
    Boolean isValidated;
    Station boardingStation;
    Station landingStation;
    BigDecimal price;

    @SerializedName("qrcode_token")
    String qrCodeToken;

    public String getPassengerName() {
        return this.passengerName;
    }
    public String getBoardingStationName() {
        return this.boardingStation.getName();
    }

    public String getToken() {
        return this.qrCodeToken;
    }
}
