package com.softuni.journeyhub.places.controllers;

import com.softuni.journeyhub.controllers.BaseController;
import com.softuni.journeyhub.places.models.PlaceAddBindingModel;
import com.softuni.journeyhub.places.services.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class PlaceController extends BaseController{

    private PlaceService placeService;

    @Autowired
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping("/place/add")
    private ModelAndView getAddPlace(ModelAndView modelAndView, @ModelAttribute PlaceAddBindingModel placeAddBindingModel){
        return this.view("add-place", "locations", this.placeService.getLocationsNames());
    }

    @PostMapping("/place/add")
    private ModelAndView addPlace(ModelAndView modelAndView,
                                  @Valid @ModelAttribute PlaceAddBindingModel placeAddBindingModel,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return this.view("add-place", "locations", this.placeService.getLocationsNames());
        }
        this.placeService.addPlace(placeAddBindingModel);
        return this.redirect("/places");
    }
}
