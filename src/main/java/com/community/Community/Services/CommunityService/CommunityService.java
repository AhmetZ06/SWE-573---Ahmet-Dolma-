package com.community.Community.Services.CommunityService;

import com.community.Community.Repositories.CommunityRepository;
import com.community.Community.dto.Community_Create_Dto;
import com.community.Community.models.Community;
import com.community.Community.models.Users.Roles_In_Communities;
import com.community.Community.models.Users.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.community.Community.Repositories.UserRepository;

import java.util.List;

@Service
public class CommunityService{

    private CommunityRepository communityRepository;

    private  UserRepository userRepository;
    private RolesService rolesService;

    public CommunityService(CommunityRepository communityRepository,
                            UserRepository userRepository,
                            RolesService rolesService) {
        this.communityRepository = communityRepository;
        this.userRepository = userRepository;
        this.rolesService = rolesService;
    }

    public void saveCommunity(Community_Create_Dto communityDto) {

        Community community = new Community();
        community.setName(communityDto.getName());
        community.setDescription(communityDto.getDescription());
        community.setIsPrivate(communityDto.getisPrivate());
        User owneruser = userRepository.findByUserId(communityDto.getKralid());
        community.setOwner(owneruser);
        communityRepository.save(community);
        setAdminbyDefault(owneruser, community);

    }

    public Community findByName(String name) {
        return communityRepository.findByName(name);
    }

    public Community findByCommunityId(long communityId) {
        return communityRepository.findByCommunityId(communityId);
    }


    public void setAdminbyDefault(User user, Community community) {

        rolesService.addMemberToUserInCommunity(community, user, "ADMIN");

    }
    public List<Community> getAllCommunities() {
        return communityRepository.findAll();
    }

    public List<Community> getAllCommunitiesSorted() {
        return communityRepository.findAllByOrderByNameAsc();
    }

    public Community getCommunityById(Long communityId) {
        return communityRepository.findById(communityId).orElse(null);
    }

    public List<Community> getCommunitiesByTitleContaining(String title) {
        return communityRepository.findByNameContainingIgnoreCaseOrderByNameAsc(title);
    }


}