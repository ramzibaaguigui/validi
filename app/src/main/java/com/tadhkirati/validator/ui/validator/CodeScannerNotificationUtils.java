package com.tadhkirati.validator.ui.validator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tadhkirati.validator.R;

public class CodeScannerNotificationUtils {
    public static void displayValidatedSuccessfully(Context context, ViewGroup parent) {
        constructTicketValidationToast(context, false, parent)
                .show();
    }

    public static void displayValidationError(Context context, ViewGroup parent) {
        constructTicketValidationToast(context, false, parent)
                .show();
    }

    public static void displayValidationTicketExpired(Context context) {

    }

    public static void displayValidationTicketNonExisting(Context context) {

    }


    public static Toast constructTicketValidationToast(Context context, boolean error, ViewGroup parent) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.view_validation_toast, parent, false);

        TextView messageTextView = view.findViewById(R.id.text_view_validation_toast);
        if (error)
            messageTextView.setText(R.string.string_update_user_error);
        Toast toast = new Toast(context);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        return toast;
    }
}
