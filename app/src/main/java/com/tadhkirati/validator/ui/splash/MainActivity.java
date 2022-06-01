package com.tadhkirati.validator.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tadhkirati.validator.R;
import com.tadhkirati.validator.models.User;
import com.tadhkirati.validator.ui.login.LoginActivity;
import com.tadhkirati.validator.ui.login.LoginUtils;
import com.tadhkirati.validator.ui.login.UserLoginSharedPreferences;
import com.tadhkirati.validator.ui.validator.ValidatorActivity;

public class MainActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY_MILLIS = 1000;
    private User loggedUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(this::handleUserLogging, SPLASH_DELAY_MILLIS);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    private boolean isUserLoggedIn() {
        User user = UserLoginSharedPreferences.retrieveLoggedUser(this);
        if (user == null) {
            return false;
        }
        loggedUser = user;
        return true;
    }

    private void goValidatorActivity() {
        Intent intent = new Intent(this, ValidatorActivity.class);
        // here we are going to put some information related to the user
        intent.putExtras(LoginUtils.bundleLoggedUser(this.loggedUser));
        startActivity(intent);
    }

    private void goLoginActivity() {
        final Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void handleUserLogging() {
        if (isUserLoggedIn()) {
            goValidatorActivity();
        } else {
            goLoginActivity();
        }
    }


}
