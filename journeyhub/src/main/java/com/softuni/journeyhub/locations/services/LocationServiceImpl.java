package com.softuni.journeyhub.locations.services;

import com.softuni.journeyhub.locations.entities.Location;
import com.softuni.journeyhub.locations.models.LocationBindingModel;
import com.softuni.journeyhub.locations.repositories.LocationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {

    private LocationRepository locationRepository;

    private ModelMapper modelMapper;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository, ModelMapper modelMapper) {
        this.locationRepository = locationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addLocation(LocationBindingModel locationBindingModel) {
        Location location = this.modelMapper.map(locationBindingModel, Location.class);
        this.locationRepository.save(location);
    }

    @Override
    public List<LocationBindingModel> getAllLocations() {
        List<LocationBindingModel> locations = this.locationRepository.findAll()
                .stream().map(l -> this.modelMapper.map(l, LocationBindingModel.class))
                .collect(Collectors.toList());
        return locations;
    }

    @Override
    public LocationBindingModel getLocationBindingByName(String name) {
        return this.modelMapper.map(this.locationRepository.getByName(name), LocationBindingModel.class);
    }

    @Override
    public Location getLocationByName(String name) {
        return this.locationRepository.getByName(name);
    }
}
