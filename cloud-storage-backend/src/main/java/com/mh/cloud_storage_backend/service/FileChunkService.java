package com.mh.cloud_storage_backend.service;

import com.mh.cloud_storage_backend.model.entities.FileChunks;
import com.mh.cloud_storage_backend.model.entities.Files;
import com.mh.cloud_storage_backend.model.entities.dto.FileChunkDTO;
import com.mh.cloud_storage_backend.model.repository.FileChunkRepository;
import com.mh.cloud_storage_backend.model.repository.FilesRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class FileChunkService {

    private FilesRepo filesRepo;
    private FileSystemStorageService systemStorageService;
    private FileChunkRepository fileChunkRepository;

    public FileChunkService(FilesRepo filesRepo, FileSystemStorageService systemStorageService, FileChunkRepository fileChunkRepository) {
        this.systemStorageService = systemStorageService;
        this.filesRepo = filesRepo;
        this.fileChunkRepository = fileChunkRepository;
    }

    public boolean uploadFileChunk(MultipartFile fileChunk, FileChunkDTO fileChunkDTO) {
        // Validate the file and parameters

        Files ownerFile = validateFileChunk(fileChunkDTO);
        if (ownerFile == null) {
            return false;
        }
        FileChunks fileChunks = new FileChunks(fileChunkDTO, ownerFile);
        fileChunkRepository.save(fileChunks);

        systemStorageService.store(fileChunk, ownerFile.getOwner().getEmail(), ownerFile.getLocalDirectory(), ownerFile.getId(), fileChunks.getId());
        return true;
    }

    private Files validateFileChunk(FileChunkDTO fileChunkDTO) {
        // validate the file chunk DTO
        if (fileChunkDTO.getOrderNumber() < 0 || fileChunkDTO.getFileId() == null || fileChunkDTO.getOrderNumber() == null) {
            return null;
        }

        return filesRepo.findById(fileChunkDTO.getFileId())
                .orElse(null);
    }

}
