package com.tadhkirati.validator.ui.traveldetails;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.tadhkirati.validator.R;
import com.tadhkirati.validator.models.Ticket;

public class TicketDetailsBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private View draggableView;
    private TextView ticketInformationTextView;
    private TextView passengerNameTextView;
    private TextView boardingStationTextView;
    private TextView landingStationTextView;
    private TextView paymentMethodTextView;
    private TextView travelClassTextView;
    private TextView ticketIsValidatedTextView;
    private TextView priceTextView;

    private Button validateButton;

    private Ticket currentTicket;
    private int ticketPosition;
    private OnValidateTicketListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dialog_bottom_ticket_details, container, false);
        initViews(root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        displayTicket();
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new BottomSheetDialog(getContext(), R.style.BottomSheetDialogTheme);
    }

    private void displayTicket() {
        passengerNameTextView.setText(currentTicket.getPassengerName());

        landingStationTextView.setText(currentTicket.getLandingStationName());
        landingStationTextView.setSelected(true);

        boardingStationTextView.setSelected(true);
        boardingStationTextView.setText(currentTicket.getBoardingStationName());
        paymentMethodTextView.setText(currentTicket.paymentMethod());
        travelClassTextView.setText(currentTicket.travelClass());
        ticketIsValidatedTextView.setText(String.valueOf(currentTicket.isValidated()));
        priceTextView.setText(String.valueOf(currentTicket.getPrice()));

        if (currentTicket.isValidated())
            validateButton.setVisibility(View.GONE);

        validateButton.setOnClickListener(view -> {
            if (listener == null)
                return;
            listener.onValidate(currentTicket, ticketPosition);
        });
    }


    private void initViews(View view) {
        draggableView = view.findViewById(R.id.view_draggable);
        ticketInformationTextView = view.findViewById(R.id.text_view_ticket_information);
        passengerNameTextView = view.findViewById(R.id.text_view_passenger_name_value);
        boardingStationTextView = view.findViewById(R.id.text_view_travel_departure_station_value);
        landingStationTextView = view.findViewById(R.id.text_view_travel_arrival_station_value);
        validateButton = view.findViewById(R.id.button_load_travel_tickets);
        paymentMethodTextView = view.findViewById(R.id.text_view_ticket_payment_method_value);
        priceTextView = view.findViewById(R.id.text_view_ticket_price_value);
        ticketIsValidatedTextView = view.findViewById(R.id.text_view_ticket_is_validated_value);
        travelClassTextView = view.findViewById(R.id.text_view_ticket_travel_class_value);
    }

    public void setOnValidateTicketListener(OnValidateTicketListener listener) {
        this.listener = listener;
    }

    public void updateTicket(Ticket validatedTicket) {
        this.currentTicket = validatedTicket;
        displayTicket();
    }

    public interface OnValidateTicketListener {
        void onValidate(Ticket ticket, int position);
    }

    private TicketDetailsBottomSheetDialogFragment(Ticket ticket, int ticketPosition) {
        this.currentTicket = ticket;
        this.ticketPosition = ticketPosition;
    }

    public static TicketDetailsBottomSheetDialogFragment create(Ticket ticket, int position) {
        return new TicketDetailsBottomSheetDialogFragment(ticket, position);
    }

}
