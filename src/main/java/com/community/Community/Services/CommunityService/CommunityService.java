package com.community.Community.Services.CommunityService;

import com.community.Community.Repositories.CommunityRepository;
import com.community.Community.dto.Community_Create_Dto;
import com.community.Community.models.Community;
import com.community.Community.models.Users.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.community.Community.Repositories.UserRepository;

import java.util.List;

@Service
public class CommunityService implements ICommunityService{

    private CommunityRepository communityRepository;

    private  UserRepository userRepository;

    public CommunityService(CommunityRepository communityRepository,
                            UserRepository userRepository) {
        this.communityRepository = communityRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void saveCommunity(Community_Create_Dto communityDto) {

        Community community = new Community();
        community.setName(communityDto.getName());
        community.setDescription(communityDto.getDescription());
        community.setIsPrivate(communityDto.getisPrivate());
        community.setOwner(userRepository.findByUserId(communityDto.getKralid()));
        communityRepository.save(community);

    }

    @Override
    public Community findByName(String name) {
        return communityRepository.findByName(name);
    }

    @Override
    public Community findByCommunityId(long communityId) {
        return communityRepository.findByCommunityId(communityId);
    }


//    private Community_Create_Dto mapToCommunity_Create_Dto(Community community){
//
//        Community_Create_Dto communityCreateDto = new Community_Create_Dto();
//
//        communityCreateDto.setName(community.getName());
//        communityCreateDto.setDescription(community.getDescription());
//        communityCreateDto.setPrivate(community.isPrivate());
//        setAdminbyDefault(communityCreateDto);
//        return communityCreateDto;
//    }


    public void setAdminbyDefault(Community_Create_Dto communityCreateDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        User user = userRepository.findByEmail(currentUsername);

        long adminID = 0;

        if (user != null) {
            adminID = user.getUserId();
        }

        communityCreateDto.setKralid(adminID);

    }

    @Override
    public List<Community> getAllCommunities() {
        return communityRepository.findAll();
    }


    @Override
    public void addUserToCommunity(Long communityId, String username) {
        Community community = communityRepository.findById(communityId).orElseThrow(() -> new IllegalArgumentException("Community not found"));
        User user = userRepository.findByUsername(username);

        if (community.getModerators().contains(user)) {
            // Possibly log or handle the case where the user is already a moderator
            return; // Simply return without adding the user
        }
        if (community.getUsers().contains(user)) {
            // Possibly log or handle the case where the user is already added
            return; // Simply return without adding the user again
        }
        if (community.getOwner() != null && community.getOwner().equals(user)) {
            // Possibly log or handle the case where the user is the owner
            return; // Simply return without adding the user
        }
        community.getUsers().add(user);
        communityRepository.save(community);
    }

    @Override
    public Community getCommunityById(Long communityId) {
        return communityRepository.findById(communityId).orElse(null);
    }





}