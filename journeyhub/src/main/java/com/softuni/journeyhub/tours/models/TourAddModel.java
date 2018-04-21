package com.softuni.journeyhub.tours.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class TourAddModel {
    @NotNull
    @Size(min = 3)
    private String name;

    private List<Long> places;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getPlaces() {
        return places;
    }

    public void setPlaces(List<Long> places) {
        this.places = places;
    }
}
