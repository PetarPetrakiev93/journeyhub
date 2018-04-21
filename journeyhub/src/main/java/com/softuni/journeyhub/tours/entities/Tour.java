package com.softuni.journeyhub.tours.entities;

import com.softuni.journeyhub.places.entities.Place;
import com.softuni.journeyhub.users.entities.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tours")
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "tour_place",
            joinColumns = @JoinColumn(name = "tour_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "place_id", referencedColumnName = "id"))
    private List<Place> places;

    private Boolean suggested;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Tour() {
        suggested = false;
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

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public Boolean getSuggested() {
        return suggested;
    }

    public void setSuggested(Boolean suggested) {
        this.suggested = suggested;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
