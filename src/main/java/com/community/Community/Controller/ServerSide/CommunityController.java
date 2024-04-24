package com.community.Community.Controller.ServerSide;

import com.community.Community.Services.CommunityService.ICommunityService;
import com.community.Community.dto.Community_Create_Dto;
import com.community.Community.dto.UserDto;
import com.community.Community.models.Community;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommunityController {

    private ICommunityService communityService;

    public CommunityController(ICommunityService communityService) {
        this.communityService = communityService;
    }

    @GetMapping("/communities")
    public String showCommunities() {
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

        return "redirect:/communities";
    }

}