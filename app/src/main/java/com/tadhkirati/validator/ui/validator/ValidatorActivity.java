package com.tadhkirati.validator.ui.validator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.tadhkirati.validator.R;
import com.tadhkirati.validator.models.User;
import com.tadhkirati.validator.ui.login.LoginActivity;
import com.tadhkirati.validator.ui.login.LoginUtils;
import com.tadhkirati.validator.ui.login.UserLoginSharedPreferences;
import com.tadhkirati.validator.ui.traveldetails.CodeScannerView;
import com.tadhkirati.validator.ui.traveldetails.TravelDetailsView;
import com.tadhkirati.validator.ui.validator.codescanner.CodeScannerFragment;
import com.tadhkirati.validator.ui.validator.codescanner.CodeScannerViewModel;
import com.tadhkirati.validator.ui.validator.profile.ProfileViewModel;
import com.tadhkirati.validator.ui.validator.profile.UpdatePasswordViewModel;
import com.tadhkirati.validator.ui.validator.travels.TravelsViewModel;

public class ValidatorActivity extends AppCompatActivity implements CodeScannerView {

    private ImageButton openDrawerButton;
    private ImageButton logoutImageButton;
    private DrawerLayout validatorDrawerLayout;
    private ImageButton closeDrawerImageButton;

    private LinearLayoutCompat selectProfileContainer;
    private LinearLayoutCompat selectTravelsContainer;
    private LinearLayoutCompat selectCodeScannerContainer;

    ViewPager2 viewPager;
    ValidatorViewPagerFragmentStateAdapter viewPagerAdapter;
    private TextView selectedFragmentTitleTextView;

    private ProfileViewModel profileViewModel;
    private TravelsViewModel travelsViewModel;
    private CodeScannerViewModel codeScannerViewModel;
    private UpdatePasswordViewModel updatePasswordViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validator);
        initViewModels();
        extractLoggedUser();
        initViews();
        initListeners();
        initViewPager();
        showLoggedUser();
    }

    private void initViews() {
        validatorDrawerLayout = findViewById(R.id.drawer_layout_validator);
        openDrawerButton = findViewById(R.id.image_button_show_drawer);
        logoutImageButton = findViewById(R.id.image_button_logout);

        // initting the view of drawer layout
        closeDrawerImageButton = findViewById(R.id.image_button_close_drawer);
        selectProfileContainer = findViewById(R.id.container_profile_select);
        selectTravelsContainer = findViewById(R.id.container_travels_select);
        selectCodeScannerContainer = findViewById(R.id.container_code_scanner_select);

        selectedFragmentTitleTextView = findViewById(R.id.text_view_selected_fragment_title);

    }

    private void showLoggedUser() {
        User user = profileViewModel.getLoggedUser();
        if (user == null) {
            Toast.makeText(this, "null user", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, user.toString(), Toast.LENGTH_SHORT).show();
    }

    private void initListeners() {
        openDrawerButton.setOnClickListener(view -> showDrawer());
        logoutImageButton.setOnClickListener(view -> logout());
        closeDrawerImageButton.setOnClickListener(view -> closeDrawer());
        selectProfileContainer.setOnClickListener(view -> {
            selectProfile();
            closeDrawer();
        });

        selectTravelsContainer.setOnClickListener(view -> {
            selectTravels();
            closeDrawer();
        });

        /*selectCodeScannerContainer.setOnClickListener(view -> {
            selectCodeScanner();
            closeDrawer();
        });*/
    }

    private void initViewPager() {
        viewPager = findViewById(R.id.view_pager);
        viewPager.setUserInputEnabled(false);
        viewPagerAdapter = new ValidatorViewPagerFragmentStateAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);
    }

    private void showDrawer() {
        if (!validatorDrawerLayout.isOpen())
            validatorDrawerLayout.openDrawer(GravityCompat.START);
    }

    private void closeDrawer() {
        if (validatorDrawerLayout.isOpen())
            validatorDrawerLayout.closeDrawer(GravityCompat.START);
    }

    private void logout() {
        UserLoginSharedPreferences.deleteLoggedUser(this);
        Toast.makeText(this, "logging out ", Toast.LENGTH_SHORT).show();
        goLoginActivity();
    }

    private void goLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void selectProfile() {
        Toast.makeText(this, "selecting profile", Toast.LENGTH_SHORT).show();
        viewPager.setCurrentItem(ValidatorViewPagerFragmentStateAdapter.FRAGMENT_PROFILE);
        selectedFragmentTitleTextView.setText(R.string.profile_string);
    }

    private void selectTravels() {
        Toast.makeText(this, "selecting travels", Toast.LENGTH_SHORT).show();
        viewPager.setCurrentItem(ValidatorViewPagerFragmentStateAdapter.FRAGMENT_TRAVELS);
        selectedFragmentTitleTextView.setText(R.string.my_travels_string);
    }

    private void selectCodeScanner() {
        Toast.makeText(this, "selecting code scanner", Toast.LENGTH_SHORT).show();
        // viewPager.setCurrentItem(ValidatorViewPagerFragmentStateAdapter.FRAGMENT_CODE_SCANNER);
        selectedFragmentTitleTextView.setText(R.string.qr_code_scanner_string);
    }

    private void extractLoggedUser() {
        Intent intent = getIntent();
        User loggedUser = LoginUtils.extractLoggedUser(intent.getExtras());
        profileViewModel.setLoggedUser(loggedUser);
    }

    private void initViewModels() {
        profileViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())
                .create(ProfileViewModel.class);

        travelsViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())
                .create(TravelsViewModel.class);

        codeScannerViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())
                .create(CodeScannerViewModel.class);
        updatePasswordViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())
                .create(UpdatePasswordViewModel.class);
    }

    public ProfileViewModel getProfileViewModel() {
        return this.profileViewModel;
    }

    public TravelsViewModel getTravelsViewModel() {
        return this.travelsViewModel;
    }

    public CodeScannerViewModel getCodeScannerViewModel() {
        return this.codeScannerViewModel;
    }

    public UpdatePasswordViewModel getUpdatePasswordViewModel() {
        return this.updatePasswordViewModel;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CodeScannerFragment.CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length == 1) {
                codeScannerViewModel.setCameraPermissionAccepted(true);
            }
        }
    }
}
