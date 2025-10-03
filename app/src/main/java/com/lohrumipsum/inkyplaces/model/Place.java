package com.lohrumipsum.inkyplaces.model;

import com.google.gson.annotations.SerializedName;

public class Place {
    @SerializedName("place_id")
    private String placeId;
    private String name;
    private String vicinity;
    private Geometry geometry;

    public String getPlaceId() { return placeId; }
    public String getName() {
        return name;
    }
    public String getVicinity() {
        return vicinity;
    }
    public Geometry getGeometry() {
        return geometry;
    }
}