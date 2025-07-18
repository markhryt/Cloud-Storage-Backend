package com.mh.cloud_storage_backend.controller;

import com.mh.cloud_storage_backend.model.entities.dto.FileUploadRequest;
import com.mh.cloud_storage_backend.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class FileUploadController {

    private final FileService fileService;

    @Autowired
    public FileUploadController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(
            @RequestPart("file") MultipartFile multipartFile,
            @RequestParam(value = "tags", required = false) String tags,
            @RequestParam(value = "email", required = false) String email
    ) {
        FileUploadRequest metadata = new FileUploadRequest();
        metadata.setTags(tags);
        metadata.setOwnerEmail(email);
        fileService.addFile(multipartFile, metadata);
        return ResponseEntity.ok("File uploaded successfully: " + multipartFile.getOriginalFilename());
    }
}