package com.community.Community.Repositories;

import com.community.Community.models.Community;
import com.community.Community.models.Users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long>{

    Community findByName(String name);
    Community findByCommunityId(long communityId);
    Community findByAdmin_UserId(Long userId);;



    // Method to find communities where description contains a given string
    List<Community> findByDescriptionContainingIgnoreCase(String description);

}
