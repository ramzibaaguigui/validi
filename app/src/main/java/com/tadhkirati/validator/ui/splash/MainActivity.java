package com.tadhkirati.validator.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.ImageFilterView;

import com.tadhkirati.validator.R;
import com.tadhkirati.validator.models.User;
import com.tadhkirati.validator.ui.login.LoginActivity;
import com.tadhkirati.validator.ui.login.LoginUtils;
import com.tadhkirati.validator.ui.login.UserLoginSharedPreferences;
import com.tadhkirati.validator.ui.validator.ValidatorActivity;

public class MainActivity extends AppCompatActivity {

    private ImageFilterView logoImage;
    private TextView appTextView;

    private static final int SPLASH_DELAY_MILLIS = 2000;
    private User loggedUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initViews();
        animateImage();
        animateText();
        new Handler().postDelayed(this::handleUserLogging, SPLASH_DELAY_MILLIS);
    }

    private void initViews() {
        appTextView = findViewById(R.id.text_view_app_name);
        logoImage = findViewById(R.id.image_view_logo);
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

    private void animateImage() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top_animation);
        logoImage.startAnimation(animation);
    }

    private void animateText() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.start_to_end_animation);
        appTextView.startAnimation(animation);
    }


}
