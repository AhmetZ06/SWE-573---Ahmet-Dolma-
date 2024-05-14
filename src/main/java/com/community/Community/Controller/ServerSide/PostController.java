package com.community.Community.Controller.ServerSide;

import com.community.Community.Repositories.PostRepository;
import com.community.Community.Repositories.PostTemplateRepository;
import com.community.Community.Repositories.UserRepository;
import com.community.Community.Services.CommunityService.CommunityService;
import com.community.Community.Services.CommunityService.RolesService;
import com.community.Community.Services.PostServices.PostService;
import com.community.Community.Services.PostServices.PostTemplateService;
import com.community.Community.Services.UserServices.CustomUserDetailsService;
import com.community.Community.dto.PostDto;
import com.community.Community.models.Community;
import com.community.Community.models.Posts.Post;
import com.community.Community.models.Posts.PostTemplate;
import com.community.Community.models.Users.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequestMapping("/posts")
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

//    @GetMapping("/create")
//    public String showCreatePostForm(Model model, @RequestParam Long communityId) {
//        Community community = communityService.getCommunityById(communityId);
//        User user = userService.getAuthenticatedUser();
//        model.addAttribute("post", new Post());
//        model.addAttribute("templates", postTemplateService.getAllTemplates());
//        return "create-post";
//    }
//
//    @PostMapping("/create")
//    public String createPost(@Valid @ModelAttribute Post post, BindingResult result, @RequestParam Long templateId, Model model) {
//        if (result.hasErrors()) {
//            model.addAttribute("templates", postTemplateService.getAllTemplates());
//            return "create-post";
//        }
//        postService.createPostWithTemplate(post, templateId);
//        return "redirect:/posts";
//    }
//
//    @GetMapping
//    public String viewPosts(Model model) {
//        model.addAttribute("posts", postService.getAllPosts());
//        return "view-posts";
//    }
}