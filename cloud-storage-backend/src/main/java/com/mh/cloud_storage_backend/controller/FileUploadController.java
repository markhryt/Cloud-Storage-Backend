package com.mh.cloud_storage_backend.controller;

import com.mh.cloud_storage_backend.model.entities.requests.FileChunkDTO;
import com.mh.cloud_storage_backend.model.entities.requests.FileUploadDTO;
import com.mh.cloud_storage_backend.service.FileChunkService;
import com.mh.cloud_storage_backend.service.FileService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class FileUploadController {

    private final FileService fileService;
    private final FileChunkService fileChunkService;

    public FileUploadController(FileService fileService, FileChunkService fileChunkService) {
        this.fileService = fileService;
        this.fileChunkService = fileChunkService;
    }


    /**
        Handles uploading a file entity.
        The file metadata is uploaded in json format, as the request body, as well as the file chunks count
     */
    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(
            @RequestBody FileUploadDTO fileUploadDTO,
            HttpServletRequest request
    ) {
        fileService.addFile(fileUploadDTO, request);

        return ResponseEntity.ok("File entity uploaded successfully: ");
    }

    @PostMapping("/chunk-upload")
    public ResponseEntity<String> handleChunkUpload(
            @RequestPart("metadata") FileChunkDTO fileChunkDTO,
            @RequestPart("file") MultipartFile file
            ){

        System.out.println(fileChunkDTO.getOrderNumber());
        System.out.println(fileChunkDTO.getFileId());

        System.out.println(file.getName());

        if (fileChunkService.uploadFileChunk(file, fileChunkDTO)){
            return ResponseEntity.ok("File entity uploaded successfully: ");
        }

        return ResponseEntity.ok("File entity upload failed: " + "Please check the file metadata and try again.");

    }
}