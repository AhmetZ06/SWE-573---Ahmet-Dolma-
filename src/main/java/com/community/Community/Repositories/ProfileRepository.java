package com.community.Community.Repositories;

import com.community.Community.models.Users.Profile;
import com.community.Community.models.Users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Profile findProfileByProfileId(Long profileId);

    Profile findProfileByUser(User user);

}
