package com.mh.cloud_storage_backend.service;

import com.mh.cloud_storage_backend.model.entities.Files;
import com.mh.cloud_storage_backend.model.entities.Users;
import com.mh.cloud_storage_backend.model.entities.dto.FileUploadRequest;
import com.mh.cloud_storage_backend.model.repository.FilesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    private final FilesRepo fileRepo;
    private final UsersService usersService;


    private final FileSystemStorageService fileSystemStorageService;
    @Autowired
    public FileService(FilesRepo fileRepo, FileSystemStorageService fileSystemStorageService, UsersService usersService) {
        this.fileRepo = fileRepo;
        this.fileSystemStorageService = fileSystemStorageService;
        this.usersService = usersService;
    }


    public void addFile(MultipartFile file, FileUploadRequest fileUploadRequest) {
        // Here we split the logic into two parts:
        // 1. Take MultipartFile metadata and create a DB entity
        // 2. Save the actual file to the filesystem locally using the FileSystemStorageService.
        this.addFileToDB(fileUploadRequest, file.getOriginalFilename());
        fileSystemStorageService.store(file);

    }
    public Files addFileToDB(FileUploadRequest fileRequest, String filename) {
        if (filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException("Filename cannot be empty");
        }

        Files fileEntity = new Files();
        Users owner = usersService.getUserByEmail(fileRequest.getOwnerEmail());
        fileEntity.setName(filename);
        fileEntity.setTags(fileRequest.getTags());
        fileEntity.setOwner(owner);

        return fileRepo.save(fileEntity);
    }

}
