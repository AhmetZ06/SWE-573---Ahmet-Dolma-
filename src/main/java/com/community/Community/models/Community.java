package com.community.Community.models;

import com.community.Community.models.Posts.*;
import com.community.Community.models.PostTemplates.*;
import com.community.Community.models.Users.Roles_In_Communities;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "communities")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Community {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long communityId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private boolean isPrivate;

    @Column(nullable = false)
    private boolean isArchived;

    // One-to-many relationship with Post
    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Post> posts;

    // One-to-many relationship with PostTemplate
    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PostTemplate> templates;

    // One-to-many relationship with RolesInCommunities
    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Roles_In_Communities> rolesInCommunities;

    @Override
    public String toString() {
        return "Community{" +
                "communityId=" + communityId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", isPrivate=" + isPrivate +
                ", isArchived=" + isArchived +
                '}';
    }
}
