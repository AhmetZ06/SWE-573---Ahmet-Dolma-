package com.community.Community.Controller.ServerSide;

import com.community.Community.Repositories.PostRepository;
import com.community.Community.Repositories.UserRepository;
import com.community.Community.Services.CommunityService.CommunityService;
import com.community.Community.Services.CommunityService.RolesService;
import com.community.Community.Services.PostServices.PostService;
import com.community.Community.Services.UserServices.CustomUserDetailsService;
import com.community.Community.dto.Community_Create_Dto;
import com.community.Community.models.Community;
import com.community.Community.models.Posts.Post;
import com.community.Community.models.Users.Roles_In_Communities;
import com.community.Community.models.Users.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class CommunityController {

    private CommunityService communityService;

    private CustomUserDetailsService userService;

    private PostRepository postRepository;
    private PostService postService;

    private UserRepository userRepository;
    private RolesService rolesService;

    public CommunityController(CommunityService communityService,
                               CustomUserDetailsService userService,
                                PostRepository postRepository,
                                PostService postService,
                                UserRepository userRepository,
                                RolesService rolesService) {
        this.communityService = communityService;
        this.userService = userService;
        this.postRepository = postRepository;
        this.postService = postService;
        this.userRepository = userRepository;
        this.rolesService = rolesService;
    }

    @GetMapping("/createCommunity")
    public String showCreateCommunityForm(Model model) {
        Community_Create_Dto community= new Community_Create_Dto();
        model.addAttribute("community", community);
        return "Communities/createCommunity";
    }

    @PostMapping("/createCommunity/save")
    public String createCommunity(@Valid @ModelAttribute("community") Community_Create_Dto community_create_dto,
                                  BindingResult result,
                                  Model model) {

        String communityName = community_create_dto.getName();
        User user = userService.getAuthenticatedUser();
        community_create_dto.setKralid(user.getUserId());

        if (communityName == null || communityName.isEmpty()) {
            result.rejectValue("name", "error.name", "Community name cannot be empty");
            model.addAttribute("community", community_create_dto);
            return "Communities/createCommunity";
        }

        Community existingCommunity = communityService.findByName(community_create_dto.getName());

        if (existingCommunity != null && existingCommunity.getName() != null && !existingCommunity.getName().isEmpty()) {
            result.rejectValue("name", null,
                    "There is already a community registered with the same name");
        }

        if (result.hasErrors()) {
            model.addAttribute("community", community_create_dto);
            return "Communities/createCommunity";
        }

        communityService.saveCommunity(community_create_dto);

        return "redirect:/Communities";
    }


    @GetMapping("/Communities/community/{communityId}")
    public String showCommunity(@PathVariable("communityId") Long communityId, Model model) {

        Community community = communityService.getCommunityById(communityId);
        User currentUser = userService.getAuthenticatedUser();
        model.addAttribute("community", community);

        Roles_In_Communities roles = rolesService.getRoleByUserIdAndCommunityId(currentUser.getUserId(),    communityId);

        boolean isKralid = currentUser.getUserId()== community.getOwner().getUserId();

        model.addAttribute("isKralid", isKralid);

        List<Post> posts = postRepository.findByCommunity(community);

        model.addAttribute("privatecommunity", community.getIsPrivate());

        model.addAttribute("communityId", communityId);

        if (roles != null) {
            boolean isSubscribed = true;
            boolean showPosts = true;
            model.addAttribute("isMember", roles.getRole().equals("MEMBER"));
            model.addAttribute("isAdmin", roles.getRole().equals("ADMIN"));
            model.addAttribute("isModerator", roles.getRole().equals("MODERATOR"));
            model.addAttribute("isSubscribed", isSubscribed);
            model.addAttribute("posts", posts);
            model.addAttribute("show_posts",true);

        } else {
            model.addAttribute("isMember", false);
            model.addAttribute("isAdmin", false);
            model.addAttribute("isModerator", false);
            model.addAttribute("isSubscribed", false);
            if (community.getIsPrivate()) {
                model.addAttribute("posts", null);
            } else {
                model.addAttribute("posts", posts);
                model.addAttribute("show_posts",true);
            }

        }
        return "Communities/genericCommunityTemplate2";
    }


    @GetMapping("/Communities")
    public String showCommunities(Model model) {
        List<Community> communities = communityService.getAllCommunitiesSorted();
        model.addAttribute("communities", communities);

        User currentUser = userService.getAuthenticatedUser();
        List<Roles_In_Communities> rolesForUser = rolesService.getAllCommunitiesForAUser(currentUser);
        model.addAttribute("roles_for_a_user", rolesForUser);

        return "Communities/Communities";
    }


    @PostMapping("/Communities/community/{communityId}/join")
    public String joinCommunity(@PathVariable("communityId") Long communityId) {

            Community community = communityService.getCommunityById(communityId);

            User user = userService.getAuthenticatedUser();

            rolesService.addMemberToUserInCommunity(community, user, "MEMBER");

            return "redirect:/Communities/community/" + communityId;
    }

    @PostMapping("/Communities/community/{communityId}/leave")
    public String leaveCommunity(@PathVariable("communityId") Long communityId) {

        Community community = communityService.getCommunityById(communityId);

        User user = userService.getAuthenticatedUser();

        rolesService.removeMemberFromUserInCommunity(user,community);

        return "redirect:/Communities/community/" + communityId;

    }

    @GetMapping("/searchCommunity")
    public String showsearchedCommunities(Model model,
                                          @RequestParam(value = "writing", required = true) String writing)
    {
        List<Community> communities = communityService.getCommunitiesByTitleContaining(writing);
        model.addAttribute("communities", communities);

        return "Communities/SearchedCommunities";
    }







}
