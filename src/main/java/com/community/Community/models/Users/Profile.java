package com.community.Community.models.Users;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "profile")
    private User user;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "user_info", length = 500)
    private String userInfo;
}
