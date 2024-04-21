package com.community.Community.Repositories;

import com.community.Community.models.Users.User;
import com.community.Community.models.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

}