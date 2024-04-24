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

        System.out.println(STR."Saving Community: \{communityDto.getName()}");
        Community community = new Community();
        community.setName(communityDto.getName());
        community.setDescription(communityDto.getDescription());
        community.setPrivate(communityDto.isPrivate());
        communityRepository.save(community);
        System.out.println(STR."Community saved");
    }

    @Override
    public Community findByName(String name) {
        return communityRepository.findByName(name);
    }

    @Override
    public Community findByCommunityId(long communityId) {
        return communityRepository.findByCommunityId(communityId);
    }


    private Community_Create_Dto mapToCommunity_Create_Dto(Community community){

        Community_Create_Dto communityCreateDto = new Community_Create_Dto();

        communityCreateDto.setName(community.getName());
        communityCreateDto.setDescription(community.getDescription());
        communityCreateDto.setPrivate(community.isPrivate());

//        public void setAdminbyDefault(long communityId) {
//
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            String currentUsername = authentication.getName();
//
//            User user = userRepository.findByUsername(currentUsername);
//
//            if (user != null) {
//                long userId = user.getUserID();
//            }
//
//            communityCreateDto.setAdmin_id(user.getUserID());
//
//        }

        return communityCreateDto;
    }
}