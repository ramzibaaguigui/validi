package com.tadhkirati.validator.ui.traveldetails;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.tadhkirati.validator.R;
import com.tadhkirati.validator.models.Travel;
import com.tadhkirati.validator.ui.validator.travels.TravelsFragment;

public class TravelDetailsActivity extends AppCompatActivity {


    private RecyclerView ticketsRecyclerView;
    private TextView travelTitleTextView;
    private ImageButton searchTicketsImageButton;
    private CheckBox showValidatedCheckbox;
    private CheckBox showNonValidatedCheckbox;

    private Travel loadedTravel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_details);
        initViews();
        getLoadedTravel();
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
        this.loadedTravel = (Travel) bundle.getSerializable(TravelsFragment.LOADED_TRAVEL_KEY);
    }

}
