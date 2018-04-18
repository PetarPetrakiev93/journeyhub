package com.softuni.journeyhub.events.entities;

import com.softuni.journeyhub.image.models.Image;
import com.softuni.journeyhub.locations.entities.Location;
import com.softuni.journeyhub.tours.entities.Tour;
import com.softuni.journeyhub.users.entities.User;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String text;

    private String type;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    private Double rating;

    private Long ratingUpdates;

    @OneToMany(mappedBy = "event", targetEntity = Image.class, cascade = CascadeType.ALL)
    private List<Image> images;

    @ManyToMany(mappedBy = "events", targetEntity = Tour.class, cascade = CascadeType.ALL)
    private List<Tour> tours;

    @ManyToMany(mappedBy = "likedEvents", targetEntity = User.class, cascade = CascadeType.ALL)
    private List<User> users;

    public Event() {
        this.rating = 0.0D;
        this.ratingUpdates = 0L;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public Long getRatingUpdates() {
        return ratingUpdates;
    }

    public void setRatingUpdates(Long ratingUpdates) {
        this.ratingUpdates = ratingUpdates;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
