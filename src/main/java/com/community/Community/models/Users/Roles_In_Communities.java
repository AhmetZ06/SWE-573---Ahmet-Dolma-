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
    @MapsId("userId") // This should match exactly the property name in the composite key class
    @JoinColumn(name = "userId", referencedColumnName = "userId") // This should match the column name specified in the composite key class
    private User user;

    @ManyToOne
    @MapsId("communityId") // This should match exactly the property name in the composite key class
    @JoinColumn(name = "communityId",referencedColumnName = "communityId") // This should match the column name specified in the composite key class
    private Community community;

    @Column(nullable = false)
    private String role;

}
