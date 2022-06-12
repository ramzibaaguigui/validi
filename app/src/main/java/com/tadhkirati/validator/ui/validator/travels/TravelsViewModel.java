package com.tadhkirati.validator.ui.validator.travels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.google.gson.Gson;
import com.tadhkirati.validator.api.payload.ApiResponse;
import com.tadhkirati.validator.api.retrofit.ResponseHandler;
import com.tadhkirati.validator.api.retrofit.TravelApiRepository;
import com.tadhkirati.validator.models.Travel;

import java.util.List;

public class TravelsViewModel extends AndroidViewModel {
    public static final int STATE_INITIAL = 0;
    public static final int STATE_LOADING_TRAVELS = 1;
    public static final int STATE_LOADED_SUCCESSFULLY = 2;
    public static final int STATE_LOADING_ERROR = 3;
    public static final int STATE_LOADING_CONNECTIVITY_ERROR = 4;
    private MutableLiveData<List<Travel>> travels = new MutableLiveData<>();
    private MutableLiveData<Integer> currentState = new MutableLiveData<>(STATE_INITIAL);

    public TravelsViewModel(@NonNull Application application) {
        super(application);
    }

    public void observeTravelLoadingState(LifecycleOwner owner, Observer<Integer> observer) {
        currentState.observe(owner, observer);
    }

    public int getCurrentState() {
        return currentState.getValue();
    }

    public void loadTravels(String accessToken) {

        currentState.setValue(STATE_LOADING_TRAVELS);

        TravelApiRepository.loadTodayTravels(
                accessToken,
                new ResponseHandler<>() {
                    @Override
                    public void handleSuccess(ApiResponse<List<Travel>> response) {
                        Log.i("API_RESPONSE", String.valueOf(response == null ? null : response.isSuccessful()));
                        Log.i("response", new Gson().toJson(response));

                        if (response == null) {
                            currentState.setValue(STATE_LOADING_CONNECTIVITY_ERROR);
                            return;
                        }

                        if (response.isSuccessful()) {
                            travels.setValue(response.getData());
                            currentState.setValue(STATE_LOADED_SUCCESSFULLY);
                            return;
                        }

                        currentState.setValue(STATE_LOADING_ERROR);
                    }

                    @Override
                    public void handleError() {
                        currentState.setValue(STATE_LOADING_CONNECTIVITY_ERROR);
                    }
                }
        );
    }

    public List<Travel> getLoadedTravels() {
        return this.travels.getValue();
    }


}
