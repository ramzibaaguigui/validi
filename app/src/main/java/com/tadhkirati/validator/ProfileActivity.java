package com.tadhkirati.validator;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.ImageFilterView;

import java.io.Serializable;

public class ProfileActivity extends Activity {
    private ImageFilterView profileImageFilterView;
    private TextView firstNameTextView;
    private TextView lastNameTextView;
    private TextView phoneNumberTextView;

    private Button uploadProfilePictureButton;
    private ImageFilterView uploadedPictureImageFilterView;

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText phoneNumberEditText;

    private TextView firstNameRequiredTextView;
    private TextView lastNameRequiredTextView;
    private TextView phoneNumberRequiredTextView;

    private Button submitEditProfileButton;

    private EditText currentPasswordEditText;
    private EditText newPasswordEditText;
    private EditText confirmNewPasswordEditText;

    private TextView currentPasswordRequiredTextView;
    private TextView newPasswordRequiredTextView;
    private TextView confirmNewPasswordRequiredTextView;

    private Button submitEditPasswordButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initViews();
    }

    private void initViews() {
        initProfileDetails();
        initEditProfile();
        initEditPassword();
    }

    private void initProfileDetails() {
        profileImageFilterView = findViewById(R.id.image_view_profile);
        firstNameTextView = findViewById(R.id.text_view_first_name);
        lastNameTextView = findViewById(R.id.text_view_last_name);
        phoneNumberTextView = findViewById(R.id.text_view_phone_number);
    }

    private void initEditProfile() {
        uploadedPictureImageFilterView = findViewById(R.id.image_view_uploaded_profile_picture);
        uploadProfilePictureButton = findViewById(R.id.button_upload_profile_picture);
        firstNameEditText = findViewById(R.id.edit_text_first_name);
        firstNameRequiredTextView = findViewById(R.id.text_view_first_name_required);

        lastNameEditText = findViewById(R.id.edit_text_last_name);
        lastNameRequiredTextView = findViewById(R.id.text_view_last_name_required);

        phoneNumberEditText = findViewById(R.id.edit_text_phone_number);
        phoneNumberRequiredTextView = findViewById(R.id.text_view_phone_number_required);
        submitEditProfileButton = findViewById(R.id.button_submit_edit_profile);
    }

    private void initEditPassword() {
        currentPasswordEditText = findViewById(R.id.edit_text_current_password);
        currentPasswordRequiredTextView = findViewById(R.id.text_view_current_password_required);
        newPasswordEditText = findViewById(R.id.edit_text_new_password);
        newPasswordRequiredTextView = findViewById(R.id.text_view_new_password_required);
        confirmNewPasswordEditText = findViewById(R.id.edit_text_confirm_new_password);
        confirmNewPasswordRequiredTextView = findViewById(R.id.text_view_confirm_new_password_required);
        submitEditPasswordButton = findViewById(R.id.button_submit_edit_password);
    }





}
