package com.community.Community.Services.PostServices;

import com.community.Community.Repositories.*;
import com.community.Community.Services.UserServices.CustomUserDetailsService;
import com.community.Community.dto.PostDto;
import com.community.Community.models.Users.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class PostService{

    private  PostRepository postRepository;
    private CommunityRepository communityRepository;
    private UserRepository userRepository;
    private CustomUserDetailsService userService;
    private PostTemplateRepository postTemplateRepository;

    public PostService(PostRepository postRepository,
                       CommunityRepository communityRepository,
                       UserRepository userRepository,
                       CustomUserDetailsService userService,
                       PostTemplateRepository postTemplateRepository)
    {
        this.postRepository = postRepository;
        this.communityRepository = communityRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.postTemplateRepository = postTemplateRepository;
    }







}
