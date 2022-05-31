package com.tadhkirati.validator.ui.traveldetails;

import android.app.Application;
import android.util.Log;

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
    public static final int STATE_VALIDATION_PROGRESS = 1;

    public static final int STATE_LOADED_SUCCESSFULLY = 2;
    public static final int STATE_VALIDATION_SUCCESS = 2;

    public static final int STATE_LOADING_ERROR = 3;
    public static final int STATE_VALIDATION_ERROR = 3;

    public static final int STATE_CONNECTIVITY_ERROR = 4;

    private final MutableLiveData<List<Ticket>> loadedTickets = new MutableLiveData<>();
    private final MutableLiveData<Integer> ticketLoadingState = new MutableLiveData<>(STATE_INITIAL);
    private final MutableLiveData<Integer> ticketValidationState = new MutableLiveData<>(STATE_INITIAL);
    private final MutableLiveData<Ticket> lastValidatedTicket = new MutableLiveData<>(null);

    public TravelDetailsViewModel(@NonNull Application application) {
        super(application);
    }

    public void observeState(LifecycleOwner owner, Observer<Integer> observer) {
        this.ticketLoadingState.observe(owner, observer);
    }

    public List<Ticket> getTickets() {
        return this.loadedTickets.getValue();
    }

    public void loadTickets(Long travelId, String accessToken) {
        ticketLoadingState.setValue(STATE_LOADING_PROGRESS);

        TicketApiRepository.getTravelTickets(travelId, accessToken, new ResponseHandler<List<Ticket>>() {
            @Override
            public void handleSuccess(ApiResponse<List<Ticket>> response) {
                Log.i("RESPONSE_TICKETS", String.valueOf(response));
                if (response == null) {
                    ticketLoadingState.setValue(STATE_CONNECTIVITY_ERROR);
                    return;
                }
                if (response.isSuccessful()) {
                    loadedTickets.setValue(response.getData());
                    ticketLoadingState.setValue(STATE_LOADED_SUCCESSFULLY);
                    return;
                }

                ticketLoadingState.setValue(STATE_LOADING_ERROR);
            }

            @Override
            public void handleError() {

                Log.i("ERROR_HERE", "error here");
                ticketLoadingState.setValue(STATE_CONNECTIVITY_ERROR);
            }
        });
    }

    public void validateTicket(String accessToken, String qrCode, Long travelId) {
        ticketValidationState.setValue(STATE_VALIDATION_PROGRESS);

        TicketApiRepository.validateTicket2(accessToken, travelId, qrCode,
                new ResponseHandler<Ticket>() {
                    @Override
                    public void handleSuccess(ApiResponse<Ticket> response) {
                        if (response == null) {
                            ticketValidationState.setValue(STATE_CONNECTIVITY_ERROR);
                            return;
                        }

                        if (response.isSuccessful()) {

                            ticketValidationState.setValue(STATE_VALIDATION_SUCCESS);
                            return;
                        }

                        ticketValidationState.setValue(STATE_LOADING_ERROR);
                    }

                    @Override
                    public void handleError() {
                        ticketValidationState.setValue(STATE_CONNECTIVITY_ERROR);
                    }
                });
    }

    public Ticket getLastValidatedTicket() {
        return this.lastValidatedTicket.getValue();
    }

    public void freeLastValidatedTicket() {
        this.ticketValidationState.setValue(STATE_INITIAL);
        this.lastValidatedTicket.setValue(null);
    }

    public void observeTicketValidationState(LifecycleOwner owner, Observer<Integer> observer) {
        this.ticketValidationState.observe(owner, observer);
    }

}
