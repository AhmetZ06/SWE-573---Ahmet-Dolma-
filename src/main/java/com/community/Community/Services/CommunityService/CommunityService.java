package com.community.Community.Services.CommunityService;

import com.community.Community.Repositories.CommunityRepository;
import com.community.Community.Repositories.UserRepository;
import com.community.Community.models.Users.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CommunityService implements ICommunityService{

    private CommunityRepository communityRepository;

    public CommunityService(CommunityRepository communityRepository) {
        this.communityRepository = communityRepository;
    }



}