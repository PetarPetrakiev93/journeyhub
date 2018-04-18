package com.softuni.journeyhub.image.services;

import com.softuni.journeyhub.events.entities.Event;
import com.softuni.journeyhub.image.cloud.CloudImageExtractor;
import com.softuni.journeyhub.image.cloud.CloudImageUploader;
import com.softuni.journeyhub.image.models.Image;
import com.softuni.journeyhub.image.repositories.ImageRepository;
import com.softuni.journeyhub.places.entities.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    private ImageRepository imageRepository;
    private CloudImageExtractor extractor;
    private CloudImageUploader uploader;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository, CloudImageExtractor extractor, CloudImageUploader uploader) {
        this.imageRepository = imageRepository;
        this.extractor = extractor;
        this.uploader = uploader;
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
    public void updateImage(Image image) throws IOException {
        this.imageRepository.save(this.extractor.updateImages(image));
    }

    @Override
    public List<Image> getAllImages() {
        return this.imageRepository.findAll();
    }
}
