package com.tadhkirati.validator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TravelStationsRecyclerViewAdapter extends RecyclerView.Adapter<TravelStationsRecyclerViewAdapter.TravelViewHolder> {
    private static final int VIEW_TYPE_FIRST = 0;
    private static final int VIEW_TYPE_MIDDLE = 1;
    private static final int VIEW_TYPE_LAST = 2;

    @NonNull
    @Override
    public TravelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutResourceId;
        switch (viewType) {
            case VIEW_TYPE_FIRST:
                layoutResourceId = R.layout.item_departure_station_view;
                break;

            case VIEW_TYPE_LAST:
                layoutResourceId = R.layout.item_arrival_station_view;
                break;

            default:
                layoutResourceId = R.layout.item_middle_station_view;
                break;
        }
        return new TravelViewHolder(LayoutInflater.from(parent.getContext()).inflate(layoutResourceId, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TravelViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_FIRST;
        } else if (position == getItemCount() - 1) {
            return VIEW_TYPE_LAST;
        } else {
            return VIEW_TYPE_MIDDLE;
        }
    }

    static class TravelViewHolder extends RecyclerView.ViewHolder {
        TextView stationTravelTimeTextView;
        TextView stationTravelPriceTextView;
        TextView stationLocationTextView;
        TextView stationNameTextView;

        public TravelViewHolder(@NonNull View itemView) {
            super(itemView);
            stationLocationTextView = itemView.findViewById(R.id.text_view_location_name);
            stationTravelPriceTextView = itemView.findViewById(R.id.text_view_station_travel_price);
            stationTravelTimeTextView = itemView.findViewById(R.id.text_view_station_travel_time);
            stationNameTextView = itemView.findViewById(R.id.text_view_station_name);
        }
    }
}
