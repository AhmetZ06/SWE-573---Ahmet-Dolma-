package com.community.Community.models.Posts;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "custom_fields")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customFieldId;

    @Column(nullable = false)
    private String fieldName;

    @Column(nullable = false)
    private String fieldType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "templateId", referencedColumnName = "templateId", nullable = false)
    private PostTemplate postTemplate;
}
