package com.softuni.journeyhub.locations.controllers;

import com.softuni.journeyhub.locations.models.LocationRestModel;
import com.softuni.journeyhub.locations.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/location")
public class LocationRestController {
    private LocationService locationService;

    @Autowired
    public LocationRestController(LocationService locationService) {
        this.locationService = locationService;
    }
    @RequestMapping(value = "/{name}", method = RequestMethod.GET, produces="application/json")
    private ResponseEntity<LocationRestModel> getLocation(@PathVariable String name){
        return new ResponseEntity<LocationRestModel>(this.locationService.getRestLocation(name), HttpStatus.OK);
    }
}
