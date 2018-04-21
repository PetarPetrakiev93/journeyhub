package com.softuni.journeyhub.tours.controllers;

import com.softuni.journeyhub.controllers.BaseController;
import com.softuni.journeyhub.tours.models.TourAddModel;
import com.softuni.journeyhub.tours.services.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class TourController extends BaseController{
    private TourService tourService;

    @Autowired
    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    @GetMapping("/tours/build")
    private ModelAndView getBuildTour(@RequestParam(value = "location", required = false) String location,
                                      ModelAndView modelAndView,
                                      Principal principal,
                                      @ModelAttribute TourAddModel tourAddBinding){
        if(location != null){
            if(location.equals("")){
                modelAndView.addObject("otherPlaces", this.tourService.getNotLikedPlacesByUser(principal.getName()));
            }else{
                modelAndView.addObject("otherPlaces", this.tourService.getPlaceByLocation(location));
            }
        }else{
            modelAndView.addObject("otherPlaces", this.tourService.getNotLikedPlacesByUser(principal.getName()));
        }
        modelAndView.addObject("likedPlaces", this.tourService.getLikedPlacesByUser(principal.getName()));
        modelAndView.addObject("tourAddBinding", new TourAddModel());
        modelAndView.setViewName("tour-build");
        return modelAndView;
    }

    @PostMapping("/tour/add")
    private ModelAndView addPlace(ModelAndView modelAndView, Principal principal,
                                  @Valid @ModelAttribute TourAddModel tourAddBinding,
                                  BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            modelAndView.addObject("likedPlaces", this.tourService.getLikedPlacesByUser(principal.getName()));
            modelAndView.addObject("otherPlaces", this.tourService.getNotLikedPlacesByUser(principal.getName()));
            modelAndView.addObject("tourAddBinding", tourAddBinding);
            modelAndView.setViewName("tour-build");
            return modelAndView;
        }
        this.tourService.addTour(tourAddBinding, principal.getName());
        return this.redirect("/tours/mine");
    }

    @GetMapping("/tours/mine")
    private ModelAndView getMineTours(ModelAndView modelAndView, Principal principal){
        modelAndView.addObject("view", "Mine tours");
        modelAndView.addObject("tours", this.tourService.getMineTours(principal.getName()));
         modelAndView.setViewName("tours");
        return modelAndView;
    }

    @GetMapping("/tours/suggested")
    private ModelAndView getSuggestedTours(ModelAndView modelAndView){
        modelAndView.addObject("view", "Suggested Tours");
        modelAndView.addObject("tours", this.tourService.getSuggested());
        modelAndView.setViewName("tours");
        return modelAndView;
    }

    @GetMapping("/tours/{id}")
    private ModelAndView getTourView(ModelAndView modelAndView, @PathVariable Long id){
        return this.view("tour-view", "tour",tourService.getTourById(id));
    }
}
