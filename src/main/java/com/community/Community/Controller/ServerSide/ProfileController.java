package com.community.Community.Controller.ServerSide;

import com.community.Community.Services.CommunityService.CommunityService;
import com.community.Community.Services.CommunityService.RolesService;
import com.community.Community.Services.FileUploadService;
import com.community.Community.Services.PostServices.PostService;
import com.community.Community.Services.UserServices.CustomUserDetailsService;
import com.community.Community.Services.UserServices.Profile_Service;
import com.community.Community.Services.UserServices.UserService;
import com.community.Community.models.Posts.Post;
import com.community.Community.models.Users.Profile;
import com.community.Community.models.Users.Roles_In_Communities;
import com.community.Community.models.Users.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final FileUploadService fileUploadService;
    private final CustomUserDetailsService userService;
    private final PostService postService;
    private final CommunityService communityService;
    private final Profile_Service profileService;
    private final RolesService rolesService;
    private final UserService UserService2;

    @Autowired
    public ProfileController(FileUploadService fileUploadService, CustomUserDetailsService userService,
                             PostService postService, CommunityService communityService,
                             Profile_Service profileService, RolesService rolesService,
                             UserService UserService2) {
        this.fileUploadService = fileUploadService;
        this.userService = userService;
        this.postService = postService;
        this.communityService = communityService;
        this.profileService = profileService;
        this.rolesService = rolesService;
        this.UserService2 = UserService2;
        // Initialize the storage directory
        this.fileUploadService.init();
    }

    @GetMapping("/{userId}")
    public String getUserProfile(@PathVariable Long userId, Model model) {
        User user = userService.findUserById(userId);
        Profile profile = profileService.getProfileByUser(user);
        List<Post> userPosts = postService.findPostsByUser(user);
        List<Roles_In_Communities> joinedCommunities = rolesService.getAllCommunitiesForAUser(user);

        model.addAttribute("profile", profile);
        model.addAttribute("userPosts", userPosts);
        model.addAttribute("joinedCommunities", joinedCommunities);
        return "profile";
    }

    @PostMapping("/upload/{profileId}")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, @PathVariable Long profileId, Model model) {
        String filename = fileUploadService.store(file);
        String fileDownloadUri = "/files/" + filename;
        profileService.updateProfileImageUrl(profileId, fileDownloadUri);
        model.addAttribute("message", "File uploaded successfully: " + filename);

        return "redirect:/profile/" + UserService2.findUserByProfile(profileService.getProfileByProfileId(profileId)).getUserId();  // Redirect to the profile page
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            Path file = fileUploadService.load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("Could not read the file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
