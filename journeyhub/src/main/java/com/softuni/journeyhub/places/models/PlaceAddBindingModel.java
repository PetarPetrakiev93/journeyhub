package com.softuni.journeyhub.places.models;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class PlaceAddBindingModel {
    @NotNull
    @Size(min = 5, message = "Name should be at least 5 symbols.")
    private String name;

    private String text;

    @NotNull
    private String location;

    private MultipartFile[] images;

    public PlaceAddBindingModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public MultipartFile[] getImages() {
        return images;
    }

    public void setImages(MultipartFile[] images) {
        this.images = images;
    }
}
