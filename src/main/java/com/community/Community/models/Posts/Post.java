package com.community.Community.models.Posts;

import com.community.Community.models.Community;
import com.community.Community.models.Discussion.Comments;
import com.community.Community.models.Users.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "posts")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long postId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, updatable = false)
    private Instant creationDate;

    @Column
    private LocalDateTime lastModifiedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "communityId", referencedColumnName = "communityId", nullable = false)
    private Community community;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comments> comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "post_upvotes",
            joinColumns = @JoinColumn(name = "postId"),
            inverseJoinColumns = @JoinColumn(name = "userId")
    )
    private Set<User> upvoters;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "post_downvotes",
            joinColumns = @JoinColumn(name = "postId"),
            inverseJoinColumns = @JoinColumn(name = "userId")
    )
    private Set<User> downvoters;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TemplateId", referencedColumnName = "TemplateId", nullable = false)
    private PostTemplate postTemplate;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostFieldValue> fieldValues;

    @PrePersist
    protected void onCreate() {
        creationDate = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        lastModifiedDate = LocalDateTime.now();
    }
}
