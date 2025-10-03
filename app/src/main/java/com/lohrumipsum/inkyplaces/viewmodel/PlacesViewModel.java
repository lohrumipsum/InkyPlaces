package com.lohrumipsum.inkyplaces.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.lohrumipsum.inkyplaces.model.Place;
import com.lohrumipsum.inkyplaces.model.PlaceDetails;
import com.lohrumipsum.inkyplaces.repository.PlacesRepository;
import java.util.List;

public class PlacesViewModel extends ViewModel {
    private final MutableLiveData<List<Place>> places = new MutableLiveData<>();
    private final MutableLiveData<PlaceDetails> placeDetails = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final PlacesRepository placesRepository;

    public PlacesViewModel() {
        placesRepository = new PlacesRepository();
    }

    public LiveData<List<Place>> getPlaces() {
        return places;
    }

    public LiveData<PlaceDetails> getPlaceDetails() {
        return placeDetails;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void searchPlaces(String query, String apiKey) {
        placesRepository.searchPlaces(query, apiKey, new PlacesRepository.PlacesCallback() {
            @Override
            public void onSuccess(List<Place> result) {
                places.setValue(result);
            }

            @Override
            public void onError(String message) {
                error.setValue(message);
            }
        });
    }

    public void fetchPlaceDetails(String placeId, String apiKey) {
        placesRepository.getPlaceDetails(placeId, apiKey, new PlacesRepository.PlaceDetailsCallback() {
            @Override
            public void onSuccess(PlaceDetails result) {
                placeDetails.setValue(result);
            }

            @Override
            public void onError(String message) {
                error.setValue(message);
            }
        });
    }
}
