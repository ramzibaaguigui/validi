package com.tadhkirati.validator.api.payload;

import com.tadhkirati.validator.models.User;

public class LoginResponse {
    private User user;
    private String token;

    public User getUser() {
        return this.user;
    }

    public String token() {
        return this.token;
    }
}
