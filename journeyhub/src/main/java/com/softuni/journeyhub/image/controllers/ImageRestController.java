package com.softuni.journeyhub.image.controllers;

import com.softuni.journeyhub.image.models.PlaceIdModel;
import com.softuni.journeyhub.image.models.PlacesImages;
import com.softuni.journeyhub.image.services.ImageService;
import com.softuni.journeyhub.places.entities.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/images")
public class ImageRestController {

    private ImageService imageService;

    @Autowired
    public ImageRestController(ImageService imageService) {
        this.imageService = imageService;
    }

    @RequestMapping(value = "/places", method = RequestMethod.POST, produces="application/json")
    private ResponseEntity<PlacesImages> getPlacesImages(@RequestBody List<PlaceIdModel> placeList){
        try {
            return new ResponseEntity<PlacesImages>(this.imageService.getPlacesImages(placeList), HttpStatus.OK);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
