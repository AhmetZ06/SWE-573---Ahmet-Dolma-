package com.community.Community.Services.CommunityService;

import com.community.Community.dto.Community_Create_Dto;
import com.community.Community.models.Community;

import java.util.List;

public interface ICommunityService {

    void saveCommunity(Community_Create_Dto communityDto);
    Community findByName(String name);
    Community findByCommunityId(long communityId);

    void setAdminbyDefault(Community_Create_Dto communityCreateDto);

    List<Community> getAllCommunities();

    Community getCommunityById(Long communityId);
//    void setAdminbyDefault(long communityId);

}
