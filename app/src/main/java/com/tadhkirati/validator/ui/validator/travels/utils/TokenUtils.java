package com.tadhkirati.validator.ui.validator.travels.utils;

import com.tadhkirati.validator.ui.login.UserLoginSharedPreferences;

public class TokenUtils {

    public static String generateTokenHeader(String accessToken) {
        return "Bearer " + accessToken;

    }
}
