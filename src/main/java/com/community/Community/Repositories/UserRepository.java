package com.community.Community.Repositories;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.community.Community.models.Users.*;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
