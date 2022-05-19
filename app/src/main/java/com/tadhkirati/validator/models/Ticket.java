package com.tadhkirati.validator.models;

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

    public String getPassengerName() {
        return this.passengerName;
    }
    public String getBoardingStationName() {
        return this.boardingStation.getName();
    }
}
