package com.lohrumipsum.inkyplaces.network;

import com.lohrumipsum.inkyplaces.model.PlaceDetailsResponse;
import com.lohrumipsum.inkyplaces.model.PlacesApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlacesApiService {
    // Corrected the endpoint to be the full path from the base URL.
    @GET("maps/api/place/textsearch/json")
    Call<PlacesApiResponse> searchPlaces(@Query("query") String query, @Query("key") String apiKey);

    // New endpoint to get details for a specific place
    @GET("maps/api/place/details/json")
    Call<PlaceDetailsResponse> getPlaceDetails(
            @Query("place_id") String placeId,
            @Query("fields") String fields,
            @Query("key") String apiKey
    );
}
