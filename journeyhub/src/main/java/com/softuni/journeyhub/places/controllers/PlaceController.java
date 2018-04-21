package com.softuni.journeyhub.places.controllers;

import com.softuni.journeyhub.controllers.BaseController;
import com.softuni.journeyhub.places.models.PlaceAddBindingModel;
import com.softuni.journeyhub.places.models.PlaceEditBindingModel;
import com.softuni.journeyhub.places.services.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

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
        return this.redirect("/places/show");
    }

    @GetMapping("/places/show")
    private ModelAndView showPlaces(@PageableDefault(size = 9)Pageable pageable){
        return this.view("show-places", "places", this.placeService.getAllPlacesByPage(pageable));
    }
    @GetMapping("/places/show/{id}")
    private ModelAndView infoPlaces(ModelAndView modelAndView, @PathVariable Long id, Principal principal){
        return this.view("place-info", "place", this.placeService.getPlaceInfo(id , principal.getName()));
    }

    @GetMapping("/places/edit/{id}")
    private ModelAndView getEditPlaces(ModelAndView modelAndView, @PathVariable Long id){
        modelAndView.addObject("locations", this.placeService.getLocationsNames());
        modelAndView.addObject("placeEdit", this.placeService.getEditPlace(id));
        modelAndView.setViewName("edit-place");
        return modelAndView;
    }

    @PostMapping("/places/edit/{id}")
    private ModelAndView editPlace(ModelAndView modelAndView,
                                   @Valid @ModelAttribute PlaceEditBindingModel placeEditBindingModel,
                                   BindingResult bindingResult,
                                   @PathVariable String id) {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("locations", this.placeService.getLocationsNames());
            modelAndView.setViewName("edit-place");
            return modelAndView;
        }
        this.placeService.editPlace(placeEditBindingModel);
        return this.redirect("/places/show");
    }

    @GetMapping("/places/delete/{id}")
    private ModelAndView deletePlaces(ModelAndView modelAndView, @PathVariable Long id){
        this.placeService.delete(id);
        return this.redirect("/places/show");
    }

    @GetMapping("/places/like/{id}")
    private ModelAndView likePlaces(ModelAndView modelAndView, @PathVariable Long id, Principal principal){
        this.placeService.likePlace(id, principal.getName());
        return this.redirect("/places/show/"+ id);
    }
}
