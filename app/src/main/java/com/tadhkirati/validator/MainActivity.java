package com.tadhkirati.validator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.tadhkirati.validator.ui.login.LoginActivity;
import com.tadhkirati.validator.ui.login.UserLoginSharedPreferences;
import com.tadhkirati.validator.ui.validator.ValidatorActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        final Button button = findViewById(R.id.button);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(this, ProfileFragment.class);
            startActivity(intent);
        });



        final Button newButton = findViewById(R.id.button_new);
        newButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, ValidatorActivity.class);
            startActivity(intent);
        });

        final Button goTravelDetailsButton = findViewById(R.id.button_go_details);
        goTravelDetailsButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, TravelDetailsActivity.class);
            startActivity(intent);
        });

        final Button loginButton = findViewById(R.id.button_login);
        loginButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        final Button showLoggedUserButton = findViewById(R.id.button_show_logged_user);
        showLoggedUserButton.setOnClickListener(view -> {
            Toast.makeText(this,
                    UserLoginSharedPreferences.retrieveLoggedUser(this).toString(), Toast.LENGTH_SHORT).show();
        });
    }
}