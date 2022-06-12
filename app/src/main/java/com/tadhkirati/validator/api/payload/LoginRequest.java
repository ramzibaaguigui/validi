package com.tadhkirati.validator.api.payload;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginRequest implements Serializable {
    @SerializedName("phone_number")
    String phoneNumber;
    @SerializedName("password")
    String password;

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public LoginRequest withPhone(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public LoginRequest withPassword(String password) {
        this.password = password;
        return this;
    }


    public static LoginRequest create() {

        return new LoginRequest();
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}

