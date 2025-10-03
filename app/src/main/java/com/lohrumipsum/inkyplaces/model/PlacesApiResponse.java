package com.lohrumipsum.inkyplaces.model;

import java.util.List;

public class PlacesApiResponse {
    private List<Place> results;
    private String status;

    // Getters
    public List<Place> getResults() {
        return results;
    }

    public String getStatus() {
        return status;
    }
}
