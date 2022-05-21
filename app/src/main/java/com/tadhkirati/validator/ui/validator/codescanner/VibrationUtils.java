package com.tadhkirati.validator.ui.validator.codescanner;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

public class VibrationUtils {
    private static final int DURATION_VIBRATION_SUCCESS_MILLIS = 300;
    private static final int DURATION_VIBRATION_FAILED_MILLIS = 500;
    public static void vibrateSuccess(Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(DURATION_VIBRATION_SUCCESS_MILLIS, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(DURATION_VIBRATION_SUCCESS_MILLIS);
        }
    }

    public static void vibrateFailed(Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(DURATION_VIBRATION_FAILED_MILLIS, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(DURATION_VIBRATION_SUCCESS_MILLIS);
        }
    }
}
