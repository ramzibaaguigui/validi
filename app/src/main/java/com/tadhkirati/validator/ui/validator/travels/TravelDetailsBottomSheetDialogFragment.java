package com.tadhkirati.validator.ui.validator.travels;

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
import com.tadhkirati.validator.models.Travel;

public class TravelDetailsBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private TextView travelNameTextView;
    private TextView travelStatusTextView;
    private TextView departureStationTextView;
    private TextView arrivalStationTextView;
    private TextView departureTimeTextView;
    private TextView arrivalTimeTextView;
    private TextView durationTextView;
    private TextView distanceTextView;
    private TextView firstClassLimitTextView;
    private TextView secondClassLimitTextView;
    private TextView wifiAvailableTextView;
    private TextView hasRestaurantsTextView;


    private Button loadTicketsButton;

    private OnTravelActionClickListener listener;

    private Travel travel;

    public static TravelDetailsBottomSheetDialogFragment createInstance(Travel travel) {
        return new TravelDetailsBottomSheetDialogFragment(travel);
    }

    private TravelDetailsBottomSheetDialogFragment(Travel travel) {
        this.travel = travel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dialog_bottom_travel_details, container, false);
        initViews(root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        displayTravel();
    }

    public void displayTravel() {
        departureStationTextView.setText(travel.getDepartureStation());
        arrivalStationTextView.setText(travel.getArrivalStation());

        loadTicketsButton.setOnClickListener(view -> {
            if (listener == null)
                return;
            listener.loadTravel(travel);
        });
    }

    private void initViews(View view) {
        // TODO: note that this is not complete
        // TODO: there are still a lot of views that are left undefined
        travelNameTextView = view.findViewById(R.id.text_view_travel_information);
        departureStationTextView = view.findViewById(R.id.text_view_travel_departure_station_value);
        arrivalStationTextView = view.findViewById(R.id.text_view_travel_arrival_station_value);
        loadTicketsButton = view.findViewById(R.id.button_load_travel_tickets);
    }

    public void setOnTravelActionClickListener(OnTravelActionClickListener listener) {
        this.listener = listener;
    }

    public interface OnTravelActionClickListener {
        // TODO: add here the action methods that are goint obe available
        // for the user
        void loadTravel(Travel travel);
    }
}
