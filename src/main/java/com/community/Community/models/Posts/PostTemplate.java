package com.community.Community.models.Posts;

import com.community.Community.models.Community;
import com.community.Community.models.PostTemplates.Geolocation;
import com.community.Community.models.Users.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "post_templates")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long templateId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "communityId", referencedColumnName = "communityId")
    private Community community;

    @OneToMany(mappedBy = "template")
    private Set<Post> posts;

    @Column
    private boolean include_title = true;

    @Column
    private boolean include_ContentData = true;

    @Column
    private boolean include_Image = false;

    @Column
    private boolean include_Geolocation = false;

    @Column
    private boolean include_Discussion = false;

    @Column
    private boolean include_Report = false;



}
