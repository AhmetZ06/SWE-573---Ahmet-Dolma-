package com.community.Community.models.PostTemplates;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class Geolocation {
    private Double latitude;
    private Double longitude;
    private String address;
}
