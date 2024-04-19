package com.community.Community.models;

import com.community.Community.models.Users.User;
import jakarta.persistence.*;
import lombok.*;
import com.community.Community.models.Posts.Post;

@Entity
@Table(name = "reports")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(nullable = false)
    private String reason;

    // Other fields and methods can be added as required.

    @Override
    public String toString() {
        return "Report{" +
                "reportId=" + reportId +
                ", reason='" + reason + '\'' +
                // You may want to include user and post info, but be mindful of lazy loading.
                '}';
    }
}
