package com.tadhkirati.validator.ui.validator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.tadhkirati.validator.R;
import com.tadhkirati.validator.ValidatorViewPagerFragmentStateAdapter;
import com.tadhkirati.validator.models.User;
import com.tadhkirati.validator.ui.login.LoginUtils;

public class ValidatorActivity extends AppCompatActivity {

    private ImageButton openDrawerButton;
    private ImageButton logoutImageButton;
    private DrawerLayout validatorDrawerLayout;
    private ImageButton closeDrawerImageButton;

    private LinearLayoutCompat selectProfileContainer;
    private LinearLayoutCompat selectTravelsContainer;
    private LinearLayoutCompat selectCodeScannerContainer;

    ViewPager2 viewPager;
    ValidatorViewPagerFragmentStateAdapter viewPagerAdapter;

    private ProfileViewModel profileViewModel;
    private TravelsViewModel travelsViewModel;

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

        selectCodeScannerContainer.setOnClickListener(view -> {
            selectCodeScanner();
            closeDrawer();
        });
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
        Toast.makeText(this, "logging out ", Toast.LENGTH_SHORT).show();
    }

    private void selectProfile() {
        Toast.makeText(this, "selecting profile", Toast.LENGTH_SHORT).show();
        viewPager.setCurrentItem(ValidatorViewPagerFragmentStateAdapter.FRAGMENT_PROFILE);
    }

    private void selectTravels() {
        Toast.makeText(this, "selecting travels", Toast.LENGTH_SHORT).show();
        viewPager.setCurrentItem(ValidatorViewPagerFragmentStateAdapter.FRAGMENT_TRAVELS);
    }

    private void selectCodeScanner() {
        Toast.makeText(this, "selecting code scanner", Toast.LENGTH_SHORT).show();
        viewPager.setCurrentItem(ValidatorViewPagerFragmentStateAdapter.FRAGMENT_CODE_SCANNER);
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
    }

    public ProfileViewModel getProfileViewModel() {
        return this.profileViewModel;
    }

    public TravelsViewModel getTravelsViewModel() {
        return this.travelsViewModel;
    }

}
