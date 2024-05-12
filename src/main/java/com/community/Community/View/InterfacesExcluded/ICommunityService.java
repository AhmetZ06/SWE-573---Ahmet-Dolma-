package com.community.Community.Services.InterfacesExcluded;

import com.community.Community.Bean.UserSession;
import com.community.Community.Repositories.UserRepository;
import com.community.Community.dto.Community_Create_Dto;
import com.community.Community.dto.UserDto;
import com.community.Community.models.Community;
import com.community.Community.models.Users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ICommunityService {

    void saveCommunity(Community_Create_Dto communityDto);
    Community findByName(String name);
    Community findByCommunityId(long communityId);
    void setAdminbyDefault(Community_Create_Dto communityCreateDto);

    List<Community> getAllCommunities();

    Community getCommunityById(Long communityId);

    int countCommunityMembers(Long communityId);

    List<Community> getAllCommunitiesSorted();

    public void getAuthenticatedUser(UserDto userDto);

    public void addUserToCommunity(Long communityId, String username);
    public boolean isUserPartOfCommunity(Community community, User User);

}
