package com.community.Community.dto;

import com.community.Community.models.Users.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostDto {
    private Long postId;
    private String title;
    private Long templateId;
    private Long communityId;
    private String contentData;
    private boolean image_ok;
    private boolean geolocation_ok;
    private boolean discussion_ok;
    private User user;
    private LocalDateTime CreationDate;
    private LocalDateTime UpdateDate;
}
