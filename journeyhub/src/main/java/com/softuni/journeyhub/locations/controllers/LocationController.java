package com.softuni.journeyhub.locations.controllers;

import com.softuni.journeyhub.controllers.BaseController;
import com.softuni.journeyhub.locations.models.LocationBindingModel;
import com.softuni.journeyhub.locations.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class LocationController extends BaseController{

    private LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/locations")
    private ModelAndView getLoacations(ModelAndView modelAndView){
        List<LocationBindingModel> locationBindingModels = this.locationService.getAllLocations();
        return this.view("locations", "locationBindingModels", locationBindingModels);
    }

    @GetMapping("/location/add")
    private ModelAndView getAddLoacation(ModelAndView modelAndView,
                                         @ModelAttribute LocationBindingModel locationBindingModel){
        return this.view("location-add");
    }

    @PostMapping("/location/add")
    private ModelAndView addLocation(ModelAndView modelAndView,
                                     @Valid @ModelAttribute LocationBindingModel locationBindingModel,
                                     BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            return this.view("location-add");
        }
        this.locationService.addLocation(locationBindingModel);

        return this.redirect("/locations");
    }
}
