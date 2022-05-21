package com.tadhkirati.validator.ui.validator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.tadhkirati.validator.R;
import com.tadhkirati.validator.TravelDetailsBottomSheetDialogFragment;
import com.tadhkirati.validator.TravelsRecyclerViewAdapter;
import com.tadhkirati.validator.models.Travel;

import java.util.List;

public class TravelsFragment extends Fragment {

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

    }

    private void displayTravelDetailsBottomSheetFragment(Travel travel) {
        TravelDetailsBottomSheetDialogFragment fragment = new TravelDetailsBottomSheetDialogFragment();
    }

    private void observeTravelsLoadingState() {
        travelsViewModel.observeState(this, state -> {

        });
    }

}
