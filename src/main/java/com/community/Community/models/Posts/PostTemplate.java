package com.community.Community.models.Posts;

import com.community.Community.models.Community;
import com.community.Community.models.PostTemplates.Geolocation;
import com.community.Community.models.Users.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Map;

@Entity
@Table(name = "post_templates")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long templateId;

    // Reference to the creator of the template, a User who is the community creator
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createdBy", nullable = false)
    private User creator;

    // Reference to the Community that this template is associated with
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "communityId", nullable = false)
    private Community community;

    @Column(nullable = false)
    private String templateName;

    @Lob
    private byte[] image; // For storing image data

    @Embedded
    private Geolocation geolocation; // Using the embeddable Geolocation class

    @ElementCollection
    @CollectionTable(name = "template_contents", joinColumns = @JoinColumn(name = "templateId"))
    @MapKeyColumn(name = "content_type")
    @Column(name = "content")
    private Map<String, String> contents; // For various strings like additional info

    @Temporal(TemporalType.TIMESTAMP)
    private Date timeOfEvent; // For storing the time of an event associated with the template

    // ... other fields and methods ...

    @Override
    public String toString() {
        return "PostTemplate{" +
                "templateId=" + templateId +
                ", templateName='" + templateName + '\'' +
                ", geolocation='" + geolocation + '\'' +
                ", timeOfEvent=" + timeOfEvent +
                // ... Add other fields if needed for the toString representation
                '}';
    }
}
