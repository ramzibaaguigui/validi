package com.tadhkirati.validator.ui.validator.travels;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        final Travel currentTravel = travels.get(position);
        holder.arrivalTextView.setText(currentTravel.getArrivalStation());
        holder.departureTextView.setText(currentTravel.getDepartureStation());

        holder.container.setOnClickListener(view -> {
            if (this.listener == null)
                return;
            listener.onClickTravel(currentTravel);
        });
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
            departureTextView = itemView.findViewById(R.id.text_view_departure);
            arrivalTextView = itemView.findViewById(R.id.text_view_arrival);
        }
    }
}