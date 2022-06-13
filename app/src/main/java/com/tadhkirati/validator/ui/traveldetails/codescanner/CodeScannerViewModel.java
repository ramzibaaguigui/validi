package com.tadhkirati.validator.ui.traveldetails.codescanner;

import android.app.Application;
import android.os.CountDownTimer;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.tadhkirati.validator.models.Ticket;

public class CodeScannerViewModel extends AndroidViewModel {
    public static final int STATE_INITIAL = 0;
    public static final int STATE_VALIDATION_IN_PROGRESS = 1;
    public static final int STATE_TICKET_VALIDATION_SUCCESS = 2;
    public static final int STATE_VALIDATION_ERROR = 3;
    public static final int STATE_CONNECTIVITY_ERROR = 4;
    private final MutableLiveData<Integer> currentState = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isCameraPermissionAccepted = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> canScanCode = new MutableLiveData<>(true);
    private final MutableLiveData<String> scannedTicketToken = new MutableLiveData<>();
    private final MutableLiveData<Ticket> validatedTicket = new MutableLiveData<>();
    private final MutableLiveData<Long> travelId = new MutableLiveData<>();
    private final MutableLiveData<Integer> inValidationTicketPosition = new MutableLiveData<>();

    public CodeScannerViewModel(@NonNull Application application) {
        super(application);
    }

    public boolean getCanScanCode() {
        return canScanCode.getValue();
    }

    public void setCanScanCode(boolean canScan) {
        this.canScanCode.setValue(canScan);
    }

    public Ticket getValidatedTicket() {
        return this.validatedTicket.getValue();
    }

    public void enableScanningAfterTwoSeconds() {
        Log.i("COUNTDOWN_TIMER", "STARTED COUNTER");
        CountDownTimer timer = new CountDownTimer(2000, 500) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                Log.i("COUNTDOWN", "TIMER FINISHED");
                canScanCode.setValue(true);
                Log.i("SCAN_CODE:", String.valueOf(canScanCode.getValue()));
                Log.i("SCANNER_TIME", String.valueOf(canScanCode.getValue()));
            }
        };
        timer.start();
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


    public void observeValidationState(LifecycleOwner owner, Observer<Integer> observer) {
        this.currentState.observe(owner, observer);
    }

    public String getScannedTicketToken() {
        return this.scannedTicketToken.getValue();
    }

    public void setScannedTicketToken(String scannedTicketToken) {
        this.scannedTicketToken.postValue(scannedTicketToken);
    }

    public Long getTravelId() {
        return this.travelId.getValue();
    }

    public void setTravelId(Long travelId) {
        this.travelId.setValue(travelId);
    }


}
