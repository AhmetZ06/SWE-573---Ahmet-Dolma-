package com.community.Community.Services.CommunityService;


import com.community.Community.Repositories.CommunityRepository;
import com.community.Community.Repositories.Roles_In_Communities_Respository;
import com.community.Community.Repositories.UserRepository;
import com.community.Community.dto.Roles_Dto;
import com.community.Community.models.Community;
import com.community.Community.models.Users.Roles_In_Communities;
import com.community.Community.models.Users.User;
import com.community.Community.models.Users.User_Community_Composite_Key;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RolesService {

    private Roles_In_Communities_Respository rolesInCommunitiesRepository;
    private UserRepository userRepository;

    private CommunityRepository communityRepository;

    public RolesService(Roles_In_Communities_Respository rolesInCommunitiesRepository,
                        UserRepository userRepository,
                        CommunityRepository communityRepository) {
        this.rolesInCommunitiesRepository = rolesInCommunitiesRepository;
        this.userRepository = userRepository;
        this.communityRepository = communityRepository;
    }

    public Roles_In_Communities getRoleByUserIdAndCommunityId(Long userId, Long communityId) {

        User_Community_Composite_Key key = new User_Community_Composite_Key(userId, communityId);
        return rolesInCommunitiesRepository.findById(key).orElse(null);

    }

    public List<Roles_In_Communities> getAllCommunitiesForAUser(User user) {
        return rolesInCommunitiesRepository.findByUser(user);
    }







    public void addMemberToUserInCommunity(Community community, User user, String role) {


        long userid = user.getUserId();
        long communityid = community.getCommunityId();

        User_Community_Composite_Key key = new User_Community_Composite_Key(userid, communityid);

        Roles_In_Communities ric = new Roles_In_Communities();

        ric.setId(key);
        ric.setUser(user);           // Set the user
        ric.setCommunity(community); // Set the community
        ric.setRole(role);

        rolesInCommunitiesRepository.save(ric);
    }

    @Transactional
    public void removeMemberFromUserInCommunity(User user, Community community) {

        rolesInCommunitiesRepository.deleteRoles_In_CommunitiesByUserAndCommunity(user, community);

    }

}
