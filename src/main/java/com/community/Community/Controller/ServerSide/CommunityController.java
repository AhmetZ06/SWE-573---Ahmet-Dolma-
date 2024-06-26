package com.community.Community.Controller.ServerSide;

import com.community.Community.Services.CommunityService.ICommunityService;
import com.community.Community.Services.UserServices.CustomUserDetailsService;
import com.community.Community.Services.UserServices.IUserService;
import com.community.Community.dto.Community_Create_Dto;
import com.community.Community.dto.UserDto;
import com.community.Community.models.Community;
import com.community.Community.models.Users.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommunityController {

    private ICommunityService communityService;

    private CustomUserDetailsService userService;

    public CommunityController(ICommunityService communityService, CustomUserDetailsService userService) {
        this.communityService = communityService;
        this.userService = userService;
    }

    //Show communities
    @GetMapping("/Communities")
    public String showCommunities(Model model) {

        model.addAttribute("listCommunities", communityService.getAllCommunities());

        return "Communities/Communities";
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

        communityService.setAdminbyDefault(community_create_dto);

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
        model.addAttribute("community", community);

        User currentUser = userService.getAuthenticatedUser();

        boolean isKralid = currentUser.getUserId()== community.getKralid();

        model.addAttribute("isKralid", isKralid);

        return "Communities/genericCommunityTemplate";
    }



}
