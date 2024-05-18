package com.community.Community.Services.PostServices;

import com.community.Community.Repositories.CommunityRepository;
import com.community.Community.Repositories.PostRepository;
import com.community.Community.Repositories.PostTemplateRepository;
import com.community.Community.Repositories.UserRepository;
import com.community.Community.Services.CommunityService.CommunityService;
import com.community.Community.models.Community;
import com.community.Community.models.Posts.CustomField;
import com.community.Community.models.Posts.PostTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostTemplateService {

    private final PostRepository postRepository;
    private final CommunityService communityService;
    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;
    private final PostTemplateRepository postTemplateRepository;

    // Constructor injection for all required repositories and services
    public PostTemplateService(PostRepository postRepository,
                               CommunityService communityService,
                               CommunityRepository communityRepository,
                               UserRepository userRepository,
                               PostTemplateRepository postTemplateRepository) {
        this.postRepository = postRepository;
        this.communityService = communityService;
        this.communityRepository = communityRepository;
        this.userRepository = userRepository;
        this.postTemplateRepository = postTemplateRepository;
    }

    // Retrieve all post templates
    public List<PostTemplate> getAllPostTemplates() {
        return postTemplateRepository.findAll();
    }

    // Save a post template
    public PostTemplate savePostTemplate(PostTemplate postTemplate) {
        return postTemplateRepository.save(postTemplate);
    }

    // Create a new post template
    @Transactional
    public PostTemplate createPostTemplate(PostTemplate postTemplate) {
        // Set the post template reference for each custom field
        for (CustomField field : postTemplate.getFields()) {
            field.setPostTemplate(postTemplate);
        }
        return postTemplateRepository.save(postTemplate);
    }

    // Retrieve all post templates
    public List<PostTemplate> getAllTemplates() {
        return postTemplateRepository.findAll();
    }

    // Retrieve post templates by community ID
    public List<PostTemplate> getPostTemplateByCommunityid(Long id) {
        Community community = communityService.getCommunityById(id);
        return postTemplateRepository.findPostTemplateByCommunity(community);
    }

    public PostTemplate getPostTemplateById(Long id) {
        return postTemplateRepository.findPostTemplateByTemplateId(id);
    }


}