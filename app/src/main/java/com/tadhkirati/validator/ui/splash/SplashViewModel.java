package com.tadhkirati.validator.ui.splash;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.tadhkirati.validator.ui.validator.ValidatorActivity;

public class SplashViewModel extends AndroidViewModel {

    public static final int STATE_INITIAL = 0;
    public static final int STATE_USER_LOGIN_SUCCESS = 1;
    public static final int STATE_USER_LOGIN_ERROR = 2;
    public static final int STATE_USER_NETWORK_ERROR = 3;

    public SplashViewModel(@NonNull Application application) {
        super(application);
    }

    public void validateLoggedUser() {

    }


}
