package com.tadhkirati.validator.api.payload;

import com.tadhkirati.validator.models.User;

import java.io.Serializable;

public class LoginResponse implements Serializable {
    private User user;
    private String token;

    public User getUser() {
        return this.user;
    }

    public String token() {
        return this.token;
    }
}
