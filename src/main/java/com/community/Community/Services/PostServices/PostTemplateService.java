package com.community.Community.Services.PostServices;

import com.community.Community.Repositories.CommunityRepository;
import com.community.Community.Repositories.PostRepository;
import com.community.Community.Repositories.PostTemplateRepository;
import com.community.Community.Repositories.UserRepository;
import com.community.Community.Services.CommunityService.ICommunityService;
import com.community.Community.models.Community;
import com.community.Community.models.Posts.PostTemplate;
import org.springframework.stereotype.Service;

@Service
public class PostTemplateService {

    private PostRepository postRepository;
    private ICommunityService communityService;

    private CommunityRepository communityRepository;

    private UserRepository userRepository;
    private PostTemplateRepository postTemplateRepository;


    public PostTemplateService(PostRepository postRepository) {
        this.postRepository = postRepository;
        this.communityService = communityService;
        this.communityRepository = communityRepository;
        this.userRepository = userRepository;
        this.postTemplateRepository = postTemplateRepository;

    }




}
