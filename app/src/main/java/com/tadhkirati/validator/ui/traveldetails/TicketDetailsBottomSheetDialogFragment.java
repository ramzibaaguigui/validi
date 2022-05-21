package com.tadhkirati.validator.ui.traveldetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.tadhkirati.validator.R;

public class TicketDetailsBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private View draggableView;
    private TextView ticketInformationTextView;
    private TextView passengerNameTextView;
    private TextView boardingStationTextView;
    private TextView landingStationTextView;
    private Button validateButton;

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
    }

    private void initViews(View view) {
        draggableView = view.findViewById(R.id.view_draggable);
        ticketInformationTextView = view.findViewById(R.id.text_view_travel_information);
        passengerNameTextView = view.findViewById(R.id.text_view_travel_status_value);
        boardingStationTextView = view.findViewById(R.id.text_view_travel_departure_station_value);
        landingStationTextView = view.findViewById(R.id.text_view_travel_arrival_station_value);
        validateButton = view.findViewById(R.id.button_load_travel_tickets);
    }

    public interface OnValidateClickListener {
        void onValidate();
    }
}
