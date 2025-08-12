package com.mh.cloud_storage_backend.service;

import com.mh.cloud_storage_backend.model.entities.Files;
import com.mh.cloud_storage_backend.model.entities.Users;
import com.mh.cloud_storage_backend.model.entities.requests.FileUploadDTO;
import com.mh.cloud_storage_backend.model.repository.FilesRepo;
import com.mh.cloud_storage_backend.model.util.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class FileService {

    private final FilesRepo fileRepo;
    private final UsersService usersService;
    private final JWTUtil jwtUtil;
    private final FileChunkService fileChunkService;

    public FileService(FilesRepo fileRepo, UsersService usersService,
                       JWTUtil jwtUtil, FileChunkService fileChunkService) {
        this.fileRepo = fileRepo;
        this.usersService = usersService;
        this.jwtUtil = jwtUtil;
        this.fileChunkService = fileChunkService;
    }


    public void addFile(FileUploadDTO fileUploadDTO, HttpServletRequest request) {
        String userEmail = jwtUtil.extractUsernameByRequest(request);
        Users user = usersService.getUserByEmail(userEmail);
        Files file = new Files(fileUploadDTO, user);
        fileRepo.save(file);
    }

}
