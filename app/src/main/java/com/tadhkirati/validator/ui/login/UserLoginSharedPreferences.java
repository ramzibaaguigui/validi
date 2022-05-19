package com.tadhkirati.validator.ui.login;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.tadhkirati.validator.models.User;

public class UserLoginSharedPreferences {
    private static final String ACCESS_TOKEN_KEY = "ACCESS_TOKEN";
    private static final String USER_LOGIN_SHARED_PREFERENCES_KEY = "USER_LOGIN_SHARED_PREFERENCES";
    private static final String LOGGED_USER_KEY = "LOGGED_USER";

    public static boolean storeAccessToken(Context context, String token) {
        return sharedPreferences(context).edit()
                .putString(ACCESS_TOKEN_KEY, token)
                .commit();
    }

    public static String retrieveStoredAccessToken(Context context) {
        return sharedPreferences(context).getString(ACCESS_TOKEN_KEY, null);
    }

    public static boolean storeLoggedUser(Context context, User user) {
        if (user == null)
            return false;

        String userSerialized = new Gson().toJson(user);
        sharedPreferences(context).edit()
                .putString(LOGGED_USER_KEY, userSerialized)
                .commit();
        return true;
    }

    public static User retrieveLoggedUser(Context context) {
        String userSerialized = sharedPreferences(context)
                .getString(LOGGED_USER_KEY, null);
        if (userSerialized == null)
            return null;
        return new Gson().fromJson(userSerialized, User.class);
    }

    private static SharedPreferences sharedPreferences(Context context) {
        return context.getSharedPreferences(USER_LOGIN_SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
    }
}
