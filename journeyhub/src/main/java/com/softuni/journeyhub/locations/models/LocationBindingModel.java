package com.softuni.journeyhub.locations.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LocationBindingModel {
    @NotNull
    @Size(min = 2, message = "Name should be at least 2 symbols.")
    private String name;

    private Double longitude;

    private Double latitude;

    public LocationBindingModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
