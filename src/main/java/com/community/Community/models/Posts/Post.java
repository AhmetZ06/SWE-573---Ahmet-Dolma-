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

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Discussion> discussions;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Report> reports;

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
