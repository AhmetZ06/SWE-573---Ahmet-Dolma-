package com.community.Community.models.Posts;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "post_field_values")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostFieldValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId", referencedColumnName = "id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customFieldId", referencedColumnName = "customFieldId")
    private CustomField customField;

    @Column(nullable = false)
    private String value;

}