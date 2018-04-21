package com.softuni.journeyhub.places.services;

import com.softuni.journeyhub.image.entity.Image;
import com.softuni.journeyhub.image.services.ImageService;
import com.softuni.journeyhub.locations.entities.Location;
import com.softuni.journeyhub.locations.models.LocationBindingModel;
import com.softuni.journeyhub.locations.services.LocationService;
import com.softuni.journeyhub.places.entities.Place;
import com.softuni.journeyhub.places.exceptions.PlaceNotFoundException;
import com.softuni.journeyhub.places.models.PlaceAddBindingModel;
import com.softuni.journeyhub.places.models.PlaceEditBindingModel;
import com.softuni.journeyhub.places.models.PlaceShowBindingModel;
import com.softuni.journeyhub.places.repositories.PlacePagingRepository;
import com.softuni.journeyhub.places.repositories.PlaceRepository;
import com.softuni.journeyhub.users.entities.User;
import com.softuni.journeyhub.users.services.UserService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    private PlacePagingRepository placePagingRepository;

    private UserService userService;

    @Autowired
    public PlaceServiceImpl(PlaceRepository placeRepository, LocationService locationService, ImageService imageService, ModelMapper modelMapper, PlacePagingRepository placePagingRepository, UserService userService) {
        this.placeRepository = placeRepository;
        this.locationService = locationService;
        this.imageService = imageService;
        this.modelMapper = modelMapper;
        this.placePagingRepository = placePagingRepository;
        this.userService = userService;
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
        if(model.getImages().length == 1 && !model.getImages()[0].getOriginalFilename().equals("")) {
            for (MultipartFile multipartFile : model.getImages()) {
                try {
                    this.imageService.uploadPlaceImage(multipartFile, place);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Page<Place> getAllPlacesByPage(Pageable pageable){
        return this.placePagingRepository.findAll(pageable);
    }

    @Override
    public Place getPlaceById(Long id) {
        return this.placeRepository.getOne(id);
    }

    @Override
    public PlaceShowBindingModel getPlaceInfo(Long id, String name) {
        Converter<Location, String> getLocation = ctx -> ctx.getSource().getName();
        TypeMap<Place, PlaceShowBindingModel> placeTypeMap = this.modelMapper.typeMap(Place.class, PlaceShowBindingModel.class);
        placeTypeMap.addMappings(mapper -> mapper.using(getLocation).map(Place::getLocation, PlaceShowBindingModel::setLocation));
        Place place = this.placeRepository.getOne(id);
        try {
            Long test = place.getId();
        }catch (Exception e){
            throw new PlaceNotFoundException();
        }
        PlaceShowBindingModel placeShowBindingModel = placeTypeMap.map(place);
        boolean isLiked = false;
        for (User user : place.getUsers()) {
            if(user.getUsername().equals(name)){
                isLiked = true;
                break;
            }
        }
        placeShowBindingModel.setLiked(isLiked);
        placeShowBindingModel.setImages(new ArrayList<>());
        for (Image image : place.getImages()) {
            try {
                placeShowBindingModel.getImages().add(this.imageService.getImageUrl(image.getId()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return placeShowBindingModel;
    }

    @Override
    public PlaceEditBindingModel getEditPlace(Long id) {
        Converter<Location, String> getLocation = ctx -> ctx.getSource().getName();
        TypeMap<Place, PlaceEditBindingModel> placeTypeMap = this.modelMapper.typeMap(Place.class, PlaceEditBindingModel.class);
        placeTypeMap.addMappings(mapper -> mapper.using(getLocation).map(Place::getLocation, PlaceEditBindingModel::setLocation));
        Place place = this.placeRepository.getOne(id);
        try {
            Long test = place.getId();
        }catch (Exception e){
            throw new PlaceNotFoundException();
        }
        place.setImages(new ArrayList<>());
        PlaceEditBindingModel placeEditBindingModel = placeTypeMap.map(place);
        placeEditBindingModel.setImages(new MultipartFile[0]);
        return placeEditBindingModel;
    }

    @Override
    public void editPlace(PlaceEditBindingModel editBindingModel){
        Converter<String, Location> getLocation = ctx -> this.locationService.getLocationByName(ctx.getSource());
        TypeMap<PlaceEditBindingModel, Place> placeTypeMap = this.modelMapper.typeMap(PlaceEditBindingModel.class, Place.class);
        placeTypeMap.addMappings(mapper -> mapper.using(getLocation).map(PlaceEditBindingModel::getLocation, Place::setLocation));
        Place place = placeTypeMap.map(editBindingModel);
        place.setImages(this.placeRepository.getOne(place.getId()).getImages());
        place = this.placeRepository.save(place);
        if(editBindingModel.getImages().length == 1 && !editBindingModel.getImages()[0].getOriginalFilename().equals("")) {
            for (MultipartFile multipartFile : editBindingModel.getImages()) {
                try {
                    this.imageService.uploadPlaceImage(multipartFile, place);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void delete(Long id){
        this.placeRepository.delete(this.placeRepository.getOne(id));
    }

    @Override
    public void likePlace(Long id, String username) {
        Place place = this.placeRepository.getOne(id);
        User user = this.userService.getUserByUsername(username);

        user.getLikedPlaces().add(place);
        this.userService.update(user);
    }

}
