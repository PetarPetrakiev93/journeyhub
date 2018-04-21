package com.softuni.journeyhub.image.models;

import com.softuni.journeyhub.events.entities.Event;

public class EventImage {
    private Event eventId;

    private String url;

    public EventImage() {
    }

    public Event getEventId() {
        return eventId;
    }

    public void setEventId(Event eventId) {
        this.eventId = eventId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
