package com.mh.cloud_storage_backend.controller;

import com.mh.cloud_storage_backend.model.entities.requests.AuthenticationRequest;
import com.mh.cloud_storage_backend.model.util.JWTUtil;
import com.mh.cloud_storage_backend.service.FolderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/folders")
public class FolderController {
    private final FolderService folderService;
    private final JWTUtil jwtUtil;

    public FolderController(FolderService folderService, JWTUtil jwtUtil) {
        this.folderService = folderService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createFolder(@RequestBody AuthenticationRequest.FolderCreateRequest folderName, HttpServletRequest request) {
        // Logic to create a folder with the given name
        if (folderName == null) {
            return ResponseEntity.badRequest().body("Folder name cannot be empty");
        }
        String userEmail = jwtUtil.extractUsernameByRequest(request);
        if (folderService.createFolder(folderName.getFolderName(), userEmail)) {
            return ResponseEntity.ok("Folder created");
        }
        return ResponseEntity.badRequest().body("Could not create folder");

    }
}
