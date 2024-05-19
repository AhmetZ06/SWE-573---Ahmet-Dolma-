package com.community.Community.Controller.ServerSide;

import com.community.Community.Services.FileUploadService;
import com.community.Community.Services.UserServices.Profile_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.MalformedURLException;

import java.nio.file.Path;

@Controller
@RequestMapping("/files")
public class FileUploadController {

    private final FileUploadService fileUploadService;
    private final Profile_Service profileService;

    @Autowired
    public FileUploadController(FileUploadService fileUploadService, Profile_Service profileService) {
        this.fileUploadService = fileUploadService;
        this.profileService = profileService;
        // Initialize the storage directory
        this.fileUploadService.init();
    }

    @GetMapping("/upload")
    public String listUploadedFiles(Model model) {
        return "Images/UploadForm";  // Correct the template path if necessary
    }

    @PostMapping("/upload/{profileId}")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, @PathVariable Long profileId, Model model) {
        String filename = fileUploadService.store(file);
        String fileDownloadUri = "/files/" + filename;
        profileService.updateProfileImageUrl(profileId, fileDownloadUri);
        model.addAttribute("message", "File uploaded successfully: " + filename);
        model.addAttribute("imageUrl", fileDownloadUri);
        return "Images/UploadForm";  // Correct the template path if necessary
    }

    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            Path file = fileUploadService.load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("Could not read the file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
