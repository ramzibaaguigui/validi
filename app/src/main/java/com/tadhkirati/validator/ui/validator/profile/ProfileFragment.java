package com.tadhkirati.validator.ui.validator.profile;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.tadhkirati.validator.ui.login.LoginUtils;
import com.tadhkirati.validator.ui.validator.ValidatorActivity;
import com.tadhkirati.validator.ui.validator.profile.utils.PasswordValidationUtils;
import com.tadhkirati.validator.ui.validator.profile.utils.ProfileAnimationUtils;
import com.tadhkirati.validator.ui.validator.profile.utils.ProfileValidationUtils;
import com.tadhkirati.validator.ui.validator.profile.utils.UserPasswordUpdateUtils;
import com.tadhkirati.validator.ui.validator.profile.utils.UserProfileUpdateUtils;

public class ProfileFragment extends Fragment {

    private static final int MIN_PASSWORD_LENGTH = 8;

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
    private UpdatePasswordViewModel updatePasswordViewModel;

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
        initViewModels();
        displayUserProfile();
        syncEditProfileViewModel();
        syncUpdatePasswordViewModel();
        // observeState();
        initListeners();
        observeUserUpdateState();
        observeUpdatePasswordState();
    }

    private void initViews(View view) {
        initProfileDetails(view);
        initEditProfile(view);
        initEditPassword(view);
    }

    private void initListeners() {
        submitEditProfileButton.setOnClickListener(view -> updateUserProfile());
        submitEditPasswordButton.setOnClickListener(view -> updateUserPassword());
    }

    private void initViewModels() {
        if (getActivity() == null)
            return;
        profileViewModel = ((ValidatorActivity) getActivity()).getProfileViewModel();
        updatePasswordViewModel = ((ValidatorActivity) getActivity()).getUpdatePasswordViewModel();
    }

    private void displayUserProfile() {
        User loggedUser = profileViewModel.getLoggedUser();
        firstNameTextView.setText(loggedUser.getFirstName());
        firstNameEditText.setText(loggedUser.getFirstName());

        lastNameTextView.setText(loggedUser.getLastName());
        lastNameEditText.setText(loggedUser.getLastName());

        phoneNumberTextView.setText(loggedUser.getPhone_number());
        phoneNumberEditText.setText(loggedUser.getPhone_number());
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
        profileViewModel.observeUserUpdateState(this, new Observer<Integer>() {
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
        Toast.makeText(requireActivity(), "User update Progress", Toast.LENGTH_SHORT).show();
    }

    private void syncEditProfileViewModel() {
        firstNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.i("TEXT_CHANGED", "updating first name to : " + editable.toString());
                profileViewModel.setEnteredFirstName(editable.toString());
                validateFirstName();
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
                validateLastName();
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
                validatePhoneNumber();
            }
        });
    }

    private void syncUpdatePasswordViewModel() {
        currentPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                updatePasswordViewModel.updateEnteredCurrentPassword(editable.toString());
                validatePasswords();
            }
        });

        newPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                updatePasswordViewModel.updateEnteredNewPassword(editable.toString());
                validatePasswords();
            }
        });

        confirmNewPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                updatePasswordViewModel.updateEnteredNewPasswordConfirm(editable.toString());
                validatePasswords();
            }

        });
    }


    private void validatePasswords() {
        validateCurrentPassword();
        validateNewPassword();
        validateNewPasswordConfirm();
    }

    private void handleUserUpdateError() {
        Toast.makeText(getActivity(), R.string.string_update_user_error, Toast.LENGTH_SHORT).show();
    }

    private void handleUserUpdateSuccess() {
        displayUserProfile();
        UserProfileUpdateUtils
                .handleUserUpdatedSuccessfully(requireActivity());
        profileViewModel.setUserUpdateStateInitial();
    }

    private void updateUserProfile() {
        if (profileIsValid()) {
            profileViewModel.updateUser(LoginUtils.formTokenHeader(requireActivity()));
            return;
        }

        showProfileInvalid();
    }

    private void showProfileInvalid() {
        UserProfileUpdateUtils.handleUserProfileInvalid(requireActivity());

    }

    private boolean profileIsValid() {
        return validateFirstName() &&
                validateLastName() &&
                validatePhoneNumber();
    }

    private boolean validateFirstName() {
        String firstNameValidationMessage = ProfileValidationUtils
                .validateFirstName(requireActivity(), profileViewModel.getEnteredFirstName());
        firstNameRequiredTextView.setText(firstNameValidationMessage);
        firstNameRequiredTextView.setVisibility(firstNameValidationMessage == null ? View.GONE : View.VISIBLE);
        ProfileAnimationUtils.flashViewIfVisible(firstNameRequiredTextView);
        return firstNameValidationMessage == null;
    }

    private boolean validateLastName() {
        String lastNameValidationMessage = ProfileValidationUtils
                .validateLastName(requireActivity(), profileViewModel.getEnteredLastName());
        lastNameRequiredTextView.setText(lastNameValidationMessage);
        lastNameRequiredTextView.setVisibility(lastNameValidationMessage == null ? View.GONE : View.VISIBLE);
        ProfileAnimationUtils.flashViewIfVisible(lastNameRequiredTextView);
        return lastNameValidationMessage == null;
    }

    private boolean validatePhoneNumber() {
        String phoneNumberValidationMessage = ProfileValidationUtils
                .validatePhoneNumber(requireActivity(), profileViewModel.getEnteredPhoneNumber());
        phoneNumberRequiredTextView.setText(phoneNumberValidationMessage);
        phoneNumberRequiredTextView.setVisibility(phoneNumberValidationMessage == null ? View.GONE : View.VISIBLE);
        ProfileAnimationUtils.flashViewIfVisible(phoneNumberRequiredTextView);
        return phoneNumberValidationMessage == null;
    }

    private boolean validateCurrentPassword() {
        String message = PasswordValidationUtils
                .validatePassword(requireActivity(), updatePasswordViewModel.getEnteredCurrentPassword());
        if (message == null) {
            currentPasswordRequiredTextView.setVisibility(View.GONE);
            return true;
        }

        currentPasswordRequiredTextView.setText(message);
        currentPasswordRequiredTextView.setVisibility(View.VISIBLE);
        ProfileAnimationUtils.flashViewIfVisible(currentPasswordRequiredTextView);
        return false;
    }

    private boolean validateNewPassword() {
        String message = PasswordValidationUtils
                .validatePassword(requireActivity(), updatePasswordViewModel.getEnteredNewPassword());
        if (message == null) {
            newPasswordRequiredTextView.setVisibility(View.GONE);
            return true;
        }

        newPasswordRequiredTextView.setText(message);
        newPasswordRequiredTextView.setVisibility(View.VISIBLE);
        ProfileAnimationUtils.flashView(newPasswordRequiredTextView);
        return false;
    }

    private boolean validateNewPasswordConfirm() {
        String message = PasswordValidationUtils
                .validateNewPasswordConfirm(requireActivity(),
                        updatePasswordViewModel.getEnteredNewPassword(),
                        updatePasswordViewModel.getEnteredNewPasswordConfirm());

        if (message == null ) {
            confirmNewPasswordRequiredTextView.setVisibility(View.GONE);
            return true;
        }

        confirmNewPasswordRequiredTextView.setText(message);
        confirmNewPasswordRequiredTextView.setVisibility(View.VISIBLE);
        ProfileAnimationUtils.flashView(confirmNewPasswordRequiredTextView);
        return false;
    }

    private void updateUserPassword() {
        // Toast.makeText(requireActivity(), "password update hit", Toast.LENGTH_SHORT).show();
        if (passwordIsValid()) {

            String authToken = LoginUtils.formTokenHeader(requireActivity());
            Log.i("token", "token is " + authToken);
            // TODO: use UpdatePasswordViewModel instead of ProfileViewModel
            updatePasswordViewModel.updatePassword(authToken);
            return;
        }
        showPasswordInvalid();
    }

    private void observeUpdatePasswordState() {
        updatePasswordViewModel.observePasswordUpdateState(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer state) {
                if (state == UpdatePasswordViewModel.STATE_UPDATE_PASSWORD_SUCCESS) {
                    handlePasswordUpdateSuccess();
                    return;
                }

                if (state == UpdatePasswordViewModel.STATE_UPDATE_PASSWORD_PROGRESS) {
                    handlePasswordUpdateProgress();
                    return;
                }

                if (state == UpdatePasswordViewModel.STATE_UPDATE_PASSWORD_ERROR) {
                    handlePasswordUpdateError();
                    return;
                }

                if (state == UpdatePasswordViewModel.STATE_UPDATE_PASSWORD_CONNECTIVITY_ERROR) {
                    handlePasswordUpdateConnectivityError();
                    return;
                }
            }

            public void handlePasswordUpdateSuccess() {
                UserPasswordUpdateUtils.handlePasswordUpdateSuccess(requireActivity());
            }

            public void handlePasswordUpdateProgress() {

            }

            public void handlePasswordUpdateError() {
                UserPasswordUpdateUtils.handlePasswordUpdateError(requireActivity());
            }

            public void handlePasswordUpdateConnectivityError() {
                UserPasswordUpdateUtils.handlePasswordUpdateError(requireActivity());
            }
        });
    }

    private boolean passwordIsValid() {
        return newPasswordEditText.getText().toString()
                .equals(confirmNewPasswordEditText.getText().toString())
                && currentPasswordEditText.getText().length() >= MIN_PASSWORD_LENGTH;
    }

    private void showPasswordInvalid() {

        UserPasswordUpdateUtils.handlePasswordInvalid(requireActivity());
//        Toast.makeText(requireActivity(), "Password invalid", Toast1.LENGTH_SHORT).show();
    }

    private void observeUserUpdateState() {
        profileViewModel.observeUserUpdateState(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer state) {
                if (state == ProfileViewModel.STATE_USER_UPDATE_SUCCESS) {
                    handleUserUpdateSuccess();
                    return;
                }
                if (state == ProfileViewModel.STATE_USER_UPDATE_ERROR) {
                    handleUserUpdateError();
                    return;
                }

                if (state == ProfileViewModel.STATE_USER_UPDATE_IN_PROGRESS) {
                    handleUserUpdateInProgress();
                    return;
                }

                if (state == ProfileViewModel.STATE_CONNECTIVITY_ERROR) {
                    handleUserUpdateConnectivityError();
                }
            }

        });
    }

    private void handleUserUpdateConnectivityError() {
        UserProfileUpdateUtils.handleUserUpdatedError(requireActivity());
    }
}
