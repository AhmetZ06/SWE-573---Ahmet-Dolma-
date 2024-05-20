package com.community.Community.Services.PostServices;

import com.community.Community.Repositories.*;
import com.community.Community.Services.UserServices.CustomUserDetailsService;
import com.community.Community.models.Posts.Post;
import com.community.Community.models.Posts.CustomField;
import com.community.Community.models.Posts.PostTemplate;
import com.community.Community.models.Posts.PostFieldValue;
import com.community.Community.models.Users.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;
    private final CustomUserDetailsService userService;
    private final PostTemplateRepository postTemplateRepository;
    private final PostFieldValueRepository postFieldValueRepository;

    // Constructor injection for all required repositories and services
    public PostService(PostRepository postRepository,
                       CommunityRepository communityRepository,
                       UserRepository userRepository,
                       CustomUserDetailsService userService,
                       PostTemplateRepository postTemplateRepository,
                       PostFieldValueRepository postFieldValueRepository) {
        this.postRepository = postRepository;
        this.communityRepository = communityRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.postTemplateRepository = postTemplateRepository;
        this.postFieldValueRepository = postFieldValueRepository;
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

    @Transactional
    public void savePost(Post post, Map<String, String> fields) {
        List<PostFieldValue> postFieldValues = new ArrayList<>();
        PostTemplate template = post.getPostTemplate();

        for (CustomField field : template.getFields()) {
            String fieldName = field.getFieldName();
            String value = fields.get("fields[" + fieldName + "]");

            if (field.getFieldType().equals("GEOLOCATION")) {
                String lat = fields.get("fields[" + fieldName + "_lat]");
                String lon = fields.get("fields[" + fieldName + "_lon]");
                if (!isValidLatitude(lat) || !isValidLongitude(lon)) {
                    throw new IllegalArgumentException("Invalid latitude or longitude value");
                }
                value = lat + "," + lon;
            } else if (field.getFieldType().equals("INTEGER")) {
                if (!isInteger(value)) {
                    throw new IllegalArgumentException("Invalid integer value for field: " + fieldName);
                }
            } else if (field.getFieldType().equals("DOUBLE")) {
                if (!isDouble(value)) {
                    throw new IllegalArgumentException("Invalid double value for field: " + fieldName);
                }
            }

            PostFieldValue postFieldValue = new PostFieldValue();
            postFieldValue.setCustomField(field);
            postFieldValue.setFieldValue(value);
            postFieldValue.setPost(post);
            postFieldValues.add(postFieldValue);
        }

        post.setFieldValues(postFieldValues);

        postRepository.save(post);
        postFieldValueRepository.saveAll(postFieldValues);
    }

    private boolean isValidLatitude(String latitude) {
        try {
            double lat = Double.parseDouble(latitude);
            return lat >= -90 && lat <= 90;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void initializepost(Post post) {
        post.setCreationDate(Instant.now());
    }

    private boolean isValidLongitude(String longitude) {
        try {
            double lon = Double.parseDouble(longitude);
            return lon >= -180 && lon <= 180;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
