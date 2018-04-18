package com.softuni.journeyhub.image.models;

import com.softuni.journeyhub.events.entities.Event;
import com.softuni.journeyhub.places.entities.Place;

import javax.persistence.*;

@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String url;

    private String cloudId;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "place_id", referencedColumnName = "id")
    private Place place;

    public Image() {
    }

    public Image(String name, String url, String cloudId) {
        this.setName(name);
        this.setUrl(url);
        this.setCloudId(cloudId);
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public String getCloudId() {
        return cloudId;
    }

    public void setCloudId(String cloudId) {
        this.cloudId = cloudId;
    }
}
