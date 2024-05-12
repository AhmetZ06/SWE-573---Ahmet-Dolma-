package com.community.Community.Controller.ServerSide;

import com.community.Community.Services.CommunityService.CommunityService;
import com.community.Community.Services.PostServices.PostService;
import com.community.Community.dto.PostDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PostController {

    private PostService postService;

    private CommunityService communityService;

    public PostController(PostService postService, CommunityService communityService) {
        this.postService = postService;
        this.communityService = communityService;
    }
    @GetMapping("/createPost")
    public String showCreatePostForm(@RequestParam("communityId") Long communityId, Model model) {

        model.addAttribute("communityId", communityId);

        return "Communities/Posts/DefaultPostTemplate";
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




}
