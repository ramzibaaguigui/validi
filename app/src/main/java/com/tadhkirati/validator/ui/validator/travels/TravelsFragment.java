package com.tadhkirati.validator.ui.validator.travels;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tadhkirati.validator.R;
import com.tadhkirati.validator.ui.traveldetails.TravelDetailsActivity;
import com.tadhkirati.validator.models.Travel;
import com.tadhkirati.validator.ui.login.UserLoginSharedPreferences;
import com.tadhkirati.validator.ui.validator.ValidatorActivity;

public class TravelsFragment extends Fragment {
    public static final String LOADED_TRAVEL_KEY = "LOADED_TRAVEL";
    private TravelsViewModel travelsViewModel;

    private TextView travelDateTextView;
    private TextView travelCountTextView;
    private RecyclerView travelsRecyclerView;
    private TravelsRecyclerViewAdapter travelsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_travels, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initViewModel();
    }

    private void initViewModel() {
        if (getActivity() == null)
            return;
        travelsViewModel = ((ValidatorActivity) getActivity()).getTravelsViewModel();
    }
    private void initViews(View view) {
        travelDateTextView = view.findViewById(R.id.text_view_travel_date);
        travelCountTextView = view.findViewById(R.id.text_view_travel_count);
        travelsRecyclerView = view.findViewById(R.id.recycler_view_travels);

    }

    private void initRecyclerView() {
        travelsAdapter = new TravelsRecyclerViewAdapter(TravelDummyData.getTravels());
        travelsRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        travelsAdapter.setOnTravelActionListener(this::displayTravelDetailsBottomSheetFragment);
        travelsRecyclerView.setAdapter(travelsAdapter);

    }



    private void displayTravelDetailsBottomSheetFragment(Travel travel) {
        var fragment = TravelDetailsBottomSheetDialogFragment
                .createInstance(travel);
        fragment.setOnTravelActionClickListener(this::loadTravelTickets);

    }

    private void loadTravelTickets(Travel travel) {
        Intent intent = new Intent(requireActivity(), TravelDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(LOADED_TRAVEL_KEY, travel);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void observeTravelsLoadingState() {
        travelsViewModel.observeState(this, state -> {
            // handle the states one by one here

        });
    }

    private void loadTravels() {
        travelsViewModel.loadTravels(UserLoginSharedPreferences.retrieveStoredAccessToken(requireActivity()));
    }

    private void handleVisibilityLoading() {
        /*
        TODO: show a progress bar indicating that there is a work running
        TODO: running in the background
         */
    }

    private void handleVisibilityLoadedSuccess() {
        // TODO: show the views containing the data loaded from the server
    }

    private void handleVisibilityLoadingError() {
        // TODO: show a view indicating that there has been an error
        // alongside a retry button to reload the travels if possible

    }

}
