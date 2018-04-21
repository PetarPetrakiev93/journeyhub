package com.softuni.journeyhub.image.services;

import com.softuni.journeyhub.events.entities.Event;
import com.softuni.journeyhub.image.entity.Image;
import com.softuni.journeyhub.image.models.PlaceIdModel;
import com.softuni.journeyhub.image.models.PlaceImage;
import com.softuni.journeyhub.image.models.PlacesImages;
import com.softuni.journeyhub.places.entities.Place;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface ImageService {
    Image uploadImage(MultipartFile file) throws IOException;
    Image uploadPlaceImage(MultipartFile file, Place place) throws IOException;
    Image uploadEventImage(MultipartFile file, Event event) throws IOException;
    String getImageUrl(Long imageId) throws IOException;
    List<Image> getAllImages();
    CompletableFuture<PlaceImage> getPlaceImage(Place place);
    PlacesImages getPlacesImages(List<PlaceIdModel> places) throws ExecutionException, InterruptedException;
}
