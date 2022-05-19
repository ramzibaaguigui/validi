package com.tadhkirati.validator.ui.validator;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.tadhkirati.validator.R;
import com.tadhkirati.validator.models.User;

public class ProfileFragment extends Fragment {

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

    private ProfileViewModel profileViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initViewModel();
        displayUserProfile();
        syncViewModel();
        observeState();
    }

    private void initViews(View view) {
        initProfileDetails(view);
        initEditProfile(view);
        initEditPassword(view);
    }

    private void initViewModel() {
        if (getActivity() == null)
            return;
        profileViewModel = ((ValidatorActivity) getActivity()).getProfileViewModel();
    }

    private void displayUserProfile() {
        User loggedUser = profileViewModel.getLoggedUser();
        firstNameTextView.setText(loggedUser.getFirstName());
        firstNameEditText.setText(loggedUser.getFirstName());

        lastNameTextView.setText(loggedUser.getLastName());
        lastNameEditText.setText(loggedUser.getLastName());

        phoneNumberTextView.setText(loggedUser.getPhoneNumber());
        phoneNumberEditText.setText(loggedUser.getPhoneNumber());
    }

    private void initProfileDetails(View view) {
        profileImageFilterView = view.findViewById(R.id.image_view_profile);
        firstNameTextView = view.findViewById(R.id.text_view_first_name);
        lastNameTextView = view.findViewById(R.id.text_view_last_name);
        phoneNumberTextView = view.findViewById(R.id.text_view_phone_number);
    }

    private void initEditProfile(View view) {
        uploadedPictureImageFilterView = view.findViewById(R.id.image_view_uploaded_profile_picture);
        uploadProfilePictureButton = view.findViewById(R.id.button_upload_profile_picture);
        firstNameEditText = view.findViewById(R.id.edit_text_first_name);
        firstNameRequiredTextView = view.findViewById(R.id.text_view_first_name_required);

        lastNameEditText = view.findViewById(R.id.edit_text_last_name);
        lastNameRequiredTextView = view.findViewById(R.id.text_view_last_name_required);

        phoneNumberEditText = view.findViewById(R.id.edit_text_phone_number);
        phoneNumberRequiredTextView = view.findViewById(R.id.text_view_phone_number_required);
        submitEditProfileButton = view.findViewById(R.id.button_submit_edit_profile);
    }

    private void initEditPassword(View view) {
        currentPasswordEditText = view.findViewById(R.id.edit_text_current_password);
        currentPasswordRequiredTextView = view.findViewById(R.id.text_view_current_password_required);
        newPasswordEditText = view.findViewById(R.id.edit_text_new_password);
        newPasswordRequiredTextView = view.findViewById(R.id.text_view_new_password_required);
        confirmNewPasswordEditText = view.findViewById(R.id.edit_text_confirm_new_password);
        confirmNewPasswordRequiredTextView = view.findViewById(R.id.text_view_confirm_new_password_required);
        submitEditPasswordButton = view.findViewById(R.id.button_submit_edit_password);
    }

    private void observeState() {
        profileViewModel.observeState(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer state) {
                if (state == ProfileViewModel.STATE_USER_UPDATE_IN_PROGRESS) {
                    handleUserUpdateInProgress();
                    return;
                }
                if (state == ProfileViewModel.STATE_USER_UPDATE_ERROR) {
                    handleUserUpdateError();
                    return;
                }
                if (state == ProfileViewModel.STATE_USER_UPDATE_SUCCESS) {
                    handleUserUpdateSuccess();
                }
            }
        });
    }

    private void handleUserUpdateInProgress() {
        // TODO: here we will display a progress bar
        // indication that a request has been sent to the server

    }

    private void syncViewModel() {
        firstNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                profileViewModel.setEnteredFirstName(editable.toString());
            }
        });
        lastNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                profileViewModel.setEnteredLastName(editable.toString());
            }
        });
        phoneNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                profileViewModel.setEnteredPhoneNumber(editable.toString());
            }
        });
    }

    private void handleUserUpdateError() {
        Toast.makeText(getActivity(), R.string.string_update_user_error, Toast.LENGTH_SHORT).show();
    }

    private void handleUserUpdateSuccess() {
        displayUserProfile();
        profileViewModel.setStateInitial();
    }

}
