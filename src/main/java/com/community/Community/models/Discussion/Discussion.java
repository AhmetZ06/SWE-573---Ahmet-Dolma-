package com.community.Community.models.Discussion;

import com.community.Community.models.Posts.Post;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "discussions")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Discussion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long discussionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId", nullable = false)
    private Post post;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private boolean isArchived;

}
