package com.lohrumipsum.inkyplaces.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class OpeningHours {
    @SerializedName("weekday_text")
    private List<String> weekdayText;

    public List<String> getWeekdayText() {
        return weekdayText;
    }
}
