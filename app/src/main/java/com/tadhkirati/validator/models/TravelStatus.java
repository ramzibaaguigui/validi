package com.tadhkirati.validator.models;

import android.content.Context;

import com.tadhkirati.validator.R;

public enum TravelStatus {
    completed, pending, underway;

    public static String getStringForStatus(Context context, TravelStatus status) {
        return context.getResources().getString(
                getStringIdForTravelStatus(status)
        );
    }

    private static int getStringIdForTravelStatus(TravelStatus status) {
        if (status.equals(TravelStatus.pending))
            return R.string.pending_string;
        if (status.equals(TravelStatus.underway))
            return R.string.underway_string;
        if (status.equals(TravelStatus.completed))
            return R.string.completed_string;
        return R.string.none_string;
    }
}
