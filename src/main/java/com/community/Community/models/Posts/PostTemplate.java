package com.community.Community.models.Posts;

import com.community.Community.models.Community;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "post_templates")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long templateId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "communityId", referencedColumnName = "communityId", nullable = false)
    private Community community;

    @OneToMany(mappedBy = "postTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CustomField> fields;
}
