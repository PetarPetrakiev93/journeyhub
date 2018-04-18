package com.softuni.journeyhub.places.services;

import com.softuni.journeyhub.image.services.ImageService;
import com.softuni.journeyhub.locations.entities.Location;
import com.softuni.journeyhub.locations.models.LocationBindingModel;
import com.softuni.journeyhub.locations.services.LocationService;
import com.softuni.journeyhub.places.entities.Place;
import com.softuni.journeyhub.places.models.PlaceAddBindingModel;
import com.softuni.journeyhub.places.repositories.PlaceRepository;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaceServiceImpl implements PlaceService {
    private PlaceRepository placeRepository;

    private LocationService locationService;

    private ImageService imageService;

    private ModelMapper modelMapper;

    @Autowired
    public PlaceServiceImpl(PlaceRepository placeRepository, LocationService locationService, ImageService imageService, ModelMapper modelMapper) {
        this.placeRepository = placeRepository;
        this.locationService = locationService;
        this.imageService = imageService;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<String> getLocationsNames() {
        List<LocationBindingModel> locations = this.locationService.getAllLocations();
        return locations.stream().map(l -> l.getName()).collect(Collectors.toList());
    }

    @Override
    public void addPlace(PlaceAddBindingModel model) {
        Converter<String, Location> getLocation = ctx -> this.locationService.getLocationByName(ctx.getSource());
        TypeMap<PlaceAddBindingModel, Place> placeTypeMap = this.modelMapper.typeMap(PlaceAddBindingModel.class, Place.class);
        placeTypeMap.addMappings(mapper -> mapper.using(getLocation).map(PlaceAddBindingModel::getLocation, Place::setLocation));
        Place place = placeTypeMap.map(model);
        place.setImages(new ArrayList<>());
        place = this.placeRepository.save(place);
        if(model.getImages() != null) {
            for (MultipartFile multipartFile : model.getImages()) {
                try {
                    this.imageService.uploadPlaceImage(multipartFile, place);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
