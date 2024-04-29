package com.community.Community.models.Posts;

import com.community.Community.models.*;
import com.community.Community.models.Discussion.Discussion;
import com.community.Community.models.PostTemplates.Geolocation;
import com.community.Community.models.Users.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contentData;

    @Column
    private LocalDateTime CreationDate;

    @Column
    private LocalDateTime UpdateDate;

    @Column
    private String image;

    @Column
    private Geolocation geolocation;

    @Column
    private int upvotes;

    @Column
    private int downvotes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "templateId")
    private PostTemplate template;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Discussion> discussions;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Report> reports;


}
