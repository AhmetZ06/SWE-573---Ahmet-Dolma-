package com.community.Community.Services.PostServices;

import com.community.Community.Repositories.CommunityRepository;
import com.community.Community.Repositories.PostRepository;
import com.community.Community.Repositories.PostTemplateRepository;
import com.community.Community.Repositories.UserRepository;
import com.community.Community.Services.CommunityService.CommunityService;
import com.community.Community.models.Posts.PostTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostTemplateService {

    private PostRepository postRepository;
    private CommunityService communityService;

    private CommunityRepository communityRepository;

    private UserRepository userRepository;
    private PostTemplateRepository postTemplateRepository;


    public PostTemplateService(PostRepository postRepository
            , CommunityService communityService
            , CommunityRepository communityRepository
            , UserRepository userRepository
            , PostTemplateRepository postTemplateRepository) {
        this.postRepository = postRepository;
        this.communityService = communityService;
        this.communityRepository = communityRepository;
        this.userRepository = userRepository;
        this.postTemplateRepository = postTemplateRepository;

    }

    public List<PostTemplate> getAllPostTemplates() {
        return postTemplateRepository.findAll();
    }

    public PostTemplate savePostTemplate(PostTemplate postTemplate) {
        return postTemplateRepository.save(postTemplate);
    }




}
