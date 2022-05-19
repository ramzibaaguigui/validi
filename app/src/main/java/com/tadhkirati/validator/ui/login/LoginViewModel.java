package com.tadhkirati.validator.ui.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.tadhkirati.validator.api.payload.ApiResponse;
import com.tadhkirati.validator.api.payload.LoginRequest;
import com.tadhkirati.validator.api.payload.LoginResponse;
import com.tadhkirati.validator.api.retrofit.ApiService;
import com.tadhkirati.validator.api.retrofit.ResponseHandler;
import com.tadhkirati.validator.api.retrofit.UserAuthRepository;
import com.tadhkirati.validator.models.User;

public class LoginViewModel extends AndroidViewModel {

    public static final int STATE_INITIAL = 0;
    public static final int STATE_LOGIN_PROGRESS = 1;
    public static final int STATE_LOGIN_ERROR = 2;
    public static final int STATE_LOGIN_SUCCESS = 3;
    public static final int STATE_CONNECTIVITY_ERROR = 4;

    private final MutableLiveData<Integer> state = new MutableLiveData<>(STATE_INITIAL);
    private final MutableLiveData<String> phoneNumber = new MutableLiveData<>("");
    private final MutableLiveData<String> password = new MutableLiveData<>("");
    private final MutableLiveData<User> loggedUser = new MutableLiveData<>(null);
    private final MutableLiveData<String> accessToken = new MutableLiveData<>(null);

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public void updatePassword(String password) {
        this.password.setValue(password);
    }

    public void updatePhoneNumber(String phoneNumber) {
        this.phoneNumber.setValue(phoneNumber);
    }

    private void setState(int state) {
        this.state.setValue(state);
    }

    public String getPassword() {
        return password.getValue();
    }

    public String getPhoneNumber() {
        return phoneNumber.getValue();
    }

    public void login() {
        LoginRequest loginRequest = LoginRequest.create()
                .withPhone(getPhoneNumber())
                .withPassword(getPassword());

        UserAuthRepository.login(loginRequest, new ResponseHandler<>() {
            @Override
            public void handleSuccess(ApiResponse<LoginResponse> response) {
                if (response == null) {
                    handleConnectivityError();
                    return;

                }
                if (response.isSuccessful()) {
                    handleLoginSuccessful(
                            response.getData().getUser(),
                            response.getData().token()
                    );
                } else {
                    handleLoginError();
                }
            }

            @Override
            public void handleError() {
                handleConnectivityError();
            }
        });
    }

    private void handleLoginSuccessful(User user, String token) {
        loggedUser.setValue(user);
        this.accessToken.setValue(token);
        setState(STATE_LOGIN_SUCCESS);
    }

    private void handleLoginError() {
        setState(STATE_LOGIN_ERROR);
        // there is still somethings to do here

    }

    private void handleConnectivityError() {
        setState(STATE_CONNECTIVITY_ERROR);
        // there is still something to do here
    }

    public void observeState(LifecycleOwner owner, Observer<Integer> observer) {
        state.observe(owner, observer);
    }

    public User getLoggedUser() {
        return loggedUser.getValue();
    }

    public String getAccessToken() {
        return accessToken.getValue();
    }


}
