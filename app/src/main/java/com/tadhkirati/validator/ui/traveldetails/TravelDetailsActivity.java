package com.tadhkirati.validator.ui.traveldetails;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tadhkirati.validator.R;
import com.tadhkirati.validator.models.Ticket;
import com.tadhkirati.validator.models.Travel;
import com.tadhkirati.validator.ui.login.LoginUtils;
import com.tadhkirati.validator.ui.login.UserLoginSharedPreferences;
import com.tadhkirati.validator.ui.validator.travels.TravelsFragment;

public class TravelDetailsActivity extends AppCompatActivity {


    private RecyclerView ticketsRecyclerView;
    private TextView travelTitleTextView;
    private ImageButton searchTicketsImageButton;
    private CheckBox showValidatedCheckbox;
    private CheckBox showNonValidatedCheckbox;
    private TravelDetailsViewModel travelsViewModel;

    private TravelDetailsTicketsRecyclerViewAdapter ticketsAdapter;

    private Travel loadedTravel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_details);
        initViewModel();
        initViews();
        getLoadedTravel();

        observeTicketsState();
        loadTickets();
    }

    private void initViews() {
        ticketsRecyclerView = findViewById(R.id.recycler_view_tickets);
        travelTitleTextView = findViewById(R.id.text_view_travel_title);
        searchTicketsImageButton = findViewById(R.id.image_button_search_tickets);
        showValidatedCheckbox = findViewById(R.id.checkbox_show_validated);
        showNonValidatedCheckbox = findViewById(R.id.checkbox_show_non_validated);
    }

    private void getLoadedTravel() {
        Bundle bundle = getIntent().getExtras();
        this.loadedTravel = (Travel) bundle.getParcelable(TravelsFragment.LOADED_TRAVEL_KEY);
    }

    private void initViewModel() {
        travelsViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())
                .create(TravelDetailsViewModel.class);
    }

    private void initRecyclerView() {
        ticketsAdapter = TravelDetailsTicketsRecyclerViewAdapter
                .create(travelsViewModel.getTickets());
        ticketsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                ticketsAdapter.setOnTicketItemClickListener(new TravelDetailsTicketsRecyclerViewAdapter.OnTicketItemClickListener() {
            @Override
            public void onTicketClick(Ticket ticket) {

            }
        });
        ticketsRecyclerView.setAdapter(ticketsAdapter);

    }

    private void observeTicketsState() {
        travelsViewModel.observeState(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer currentState) {
                if (currentState == TravelDetailsViewModel.STATE_LOADED_SUCCESSFULLY) {
                    handleTicketsLoadedSuccessfully();
                    return;
                }

                if (currentState == TravelDetailsViewModel.STATE_LOADING_PROGRESS) {
                    handleTicketsLoadingProgress();
                    return;
                }

                if (currentState == TravelDetailsViewModel.STATE_LOADING_ERROR) {
                    handleTicketsLoadingError();
                    return;
                }

                if (currentState == TravelDetailsViewModel.STATE_CONNECTIVITY_ERROR) {
                    handleTicketsLoadingConnectivityError();
                }
            }
        });
    }

    private void handleTicketsLoadedSuccessfully() {
        ticketsAdapter = TravelDetailsTicketsRecyclerViewAdapter.create(travelsViewModel.getTickets());
        ticketsAdapter.setOnTicketItemClickListener(new TravelDetailsTicketsRecyclerViewAdapter.OnTicketItemClickListener() {
            @Override
            public void onTicketClick(Ticket ticket) {

            }
        });
        ticketsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ticketsRecyclerView.setAdapter(ticketsAdapter);
    }

    private void handleTicketsLoadingProgress() {
        Toast.makeText(this, "loading progress", Toast.LENGTH_SHORT).show();
    }

    private void handleTicketsLoadingError() {
        Toast.makeText(this, "loading error", Toast.LENGTH_SHORT).show();
    }

    private void handleTicketsLoadingConnectivityError() {
        Toast.makeText(this, "loading connectivity error", Toast.LENGTH_SHORT).show();

    }

    private void loadTickets() {
        Long travelId = loadedTravel.getId();
        String token = LoginUtils.formTokenHeader(this);
        travelsViewModel.loadTickets(1L, token);
    }
}
