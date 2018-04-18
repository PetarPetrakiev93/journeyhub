package com.softuni.journeyhub.image.services;

import com.softuni.journeyhub.events.entities.Event;
import com.softuni.journeyhub.image.models.Image;
import com.softuni.journeyhub.places.entities.Place;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    Image uploadImage(MultipartFile file) throws IOException;
    Image uploadPlaceImage(MultipartFile file, Place place) throws IOException;
    Image uploadEventImage(MultipartFile file, Event event) throws IOException;
    void updateImage(Image image) throws IOException;
    List<Image> getAllImages();
}
