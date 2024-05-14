package com.community.Community.models.Posts;

import com.community.Community.models.Community;
import com.community.Community.models.Posts.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "post_templates")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostTemplate {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long templateId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "postTemplate")
    private List<CustomField> fields;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "communityId", referencedColumnName = "communityId")
    private Community community;
}
