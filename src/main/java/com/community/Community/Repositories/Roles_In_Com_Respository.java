package com.community.Community.Repositories;

import com.community.Community.models.Users.Roles_In_Communities;
import com.community.Community.models.Users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface Roles_In_Com_Respository extends JpaRepository<Roles_In_Communities, Long> {

    User findByUserId(Long userId);
    Roles_In_Communities findByUserIdAndCommunityId(Long userId);


}
