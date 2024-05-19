package com.community.Community.Services.UserServices;

import com.community.Community.Repositories.ProfileRepository;
import com.community.Community.models.Users.Profile;
import com.community.Community.models.Users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Profile_Service {

    private final ProfileRepository profileRepository;

    @Autowired
    public Profile_Service(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public void initialize(User user, Profile profile) {
        profile.setUser(user);
        profileRepository.save(profile);
    }

    public Profile updateProfileImageUrl(Long profileId, String imageUrl) {
        Profile profile = profileRepository.findProfileByProfileId(profileId);
        profile.setImageUrl(imageUrl);
        return profileRepository.save(profile);
    }

    public Profile getProfileByUser(User user) {
        return profileRepository.findProfileByUser(user);
    }

    public Profile getProfileByProfileId(Long profileId) {
        return profileRepository.findProfileByProfileId(profileId);
    }


}
