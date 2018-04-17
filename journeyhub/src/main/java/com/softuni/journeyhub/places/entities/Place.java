package com.softuni.journeyhub.places.entities;

import com.softuni.journeyhub.image.models.Image;
import com.softuni.journeyhub.locations.entities.Location;
import com.softuni.journeyhub.tours.entities.Tour;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "places")
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String text;

    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    private Double rating;

    @OneToMany(mappedBy = "place", targetEntity = Image.class, cascade = CascadeType.ALL)
    private List<Image> images;

    @ManyToMany(mappedBy = "places", targetEntity = Tour.class, cascade = CascadeType.ALL)
    private List<Tour> tours;

    public Place() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Tour> getTours() {
        return tours;
    }

    public void setTours(List<Tour> tours) {
        this.tours = tours;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
