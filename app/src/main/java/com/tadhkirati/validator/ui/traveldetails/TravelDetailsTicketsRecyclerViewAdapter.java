package com.tadhkirati.validator.ui.traveldetails;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tadhkirati.validator.R;
import com.tadhkirati.validator.models.Ticket;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class TravelDetailsTicketsRecyclerViewAdapter extends RecyclerView.Adapter<TravelDetailsTicketsRecyclerViewAdapter.TicketViewHolder> {
    private OnTicketItemClickListener ticketItemClickListener;
    private List<Ticket> tickets;


    private TravelDetailsTicketsRecyclerViewAdapter(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public static TravelDetailsTicketsRecyclerViewAdapter create(List<Ticket> tickets) {
        return new TravelDetailsTicketsRecyclerViewAdapter(tickets);
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TicketViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        final var currentTicket = getItem(position);
        holder.passengerNameTextView.setText(currentTicket.getPassengerName());
        holder.boardingStationTextView.setText(currentTicket.getBoardingStationName());

        if (currentTicket.isValidated()) {
            holder.ticketIsValidatedImageView.setImageResource(R.drawable.ic_validated);
        } else {
            holder.ticketIsValidatedImageView.setImageResource(R.drawable.ic_not_validated);
        }
        holder.container.setOnClickListener(view -> {
            if (ticketItemClickListener == null)
                return;
            ticketItemClickListener.onTicketClick(getItem(position), position);
        });


        // Animating the view upon start
        holder.container.startAnimation(
                AnimationUtils.loadAnimation(holder.container.getContext(), R.anim.bottom_to_top_animation)
        );
    }

    public Ticket getItem(int position) {
        return this.tickets.get(position);
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    public void setOnTicketItemClickListener(OnTicketItemClickListener listener) {
        this.ticketItemClickListener = listener;
    }

    public void setItem(int position, Ticket ticket) {
        this.tickets.set(position, ticket);
    }

    public void setItem(Ticket ticket) {
        ListIterator<Ticket> iterator = tickets.listIterator();
        while (iterator.hasNext()) {
            int currentIndex = iterator.nextIndex();
            Ticket current = iterator.next();
            if (current.getQrCodeToken().equals(ticket.getQrCodeToken())) {
                iterator.set(ticket);
                notifyItemChanged(currentIndex);
                return;
            }
        }
    }

    interface OnTicketItemClickListener {
        void onTicketClick(Ticket ticket, int position);
    }

    static class TicketViewHolder extends RecyclerView.ViewHolder {
        View container;
        ImageView ticketIsValidatedImageView;
        TextView passengerNameTextView;
        TextView boardingStationTextView;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            passengerNameTextView = itemView.findViewById(R.id.text_view_ticket_item_passenger_name);
            boardingStationTextView = itemView.findViewById(R.id.text_view_travel_item_arrival_station_name);
            container = itemView.findViewById(R.id.container_ticket_item);
            ticketIsValidatedImageView = itemView.findViewById(R.id.image_view_ticket_is_validated);
        }
    }

}
