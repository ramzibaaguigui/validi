package com.tadhkirati.validator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tadhkirati.validator.models.Ticket;

import java.util.List;

public class TravelDetailsTicketsRecyclerViewAdapter extends RecyclerView.Adapter<TravelDetailsTicketsRecyclerViewAdapter.TicketViewHolder> {
    private OnTicketItemClickListener ticketItemClickListener;
    private List<Ticket> tickets;

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TicketViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket_view, parent, false)  );
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        holder.passengerNameTextView.setText(getItem(position).getPassengerName());
        holder.boardingStationTextView.setText(getItem(position).getBoardingStationName());
        holder.container.setOnClickListener(view -> {
            if (ticketItemClickListener == null)
                return;
            ticketItemClickListener.onTicketClick(getItem(position));
        });
    }

    public Ticket getItem(int position) {
        return this.tickets.get(position);
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }


    static class TicketViewHolder extends RecyclerView.ViewHolder {
        View container;
        TextView passengerNameTextView;
        TextView boardingStationTextView;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            passengerNameTextView = itemView.findViewById(R.id.text_view_ticket_item_passenger_name);
            boardingStationTextView = itemView.findViewById(R.id.text_view_travel_item_boarding_station_name);
            container = itemView.findViewById(R.id.container_ticket_item);
        }
    }

    public void setOnTicketItemClickListener(OnTicketItemClickListener listener) {
        this.ticketItemClickListener = listener;
    }
    interface OnTicketItemClickListener {
        void onTicketClick(Ticket ticket);
    }
}
