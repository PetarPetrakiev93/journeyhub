package com.softuni.journeyhub.image.services;

import com.softuni.journeyhub.events.entities.Event;
import com.softuni.journeyhub.image.cloud.CloudImageExtractor;
import com.softuni.journeyhub.image.cloud.CloudImageUploader;
import com.softuni.journeyhub.image.entity.Image;
import com.softuni.journeyhub.image.models.PlaceIdModel;
import com.softuni.journeyhub.image.models.PlaceImage;
import com.softuni.journeyhub.image.models.PlacesImages;
import com.softuni.journeyhub.image.repositories.ImageRepository;
import com.softuni.journeyhub.places.entities.Place;
import com.softuni.journeyhub.places.repositories.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class ImageServiceImpl implements ImageService {
    private ImageRepository imageRepository;
    private CloudImageExtractor extractor;
    private CloudImageUploader uploader;
    private PlaceRepository placeRepository;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository, CloudImageExtractor extractor, CloudImageUploader uploader, PlaceRepository placeRepository) {
        this.imageRepository = imageRepository;
        this.extractor = extractor;
        this.uploader = uploader;
        this.placeRepository = placeRepository;
    }

    @Override
    public Image uploadImage(MultipartFile image) throws IOException {
        return this.imageRepository.save(
                this.extractor.getImage(
                        this.uploader.uploadFile(image)));
    }

    @Override
    public Image uploadPlaceImage(MultipartFile file, Place place) throws IOException {
        Image image = this.extractor.getImage(
                this.uploader.uploadFile(file));
        image.setPlace(place);
        return this.imageRepository.save(image);
    }

    @Override
    public Image uploadEventImage(MultipartFile file, Event event) throws IOException {
        Image image = this.extractor.getImage(
                this.uploader.uploadFile(file));
        image.setEvent(event);
        return this.imageRepository.save(image);
    }

    @Override
    public String getImageUrl(Long imageId) throws IOException {
        return this.extractor.getImageUrl(this.imageRepository.getOne(imageId));
    }


    @Override
    public List<Image> getAllImages() {
        return this.imageRepository.findAll();
    }


    @Override
    @Async
    public CompletableFuture<PlaceImage> getPlaceImage(Place place){
        PlaceImage placeImage = new PlaceImage();
        placeImage.setPlaceId(place.getId());
        try {
            placeImage.setUrl(this.extractor.getImageUrl(place.getImages().get(0)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(placeImage);
    }

    @Override
    public PlacesImages getPlacesImages(List<PlaceIdModel> places) throws ExecutionException, InterruptedException {
        PlacesImages placesImages = new PlacesImages();
        List<CompletableFuture<PlaceImage>> completableFutures = new ArrayList<>();
        try {
            this.extractor.updateToken();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (PlaceIdModel place : places) {
            Place p = this.placeRepository.getOne(place.getId());
            if(p.getImages().size()>0){
                completableFutures.add(this.getPlaceImage(p));
            }
        }
        CompletableFuture<PlaceImage>[] future = new CompletableFuture[completableFutures.size()];
        future = completableFutures.toArray(future);
        CompletableFuture.allOf(future).join();
        for (CompletableFuture<PlaceImage> placeImageCompletableFuture : future) {
            placesImages.getPlaceImages().add(placeImageCompletableFuture.get());
        }
        return placesImages;
    }
}
