package com.tadhkirati.validator.ui.traveldetails.codescanner;

import android.os.Build;

public class PermissionUtils {
    public static boolean minimumIsVersionM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
}
