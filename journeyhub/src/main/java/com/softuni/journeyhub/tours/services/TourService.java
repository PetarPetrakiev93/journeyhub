package com.softuni.journeyhub.tours.services;

import com.softuni.journeyhub.tours.entities.Tour;
import com.softuni.journeyhub.tours.models.PlaceBuildModel;
import com.softuni.journeyhub.tours.models.TourAddModel;
import com.softuni.journeyhub.tours.models.TourBindingModel;

import java.util.List;

public interface TourService {
    List<PlaceBuildModel> getLikedPlacesByUser(String username);
    List<PlaceBuildModel> getNotLikedPlacesByUser(String username);
    List<PlaceBuildModel> getPlaceByLocation(String location);
    void addTour(TourAddModel model, String username);
    List<Tour> getMineTours(String username);
    List<Tour> getSuggested();
    TourBindingModel getTourById(Long id);
    void updateSuggestedTours();
}
