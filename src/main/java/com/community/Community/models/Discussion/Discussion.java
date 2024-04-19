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

    // You can add additional fields that represent the content of the discussion or relationships
    // to other entities like comments if they are part of your model.

    // Constructors, getters, setters, and other methods
    // Lombok annotations will take care of generating these for you.

    @Override
    public String toString() {
        return "Discussion{" +
                "discussionId=" + discussionId +
                ", title='" + title + '\'' +
                ", isArchived=" + isArchived +
                '}';
    }
}
