package com.community.Community.models.Posts;

import com.community.Community.enums.FieldType;
import jakarta.persistence.*;
import lombok.*;
import com.community.Community.models.Posts.*;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FieldType fieldType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "templateId", referencedColumnName = "templateId")
    private PostTemplate postTemplate;
}
