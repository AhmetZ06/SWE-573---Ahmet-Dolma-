package com.community.Community.models;

import com.community.Community.models.Posts.*;
import com.community.Community.models.Users.Roles_In_Communities;
import com.community.Community.models.Users.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Set;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "communities")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Community {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long communityId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    public void setIsPrivate() {
        this.isPrivate = true;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public boolean getIsPrivate() {
        return this.isPrivate;
    }

    @Column(nullable = false)
    private boolean isPrivate;

    @Column(nullable = false)
    private boolean isArchived;

    @Column
    private String image;

    private Instant createdDate;

    @OneToMany(fetch = LAZY)
    private Set<User> users;

    @ManyToOne(fetch = LAZY)
    private User owner;

    @OneToMany(fetch = LAZY)
    private Set<PostTemplate> postTemplates;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "defaultTemplateId", referencedColumnName = "templateId")
    private PostTemplate defaultTemplate;

    @OneToMany(fetch = LAZY)
    private Set<Post> posts;

    @ManyToMany(fetch = LAZY)
    private Set<Roles_In_Communities> rolesInCommunities;

}
