package com.tadhkirati.validator.ui.validator.profile.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.tadhkirati.validator.R;

public class UserProfileUpdateUtils {

    public static void handleUserUpdatedSuccessfully(Context context) {
        Toast toast = new Toast(context);
        View view = LayoutInflater.from(context)
                .inflate(R.layout.view_toast_success, (ViewGroup) toast.getView(), false);

        TextView messageTextView = view.findViewById(R.id.text_view_toast_success);
        messageTextView.setText(generateSuccessString(context));
        toast.setView(view);
        toast.show();
    }

    private static String generateSuccessString(Context context) {
        return context.getResources().getString(R.string.user_profile_updated_successfully_string);
    }

    public static void handleUserUpdatedError(Context context) {
        Toast toast = new Toast(context);
        View view = LayoutInflater.from(context)
                .inflate(R.layout.view_toast_error, (ViewGroup) toast.getView(), false);
        TextView messageTextView = view.findViewById(R.id.text_view_toast_error);
        messageTextView.setText(generateErrorString(context));
        toast.setView(view);
        toast.show();
    }

    private static String generateErrorString(Context context) {
        return context.getResources().getString(R.string.user_profile_update_error);
    }

    public static void handleUserProfileInvalid(Context context) {
        Toast toast = new Toast(context);

        View view = LayoutInflater.from(context)
                .inflate(R.layout.view_toast_error, (ViewGroup) toast.getView(), false);

        TextView messageTextView = view.findViewById(R.id.text_view_toast_error);
        messageTextView.setText(R.string.verify_entered_profile_information_string);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }
}
