//package com.community.Community;
//
//
//import com.community.Community.Controller.ServerSide.CommunityController;
//import com.community.Community.Repositories.PostRepository;
//import com.community.Community.Repositories.UserRepository;
//import com.community.Community.Services.CommunityService.CommunityService;
//import com.community.Community.Services.CommunityService.RolesService;
//import com.community.Community.Services.FileUploadService;
//import com.community.Community.Services.PostServices.PostService;
//import com.community.Community.Services.PostServices.PostTemplateService;
//import com.community.Community.Services.UserServices.CustomUserDetailsService;
//import com.community.Community.models.Community;
//import com.community.Community.models.Posts.Post;
//import com.community.Community.models.Users.Roles_In_Communities;
//import com.community.Community.models.Users.User;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Collections;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
//
//@ExtendWith(MockitoExtension.class)
//@WebMvcTest(controllers = CommunityController.class)
//public class CommunityControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private CommunityService communityService;
//
//    @MockBean
//    private CustomUserDetailsService userService;
//
//    @MockBean
//    private PostRepository postRepository;
//
//    @MockBean
//    private PostService postService;
//
//    @MockBean
//    private UserRepository userRepository;
//
//    @MockBean
//    private RolesService rolesService;
//
//    @MockBean
//    private PostTemplateService postTemplateService;
//
//    @MockBean
//    private FileUploadService fileUploadService;
//
//    private Community community;
//    private User user;
//    private Roles_In_Communities rolesInCommunities;
//
//    @BeforeEach
//    public void setup() {
//        community = new Community();
//        community.setCommunityId(1L);
//        community.setName("Test Community");
//
//        user = new User();
//        user.setUserId(1L);
//        user.setUsername("testUser");
//
//        rolesInCommunities = new Roles_In_Communities();
//        rolesInCommunities.setRole("MEMBER");
//        rolesInCommunities.setUser(user);
//        rolesInCommunities.setCommunity(community);
//
//        when(communityService.getCommunityById(anyLong())).thenReturn(community);
//        when(userService.getAuthenticatedUser()).thenReturn(user);
//        when(rolesService.getRoleByUserIdAndCommunityId(anyLong(), anyLong())).thenReturn(rolesInCommunities);
//        when(postRepository.findByCommunity(any(Community.class))).thenReturn(Collections.emptyList());
//
//    }
//
//    @Test
//    public void testShowCommunity() throws Exception {
//        mockMvc.perform(get("/Communities/community/1"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("Communities/genericCommunityTemplate2"))
//                .andExpect(model().attributeExists("community"))
//                .andExpect(model().attributeExists("currentUser"))
//                .andExpect(model().attributeExists("userId"))
//                .andExpect(model().attributeExists("isKralid"))
//                .andExpect(model().attributeExists("privatecommunity"))
//                .andExpect(model().attributeExists("communityId"))
//                .andExpect(model().attributeExists("show_posts"))
//                .andExpect(model().attributeExists("users"))
//                .andExpect(model().attributeExists("posts"));
//    }
//}
