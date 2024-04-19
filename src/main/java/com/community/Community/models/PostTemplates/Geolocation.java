package com.community.Community.models.PostTemplates;

import jakarta.persistence.Embeddable;

@Embeddable
public class Geolocation {
    private Double latitude;
    private Double longitude;

    // Constructors, getters, setters...

    @Override
    public String toString() {
        return "Geolocation{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
