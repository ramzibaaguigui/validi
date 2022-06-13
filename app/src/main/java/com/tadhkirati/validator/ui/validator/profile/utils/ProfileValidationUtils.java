package com.tadhkirati.validator.ui.validator.profile.utils;

import android.content.Context;

import com.tadhkirati.validator.R;

public class ProfileValidationUtils {
    private static final int MIN_NAME_LENGTH = 4;
    private static final int MAX_NAME_LENGTH = 10;
    private static final String CONTAINS_NUMERICAL_CHARACTERS_REGEX = "\\d";
    private static final String CONTAINS_ALPHABETIC_CHARACTERS_REGEX = "";


    public static String validateFirstName(Context context, String firstName) {
        if (firstName.isEmpty()) {
            return context.getResources().getString(R.string.first_name_required_string);
        }

        if (!isWithinFirstLastNameLengthRange(firstName)) {
            return  context.getResources().getString(R.string.first_name_length_range_string);
        }

        if (containsNumericCharacters(firstName)) {
            return context.getResources().getString(R.string.first_name_alphabetic_only_string);
        }

        return null;
    }

    public static String validateLastName(Context context, String lastName) {
        if (lastName.isEmpty()) {
            return context.getResources().getString(R.string.last_name_required_string);
        }

        if (!isWithinFirstLastNameLengthRange(lastName)) {
            return context.getResources().getString(R.string.last_name_length_range_string);
        }

        if (containsNumericCharacters(lastName)) {
            return context.getResources().getString(R.string.last_name_alphabetic_only_string);
        }

        return null;
    }

    public static String validatePhoneNumber(Context context, String phoneNumber) {
        if (phoneNumber.isEmpty()) {
            return context.getResources().getString(R.string.phone_number_required_string);
        }

        if (containsAlphabeticCharacters(phoneNumber)) {
            return context.getResources().getString(R.string.phone_number_numeric_only_string);
        }
        return null;
    }

    // additional

    private static boolean containsNumericCharacters(String s) {
        return s.matches(CONTAINS_NUMERICAL_CHARACTERS_REGEX);
    }

    private static boolean containsAlphabeticCharacters(String s) {
        return s.matches(CONTAINS_ALPHABETIC_CHARACTERS_REGEX);
    }
    private static boolean isWithinFirstLastNameLengthRange(String s) {
        return s.length() >= MIN_NAME_LENGTH &&
                s.length() <= MAX_NAME_LENGTH;
    }



}
