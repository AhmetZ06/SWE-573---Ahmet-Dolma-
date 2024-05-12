package com.community.Community.Repositories;

import com.community.Community.models.Community;
import com.community.Community.models.Users.Roles_In_Communities;
import com.community.Community.models.Users.User;
import com.community.Community.models.Users.User_Community_Composite_Key;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface Roles_In_Communities_Respository extends JpaRepository<Roles_In_Communities, Long> {


    Roles_In_Communities findByCommunity(Community community);

    Optional<Roles_In_Communities> findByUserAndCommunity(User user, Community community);

    Optional<Roles_In_Communities> findById(User_Community_Composite_Key id);

    List<Roles_In_Communities> findByUser(User user);

    void deleteRoles_In_CommunitiesByUser(User user);
    void deleteRoles_In_CommunitiesByUserAndCommunity(User user, Community community);



}
