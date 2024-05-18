package com.community.Community.Services.PostServices;

import com.community.Community.Repositories.CommunityRepository;
import com.community.Community.Repositories.PostRepository;
import com.community.Community.Repositories.PostTemplateRepository;
import com.community.Community.Repositories.UserRepository;
import com.community.Community.Services.UserServices.CustomUserDetailsService;
import com.community.Community.models.Posts.Post;
import com.community.Community.models.Posts.CustomField;
import com.community.Community.models.Posts.PostTemplate;
import com.community.Community.models.Posts.PostFieldValue;
import com.community.Community.models.Users.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;
    private final CustomUserDetailsService userService;
    private final PostTemplateRepository postTemplateRepository;

    // Constructor injection for all required repositories and services
    public PostService(PostRepository postRepository,
                       CommunityRepository communityRepository,
                       UserRepository userRepository,
                       CustomUserDetailsService userService,
                       PostTemplateRepository postTemplateRepository) {
        this.postRepository = postRepository;
        this.communityRepository = communityRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.postTemplateRepository = postTemplateRepository;
    }

    // Method to save a post
    public Post savePost(Post post) {
        // Perform any necessary pre-save logic here
        return postRepository.save(post);
    }

    // Method to find all posts by a user
    public List<Post> findPostsByUser(User user) {
        return postRepository.findByUser(user);
    }

    // Method to find a post by its ID
    public Post findPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    // Method to find all posts by a community
    public List<Post> findPostsByCommunity(Long communityId) {
        return postRepository.findByCommunity(communityRepository.findByCommunityId(communityId));
    }

    // Method to create a post from a template
    public Post createPostFromTemplate(PostTemplate template, User user, String title, String description, List<PostFieldValue> fieldValues) {
        Post post = new Post();
        post.setPostTemplate(template);
        post.setUser(user);
        post.setTitle(title);
        post.setDescription(description);
        post.setFieldValues(fieldValues); // Ensure this is set correctly
        post.setCreationDate(Instant.now());

        // Associate field values with the post
        for (PostFieldValue fieldValue : fieldValues) {
            fieldValue.setPost(post);
        }

        return postRepository.save(post);
    }
}
