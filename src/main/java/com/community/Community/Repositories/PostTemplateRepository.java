package com.community.Community.Repositories;

import com.community.Community.models.Posts.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostTemplateRepository extends JpaRepository<Post, Long> {
}
