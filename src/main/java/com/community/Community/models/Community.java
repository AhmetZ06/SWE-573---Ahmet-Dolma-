package com.community.Community.models;

import com.community.Community.models.Posts.*;
import com.community.Community.models.PostTemplates.*;
import com.community.Community.models.Users.Roles_In_Communities;
import com.community.Community.models.Users.User;
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

    @Column
    private String image;

    @Column
    private Long kralid;

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Post> posts;

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PostTemplate> templates;

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Roles_In_Communities> rolesInCommunities;

}
