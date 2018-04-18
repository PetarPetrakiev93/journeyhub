package com.softuni.journeyhub.places.services;

import com.softuni.journeyhub.places.models.PlaceAddBindingModel;

import java.util.List;

public interface PlaceService {
    List<String> getLocationsNames();
    void addPlace(PlaceAddBindingModel place);
}
