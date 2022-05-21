package com.tadhkirati.validator.ui.validator.profile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.tadhkirati.validator.api.payload.ApiResponse;
import com.tadhkirati.validator.api.retrofit.ResponseHandler;
import com.tadhkirati.validator.api.retrofit.UpdateUserApiRepository;
import com.tadhkirati.validator.models.User;

public class ProfileViewModel extends AndroidViewModel {


    public ProfileViewModel(@NonNull Application application) {
        super(application);
    }

    private final MutableLiveData<User> loggedUser = new MutableLiveData<>();
    private final MutableLiveData<String> currentPassword = new MutableLiveData<>("");
    private final MutableLiveData<String> newPassword = new MutableLiveData<>("");
    private final MutableLiveData<String> confirmNewPassword = new MutableLiveData<>("");
    private final MutableLiveData<String> enteredFirstName = new MutableLiveData<>("");
    private final MutableLiveData<String> enteredLastName = new MutableLiveData<>("");
    private final MutableLiveData<String> enteredPhoneNumber = new MutableLiveData<>("");
    private final MutableLiveData<Integer> currentState = new MutableLiveData<>(STATE_INITIAL);

    public static final int STATE_INITIAL = 0;
    public static final int STATE_USER_UPDATE_IN_PROGRESS = 1;
    public static final int STATE_USER_UPDATE_SUCCESS = 2;
    public static final int STATE_USER_UPDATE_ERROR = 3;

    public void setLoggedUser(User user) {
        loggedUser.setValue(user);
    }

    public User getLoggedUser() {
        return loggedUser.getValue();
    }

    public String getCurrentPassword() {
        return this.currentPassword.getValue();
    }

    public void setCurrentPassword(String newPassword) {
        this.currentPassword.setValue(newPassword);
    }

    public String getNewPassword() {
        return this.newPassword.getValue();
    }

    public void setNewPassword(String newPassword) {
        this.newPassword.setValue(newPassword);
    }

    public String getConfirmNewPassword() {
        return this.confirmNewPassword.getValue();
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword.setValue(confirmNewPassword);
    }

    public String getEnteredFirstName() {
        return this.enteredFirstName.getValue();
    }

    public void setEnteredFirstName(String firstName) {
        this.enteredFirstName.setValue(firstName);
    }

    public String getEnteredLastName() {
        return this.enteredLastName.getValue();
    }

    public void setEnteredLastName(String lastName) {
        this.enteredLastName.setValue(lastName);
    }

    public String getPhoneNumber() {
        return this.enteredPhoneNumber.getValue();
    }

    public void setEnteredPhoneNumber(String phoneNumber) {
        this.enteredPhoneNumber.setValue(phoneNumber);
    }

    public void updateUser(String accessToken) {
        User newUser = User.createUser()
                .withFirstName(enteredFirstName.getValue())
                .withLastName(enteredLastName.getValue())
                .withPhoneNumber(enteredPhoneNumber.getValue());
        UpdateUserApiRepository.updateUser(newUser, accessToken, new ResponseHandler<User>() {
            @Override
            public void handleSuccess(ApiResponse<User> response) {
                if (response == null) {
                    handleError();
                    return;
                }
                if (response.isSuccessful()) {
                    // TODO; there is still something to finish here
                }
                handleUpdateError();
            }

            @Override
            public void handleError() {
                handleConnectivityError();
            }
        });
    }

    private void handleConnectivityError() {

    }

    private void handleUpdateError() {
        currentState.setValue(STATE_USER_UPDATE_ERROR);

        // todo: YOU CAN SEE IF THERE IS ANYTHING TO DO HERE LATER
    }

    private void handleUserUpdated() {

    }

    public void observeState(LifecycleOwner owner, Observer<Integer> observer) {
        currentState.observe(owner, observer);
    }

    public void setStateInitial() {
        this.currentState.setValue(STATE_INITIAL);

    }


}
