package com.community.Community.Controller.ServerSide;

import com.community.Community.Repositories.CommunityRepository;
import com.community.Community.Repositories.PostRepository;
import com.community.Community.Repositories.PostTemplateRepository;
import com.community.Community.Repositories.UserRepository;
import com.community.Community.Services.CommunityService.CommunityService;
import com.community.Community.Services.CommunityService.RolesService;
import com.community.Community.Services.PostServices.PostService;
import com.community.Community.Services.PostServices.PostTemplateService;
import com.community.Community.Services.UserServices.CustomUserDetailsService;
import com.community.Community.models.Community;
import com.community.Community.models.Posts.CustomField;
import com.community.Community.models.Posts.Post;
import com.community.Community.models.Posts.PostTemplate;
import com.community.Community.models.Posts.PostFieldValue;
import com.community.Community.models.Users.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/communities/{communityId}/posts")
public class PostController {

    private CommunityService communityService;
    private CustomUserDetailsService userService;
    private PostRepository postRepository;
    private PostService postService;
    private UserRepository userRepository;
    private RolesService rolesService;
    private PostTemplateService postTemplateService;
    private PostTemplateRepository postTemplateRepository;
    private CommunityRepository communityRepository;

    public PostController(CommunityService communityService,
                          CustomUserDetailsService userService,
                          PostRepository postRepository,
                          PostService postService,
                          UserRepository userRepository,
                          RolesService rolesService,
                          PostTemplateRepository postTemplateRepository,
                          PostTemplateService postTemplateService,
                          CommunityRepository communityRepository) {
        this.communityService = communityService;
        this.userService = userService;
        this.postRepository = postRepository;
        this.postService = postService;
        this.userRepository = userRepository;
        this.rolesService = rolesService;
        this.postTemplateRepository = postTemplateRepository;
        this.postTemplateService = postTemplateService;
        this.communityRepository = communityRepository;
    }

    @GetMapping("/create")
    public String showCreatePostForm(@PathVariable Long communityId, Model model) {
        List<PostTemplate> templates = postTemplateService.getPostTemplateByCommunityId(communityId);
        model.addAttribute("templates", templates);
        model.addAttribute("communityId", communityId);
        return "create-post";
    }

    @PostMapping("/create")
    public String createPost(@PathVariable Long communityId, @RequestParam Long templateId,
                             @ModelAttribute Post post, @RequestParam Map<String, String> fields, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("templates", postTemplateService.getPostTemplateByCommunityId(communityId));
            model.addAttribute("communityId", communityId);
            return "create_post_2";
        }
        try {
            PostTemplate postTemplate = postTemplateService.getPostTemplateByTemplateId(templateId);
            post.setPostTemplate(postTemplate);
            post.setUser(userService.getAuthenticatedUser());
            Community community = communityService.findByCommunityId(communityId);
            post.setCommunity(community);
            postService.savePost(post, fields);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("templates", postTemplateService.getPostTemplateByCommunityId(communityId));
            model.addAttribute("communityId", communityId);
            return "create_post_2";
        }
        return "redirect:/communities/" + communityId;
    }


























    @GetMapping("/templates/{templateId}/loadFields")
    public String loadTemplateFields(@PathVariable Long communityId, @PathVariable Long templateId, Model model) {
        PostTemplate template = postTemplateService.getPostTemplateByTemplateId(templateId);
        model.addAttribute("post", new Post());
        model.addAttribute("templates", postTemplateService.getPostTemplateByCommunityId(communityId));
        model.addAttribute("template", template);
        model.addAttribute("fields", template.getFields());
        model.addAttribute("communityId", communityId);
        return "create_post_2";
    }

}

