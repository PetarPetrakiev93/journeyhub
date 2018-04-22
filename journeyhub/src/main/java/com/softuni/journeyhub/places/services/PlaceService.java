package com.softuni.journeyhub.places.services;

import com.softuni.journeyhub.places.entities.Place;
import com.softuni.journeyhub.places.models.PlaceAddBindingModel;
import com.softuni.journeyhub.places.models.PlaceEditBindingModel;
import com.softuni.journeyhub.places.models.PlaceShowBindingModel;
import com.softuni.journeyhub.places.models.RatingModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PlaceService {
    List<String> getLocationsNames();
    Place addPlace(PlaceAddBindingModel place);
    Page<Place> getAllPlacesByPage(Pageable pageable);
    Place getPlaceById(Long id);
    PlaceShowBindingModel getPlaceInfo(Long id,String name);
    PlaceEditBindingModel getEditPlace(Long id);
    void editPlace(PlaceEditBindingModel editBindingModel);
    void delete(Long id);
    void likePlace(Long id, String username);
    RatingModel updateRaiting(Long id, RatingModel ratingModel);
    List<Place> getLikedPlacesByUser(String username);
    List<Place> getNotLikedPlacesByUser(String username);
    List<Place> getPlaceByLocation(String location);
    List<Place> getTopRated();
}
