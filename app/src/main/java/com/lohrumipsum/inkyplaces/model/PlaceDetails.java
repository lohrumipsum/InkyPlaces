package com.lohrumipsum.inkyplaces.model;

import com.google.gson.annotations.SerializedName;

public class PlaceDetails {
    private String name;
    @SerializedName("formatted_address")
    private String formattedAddress;
    @SerializedName("formatted_phone_number")
    private String formattedPhoneNumber;
    @SerializedName("opening_hours")
    private OpeningHours openingHours;

    public String getName() {
        return name;
    }
    public String getFormattedAddress() {
        return formattedAddress;
    }
    public String getFormattedPhoneNumber() {
        return formattedPhoneNumber;
    }
    public OpeningHours getOpeningHours() {
        return openingHours;
    }
}