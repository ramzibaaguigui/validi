package com.tadhkirati.validator.ui.validator.travels;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tadhkirati.validator.R;
import com.tadhkirati.validator.models.Travel;
import com.tadhkirati.validator.ui.login.UserLoginSharedPreferences;
import com.tadhkirati.validator.ui.traveldetails.TravelDetailsActivity;
import com.tadhkirati.validator.ui.validator.ValidatorActivity;
import com.tadhkirati.validator.ui.validator.travels.utils.TokenUtils;

public class TravelsFragment extends Fragment {
    public static final String LOADED_TRAVEL_KEY = "LOADED_TRAVEL";
    public static final String BOTTOM_SHEET_TRAVEL_DETAILS_TAG = "BOTTOM_SHEET_TRAVEL_DETAILS";

    private TravelsViewModel travelsViewModel;


    private TextView travelDateTextView;
    private TextView travelCountTextView;
    private RecyclerView travelsRecyclerView;
    private TravelsRecyclerViewAdapter travelsAdapter;
    private ProgressBar progressBar;
    private View travelsContainerCardView;
    private Button retryButton;

    private View loadingErrorView;
    private View connectivityErrorView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_travels, container, false);
        initViews(view);
        initListeners();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initViewModel();
        observeTravelsLoadingState();
        loadTravels();
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
        progressBar = view.findViewById(R.id.progress_bar);
        travelsContainerCardView = view.findViewById(R.id.card_view_travels_container);

        loadingErrorView = view.findViewById(R.id.container_loading_error);
        connectivityErrorView = view.findViewById(R.id.container_connectivity_error);
        retryButton = view.findViewById(R.id.button_retry);
    }


    private void initListeners() {
        retryButton.setOnClickListener(view -> loadTravels());
    }
    private void initRecyclerView() {
        travelsAdapter = new TravelsRecyclerViewAdapter(travelsViewModel.getLoadedTravels());
        travelsRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        travelsAdapter.setOnTravelActionListener(this::displayTravelDetailsBottomSheetFragment);
        travelsRecyclerView.setAdapter(travelsAdapter);
        Log.i("RECYCLER_VIEW_TRAVELS", travelsViewModel.getLoadedTravels().toString());
    }

    private void initTravelCountView() {
        int travelCount = travelsViewModel.getLoadedTravels().size();
        travelCountTextView.setText(String.valueOf(travelCount));
    }


    private void displayTravelDetailsBottomSheetFragment(Travel travel) {
        var fragment = TravelDetailsBottomSheetDialogFragment
                .createInstance(travel);
        // todo: we still need to set the background of the fragment to transparent
        fragment.setOnTravelActionListener(this::loadTravelTickets);
        fragment.show(requireActivity().getSupportFragmentManager(), BOTTOM_SHEET_TRAVEL_DETAILS_TAG);

    }

    private void loadTravelTickets(Travel travel) {
        Intent intent = new Intent(requireActivity(), TravelDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong("LOADED_TRAVEL_ID", travel.getId());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void observeTravelsLoadingState() {
        travelsViewModel.observeTravelLoadingState(this, state -> {
            // handle the states one by one here
            if (state == TravelsViewModel.STATE_LOADED_SUCCESSFULLY) {

                handleTravelsLoadedSuccess();
                return;
            }

            if (state == TravelsViewModel.STATE_LOADING_ERROR) {
                handleTravelsLoadingError();
                return;
            }

            if (state == TravelsViewModel.STATE_LOADING_CONNECTIVITY_ERROR) {
                handleTravelsLoadingConnectivityError();
                return;
            }

            if (state == TravelsViewModel.STATE_LOADING_TRAVELS) {
                handleTravelsLoading();
                return;
            }

            if (state == TravelsViewModel.STATE_INITIAL) {
                loadTravels();
            }

        });
    }

    private void handleTravelsLoading() {
        travelsContainerCardView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        connectivityErrorView.setVisibility(View.GONE);
        loadingErrorView.setVisibility(View.GONE);
    }

    private void handleTravelsLoadingConnectivityError() {
        showToast("connectivity error");
        travelsContainerCardView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        connectivityErrorView.setVisibility(View.VISIBLE);
        loadingErrorView.setVisibility(View.GONE);
    }

    private void handleTravelsLoadingError() {
        showToast("loading error");
        travelsContainerCardView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        connectivityErrorView.setVisibility(View.GONE);
        loadingErrorView.setVisibility(View.VISIBLE);
    }

    private void handleTravelsLoadedSuccess() {
        // hide all the errors views and show the recycler view
        showToast("loaded with success");
        progressBar.setVisibility(View.GONE);
        connectivityErrorView.setVisibility(View.GONE);
        loadingErrorView.setVisibility(View.GONE);
        travelsContainerCardView.setVisibility(View.VISIBLE);
        initRecyclerView();
        initTravelCountView();
    }


    private void loadTravels() {
        String token = TokenUtils.generateTokenHeader(
                UserLoginSharedPreferences.retrieveStoredAccessToken(requireActivity())
        );
        travelsViewModel.loadTravels(token);
    }

    private void showToast(String message) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
    }

}
