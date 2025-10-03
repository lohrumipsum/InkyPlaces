package com.lohrumipsum.inkyplaces;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lohrumipsum.inkyplaces.adapter.PlacesAdapter;
import com.lohrumipsum.inkyplaces.model.OpeningHours;
import com.lohrumipsum.inkyplaces.viewmodel.PlacesViewModel;

public class MainActivity extends AppCompatActivity {

    private PlacesViewModel placesViewModel;
    private EditText searchEditText;
    private RecyclerView recyclerView;
    private PlacesAdapter adapter;
    private TextView errorTextView;
    private TextView initialMessageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchEditText = findViewById(R.id.search_edit_text);
        recyclerView = findViewById(R.id.recycler_view);
        errorTextView = findViewById(R.id.error_text_view);
        initialMessageTextView = findViewById(R.id.initial_message_text_view);

        setupRecyclerView();

        placesViewModel = new ViewModelProvider(this).get(PlacesViewModel.class);
        observeViewModel();

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 2) {
                    initialMessageTextView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    placesViewModel.searchPlaces(s.toString(), getString(R.string.places_api_key));
                } else {
                    adapter.setPlaces(java.util.Collections.emptyList());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void setupRecyclerView() {
        adapter = new PlacesAdapter(place -> {
            placesViewModel.fetchPlaceDetails(place.getPlaceId(), getString(R.string.places_api_key));
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void observeViewModel() {
        placesViewModel.getPlaces().observe(this, places -> {
            if (places != null && !places.isEmpty()) {
                adapter.setPlaces(places);
                errorTextView.setVisibility(View.GONE);
            } else if (searchEditText.getText().length() > 0) {
                adapter.setPlaces(java.util.Collections.emptyList());
                errorTextView.setText(getString(R.string.no_results_found));
                errorTextView.setVisibility(View.VISIBLE);
            }
        });

        placesViewModel.getError().observe(this, error -> {
            if (error != null) {
                errorTextView.setText(error);
                errorTextView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                errorTextView.setVisibility(View.GONE);
            }
        });

        placesViewModel.getPlaceDetails().observe(this, details -> {
            if (details != null) {
                showDetailsDialog(details);
            }
        });
    }

    private void showDetailsDialog(com.lohrumipsum.inkyplaces.model.PlaceDetails details) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_place_details, null);
        builder.setView(dialogView);

        TextView name = dialogView.findViewById(R.id.details_name);
        TextView address = dialogView.findViewById(R.id.details_address);
        TextView phone = dialogView.findViewById(R.id.details_phone);
        TextView hours = dialogView.findViewById(R.id.details_hours);

        name.setText(details.getName() != null ? details.getName() : "N/A");
        address.setText(details.getFormattedAddress() != null ? details.getFormattedAddress() : "N/A");
        phone.setText(details.getFormattedPhoneNumber() != null ? details.getFormattedPhoneNumber() : "N/A");

        OpeningHours openingHours = details.getOpeningHours();
        if (openingHours != null && openingHours.getWeekdayText() != null) {
            hours.setText(String.join("\n", openingHours.getWeekdayText()));
        } else {
            hours.setText("N/A");
        }

        builder.setPositiveButton("OK", (dialog, id) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

