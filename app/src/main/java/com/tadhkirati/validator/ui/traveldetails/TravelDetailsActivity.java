package com.tadhkirati.validator.ui.traveldetails;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ProgressBar;
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
import com.tadhkirati.validator.ui.validator.travels.TravelsFragment;

public class TravelDetailsActivity extends AppCompatActivity {


    private RecyclerView ticketsRecyclerView;
    private TextView travelTitleTextView;
    private ImageButton searchTicketsImageButton;
    private CheckBox showValidatedCheckbox;
    private CheckBox showNonValidatedCheckbox;
    private TravelDetailsViewModel travelsViewModel;

    private ProgressBar loadingProgressBar;
    private View loadingErrorContainer;
    private View connectivityErrorContainer;

    private TravelDetailsTicketsRecyclerViewAdapter ticketsAdapter;
    private TicketDetailsBottomSheetDialogFragment ticketBottomFragment;
    private Travel loadedTravel;
    private Long travelId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_details);
        initViewModel();
        initViews();
        getLoadedTravel();

        observeTicketsState();
        loadTickets();
        observeTicketValidationState();
    }

    private void initViews() {
        ticketsRecyclerView = findViewById(R.id.recycler_view_tickets);
        travelTitleTextView = findViewById(R.id.text_view_travel_title);
        searchTicketsImageButton = findViewById(R.id.image_button_search_tickets);
        showValidatedCheckbox = findViewById(R.id.checkbox_show_validated);
        showNonValidatedCheckbox = findViewById(R.id.checkbox_show_non_validated);

        loadingProgressBar = findViewById(R.id.progress_bar_tickets);
        loadingErrorContainer = findViewById(R.id.container_loading_error);
        connectivityErrorContainer = findViewById(R.id.container_connectivity_error);


    }

    private void getLoadedTravel() {
        Bundle bundle = getIntent().getExtras();
        // Log.i("LOADED_TRAVEL", String.valueOf((Travel) bundle.getSerializable(TravelsFragment.LOADED_TRAVEL_KEY)));
        Log.i("LOADED_TRAVEL_ID", String.valueOf(bundle.getLong("LOADED_TRAVEL_ID")));
        this.travelId = bundle.getLong("LOADED_TRAVEL_ID");
        this.loadedTravel = (Travel) bundle.getSerializable(TravelsFragment.LOADED_TRAVEL_KEY);
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
            public void onTicketClick(Ticket ticket, int position) {


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
        loadingProgressBar.setVisibility(View.GONE);
        connectivityErrorContainer.setVisibility(View.GONE);
        ticketsRecyclerView.setVisibility(View.VISIBLE);
        loadingErrorContainer.setVisibility(View.GONE);

        ticketsAdapter = TravelDetailsTicketsRecyclerViewAdapter.create(travelsViewModel.getTickets());
        ticketsAdapter.setOnTicketItemClickListener(new TravelDetailsTicketsRecyclerViewAdapter.OnTicketItemClickListener() {
            @Override
            public void onTicketClick(Ticket ticket, int position) {
                ticketBottomFragment = TicketDetailsBottomSheetDialogFragment
                        .create(ticket, position);
                ticketBottomFragment.setOnValidateTicketListener(new TicketDetailsBottomSheetDialogFragment.OnValidateTicketListener() {
                    @Override
                    public void onValidate(Ticket ticket, int ticketPosition) {
                        travelsViewModel.validateTicket(
                                LoginUtils.formTokenHeader(TravelDetailsActivity.this),
                                ticket.getQrCodeToken(), travelId, ticketPosition);
                    }
                });
                ticketBottomFragment.show(getSupportFragmentManager(), "TICKET_DETAILS_FRAGMENT");
            }
        });
        ticketsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ticketsRecyclerView.setAdapter(ticketsAdapter);
    }

    private void handleTicketsLoadingProgress() {
        loadingProgressBar.setVisibility(View.VISIBLE);
        connectivityErrorContainer.setVisibility(View.GONE);
        loadingErrorContainer.setVisibility(View.GONE);
        ticketsRecyclerView.setVisibility(View.GONE);

        Toast.makeText(this, "loading progress", Toast.LENGTH_SHORT).show();

    }

    private void handleTicketsLoadingError() {
        loadingProgressBar.setVisibility(View.GONE);
        connectivityErrorContainer.setVisibility(View.GONE);
        ticketsRecyclerView.setVisibility(View.GONE);
        loadingErrorContainer.setVisibility(View.VISIBLE);

        Toast.makeText(this, "loading error", Toast.LENGTH_SHORT).show();
    }

    private void handleTicketsLoadingConnectivityError() {
        loadingProgressBar.setVisibility(View.GONE);
        ticketsRecyclerView.setVisibility(View.GONE);
        loadingErrorContainer.setVisibility(View.GONE);
        connectivityErrorContainer.setVisibility(View.VISIBLE);

        Toast.makeText(this, "loading connectivity error", Toast.LENGTH_SHORT).show();

    }

    private void loadTickets() {
        Long travelId = this.travelId;

        String token = LoginUtils.formTokenHeader(this);
        Log.i("TRAVEL_ID", String.valueOf(travelId));
        travelsViewModel.loadTickets(travelId, token);
    }

    private void observeTicketValidationState() {
        travelsViewModel.observeTicketValidationState(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer validationState) {
                if (validationState == TravelDetailsViewModel.STATE_VALIDATION_SUCCESS) {
                    handleTicketValidationSuccess();
                    return;
                }
                if (validationState == TravelDetailsViewModel.STATE_VALIDATION_ERROR) {
                    handleTicketValidationError();
                    return;
                }

                if (validationState == TravelDetailsViewModel.STATE_VALIDATION_PROGRESS) {
                    handleTicketValidationProgress();
                    return;
                }

                if (validationState == TravelDetailsViewModel.STATE_CONNECTIVITY_ERROR) {
                    handleTicketValidationConnectivityError();
                }
            }

            public void handleTicketValidationSuccess() {
                // there is something to execute here alongside freeing the last validated ticket
                // do it hjere
                Ticket validatedTicket = travelsViewModel.getLastValidatedTicket();
                // ticketsAdapter.notifyItemChanged(travelsViewModel.getInValidationTicketPosition(), travelsViewModel.getLastValidatedTicket());
                ticketBottomFragment.updateTicket(validatedTicket);
                ticketsAdapter.setItem(
                        travelsViewModel.getInValidationTicketPosition(),
                        travelsViewModel.getLastValidatedTicket()
                );
                ticketsAdapter.notifyItemChanged(travelsViewModel.getInValidationTicketPosition());

                Log.i("VALIDATED_TICKET", String.valueOf(validatedTicket));
                Toast.makeText(TravelDetailsActivity.this, "validated ticket: " + String.valueOf(validatedTicket), Toast.LENGTH_SHORT).show();
                travelsViewModel.freeLastValidatedTicket();
            }

            public void handleTicketValidationProgress() {
                Toast.makeText(TravelDetailsActivity.this, "ticket validation progress", Toast.LENGTH_SHORT)
                        .show();
            }

            public void handleTicketValidationError() {
                Toast.makeText(TravelDetailsActivity.this, "ticket validation error", Toast.LENGTH_SHORT)
                        .show();
            }

            public void handleTicketValidationConnectivityError() {
                Toast.makeText(TravelDetailsActivity.this, "ticket validation connectivity error", Toast.LENGTH_SHORT)
                        .show();
            }

        });
    }
}
