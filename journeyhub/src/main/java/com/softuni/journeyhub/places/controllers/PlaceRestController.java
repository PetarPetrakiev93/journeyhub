package com.softuni.journeyhub.places.controllers;

import com.softuni.journeyhub.places.models.RatingModel;
import com.softuni.journeyhub.places.services.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/places")
public class PlaceRestController {
    private PlaceService placeService;

    @Autowired
    public PlaceRestController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @RequestMapping(value = "/{id}/rating", method = RequestMethod.POST, produces="application/json")
    private ResponseEntity<RatingModel> updatePlaceRating(@PathVariable Long id, @RequestBody RatingModel ratingModel){
        return new ResponseEntity<RatingModel>(placeService.updateRaiting(id, ratingModel), HttpStatus.OK);
    }
}
