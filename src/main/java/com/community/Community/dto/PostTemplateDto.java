package com.community.Community.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostTemplateDto {
    private boolean include_Pool;
    private boolean include_Geolocation;
    private boolean include_Event;
}

