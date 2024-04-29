package com.community.Community.Services.PostServices;

import com.community.Community.dto.PostDto;
import com.community.Community.models.Posts.Post;

import java.util.List;

public interface IPostService {
    Post createPost(Post post);
    List<Post> getAllPosts();
    Post getPostById(Long postId);
    Post updatePost(Post post);
    void deletePost(Long postId);
    void savePost(PostDto postDto, Long communityId);

}
