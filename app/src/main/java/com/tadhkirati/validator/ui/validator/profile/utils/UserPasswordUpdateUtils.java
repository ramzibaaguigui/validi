package com.tadhkirati.validator.ui.validator.profile.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tadhkirati.validator.R;

public class UserPasswordUpdateUtils {

        public static void handlePasswordUpdateSuccess(Context context) {
            Toast toast = new Toast(context);
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.view_toast_success, (ViewGroup) toast.getView(), false);
            TextView message = view.findViewById(R.id.text_view_toast_success);
            message.setText(generateSuccessText(context));
            toast.setView(view);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
        }

        private static String generateSuccessText(Context context) {
            return context.getResources().getString(R.string.password_updated_successfully_string);
        }

        public static void handlePasswordUpdateError(Context context) {
            Toast toast = new Toast(context);

            View errorView = LayoutInflater.from(context)
                    .inflate(R.layout.view_toast_error, (ViewGroup) toast.getView(), false);
            TextView messageTextView = errorView.findViewById(R.id.text_view_toast_error);
            messageTextView.setText(generateErrorText(context));
        }

        public static void handlePasswordInvalid(Context context) {
            Toast toast = new Toast(context);

            View view = LayoutInflater.from(context)
                    .inflate(R.layout.view_toast_error, (ViewGroup) toast.getView(), false);
            TextView messageTextView = view.findViewById(R.id.text_view_toast_error);
            messageTextView.setText(R.string.update_password_invalid_string);
            toast.setView(view);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
        }

        private static String generateErrorText(Context context) {
            return context.getResources().getString(R.string.password_update_failure_string);
        }
}
