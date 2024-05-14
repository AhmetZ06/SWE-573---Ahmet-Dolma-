package com.community.Community.models.ExcludedOldFiles;

import com.community.Community.models.Community;
import com.community.Community.models.Discussion.Comments;
import com.community.Community.models.PostTemplates.Events;
import com.community.Community.models.PostTemplates.Geolocation;
import com.community.Community.models.Posts.PostTemplate;
import com.community.Community.models.Users.User;
import jakarta.persistence.*;
import lombok.*;

import javax.xml.stream.events.Comment;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

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

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contentData;

    @Column
    private Instant CreationDate;

    @Column
    private LocalDateTime UpdateDate;

    @Column
    private String image;

    @Embedded
    private Geolocation geolocation;

    @Column
    private int upvotes;

    @Column
    private int downvotes;

    @Embedded
    private Events event;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "communityId", referencedColumnName = "communityId")
    private Community community;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "templateId", referencedColumnName = "templateId")
    private PostTemplate template;

    @OneToMany(fetch = LAZY)
    private List<Comments> Comments;




}
