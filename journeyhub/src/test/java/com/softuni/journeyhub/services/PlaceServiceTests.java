package com.softuni.journeyhub.services;


import com.softuni.journeyhub.image.services.ImageServiceImpl;
import com.softuni.journeyhub.locations.entities.Location;
import com.softuni.journeyhub.locations.services.LocationServiceImpl;
import com.softuni.journeyhub.places.entities.Place;
import com.softuni.journeyhub.places.models.PlaceAddBindingModel;
import com.softuni.journeyhub.places.repositories.PlaceRepository;
import com.softuni.journeyhub.places.services.PlaceServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PlaceServiceTests {
    @Mock
    private PlaceRepository placeRepository;

    @Mock
    private LocationServiceImpl locationService;

    @InjectMocks
    private PlaceServiceImpl placeService;

    private PlaceAddBindingModel placeAddBindingModel;

    private Location location;

    @Mock
    private ModelMapper modelMapper;


    @Before
    public void setUp() {
        this.location = new Location();
        this.location.setName("Sofia");
        this.location.setLatitude(12.43);
        this.location.setLongitude(34.55);

        this.modelMapper = new ModelMapper();

        this.placeAddBindingModel = new PlaceAddBindingModel();
        this.placeAddBindingModel.setName("Mqsto 1");
        this.placeAddBindingModel.setImages(new MultipartFile[0]);
        this.placeAddBindingModel.setLocation("Sofia");
        this.placeAddBindingModel.setText("Mnogo hubavo mqsto");

        when(this.placeRepository.save(any())).thenAnswer(a -> a.getArgument(0));

        when(this.locationService.getLocationByName(location.getName()))
                .thenReturn(location);
    }

    @Test
    public void createPlace_ValidPlace_ShouldNotReturnNull(){
        Place place = this.placeService.addPlace(this.placeAddBindingModel);

        Assert.assertNotEquals("Place is null after creation", null, place);
    }
}
