package com.tadhkirati.validator.ui.validator.profile;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.google.gson.Gson;
import com.tadhkirati.validator.api.payload.ApiResponse;
import com.tadhkirati.validator.api.payload.UpdatePasswordPayload;
import com.tadhkirati.validator.api.retrofit.RetrofitClient;
import com.tadhkirati.validator.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePasswordViewModel extends AndroidViewModel {

    public UpdatePasswordViewModel(@NonNull Application application) {
        super(application);
    }

    public static final int STATE_INITIAL = 0;
    public static final int STATE_UPDATE_PASSWORD_PROGRESS = 1;
    public static final int STATE_UPDATE_PASSWORD_SUCCESS = 2;
    public static final int STATE_UPDATE_PASSWORD_ERROR = 3;
    public static final int STATE_UPDATE_PASSWORD_CONNECTIVITY_ERROR = 4;


    private MutableLiveData<Integer> passwordUpdateState = new MutableLiveData<>(STATE_INITIAL);
    private MutableLiveData<String> currentPassword = new MutableLiveData<>("");
    private MutableLiveData<String> newPassword = new MutableLiveData<>("");
    private MutableLiveData<String> confirmNewPassword = new MutableLiveData<>("");


    public void observePasswordUpdateState(LifecycleOwner owner, Observer<Integer> observer) {
        this.passwordUpdateState.observe(owner, observer);
    }

    public void updatePassword(String authToken) {
        var updatePasswordPayload = UpdatePasswordPayload
                .create().oldPassword(currentPassword.getValue())
                .newPassword(newPassword.getValue())
                .confirmPassword(confirmNewPassword.getValue());
        Log.i("PASSWORD_PAYLOAD", new Gson().toJson(updatePasswordPayload));

        RetrofitClient.apiService.updatePassword(authToken, updatePasswordPayload)
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<ApiResponse<User>> call, Response<ApiResponse<User>> response) {
                        if (response == null) {
                            passwordUpdateState.setValue(STATE_UPDATE_PASSWORD_CONNECTIVITY_ERROR);
                            return;
                        }

                        if (response.isSuccessful()) {
                            Log.i("UPDATE_PASSWORD", response.body().toString());
                            passwordUpdateState.setValue(STATE_UPDATE_PASSWORD_SUCCESS);
                            return;
                        }

                    }

                    @Override
                    public void onFailure(Call<ApiResponse<User>> call, Throwable t) {
                        passwordUpdateState.setValue(STATE_UPDATE_PASSWORD_CONNECTIVITY_ERROR);
                    }
                });
    }

    public void updateEnteredCurrentPassword(String value) {
        this.currentPassword.setValue(value);
    }

    public void updateEnteredNewPassword(String value) {
        this.newPassword.setValue(value);
    }

    public void updateEnteredNewPasswordConfirm(String value) {
        this.confirmNewPassword.setValue(value);
    }

    public String getEnteredCurrentPassword() {
        return this.currentPassword.getValue();
    }

    public String getEnteredNewPassword() {
        return newPassword.getValue();
    }

    public String getEnteredNewPasswordConfirm() {
        return confirmNewPassword.getValue();
    }


}
