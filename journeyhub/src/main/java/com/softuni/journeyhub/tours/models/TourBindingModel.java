package com.softuni.journeyhub.tours.models;

import com.softuni.journeyhub.places.entities.Place;

import java.util.List;
import java.util.Set;

public class TourBindingModel {
    private Long id;

    private String name;

    private Set<Place> places;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Place> getPlaces() {
        return places;
    }

    public void setPlaces(Set<Place> places) {
        this.places = places;
    }
}
