package com.tadhkirati.validator.ui.validator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tadhkirati.validator.R;
import com.tadhkirati.validator.TravelDetailsBottomSheetDialogFragment;
import com.tadhkirati.validator.models.Travel;

public class TravelsFragment extends Fragment {

    private TravelsViewModel travelsViewModel;
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

    }

    private void initRecyclerView() {

    }

    private void displayTravelDetailsBottomSheetFragment(Travel travel) {
        TravelDetailsBottomSheetDialogFragment fragment = new TravelDetailsBottomSheetDialogFragment();
    }
}
