package com.tadhkirati.validator.ui.validator.profile.utils;

import android.content.Context;

import com.tadhkirati.validator.R;

public class PasswordValidationUtils {

    private static final int MIN_LENGTH_PASSWORD = 8;

    public static String validatePassword(Context context, String password) {
        if (!isWithinPasswordLengthRange(password)) {
            return context.getResources().getString(R.string.password_length_range_string);
        }

        return null;
    }

    public static String validateNewPasswordConfirm(Context context,
                                                    String newPassword,
                                                    String newPasswordConfirm) {
        if (newPassword.equals(newPasswordConfirm)) {
            return null;
        }

        return context.getResources().getString(R.string.new_password_mismatch_string);
    }

    private static boolean isWithinPasswordLengthRange(String password) {
        return password.length() >= MIN_LENGTH_PASSWORD;
    }


}
