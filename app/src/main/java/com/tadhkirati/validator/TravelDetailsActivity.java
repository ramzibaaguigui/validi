package com.tadhkirati.validator;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class TravelDetailsActivity extends AppCompatActivity {


    private RecyclerView ticketsRecyclerView;
    private TextView travelTitleTextView;
    private ImageButton searchTicketsImageButton;
    private CheckBox showValidatedCheckbox;
    private CheckBox showNonValidatedCheckbox;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_details);
        initViews();

    }

    private void initViews() {
        ticketsRecyclerView = findViewById(R.id.recycler_view_tickets);
        travelTitleTextView = findViewById(R.id.text_view_travel_title);
        searchTicketsImageButton = findViewById(R.id.image_button_search_tickets);
        showValidatedCheckbox = findViewById(R.id.checkbox_show_validated);
        showNonValidatedCheckbox = findViewById(R.id.checkbox_show_non_validated);
    }

}
