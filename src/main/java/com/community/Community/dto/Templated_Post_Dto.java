package com.community.Community.dto;

import com.community.Community.models.Community;
import com.community.Community.models.Discussion.Comments;
import com.community.Community.models.PostTemplates.Events;
import com.community.Community.models.PostTemplates.Geolocation;
import com.community.Community.models.Posts.PostTemplate;
import com.community.Community.models.Users.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Templated_Post_Dto {

    private boolean include_Title = true;

    private boolean include_ContentData = true;

    private boolean include_Image = false;

    private boolean include_Geolocation = false;

    private boolean include_Discussion = false;

    private boolean include_Report = false;

    private boolean include_Upvotes = false;

    private boolean  include_Downvotes = false;

    private boolean include_Event = false;

    private boolean include_Pool = false;




}
