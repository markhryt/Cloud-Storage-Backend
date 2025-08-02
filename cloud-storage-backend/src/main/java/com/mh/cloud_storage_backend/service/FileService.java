package com.mh.cloud_storage_backend.service;

import com.mh.cloud_storage_backend.model.entities.Files;
import com.mh.cloud_storage_backend.model.entities.Users;
import com.mh.cloud_storage_backend.model.entities.requests.FileUploadRequest;
import com.mh.cloud_storage_backend.model.repository.FilesRepo;
import com.mh.cloud_storage_backend.model.util.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    private final FilesRepo fileRepo;
    private final UsersService usersService;
    private final JWTUtil jwtUtil;
    private final FileSystemStorageService fileSystemStorageService;

    public FileService(FilesRepo fileRepo, FileSystemStorageService fileSystemStorageService, UsersService usersService,
                       JWTUtil jwtUtil) {
        this.fileRepo = fileRepo;
        this.fileSystemStorageService = fileSystemStorageService;
        this.usersService = usersService;
        this.jwtUtil = jwtUtil;
    }


    public void addFile(MultipartFile file, FileUploadRequest fileUploadRequest, HttpServletRequest request) {
        // Here we split the logic into two parts:
        // 1. Take MultipartFile metadata and create a DB entity
        // 2. Save the actual file to the filesystem locally using the FileSystemStorageService.
        this.addFileToDB(fileUploadRequest, file.getOriginalFilename(), request);
        fileSystemStorageService.store(file);

    }
    public Files addFileToDB(FileUploadRequest fileRequest, String filename, HttpServletRequest request) {
        if (filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException("Filename cannot be empty");
        }

        Files fileEntity = new Files();
        Users owner = usersService.getUserByEmail(jwtUtil.extractUsername(request.getHeader("Authorization").substring(7)));
        fileEntity.setName(filename);
        fileEntity.setTags(fileRequest.getTags());
        fileEntity.setOwner(owner);

        return fileRepo.save(fileEntity);
    }

}
