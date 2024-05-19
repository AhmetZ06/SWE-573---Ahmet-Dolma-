package com.community.Community.Repositories;

import com.community.Community.models.Community;
import com.community.Community.models.Posts.Post;
import com.community.Community.models.Posts.PostTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostTemplateRepository extends JpaRepository<PostTemplate, Long> {

    List<PostTemplate> findPostTemplateByCommunity(Community community);

    PostTemplate findPostTemplateByTemplateId(Long postTemplateId);

    PostTemplate findPostTemplateByCommunityAndTitle(Community community, String templateTitle);

}
