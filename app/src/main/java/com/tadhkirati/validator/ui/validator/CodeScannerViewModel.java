package com.tadhkirati.validator.ui.validator;

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

public class CodeScannerViewModel extends AndroidViewModel {
    public static final int STATE_INITIAL = 0;
    public static final int STATE_VALIDATION_IN_PROGRESS = 1;
    public static final int STATE_VALIDATION_SUCCESS = 2;
    public static final int STATE_VALIDATION_ERROR = 3;
    public static final int STATE_CONNECTIVITY_ERROR = 4;

    public CodeScannerViewModel(@NonNull Application application) {
        super(application);
    }

    private final MutableLiveData<Integer> currentState = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isCameraPermissionAccepted = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> canScanCode = new MutableLiveData<>(true);
    private final MutableLiveData<String> ticketToken = new MutableLiveData<>();
    private final MutableLiveData<Ticket> validatedTicket = new MutableLiveData<>();

    public boolean getCanScanCode() {
        return canScanCode.getValue();
    }

    public Ticket getValidatedTicket() {
        return this.validatedTicket.getValue();
    }

    public void setCanScanCode(boolean canScan) {
        this.canScanCode.setValue(canScan);
    }

    public void observeState(LifecycleOwner owner, Observer<Integer> observer) {
        currentState.observe(owner, observer);
    }


    public void observeCameraPermissionState(LifecycleOwner owner, Observer<Boolean> observer) {
        isCameraPermissionAccepted.observe(owner, observer);
    }


    public boolean isCameraPermissionAccepted() {
        return Boolean.TRUE.equals(this.isCameraPermissionAccepted.getValue());
    }

    public void setCameraPermissionAccepted(boolean accepted) {
        this.isCameraPermissionAccepted.setValue(accepted);
    }

    public void validateTicket(String authHeader) {
        String ticketToken = this.ticketToken.getValue();
        Integer travelId = 5;
        TicketApiRepository.validateTicket(
                travelId, ticketToken, authHeader,
                new ResponseHandler<>() {
                    @Override
                    public void handleSuccess(ApiResponse<Ticket> response) {
                        if (response == null) {
                            setCanScanCode(true);
                            currentState.setValue(STATE_CONNECTIVITY_ERROR);
                            return;
                        }
                        if (response.isSuccessful()) {
                            currentState.setValue(STATE_VALIDATION_SUCCESS);
                            validatedTicket.setValue(response.getData());
                            setCanScanCode(true);
                            return;
                        }

                        currentState.setValue(STATE_VALIDATION_ERROR);
                    }

                    @Override
                    public void handleError() {
                        currentState.setValue(STATE_CONNECTIVITY_ERROR);
                    }
                });
    }

    public void observeValidationState(LifecycleOwner owner, Observer<Integer> observer) {
        this.currentState.observe(owner, observer);
    }

    public void setTicketToken(String ticketToken) {
        this.ticketToken.setValue(ticketToken);
    }


}
