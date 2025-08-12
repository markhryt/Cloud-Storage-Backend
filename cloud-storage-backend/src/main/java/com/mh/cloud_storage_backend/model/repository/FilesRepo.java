package com.mh.cloud_storage_backend.model.repository;

import com.mh.cloud_storage_backend.model.entities.Files;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilesRepo extends JpaRepository<Files, String> {
}
