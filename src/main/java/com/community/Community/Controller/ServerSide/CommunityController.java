package com.community.Community.Controller.ServerSide;

import com.community.Community.Repositories.PostRepository;
import com.community.Community.Repositories.UserRepository;
import com.community.Community.Services.CommunityService.CommunityService;
import com.community.Community.Services.CommunityService.RolesService;
import com.community.Community.Services.FileUploadService;
import com.community.Community.Services.PostServices.PostService;
import com.community.Community.Services.PostServices.PostTemplateService;
import com.community.Community.Services.UserServices.CustomUserDetailsService;
import com.community.Community.dto.Community_Create_Dto;
import com.community.Community.models.Community;
import com.community.Community.models.Posts.Post;
import com.community.Community.models.Users.Roles_In_Communities;
import com.community.Community.models.Users.User;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Controller
public class CommunityController {

    private CommunityService communityService;

    private CustomUserDetailsService userService;

    private PostRepository postRepository;
    private PostService postService;

    private UserRepository userRepository;
    private RolesService rolesService;

    private PostTemplateService postTemplateService;
    private FileUploadService fileUploadService;


    public CommunityController(CommunityService communityService,
                               CustomUserDetailsService userService,
                               PostRepository postRepository,
                               PostService postService,
                               UserRepository userRepository,
                               RolesService rolesService,
                               PostTemplateService postTemplateService,
                               FileUploadService fileUploadService) {
        this.communityService = communityService;
        this.userService = userService;
        this.postRepository = postRepository;
        this.postService = postService;
        this.userRepository = userRepository;
        this.rolesService = rolesService;
        this.postTemplateService = postTemplateService;
        this.fileUploadService = fileUploadService;

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
                                  Model model,
                                  RedirectAttributes redirectAttributes) {

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
            model.addAttribute("community", community_create_dto);
            return "Communities/createCommunity";
        }

        if (result.hasErrors()) {
            model.addAttribute("community", community_create_dto);
            return "Communities/createCommunity";
        }

        communityService.saveCommunity(community_create_dto);
        postTemplateService.setDefaultPostTemplate(communityService.findByName(community_create_dto.getName()));
        redirectAttributes.addFlashAttribute("successMessage", "Community is Created, Enjoy!");

        return "redirect:/Communities";
    }


    @GetMapping("/Communities/community/{communityId}")
    public String showCommunity(@PathVariable("communityId") Long communityId, Model model) {

        Community community = communityService.getCommunityById(communityId);
        User currentUser = userService.getAuthenticatedUser();
        model.addAttribute("community", community);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("userId", currentUser.getUserId());

        Roles_In_Communities roles = rolesService.getRoleByUserIdAndCommunityId(currentUser.getUserId(),    communityId);

        boolean isKralid = currentUser.getUserId()== community.getOwner().getUserId();

        model.addAttribute("isKralid", isKralid);

        List<Post> posts = postRepository.findByCommunity(community);

        model.addAttribute("privatecommunity", community.getIsPrivate());

        model.addAttribute("communityId", communityId);

        boolean isSubscribed = false;
        boolean isRequested = false;

        if (roles != null) {

            isSubscribed = !roles.getRole().equals("REQUESTED");

            if (roles.getRole().equals("MEMBER") || roles.getRole().equals("MODERATOR") || roles.getRole().equals("ADMIN") || isKralid) {
                model.addAttribute("show_posts",true);
                model.addAttribute("users", rolesService.getRolesInCommunity(communityService.getCommunityById(communityId)));
                model.addAttribute("show_posts",true);
                model.addAttribute("posts", posts);
            } else {
                model.addAttribute("show_posts",false);
            }

            if (roles.getRole().equals("REQUESTED")) {
                model.addAttribute("isRequested", true);
            } else {
                model.addAttribute("isRequested", false);
            }

            model.addAttribute("isMember", roles.getRole().equals("MEMBER"));
            model.addAttribute("isAdmin", roles.getRole().equals("ADMIN"));
            model.addAttribute("isModerator", roles.getRole().equals("MODERATOR"));
            model.addAttribute("isSubscribed", isSubscribed);

        } else {
            model.addAttribute("isMember", false);
            model.addAttribute("isAdmin", false);
            model.addAttribute("isModerator", false);
            model.addAttribute("isSubscribed", false);
            model.addAttribute("isRequested", false);
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
        User currentUser = userService.getAuthenticatedUser();

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("userId", currentUser.getUserId());

        List<Community> communities = communityService.getAllCommunitiesSorted();
        model.addAttribute("communities", communities);

        List<Roles_In_Communities> rolesForUser = rolesService.getAllCommunitiesForAUser(currentUser);
        model.addAttribute("roles_for_a_user", rolesForUser);

        return "Communities/Communities";
    }


    @PostMapping("/Communities/community/{communityId}/join")
    public String joinCommunity(@PathVariable("communityId") Long communityId, RedirectAttributes redirectAttributes) {

        Community community = communityService.getCommunityById(communityId);

        User user = userService.getAuthenticatedUser();

        rolesService.addMemberToUserInCommunity(community, user, "MEMBER");
        redirectAttributes.addFlashAttribute("successMessage", "Joined the Community!");
        return "redirect:/Communities/community/" + communityId;
    }

    @PostMapping("/Communities/community/{communityId}/joinrequest")
    public String joinRequestCommunity(@PathVariable("communityId") Long communityId, RedirectAttributes redirectAttributes){
        Community community = communityService.getCommunityById(communityId);
        User user = userService.getAuthenticatedUser();
        rolesService.addMemberToUserInCommunity(community, user, "REQUESTED");
        redirectAttributes.addFlashAttribute("successMessage", "Request sent to join the Community!");
        return "redirect:/Communities/community/" + communityId;
    }

    @PostMapping("/Communities/community/{communityId}/acceptrequest/{userId}")
    public String acceptRequest(@PathVariable("communityId") Long communityId, @PathVariable("userId") Long userId, RedirectAttributes redirectAttributes){
        Community community = communityService.getCommunityById(communityId);
        User user = userRepository.findByUserId(userId);
        rolesService.addMemberToUserInCommunity(community, user, "MEMBER");
        redirectAttributes.addFlashAttribute("successMessage", "Request accepted!");
        return "redirect:/Communities/community/" + communityId;
    }

    @PostMapping("/Communities/community/{communityId}/rejectrequest/{userId}")
    public String rejectRequest(@PathVariable("communityId") Long communityId, @PathVariable("userId") Long userId, RedirectAttributes redirectAttributes){
        Community community = communityService.getCommunityById(communityId);
        User user = userRepository.findByUserId(userId);
        rolesService.removeMemberFromUserInCommunity(user, community);
        redirectAttributes.addFlashAttribute("successMessage", "Request rejected!");
        return "redirect:/Communities/community/" + communityId;
    }

    @PostMapping("/Communities/community/{communityId}/remove/{userId}")
    public String removeMember(@PathVariable("communityId") Long communityId, @PathVariable("userId") Long userId, RedirectAttributes redirectAttributes){
        Community community = communityService.getCommunityById(communityId);
        User user = userRepository.findByUserId(userId);
        rolesService.removeMemberFromUserInCommunity(user, community);
        redirectAttributes.addFlashAttribute("successMessage", "Member removed!");
        return "redirect:/Communities/community/" + communityId;
    }

    @PostMapping("/Communities/community/{communityId}/makemoderator/{userId}")
    public String makeModerator(@PathVariable("communityId") Long communityId, @PathVariable("userId") Long userId, RedirectAttributes redirectAttributes){
        Community community = communityService.getCommunityById(communityId);
        User user = userRepository.findByUserId(userId);
        rolesService.addMemberToUserInCommunity(community, user, "MODERATOR");
        redirectAttributes.addFlashAttribute("successMessage", "Moderator added!");
        return "redirect:/Communities/community/" + communityId;
    }

    @PostMapping("/Communities/community/{communityId}/removemoderator/{userId}")
    public String removeModerator(@PathVariable("communityId") Long communityId, @PathVariable("userId") Long userId, RedirectAttributes redirectAttributes){
        Community community = communityService.getCommunityById(communityId);
        User user = userRepository.findByUserId(userId);
        rolesService.addMemberToUserInCommunity(community, user, "MEMBER");
        redirectAttributes.addFlashAttribute("successMessage", "Moderator Removed!");
        return "redirect:/Communities/community/" + communityId;
    }

    @PostMapping("/Communities/community/{communityId}/removeuser/{userId}")
    public String removeUser(@PathVariable("communityId") Long communityId, @PathVariable("userId") Long userId, RedirectAttributes redirectAttributes){
        Community community = communityService.getCommunityById(communityId);
        User user = userRepository.findByUserId(userId);
        rolesService.removeMemberFromUserInCommunity(user, community);
        redirectAttributes.addFlashAttribute("successMessage", "User removed!");
        return "redirect:/Communities/community/" + communityId;
    }

    @PostMapping("/Communities/community/{communityId}/canceljoinrequest")
    public String cancelJoinRequest(@PathVariable("communityId") Long communityId, RedirectAttributes redirectAttributes){
        Community community = communityService.getCommunityById(communityId);
        User user = userService.getAuthenticatedUser();
        rolesService.removeMemberFromUserInCommunity(user, community);
        redirectAttributes.addFlashAttribute("successMessage", "Request cancelled!");
        return "redirect:/Communities/community/" + communityId;
    }




    @PostMapping("/Communities/community/{communityId}/leave")
    public String leaveCommunity(@PathVariable("communityId") Long communityId, RedirectAttributes redirectAttributes) {

        Community community = communityService.getCommunityById(communityId);
        User user = userService.getAuthenticatedUser();
        rolesService.removeMemberFromUserInCommunity(user,community);
        redirectAttributes.addFlashAttribute("successMessage", "We are sad to see you leave our community :(");
        return "redirect:/Communities/community/" + communityId;

    }

    @GetMapping("/searchCommunity")
    public String showsearchedCommunities(Model model,
                                          @RequestParam(value = "writing", required = true) String writing)
    {
        User currentUser = userService.getAuthenticatedUser();

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("userId", currentUser.getUserId());

        List<Community> communities = communityService.getCommunitiesByTitleContaining(writing);
        model.addAttribute("communities", communities);

        return "Communities/SearchedCommunities";
    }

    @PostMapping("/upload/{communityId}")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, @PathVariable Long communityId, Model model) {
        String filename = fileUploadService.store(file);
        String fileDownloadUri = "/files/" + filename;
        communityService.updateCommunityImageUrl(communityId, fileDownloadUri);
        model.addAttribute("message", "File uploaded successfully: " + filename);
        return "redirect:/Communities/community/" + communityId;
    }

    @GetMapping("/{communityId}/search/template")
    public String showPostsWithTemplate(Model model,
                                        @RequestParam("template") String writing,
                                        @PathVariable long communityId) {
        return prepareModelForCommunity(model, communityId, postRepository.findByCommunityAndTemplateTitleContaining(communityId, writing));
    }

    @GetMapping("/{communityId}/search/user")
    public String showPostsWithUser(Model model,
                                    @RequestParam("user") String writing,
                                    @PathVariable long communityId) {
        return prepareModelForCommunity(model, communityId, postRepository.findByCommunityAndUserUsernameContaining(communityId, writing));
    }

    @GetMapping("/{communityId}/search/field")
    public String showPostsWithField(Model model,
                                     @RequestParam("field") String writing,
                                     @PathVariable long communityId) {
        return prepareModelForCommunity(model, communityId, postRepository.findByCommunityAndFieldValueContaining(communityId, writing));
    }

    @GetMapping("/{communityId}/search/date")
    public String showPostsWithDate(Model model,
                                    @RequestParam("dateFrom") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
                                    @RequestParam("dateTo") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
                                    @PathVariable long communityId) {
        Instant instant1 = dateTo.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant instant2 = dateFrom.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return prepareModelForCommunity(model, communityId, postRepository.findByCommunityAndCreationDateBetween(communityId, instant2, instant1));
    }

    private String prepareModelForCommunity(Model model, long communityId, List<Post> posts) {

        Community community = communityService.getCommunityById(communityId);
        User currentUser = userService.getAuthenticatedUser();

        model.addAttribute("community", community);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("userId", currentUser.getUserId());

        Roles_In_Communities roles = rolesService.getRoleByUserIdAndCommunityId(currentUser.getUserId(), communityId);

        boolean isKralid = currentUser.getUserId() == community.getOwner().getUserId();
        model.addAttribute("isKralid", isKralid);
        model.addAttribute("privatecommunity", community.getIsPrivate());
        model.addAttribute("communityId", communityId);

        if (roles != null) {
            boolean isSubscribed = !roles.getRole().equals("REQUESTED");
            model.addAttribute("isSubscribed", isSubscribed);
            model.addAttribute("isMember", roles.getRole().equals("MEMBER"));
            model.addAttribute("isAdmin", roles.getRole().equals("ADMIN"));
            model.addAttribute("isModerator", roles.getRole().equals("MODERATOR"));
            model.addAttribute("isRequested", roles.getRole().equals("REQUESTED"));
            model.addAttribute("show_posts", isSubscribed || roles.getRole().equals("ADMIN") || roles.getRole().equals("MODERATOR") || isKralid);
            model.addAttribute("users", rolesService.getRolesInCommunity(community));
            model.addAttribute("posts", posts);
        } else {
            model.addAttribute("isMember", false);
            model.addAttribute("isAdmin", false);
            model.addAttribute("isModerator", false);
            model.addAttribute("isSubscribed", false);
            model.addAttribute("show_posts", !community.getIsPrivate());
            model.addAttribute("posts", community.getIsPrivate() ? null : posts);
        }

        return "Communities/Posts/searchResults";
    }
}