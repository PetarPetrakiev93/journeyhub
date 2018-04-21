package com.softuni.journeyhub.tours.services;

import com.softuni.journeyhub.locations.entities.Location;
import com.softuni.journeyhub.places.entities.Place;
import com.softuni.journeyhub.places.models.PlaceEditBindingModel;
import com.softuni.journeyhub.places.services.PlaceService;
import com.softuni.journeyhub.tours.entities.Tour;
import com.softuni.journeyhub.tours.exception.TourNotFoundException;
import com.softuni.journeyhub.tours.models.PlaceBuildModel;
import com.softuni.journeyhub.tours.models.TourAddModel;
import com.softuni.journeyhub.tours.models.TourBindingModel;
import com.softuni.journeyhub.tours.repositories.TourRepository;
import com.softuni.journeyhub.users.services.UserService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TourServiceImpl implements TourService {
    private TourRepository tourRepository;
    private UserService userService;
    private PlaceService placeService;
    private ModelMapper modelMapper;

    @Autowired
    public TourServiceImpl(TourRepository tourRepository, UserService userService, PlaceService placeService, ModelMapper modelMapper) {
        this.tourRepository = tourRepository;
        this.userService = userService;
        this.placeService = placeService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<PlaceBuildModel> getLikedPlacesByUser(String username) {
        Converter<Location, String> getLocation = ctx -> ctx.getSource().getName();
        TypeMap<Place, PlaceBuildModel> placeTypeMap = this.modelMapper.typeMap(Place.class, PlaceBuildModel.class);
        placeTypeMap.addMappings(mapper -> mapper.using(getLocation).map(Place::getLocation, PlaceBuildModel::setLocation));
        return this.placeService.getLikedPlacesByUser(username).stream().map(
                placeTypeMap::map
        ).collect(Collectors.toList());
    }

    @Override
    public List<PlaceBuildModel> getNotLikedPlacesByUser(String username) {
        Converter<Location, String> getLocation = ctx -> ctx.getSource().getName();
        TypeMap<Place, PlaceBuildModel> placeTypeMap = this.modelMapper.typeMap(Place.class, PlaceBuildModel.class);
        placeTypeMap.addMappings(mapper -> mapper.using(getLocation).map(Place::getLocation, PlaceBuildModel::setLocation));
        return this.placeService.getNotLikedPlacesByUser(username).stream().map(
                placeTypeMap::map
        ).collect(Collectors.toList());
    }

    @Override
    public List<PlaceBuildModel> getPlaceByLocation(String location) {
        Converter<Location, String> getLocation = ctx -> ctx.getSource().getName();
        TypeMap<Place, PlaceBuildModel> placeTypeMap = this.modelMapper.typeMap(Place.class, PlaceBuildModel.class);
        placeTypeMap.addMappings(mapper -> mapper.using(getLocation).map(Place::getLocation, PlaceBuildModel::setLocation));
        return this.placeService.getPlaceByLocation(location).stream().map(
                placeTypeMap::map
        ).collect(Collectors.toList());
    }

    @Override
    public void addTour(TourAddModel model, String username) {
        Tour tour = new Tour();
        tour.setUser(this.userService.getUserByUsername(username));
        tour.setName(HtmlUtils.htmlEscape(model.getName()));
        tour.setPlaces(new ArrayList<>());
        for (Long placeId : model.getPlaces()) {
            tour.getPlaces().add(this.placeService.getPlaceById(placeId));
        }
        this.tourRepository.save(tour);
    }

    @Override
    public List<Tour> getMineTours(String username) {
        return this.tourRepository.getAllByUser_Username(username);
    }

    @Override
    public List<Tour> getSuggested() {
        return this.tourRepository.getAllBySuggestedTrue();
    }

    @Override
    public TourBindingModel getTourById(Long id) {
        Tour tour = this.tourRepository.getOne(id);
        try{
            Long test = tour.getId();
        }catch (Exception e){
            throw new TourNotFoundException();
        }
        TourBindingModel tourBindingModel = new TourBindingModel();
        this.modelMapper.map(tour, tourBindingModel);
        return tourBindingModel;
    }

    @Override
    public void updateSuggestedTours() {
        List<Tour> allTours = this.tourRepository.findAll();
        allTours.forEach(t -> {
            t.setSuggested(false);
            tourRepository.save(t);
        });
        List<Place> topPlaces = this.placeService.getTopRated();

        for (Place topPlace : topPlaces) {
            for (Tour tour : allTours) {
                for(Place place : tour.getPlaces()){
                    if(place.getId().equals(topPlace.getId())){
                        tour.setSuggested(true);
                        tourRepository.save(tour);
                    }
                }
            }
        }
    }
}
