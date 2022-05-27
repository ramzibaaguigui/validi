package com.tadhkirati.validator.ui.traveldetails;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.tadhkirati.validator.api.payload.ApiResponse;
import com.tadhkirati.validator.api.retrofit.ResponseHandler;
import com.tadhkirati.validator.api.retrofit.TicketApiRepository;
import com.tadhkirati.validator.models.Ticket;

import java.util.List;

public class TravelDetailsViewModel extends AndroidViewModel {

    public static final int STATE_INITIAL = 0;
    public static final int STATE_LOADING_PROGRESS = 1;
    public static final int STATE_LOADED_SUCCESSFULLY = 2;
    public static final int STATE_LOADING_ERROR = 3;
    public static final int STATE_CONNECTIVITY_ERROR = 4;


    public TravelDetailsViewModel(@NonNull Application application) {
        super(application);
    }

    private final MutableLiveData<List<Ticket>> loadedTickets = new MutableLiveData<>();
    private final MutableLiveData<Integer> currentState = new MutableLiveData<>();

    public void observeState(LifecycleOwner owner, Observer<Integer> observer) {
        this.currentState.observe(owner, observer);
    }

    public List<Ticket> getTickets() {
        return this.loadedTickets.getValue();
    }

    public void loadTickets(Long travelId, String accessToken) {
        TicketApiRepository.getTravelTickets(travelId, accessToken, new ResponseHandler<List<Ticket>>() {
            @Override
            public void handleSuccess(ApiResponse<List<Ticket>> response) {
                if (response == null) {
                    currentState.setValue(STATE_CONNECTIVITY_ERROR);
                    return;
                }
                if (response.isSuccessful()) {
                    loadedTickets.setValue(response.getData());
                    currentState.setValue(STATE_LOADED_SUCCESSFULLY);
                    return;
                }

                currentState.setValue(STATE_LOADING_ERROR);
            }

            @Override
            public void handleError() {
                currentState.setValue(STATE_CONNECTIVITY_ERROR);
            }
        });
    }

}
