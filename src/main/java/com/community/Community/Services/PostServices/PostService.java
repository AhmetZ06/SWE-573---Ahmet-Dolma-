package com.community.Community.Services.PostServices;

import com.community.Community.Repositories.CommunityRepository;
import com.community.Community.Repositories.PostRepository;
import com.community.Community.Repositories.PostTemplateRepository;
import com.community.Community.Repositories.UserRepository;
import com.community.Community.Services.UserServices.CustomUserDetailsService;
import com.community.Community.dto.PostDto;
import com.community.Community.dto.PostTemplateDto;
import com.community.Community.models.Community;
import com.community.Community.models.Posts.Post;
import com.community.Community.models.Posts.PostTemplate;
import com.community.Community.models.Users.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class PostService{

    private  PostRepository postRepository;
    private CommunityRepository communityRepository;
    private UserRepository userRepository;
    private CustomUserDetailsService userService;
    private PostTemplateRepository PostTemplateRepository;

    public PostService(PostRepository postRepository,
                       CommunityRepository communityRepository,
                       UserRepository userRepository,
                       CustomUserDetailsService userService,
                       PostTemplateRepository PostTemplateRepository)
    {
        this.postRepository = postRepository;
        this.communityRepository = communityRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.PostTemplateRepository = PostTemplateRepository;
    }


    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found: " + postId));
    }

    public Post updatePost(Post post) {
        if (post == null || post.getPostId() == null) {
            throw new IllegalArgumentException("Post ID cannot be null");
        }
        return postRepository.save(post);
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    public void savePost(PostDto postDto, Long communityId) {

        Post post = new Post();

        User user = userService.getAuthenticatedUser();

        long originalid = user.getUserId();

        user.setUserId(originalid);

        post.setUser(user);

        post.setTitle(postDto.getTitle());
        post.setContentData(postDto.getContentData());

        post.setCommunity(communityRepository.findByCommunityId(communityId));


        Instant now = Instant.now();
        post.setCreationDate(now);

//        post.setTemplate(PostTemplateRepository.findById(postDto.getTemplateId()).
//                orElseThrow(() -> new IllegalArgumentException
//                        ("Template not found: " + postDto.getTemplateId());

        postRepository.save(post);

        System.out.println("Post saved");

    }

    public PostTemplate convertDtoToPostTemplate(PostTemplateDto dto) {
        PostTemplate postTemplate = new PostTemplate();
        postTemplate.setInclude_Pool(dto.isInclude_Pool());
        postTemplate.setInclude_Geolocation(dto.isInclude_Geolocation());
        postTemplate.setInclude_Event(dto.isInclude_Event());
        return postTemplate;
    }


    public PostTemplate getPostTemplateById(Long templateId) {
        return PostTemplateRepository.findById(templateId).orElseThrow(() -> new IllegalArgumentException("Template not found: " + templateId));
    }

    public List<PostTemplate> getAllPostTemplatesByCom(Community community) {
        return PostTemplateRepository.findPostTemplateByCommunity(community);
    }




}
