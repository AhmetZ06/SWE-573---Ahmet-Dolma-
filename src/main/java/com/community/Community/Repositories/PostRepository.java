package com.community.Community.Repositories;

import com.community.Community.models.Community;
import com.community.Community.models.Posts.Post;
import com.community.Community.models.Posts.PostTemplate;
import com.community.Community.models.Users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUser(User user);
    List<Post> findByCommunity(Community community);
    List<Post> findByCreationDateBetween(Instant start, Instant end);
    List<Post> findByCreationDateAfter(Instant start);
    List<Post> findByCreationDateBefore(Instant end);
    List<Post> findPostByPostTemplate(PostTemplate postTemplate);
    @Query("SELECT p FROM Post p WHERE p.community.communityId = :communityId AND p.postTemplate.title LIKE %:template%")
    List<Post> findByCommunityAndTemplateTitleContaining(@Param("communityId") long communityId, @Param("template") String template);

    @Query("SELECT p FROM Post p WHERE p.community.communityId = :communityId AND p.user.username LIKE %:username%")
    List<Post> findByCommunityAndUserUsernameContaining(@Param("communityId") long communityId, @Param("username") String username);

    @Query("SELECT p FROM Post p WHERE p.community.communityId = :communityId AND EXISTS (SELECT fv FROM p.fieldValues fv WHERE fv.fieldValue LIKE %:fieldValue%)")
    List<Post> findByCommunityAndFieldValueContaining(@Param("communityId") long communityId, @Param("fieldValue") String fieldValue);

    @Query("SELECT p FROM Post p WHERE p.community.communityId = :communityId AND p.creationDate BETWEEN :dateFrom AND :dateTo")
    List<Post> findByCommunityAndCreationDateBetween(@Param("communityId") long communityId,
                                                     @Param("dateFrom") Instant dateFrom,
                                                     @Param("dateTo") Instant dateTo);

}
