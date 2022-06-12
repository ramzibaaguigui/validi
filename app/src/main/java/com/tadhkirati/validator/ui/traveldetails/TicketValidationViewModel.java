package com.tadhkirati.validator.ui.traveldetails;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class TicketValidationViewModel extends AndroidViewModel {
    public TicketValidationViewModel(@NonNull Application application) {
        super(application);
    }



    private MutableLiveData<Integer> ticketValidationState = new MutableLiveData<>(0);
}
