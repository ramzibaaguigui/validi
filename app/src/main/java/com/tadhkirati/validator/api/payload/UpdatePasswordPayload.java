package com.tadhkirati.validator.api.payload;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UpdatePasswordPayload implements Serializable {
    @SerializedName("current_password")
    private String oldPassword;

    @SerializedName("password")
    private String password;

    @SerializedName("confirm_password")
    private String confirmPassword;

    public static UpdatePasswordPayload create() {
        return new UpdatePasswordPayload();
    }

    public UpdatePasswordPayload oldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
        return this;
    }

    public UpdatePasswordPayload newPassword(String newPassword) {
        this.password = newPassword;
        return this;
    }

    public UpdatePasswordPayload confirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }
}
