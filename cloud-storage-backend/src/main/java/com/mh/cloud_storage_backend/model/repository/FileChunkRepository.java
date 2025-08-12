package com.mh.cloud_storage_backend.model.repository;

import com.mh.cloud_storage_backend.model.entities.FileChunks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileChunkRepository extends JpaRepository<FileChunks, String> {
}
