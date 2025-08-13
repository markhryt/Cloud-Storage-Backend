package com.mh.cloud_storage_backend.service;

import com.mh.cloud_storage_backend.model.entities.FileChunks;
import com.mh.cloud_storage_backend.model.entities.Files;
import com.mh.cloud_storage_backend.model.entities.requests.FileDownloadRequest;
import com.mh.cloud_storage_backend.model.repository.FileChunkRepository;
import com.mh.cloud_storage_backend.model.repository.FilesRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class FileDownloadService {

    private FilesRepo filesRepo;
    private FileChunkRepository fileChunkRepository;
    public FileDownloadService(FilesRepo filesRepo, FileChunkRepository fileChunkRepository) {
        this.filesRepo = filesRepo;
        this.fileChunkRepository = fileChunkRepository;
    }

    public Files getFileByDownloadRequest(FileDownloadRequest fileDownloadRequest) {
        Files file = filesRepo.findByLocalDirectoryAndName(fileDownloadRequest.getLocalDirectory(), fileDownloadRequest.getFilename());
        if (file == null) {
            return null;
        }
        return file;
    }

    public File getFileChunk(String chunkId) {
        // Find the chunk in database
        FileChunks chunk = fileChunkRepository.findById(chunkId).orElse(null);
        if (chunk == null) {
            return null;
        }

        Files parentFile = chunk.getOriginFile();
        String userId = parentFile.getOwner().getId();
        String localDirectory = parentFile.getLocalDirectory();
        String fileId = parentFile.getId();

        String basePath = "fileupload/storage/users/";

        String directoryPath = basePath + userId + "/" + localDirectory + "/" + fileId + "/" + chunkId;
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        if (files == null || files.length == 0) {
         return null;
        }
        return files[0];
    }
}

