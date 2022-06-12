package com.tadhkirati.validator.ui.validator;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.tadhkirati.validator.ui.validator.profile.ProfileFragment;
import com.tadhkirati.validator.ui.validator.travels.TravelsFragment;

public class ValidatorViewPagerFragmentStateAdapter extends FragmentStateAdapter {
    public static final int FRAGMENT_PROFILE = 0;
    public static final int FRAGMENT_TRAVELS = 1;
    // public static final int FRAGMENT_CODE_SCANNER = 2;


    public ValidatorViewPagerFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new ProfileFragment();
        } else {
            return new TravelsFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
