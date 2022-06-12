package com.tadhkirati.validator.api.payload;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TodayTravelRequestPayload implements Serializable {
    @SerializedName("validator_id")
    private Long validatorId;

    public static TodayTravelRequestPayload create() {
        return new TodayTravelRequestPayload();
    }

    public TodayTravelRequestPayload withValidatorId(Long validatorId) {
        this.validatorId = validatorId;
        return this;
    }
}
