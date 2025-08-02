package com.mh.cloud_storage_backend.service;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;

import com.mh.cloud_storage_backend.model.entities.Users;
import com.mh.cloud_storage_backend.model.repository.UsersRepo;
import org.springframework.stereotype.Service;

@Service
public class FolderService {
    private final UsersRepo usersRepo;
    public FolderService(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }
    public boolean createFolder(String folderPath, String userEmail) {
        try{
            Users user = usersRepo.findByEmail(userEmail)
                    .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + userEmail));
            File dir = resolveUserPath(user.getId(), folderPath);
            System.out.println(dir.getAbsolutePath());
            return dir.mkdir();
        }catch(Exception e){
            return false;
        }
    }

    public static File resolveUserPath(String username, String userInputPath) throws IOException {
        File baseDir = new File("fileupload/storage/users/" + username);
        File targetFile = new File(baseDir, userInputPath);

        // Get canonical (normalized, absolute) paths
        String basePath = baseDir.getCanonicalPath();
        String targetPath = targetFile.getCanonicalPath();

        // Security check: ensure target is within baseDir
        if (!targetPath.startsWith(basePath + File.separator)) {
            throw new SecurityException("Invalid path: potential path traversal attempt.");
        }

        return targetFile;
    }
}
