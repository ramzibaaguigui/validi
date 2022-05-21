package com.tadhkirati.validator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

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

    public static TravelDetailsBottomSheetDialogFragment createInstance() {
        return new TravelDetailsBottomSheetDialogFragment();
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

    }

    private void initViews(View view) {

    }

    public void setOnTravelActionClickListener(OnTravelActionClickListener listener) {
        this.listener = listener;
    }

    interface OnTravelActionClickListener {
        // TODO: add here the action methods that are goint obe available
        // for the user
    }
}
