package com.tadhkirati.validator.ui.splash;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tadhkirati.validator.models.User;
import com.tadhkirati.validator.ui.login.LoginActivity;
import com.tadhkirati.validator.ui.login.LoginUtils;
import com.tadhkirati.validator.ui.login.UserLoginSharedPreferences;
import com.tadhkirati.validator.ui.validator.ValidatorActivity;

public class MainActivity extends AppCompatActivity {

    private User loggedUser;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleUserLogging();
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
