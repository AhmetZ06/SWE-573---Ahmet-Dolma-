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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        if (postTemplate.getFields() == null || postTemplate.getFields().isEmpty()) {
            postTemplate.setFields(new ArrayList<>());
            postTemplate.getFields().add(new CustomField()); // Add one field by default
        }
        postTemplate.setCommunity((Community) model.getAttribute("community"));
        model.addAttribute("postTemplate", postTemplate);
        model.addAttribute("communityId", model.getAttribute("communityId"));
        return "create-template";
    }

@PostMapping("/create")
public String createTemplate(@Valid @ModelAttribute PostTemplate postTemplate, BindingResult result, Model model,
                             @PathVariable("communityId") Long communityId,
                             RedirectAttributes redirectAttributes) {

    result = postTemplateService.validatePostTemplate(postTemplate, result, communityId);

    if (result.hasErrors()) {
//        postTemplate = new PostTemplate();
        model.addAttribute("postTemplate", postTemplate);
        return "create-template";
    }

    postTemplate.setCommunity(communityService.findByCommunityId(communityId));
    postTemplateService.createPostTemplate(postTemplate);
    redirectAttributes.addFlashAttribute("successMessage", "Post template created successfully!");
    return "redirect:/Communities/community/" + model.getAttribute("communityId");
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
    public String createPost(@Valid @ModelAttribute Post post, BindingResult result, Model model,RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "create-post";
        }
        postService.savePost(post);
        redirectAttributes.addFlashAttribute("successMessage", "Post created successfully!");
        return "redirect:/communities/Community/" + model.getAttribute("communityId") ;
    }

    @PostMapping("/add-field")
    public String addField(@ModelAttribute PostTemplate postTemplate, Model model) {
        postTemplate.getFields().add(new CustomField());
        model.addAttribute("postTemplate", postTemplate);
        return "create-template";
    }


}
