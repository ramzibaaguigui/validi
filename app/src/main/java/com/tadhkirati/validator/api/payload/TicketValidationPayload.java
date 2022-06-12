package com.tadhkirati.validator.api.payload;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TicketValidationPayload implements Serializable {

    @SerializedName("travelId")
    Long travelId;

    @SerializedName("qrcode_token")
    String qrCodeToken;

    public static TicketValidationPayload createPayload() {
        return new TicketValidationPayload();
    }

    public TicketValidationPayload withTravelId(Long travelId)  {
        this.travelId = travelId;
        return this;
    }

    public TicketValidationPayload withQrCode(String qrCodeToken) {
        this.qrCodeToken = qrCodeToken;
        return this;
    }

}
