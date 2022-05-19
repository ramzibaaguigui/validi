package com.tadhkirati.validator.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.tadhkirati.validator.R;
import com.tadhkirati.validator.api.payload.LoginRequest;
import com.tadhkirati.validator.models.User;
import com.tadhkirati.validator.ui.validator.ValidatorActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText phoneNumberEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private View container;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViewModel();
        initViews();
        updateViews();
        syncViewModel();
        initListeners();
        observeState();
    }

    private void updateViews() {
        phoneNumberEditText.setText(loginViewModel.getPhoneNumber());
        passwordEditText.setText(loginViewModel.getPassword());
    }

    private void syncViewModel() {
        phoneNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                loginViewModel.updatePhoneNumber(editable.toString());
            }
        });
        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                loginViewModel.updatePassword(editable.toString());
            }
        });
    }

    private LoginRequest createLoginRequest() {
        return LoginRequest.create()
                .withPassword(passwordEditText.getText().toString())
                .withPhone(phoneNumberEditText.getText().toString());
    }

    private void initViews() {
        container = findViewById(R.id.container);
        phoneNumberEditText = findViewById(R.id.edit_text_phone_number);
        passwordEditText = findViewById(R.id.edit_text_password);
        loginButton = findViewById(R.id.button_login);

    }

    private void initListeners() {
        loginButton.setOnClickListener(view -> tryLogin());
    }

    private void tryLogin() {
        if (!credentialsAreValid()) {
            verifyYourInformation();
            return;
        }
        loginViewModel.login();
    }


    private void initViewModel() {
        loginViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())
                .create(LoginViewModel.class);

    }

    private void verifyYourInformation() {
        Toast.makeText(this, "make sure that your credentials are valid", Toast.LENGTH_SHORT).show();
    }

    private boolean credentialsAreValid() {
        return LoginUtils.loginCredentialsAreValid(
                phoneNumberEditText.getText().toString(),
                passwordEditText.getText().toString()
        );
    }

    private void observeState() {
        loginViewModel.observeState(
                this,
                state -> {
                    if (state == LoginViewModel.STATE_LOGIN_SUCCESS) {
                        handleLoginSuccess();
                        } else if (state == LoginViewModel.STATE_LOGIN_ERROR) {
                        handleLoginError();
                    } else if (state == LoginViewModel.STATE_CONNECTIVITY_ERROR) {
                        handleConnectivityError();
                    }
                }
        );
    }

    private void handleLoginSuccess() {
        storeCredentials();
        loginSuccessWithUser();
        displayLoggedUser();
    }

    private void displayLoggedUser() {
        User user = loginViewModel.getLoggedUser();
            Toast.makeText(this, user.toString(), Toast.LENGTH_SHORT).show();
    }


    private void loginSuccessWithUser() {
        Intent intent = new Intent(this, ValidatorActivity.class);
        Log.i("LOGGED_USER_INTENT", loginViewModel.getLoggedUser().getFirstName());
        intent.putExtras(LoginUtils.bundleLoggedUser(loginViewModel.getLoggedUser()));
        startActivity(intent);
    }

    private void storeCredentials() {
        User loggedUser = loginViewModel.getLoggedUser();
        String accessToken = loginViewModel.getAccessToken();
        UserLoginSharedPreferences.storeLoggedUser(this, loggedUser);
        UserLoginSharedPreferences.storeAccessToken(this, accessToken);
    }

    private void handleLoginError() {
        Toast.makeText(this, "could not find credentials specified", Toast.LENGTH_SHORT).show();
    }

    private void handleConnectivityError() {
        Toast.makeText(this, "please verify your network and retry", Toast.LENGTH_SHORT).show();
    }
}
