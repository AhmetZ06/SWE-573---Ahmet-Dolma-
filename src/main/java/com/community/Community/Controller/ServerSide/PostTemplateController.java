package com.community.Community.Controller.ServerSide;

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
import com.community.Community.models.Posts.PostTemplate;
import com.community.Community.models.Posts.Post;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/communities/{communityId}/post-templates")
public class PostTemplateController {
    private CommunityService communityService;
    private CustomUserDetailsService userService;
    private PostRepository postRepository;
    private PostService postService;
    private UserRepository userRepository;
    private RolesService rolesService;
    private PostTemplateService postTemplateService;
    private PostTemplateRepository postTemplateRepository;

    @Autowired
    public PostTemplateController(CommunityService communityService,
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

    @ModelAttribute
    public void addAttributes(@PathVariable("communityId") Long communityId, Model model) {
        model.addAttribute("communityId", communityId);
        Community community = communityService.findByCommunityId(communityId);
        model.addAttribute("community", community);
    }

    @GetMapping("/create")
    public String showCreateTemplateForm(Model model) {
        PostTemplate postTemplate = new PostTemplate();
        postTemplate.setFields(new ArrayList<>()); // Initialize the fields list
        postTemplate.setCommunity((Community) model.getAttribute("community"));
        model.addAttribute("postTemplate", postTemplate);
        return "create-template";
    }

    @PostMapping("/create")
    public String createTemplate(@Valid @ModelAttribute PostTemplate postTemplate, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "create-template";
        }
        postTemplateService.createPostTemplate(postTemplate);
        return "redirect:/communities/" + model.getAttribute("communityId") + "/post-templates";
    }

    @GetMapping
    public String viewTemplates(Model model) {
        Long communityId = (Long) model.getAttribute("communityId");
        model.addAttribute("templates", postTemplateService.getPostTemplateByCommunityId(communityId));
        return "view-templates";
    }

    @GetMapping("/create-post/{templateId}")
    public String showCreatePostForm(@PathVariable("templateId") Long templateId, Model model) {
        PostTemplate postTemplate = postTemplateService.getPostTemplateByTemplateId(templateId);
        Post post = new Post();
        post.setPostTemplate(postTemplate);
        model.addAttribute("post", post);
        model.addAttribute("template", postTemplate);
        return "create-post";
    }

    @PostMapping("/create-post")
    public String createPost(@Valid @ModelAttribute Post post, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "create-post";
        }
        postService.savePost(post);
        return "redirect:/communities/" + model.getAttribute("communityId") + "/posts";
    }

    @PostMapping("/add-field")
    public String addField(@ModelAttribute PostTemplate postTemplate, Model model) {
        postTemplate.getFields().add(new CustomField());
        model.addAttribute("postTemplate", postTemplate);
        return "create-template";
    }
}
