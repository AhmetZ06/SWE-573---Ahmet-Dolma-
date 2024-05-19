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
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

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
        if (postTemplate.getFields() == null || postTemplate.getFields().isEmpty()) {
            throw new IllegalArgumentException("Post template must have at least one field because there is a default template for each community containing a title and a description field.");
        }

        for (CustomField field : postTemplate.getFields()) {
            field.setPostTemplate(postTemplate);
        }
        return postTemplateRepository.save(postTemplate);
    }

    @Transactional
    public PostTemplate setDefaultPostTemplate(Community community) {
        PostTemplate postTemplate = new PostTemplate();
        postTemplate.setCommunity(community);
        postTemplate.setTitle("Default Post Template");
        postTemplate.setDescription("This is the default post template for the community");
        postTemplateRepository.save(postTemplate);
        return postTemplate;
    }


    public boolean isTitleAlreadyUsed(Community community, String title) {
        PostTemplate existingTemplate = postTemplateRepository.findPostTemplateByCommunityAndTitle(community, title);

        if (existingTemplate == null) {
            return true;
        } else{
            return false;
        }
    }

    public BindingResult validatePostTemplate(PostTemplate postTemplate, BindingResult result, Long communityId) {

        if (postTemplate.getFields() == null || postTemplate.getFields().isEmpty()) {
            result.rejectValue("fields", null, "You must add at least one field.");
        }

        if (!isTitleAlreadyUsed(communityService.getCommunityById(communityId), postTemplate.getTitle())) {

            result.rejectValue("title", null, "A template with this title already exists.");

        }

        return result;
    }


    public List<PostTemplate> getPostTemplateByCommunityId(long communityId) {
        Community community = communityService.findByCommunityId(communityId);
        return postTemplateRepository.findPostTemplateByCommunity(community);
    }

    public PostTemplate getPostTemplateByTemplateId(Long TemplateId) {
        return postTemplateRepository.findPostTemplateByTemplateId(TemplateId);
    }


}