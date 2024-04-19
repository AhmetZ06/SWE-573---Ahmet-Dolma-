package com.community.Community.Repositories;
import com.community.Community.models.Community;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.community.Community.models.Users.*;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    User findByUsername(String username);
    User findByUserId(Long UserID);

}
