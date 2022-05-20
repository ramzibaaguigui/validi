package com.tadhkirati.validator.ui.validator;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

public class VibrationUtils {
    private static final int DURATION_VIBRATION_MILLIS = 300;

    public static void vibrate(Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(DURATION_VIBRATION_MILLIS, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(DURATION_VIBRATION_MILLIS);
        }
    }
}
