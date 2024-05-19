package com.community.Community.Services.CommunityService;

import com.community.Community.models.Community;
import com.community.Community.Repositories.CommunityRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class CommunityImageService {

    @Value("${upload.path}")
    private String uploadPath;

    private final CommunityRepository communityRepository;

    public CommunityImageService(CommunityRepository communityRepository) {
        this.communityRepository = communityRepository;
    }

    public void init() {
        try {
            Files.createDirectories(Paths.get(uploadPath));
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage", e);
        }
    }

    public String storeCommunityImage(MultipartFile file, Long communityId) {
        try {
            // Create a unique filename and save the file
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadPath, filename);
            Files.copy(file.getInputStream(), path);

            // Update the community's imageUrl
            Community community = communityRepository.findById(communityId).orElseThrow();
            community.setImageUrl("/community-images/" + filename);
            communityRepository.save(community);

            return filename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    public Path load(String filename) {
        return Paths.get(uploadPath).resolve(filename);
    }
}
