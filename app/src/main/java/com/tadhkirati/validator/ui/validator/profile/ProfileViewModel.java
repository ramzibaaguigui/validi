package com.tadhkirati.validator.ui.validator.profile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.tadhkirati.validator.api.payload.ApiResponse;
import com.tadhkirati.validator.api.payload.UpdateValidatorInfoPayload;
import com.tadhkirati.validator.api.retrofit.ResponseHandler;
import com.tadhkirati.validator.api.retrofit.UpdateUserApiRepository;
import com.tadhkirati.validator.models.User;

public class ProfileViewModel extends AndroidViewModel {


    public static final int STATE_INITIAL = 0;
    public static final int STATE_USER_UPDATE_IN_PROGRESS = 1;
    public static final int STATE_USER_UPDATE_SUCCESS = 2;
    public static final int STATE_USER_UPDATE_ERROR = 3;
    public static final int STATE_CONNECTIVITY_ERROR = 4;
    private final MutableLiveData<User> loggedUser = new MutableLiveData<>();
    private final MutableLiveData<String> currentPassword = new MutableLiveData<>("");
    private final MutableLiveData<String> newPassword = new MutableLiveData<>("");
    private final MutableLiveData<String> confirmNewPassword = new MutableLiveData<>("");
    private final MutableLiveData<String> enteredFirstName = new MutableLiveData<>("");
    private final MutableLiveData<String> enteredLastName = new MutableLiveData<>("");
    private final MutableLiveData<String> enteredPhoneNumber = new MutableLiveData<>("");
    private final MutableLiveData<Integer> currentState = new MutableLiveData<>(STATE_INITIAL);


    public ProfileViewModel(@NonNull Application application) {
        super(application);
    }

    public User getLoggedUser() {
        return loggedUser.getValue();
    }

    public void setLoggedUser(User user) {
        loggedUser.setValue(user);
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

    public String getEnteredPhoneNumber() {
        return this.enteredPhoneNumber.getValue();
    }

    public void setEnteredPhoneNumber(String phoneNumber) {
        this.enteredPhoneNumber.setValue(phoneNumber);
    }

    public void updateUser(String accessToken) {
        UpdateValidatorInfoPayload userUpdatePayload = UpdateValidatorInfoPayload.create()
                .withFirstName(enteredFirstName.getValue())
                .withLastName(enteredLastName.getValue())
                .withPhoneNumber(enteredPhoneNumber.getValue());

        UpdateUserApiRepository.updateUser(userUpdatePayload, accessToken, new ResponseHandler<User>() {
            @Override
            public void handleSuccess(ApiResponse<User> response) {
                if (response == null) {
                    currentState.setValue(STATE_CONNECTIVITY_ERROR);
                    return;
                }
                if (response.isSuccessful()) {
                    // TODO; there is still something to finish here
                    setLoggedUser(response.getData());
                    setCurrentState(STATE_USER_UPDATE_SUCCESS);
                    return;
                }

                setCurrentState(STATE_USER_UPDATE_ERROR);
            }

            @Override
            public void handleError() {
                setCurrentState(STATE_CONNECTIVITY_ERROR);
            }
        });
    }


    private void setCurrentState(int state) {
        this.currentState.setValue(state);

    }

    private void handleUpdateError() {
        currentState.setValue(STATE_USER_UPDATE_ERROR);

        // todo: YOU CAN SEE IF THERE IS ANYTHING TO DO HERE LATER
    }


    public void observeUserUpdateState(LifecycleOwner owner, Observer<Integer> observer) {
        currentState.observe(owner, observer);
    }

    public void setUserUpdateStateInitial() {


    }
/*
    public void updatePassword(String authToken) {
        var updatePasswordPayload = UpdatePasswordPayload
                .create().oldPassword(currentPassword.getValue())
                .newPassword(newPassword.getValue())
                .confirmPassword(confirmNewPassword.getValue());
        RetrofitClient.apiService.updatePassword(authToken, updatePasswordPayload)
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<ApiResponse<User>> call, Response<ApiResponse<User>> response) {


                    }

                    @Override
                    public void onFailure(Call<ApiResponse<User>> call, Throwable t) {

                    }
                });
    }
*/

}
