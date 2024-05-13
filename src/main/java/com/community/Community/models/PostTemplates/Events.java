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
public class Events {

    private String eventTitle;
    private String eventDescription;
    private String eventDate;
    private String eventTime;

}
