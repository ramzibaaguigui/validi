package com.tadhkirati.validator.ui.login;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.tadhkirati.validator.models.User;

public class LoginUtils {
    private static final String LOGGED_USER_KEY = "LOGGED_USER";
    private static final int MIN_PASSWORD_LENGTH = 8;

    private static final String BEARER = "Bearer ";
    private static boolean phoneIsValid(String phoneNumber) {
        return true;
    }

    private static boolean passwordIsValid(String password) {
        return password.length() >= MIN_PASSWORD_LENGTH;
    }

    public static boolean loginCredentialsAreValid(String phoneNumber, String password) {
        return phoneIsValid(phoneNumber) && passwordIsValid(password);
    }

    public static Bundle bundleLoggedUser(User user) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(LOGGED_USER_KEY, user);
        return bundle;
    }

    public static User extractLoggedUser(Bundle bundle) {
        User user = (User) bundle.getSerializable(LOGGED_USER_KEY);
        if (user == null) {
            Log.d("LOGGED_USER", "the extracted user is null");
        }
        return user;

    }

    public static String formTokenHeader(Context context) {
        return BEARER.concat(UserLoginSharedPreferences.retrieveStoredAccessToken(context));
    }
}
