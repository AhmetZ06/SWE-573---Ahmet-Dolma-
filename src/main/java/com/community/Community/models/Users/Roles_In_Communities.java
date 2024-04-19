package com.community.Community.models.Users;

import jakarta.persistence.*;
import lombok.*;
import com.community.Community.models.Community;

@Entity
@Table(name = "roles_in_communities")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Roles_In_Communities {

    @EmbeddedId
    private User_Community_Composite_Key id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("communityId")
    @JoinColumn(name = "community_id")
    private Community community;

    @Column(nullable = false)
    private String role;

    // Constructors, getters, setters
}
