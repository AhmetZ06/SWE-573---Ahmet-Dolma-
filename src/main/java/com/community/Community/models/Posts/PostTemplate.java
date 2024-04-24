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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createdBy", nullable = false)
    private User creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "communityId", nullable = false)
    private Community community;

    @Column(nullable = false)
    private String templateName;

    @Lob
    private byte[] image;

    @Embedded
    private Geolocation geolocation;

    @ElementCollection
    @CollectionTable(name = "template_contents", joinColumns = @JoinColumn(name = "templateId"))
    @MapKeyColumn(name = "content_type")
    @Column(name = "content")
    private Map<String, String> contents;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timeOfEvent;


    @Override
    public String toString() {
        return "PostTemplate{" +
                "templateId=" + templateId +
                ", templateName='" + templateName + '\'' +
                ", geolocation='" + geolocation + '\'' +
                ", timeOfEvent=" + timeOfEvent +
                '}';
    }
}
