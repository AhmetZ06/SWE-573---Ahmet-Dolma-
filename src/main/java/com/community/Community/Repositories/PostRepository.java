package com.community.Community.Repositories;

import com.community.Community.models.Posts.Post;
import com.community.Community.models.Users.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post, Long> {

}
