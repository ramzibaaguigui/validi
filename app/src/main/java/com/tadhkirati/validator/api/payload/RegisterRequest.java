package com.tadhkirati.validator.api.payload;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RegisterRequest implements Serializable {
    @SerializedName("first_name")
    String firstName;
    @SerializedName("last_name")
    String lastName;
    @SerializedName("phone_number")
    String phoneNumber;
    @SerializedName("password")
    String password;
    @SerializedName("confirm_password")
    String confirmPassword;

}
