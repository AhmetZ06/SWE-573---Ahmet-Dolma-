package com.community.Community.Controller.ServerSide;

import com.community.Community.Repositories.PostRepository;
import com.community.Community.Repositories.PostTemplateRepository;
import com.community.Community.Repositories.UserRepository;
import com.community.Community.Services.CommunityService.CommunityService;
import com.community.Community.Services.CommunityService.RolesService;
import com.community.Community.Services.PostServices.PostService;
import com.community.Community.Services.UserServices.CustomUserDetailsService;
import com.community.Community.dto.PostDto;
import com.community.Community.dto.PostTemplateDto;
import com.community.Community.models.Community;
import com.community.Community.models.Posts.PostTemplate;
import com.community.Community.models.Users.Roles_In_Communities;
import com.community.Community.models.Users.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class PostController {

    private CommunityService communityService;
    private CustomUserDetailsService userService;
    private PostRepository postRepository;
    private PostService postService;
    private UserRepository userRepository;
    private RolesService rolesService;
    private PostTemplateRepository postTemplateRepository;

    public PostController(CommunityService communityService,
                               CustomUserDetailsService userService,
                               PostRepository postRepository,
                               PostService postService,
                               UserRepository userRepository,
                               RolesService rolesService,
                               PostTemplateRepository postTemplateRepository) {
        this.communityService = communityService;
        this.userService = userService;
        this.postRepository = postRepository;
        this.postService = postService;
        this.userRepository = userRepository;
        this.rolesService = rolesService;
        this.postTemplateRepository = postTemplateRepository;
    }
    @GetMapping("/createPost")
    public String showCreatePostForm(@RequestParam("communityId") Long communityId, Model model) {

        model.addAttribute("communityId", communityId);

        return "Communities/Posts/DefaultPostTemplate";
    }

    @GetMapping("/addPostTemplate")
    public String showAddPostTemplate(@RequestParam("communityId") Long communityId, Model model) {

        model.addAttribute("communityId", communityId);

        return "/Communities/Posts/Post_Template_Creation";

    }

    @PostMapping("/createPost/save")
    public String submitPost(@Valid @ModelAttribute("post") PostDto postDto,
                             BindingResult result,
                             @RequestParam("communityId") Long communityId,
                             Model model) {

        if (result.hasErrors()) {
            model.addAttribute("communityId", communityId);
            return "Communities/Posts/DefaultPostTemplate";
        }

        postService.savePost(postDto, communityId);

        return "redirect:/Communities/community/" + communityId;
    }

    @PostMapping("/addPostTemplate/save")
    public String addPostTemplate(@RequestParam("communityId") Long communityId,
                                  @Valid @ModelAttribute("postTemplateDto") PostTemplateDto postTemplateDto,
                                  BindingResult result, Model model, RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            // Return to the form with errors
            return "redirect:/addPostTemplate";
        }

        PostTemplate postTemplate = postService.convertDtoToPostTemplate(postTemplateDto);

        postTemplate.setCommunity(communityService.getCommunityById(communityId));
        postTemplateRepository.save(postTemplate);

        redirectAttrs.addFlashAttribute("successMessage", "Post template created successfully!");
        return "redirect:/Communities/community/" + communityId;
    }

    @GetMapping("/choosePostTemplate")
    public String showPostTemplateSelection(@RequestParam("communityId") Long communityId, Model model,
                                            RedirectAttributes redirectAttrs
                                            ) {

        Community com = communityService.getCommunityById(communityId);
        List<PostTemplate> templates =postService.getAllPostTemplatesByCom(com);
        model.addAttribute("templates", templates);
        model.addAttribute("communityId", communityId);
        return "Communities/Posts/TemplateSelection";
    }



}
