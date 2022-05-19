package com.tadhkirati.validator;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TravelsRecyclerViewAdapter {


    static class TravelViewHoder extends RecyclerView.ViewHolder {
        TextView departureTextView;
        TextView arrivalTextView;
        TextView timeTextView;

        public TravelViewHoder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
