package com.tadhkirati.validator;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.tadhkirati.validator.ui.validator.CodeScannerFragment;
import com.tadhkirati.validator.ui.validator.ProfileFragment;
import com.tadhkirati.validator.ui.validator.TravelsFragment;

public class ValidatorViewPagerFragmentStateAdapter extends FragmentStateAdapter {
    public static final int FRAGMENT_PROFILE = 0;
    public static final int FRAGMENT_TRAVELS = 1;
    public static final int FRAGMENT_CODE_SCANNER = 2;


    public ValidatorViewPagerFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new ProfileFragment();
        } else if (position == 1) {
            return new TravelsFragment();
        } else {
            return new CodeScannerFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
