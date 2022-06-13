package com.tadhkirati.validator.ui.traveldetails.validation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tadhkirati.validator.R;
import com.tadhkirati.validator.models.Ticket;

public class ValidationUtils {

    public static void handleTicketValidationError(Context context) {
        Toast toast = new Toast(context);
        toast.setView(LayoutInflater.from(context)
                .inflate(R.layout.view_validation_error_toast, (ViewGroup) toast.getView(), false));
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public static void handleTicketValidationSuccess(Context context, Ticket ticket) {
        Toast toast = new Toast(context);

        View view = LayoutInflater.from(context)
                .inflate(R.layout.view_validation_success_toast, (ViewGroup) toast.getView(), false);
        TextView detailsTextView = view.findViewById(R.id.text_view_ticket_details);
        detailsTextView.setText(formValidationDetailsForTicket(ticket, context));
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    private static String formValidationDetailsForTicket(Ticket ticket, Context context) {
        String ticketValidatedSuccessfullyFor = context.getResources().getString(
                R.string.ticket_validated_for_string
        );
        return ticketValidatedSuccessfullyFor + ticket.getPassengerName();
    }

}
