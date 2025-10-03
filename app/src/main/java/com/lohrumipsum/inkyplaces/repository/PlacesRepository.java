package com.lohrumipsum.inkyplaces.repository;

import androidx.annotation.NonNull;
import com.lohrumipsum.inkyplaces.model.Place;
import com.lohrumipsum.inkyplaces.model.PlaceDetails;
import com.lohrumipsum.inkyplaces.model.PlaceDetailsResponse;
import com.lohrumipsum.inkyplaces.model.PlacesApiResponse;
import com.lohrumipsum.inkyplaces.network.PlacesApiService;
import com.lohrumipsum.inkyplaces.network.RetrofitClient;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlacesRepository {
    private final PlacesApiService placesApiService;

    // Callback for search results
    public interface PlacesCallback {
        void onSuccess(List<Place> places);
        void onError(String message);
    }

    // Callback for place details
    public interface PlaceDetailsCallback {
        void onSuccess(PlaceDetails placeDetails);
        void onError(String message);
    }


    public PlacesRepository() {
        this.placesApiService = RetrofitClient.getClient().create(PlacesApiService.class);
    }

    public void searchPlaces(String query, String apiKey, final PlacesCallback callback) {
        Call<PlacesApiResponse> call = placesApiService.searchPlaces(query, apiKey);
        call.enqueue(new Callback<PlacesApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<PlacesApiResponse> call, @NonNull Response<PlacesApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getResults());
                } else {
                    callback.onError("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<PlacesApiResponse> call, @NonNull Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void getPlaceDetails(String placeId, String apiKey, final PlaceDetailsCallback callback) {
        String fields = "name,formatted_address,formatted_phone_number,opening_hours";
        Call<PlaceDetailsResponse> call = placesApiService.getPlaceDetails(placeId, fields, apiKey);

        call.enqueue(new Callback<PlaceDetailsResponse>() {
            @Override
            public void onResponse(@NonNull Call<PlaceDetailsResponse> call, @NonNull Response<PlaceDetailsResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getResult() != null) {
                    callback.onSuccess(response.body().getResult());
                } else {
                    callback.onError("Error fetching details: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<PlaceDetailsResponse> call, @NonNull Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
}
