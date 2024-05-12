package com.community.Community.dto;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
public class Roles_Dto {
    private Long userId;
    private Long communityId;
    private String role;

    public Roles_Dto(Long userId, Long communityId, String role) {
        this.userId = userId;
        this.communityId = communityId;
        this.role = role;
    }

}
