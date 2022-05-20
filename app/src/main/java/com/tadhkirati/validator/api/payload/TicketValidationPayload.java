package com.tadhkirati.validator.api.payload;

import com.google.gson.annotations.SerializedName;

public class TicketValidationPayload {
    Integer travelId;

    @SerializedName("qrcode_token")
    String qrCodeToken;

    public static TicketValidationPayload createTicketValidationRequest() {
        return new TicketValidationPayload();
    }

    public TicketValidationPayload withTravelId(Integer travelId)  {
        this.travelId = travelId;
        return this;
    }

    public TicketValidationPayload withQrCode(String qrCodeToken) {
        this.qrCodeToken = qrCodeToken;
        return this;
    }

}
