package com.community.Community.Repositories;

import com.community.Community.models.Community;
import com.community.Community.models.Posts.Post;
import com.community.Community.models.Users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUser(User user);
    List<Post> findByCommunity(Community community);

}
