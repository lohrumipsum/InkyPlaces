package com.lohrumipsum.inkyplaces.model;

import com.google.gson.annotations.SerializedName;

public class PlaceDetailsResponse {
    @SerializedName("result")
    private PlaceDetails result;

    public PlaceDetails getResult() {
        return result;
    }
}