package com.tadhkirati.validator.api.payload;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UpdateValidatorInfoPayload implements Serializable {

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("phone_number")
    private String phoneNumber;

    public static  UpdateValidatorInfoPayload create() {
        return new UpdateValidatorInfoPayload();
    }

    public UpdateValidatorInfoPayload withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UpdateValidatorInfoPayload withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UpdateValidatorInfoPayload withPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }


    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
