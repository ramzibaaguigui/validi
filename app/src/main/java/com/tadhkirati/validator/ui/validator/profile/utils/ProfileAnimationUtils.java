package com.tadhkirati.validator.ui.validator.profile.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.tadhkirati.validator.R;

public class ProfileAnimationUtils {

    public static void flashView(View view)  {
        Animation flashAnimation = AnimationUtils.loadAnimation(view.getContext(),
                R.anim.anim_flash_view);
        view.startAnimation(flashAnimation);
    }

    public static void flashViewIfVisible(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            flashView(view);
        }
    }
}
