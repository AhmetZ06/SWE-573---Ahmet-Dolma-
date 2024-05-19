package com.community.Community.dto;

import jakarta.persistence.Column;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostTemplateDto {
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;
}

