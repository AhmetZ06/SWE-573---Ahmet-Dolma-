package com.community.Community.models.Posts;

import com.community.Community.models.*;
import com.community.Community.models.Discussion.Discussion;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "posts")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "communityId", nullable = false)
    private Community community;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String contentData;

    @Column(nullable = false)
    private boolean isArchived;

    // One-to-many relationship with Discussion
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Discussion> discussions;

    // One-to-many relationship with Report
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Report> reports;

    // Constructors, toString and other methods
    // Ensure you have appropriate constructors, getters, and setters as required.

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", title='" + title + '\'' +
                ", contentData='" + contentData + '\'' +
                ", isArchived=" + isArchived +
                '}';
    }
}
