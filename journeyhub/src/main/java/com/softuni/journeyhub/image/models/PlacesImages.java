package com.softuni.journeyhub.image.models;

import java.util.ArrayList;
import java.util.List;

public class PlacesImages {
    private List<PlaceImage> placeImages;

    public PlacesImages() {
        this.placeImages = new ArrayList<>();
    }

    public List<PlaceImage> getPlaceImages() {
        return placeImages;
    }

    public void setPlaceImages(List<PlaceImage> placeImages) {
        this.placeImages = placeImages;
    }
}
