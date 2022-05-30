package com.tadhkirati.validator.ui.validator.travels;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.tadhkirati.validator.R;
import com.tadhkirati.validator.models.Travel;
import com.tadhkirati.validator.models.TravelStatus;
import com.tadhkirati.validator.ui.traveldetails.TravelStationsRecyclerViewAdapter;

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

    private RecyclerView stationsRecyclerView;
    private TravelStationsRecyclerViewAdapter stationsAdapter;

    private Button loadTicketsButton;

    private OnTravelActionClickListener listener;

    private Travel travel;

    private TravelDetailsBottomSheetDialogFragment(Travel travel) {
        this.travel = travel;
    }

    public static TravelDetailsBottomSheetDialogFragment createInstance(Travel travel) {
        return new TravelDetailsBottomSheetDialogFragment(travel);
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
//        makeBackgroundTransparent();
        displayTravel();
        initStationsRecyclerView();
        initTravelActionListener();
    }

    private void initTravelActionListener() {
        loadTicketsButton.setOnClickListener(view -> {
            if (listener == null)
                return;
            listener.onTravelClick(travel);
        });
    }



    private void initViews(View view) {
        travelNameTextView = view.findViewById(R.id.text_view_travel_information);
        travelStatusTextView = view.findViewById(R.id.text_view_travel_status_value);
        departureTimeTextView = view.findViewById(R.id.text_view_travel_departure_time_value);
        arrivalTimeTextView = view.findViewById(R.id.text_view_travel_arrival_time_value);
        durationTextView = view.findViewById(R.id.text_view_travel_duration_value);
        distanceTextView = view.findViewById(R.id.text_view_travel_distance_value);
        firstClassLimitTextView = view.findViewById(R.id.text_view_first_class_place_limit_value);
        secondClassLimitTextView = view.findViewById(R.id.text_view_second_class_place_limit_value);
        departureStationTextView = view.findViewById(R.id.text_view_travel_departure_station_value);
        arrivalStationTextView = view.findViewById(R.id.text_view_travel_arrival_station_value);
        loadTicketsButton = view.findViewById(R.id.button_load_travel_tickets);
        stationsRecyclerView = view.findViewById(R.id.recycler_view_stations);
    }

    private void displayTravel() {
//        travelStatusTextView.setText(TravelStatus.getStringForStatus(requireContext(), travel.status()));
        travelStatusTextView.setText(travel.status());

        departureStationTextView.setText(travel.getDepartureStationName());
        arrivalStationTextView.setText(travel.getArrivalStationName());
        departureTimeTextView.setText(travel.getDepartureTime().toString());
        arrivalTimeTextView.setText(travel.getArrivalTime().toString());
        durationTextView.setText(String.valueOf(travel.getDuration()));
        distanceTextView.setText(String.valueOf(travel.getDistance()));
        firstClassLimitTextView.setText(String.valueOf(travel.getFirstClassLimitPlaces()));
        secondClassLimitTextView.setText(String.valueOf(travel.getSecondClassLimitPlaces()));
    }

    private void initStationsRecyclerView() {
        stationsAdapter = TravelStationsRecyclerViewAdapter.create(travel.getStations());
        stationsRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        stationsRecyclerView.setNestedScrollingEnabled(true);
        stationsRecyclerView.setAdapter(stationsAdapter);
    }

    public void setOnTravelActionListener(OnTravelActionClickListener listener) {
        this.listener = listener;
    }

    public interface OnTravelActionClickListener {

        void onTravelClick(Travel travel);
    }

}
