package com.community.Community.Controller.ServerSide;

import com.community.Community.Repositories.PostRepository;
import com.community.Community.Repositories.PostTemplateRepository;
import com.community.Community.Repositories.UserRepository;
import com.community.Community.Services.CommunityService.CommunityService;
import com.community.Community.Services.CommunityService.RolesService;
import com.community.Community.Services.PostServices.PostService;
import com.community.Community.Services.PostServices.PostTemplateService;
import com.community.Community.Services.UserServices.CustomUserDetailsService;
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

    public PostController(CommunityService communityService,
                          CustomUserDetailsService userService,
                          PostRepository postRepository,
                          PostService postService,
                          UserRepository userRepository,
                          RolesService rolesService,
                          PostTemplateRepository postTemplateRepository,
                          PostTemplateService postTemplateService) {
        this.communityService = communityService;
        this.userService = userService;
        this.postRepository = postRepository;
        this.postService = postService;
        this.userRepository = userRepository;
        this.rolesService = rolesService;
        this.postTemplateRepository = postTemplateRepository;
        this.postTemplateService = postTemplateService;
    }

    @GetMapping("/create/{templateId}")
    public String showCreatePostForm(@PathVariable("templateId") Long templateId,
                                     @PathVariable("communityId") Long communityId,
                                     Model model) {
        PostTemplate postTemplate = postTemplateService.getPostTemplateById(templateId);
        Post post = new Post();
        post.setPostTemplate(postTemplate);
        post.setFieldValues(new ArrayList<>()); // Initialize field values
        model.addAttribute("post", post);
        model.addAttribute("template", postTemplate);
        model.addAttribute("communityId", communityId);
        return "create-post";
    }

    @PostMapping("/create")
    public String createPost(@Valid @ModelAttribute Post post,
                             BindingResult result,
                             @PathVariable("communityId") Long communityId,
                             Model model) {
        if (result.hasErrors()) {
            PostTemplate postTemplate = post.getPostTemplate();
            model.addAttribute("template", postTemplate);
            model.addAttribute("communityId", communityId);
            return "create-post";
        }

        User user = userService.getAuthenticatedUser();
        post.setUser(user);
        postService.savePost(post);
        return "redirect:/communities/" + communityId + "/posts";
    }
}

