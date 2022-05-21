package com.tadhkirati.validator.ui.validator;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.tadhkirati.validator.api.payload.ApiResponse;
import com.tadhkirati.validator.api.retrofit.ResponseHandler;
import com.tadhkirati.validator.api.retrofit.TravelApiRepository;
import com.tadhkirati.validator.models.Travel;

import java.util.List;

public class TravelsViewModel extends AndroidViewModel {
    public TravelsViewModel(@NonNull Application application) {
        super(application);
    }

    public static final int STATE_INITIAL = 0;
    public static final int STATE_LOADING_TRAVELS = 1;
    public static final int STATE_LOADED_SUCCESSFULLY = 2;
    public static final int STATE_LOADING_ERROR = 3;
    public static final int STATE_LOADING_CONNECTIVITY_ERROR = 4;

    private MutableLiveData<List<Travel>> travels = new MutableLiveData<>();
    private MutableLiveData<Integer> currentState = new MutableLiveData<>(STATE_INITIAL);


    public void observeState(LifecycleOwner owner, Observer<Integer> observer) {
        currentState.observe(owner, observer);
    }

    public int getCurrentState() {
        return currentState.getValue();
    }

    public void loadTravels(String accessToken) {
        TravelApiRepository.loadTravels(
                accessToken,
                new ResponseHandler<>() {
                    @Override
                    public void handleSuccess(ApiResponse<List<Travel>> response) {
                        if (response == null) {
                            handleConnectivityError();
                            return;
                        }
                        if (response.isSuccessful()) {
                            handleTravelsLoadedSuccessfully(response.getData());
                            return;
                        }
                        handleTravelsLoadingError();
                    }

                    @Override
                    public void handleError() {
                        handleConnectivityError();
                    }
                }
        );
    }

    public void handleTravelsLoadedSuccessfully(List<Travel> travels) {
        this.travels.setValue(travels);
        currentState.setValue(STATE_LOADED_SUCCESSFULLY);
    }

    private void handleTravelsLoadingError() {
        currentState.setValue(STATE_LOADING_ERROR);
    }

    private void handleConnectivityError() {
        currentState.setValue(STATE_LOADING_CONNECTIVITY_ERROR);
    }
}
