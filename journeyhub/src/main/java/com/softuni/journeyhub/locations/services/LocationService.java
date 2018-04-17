package com.softuni.journeyhub.locations.services;

import com.softuni.journeyhub.locations.models.LocationBindingModel;

import java.util.List;

public interface LocationService {
    void addLocation(LocationBindingModel locationBindingModel);

    List<LocationBindingModel> getAllLocations();

    LocationBindingModel getLocationByName(String name);
}
