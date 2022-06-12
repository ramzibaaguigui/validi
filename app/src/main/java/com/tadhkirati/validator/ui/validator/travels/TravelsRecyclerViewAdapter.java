package com.tadhkirati.validator.ui.validator.travels;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.tadhkirati.validator.R;
import com.tadhkirati.validator.models.Travel;

import java.util.List;

public class TravelsRecyclerViewAdapter extends RecyclerView.Adapter<TravelsRecyclerViewAdapter.TravelViewHolder> {

    private List<Travel> travels;
    private OnTravelActionListener listener;

    public TravelsRecyclerViewAdapter(List<Travel> travels) {
        this.travels = travels;
    }

    @NonNull
    @Override
    public TravelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TravelViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_travel_view, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull TravelViewHolder holder, int position) {
        Travel currentTravel = travels.get(position);
        Log.i("travel_info", new Gson().toJson(currentTravel));
        holder.arrivalTextView.setText(currentTravel.getArrivalStationName());
        holder.departureTextView.setText(currentTravel.getDepartureStationName());

        holder.container.setOnClickListener(view -> {
            if (this.listener == null)
                return;
            listener.onClickTravel(currentTravel);
        });

        holder.container.startAnimation(
                AnimationUtils.loadAnimation(
                        holder.container.getContext(),
                        R.anim.start_to_end_animation));
    }

    @Override
    public int getItemCount() {
        return travels.size();
    }

    public void setOnTravelActionListener(OnTravelActionListener listener) {
        this.listener = listener;
    }

    public interface OnTravelActionListener {
        void onClickTravel(Travel travel);
    }

    static class TravelViewHolder extends RecyclerView.ViewHolder {
        View container;
        TextView departureTextView;
        TextView arrivalTextView;
        TextView timeTextView;

        public TravelViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            departureTextView = itemView.findViewById(R.id.text_view_travel_item_departure_station);
            arrivalTextView = itemView.findViewById(R.id.text_view_travel_item_arrival_station_name);
        }
    }

}
