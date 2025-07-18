package com.mh.cloud_storage_backend.model.entities.dto;
import lombok.Data;

@Data
public class FileUploadRequest {
    private String tags;
    private String ownerEmail;
}
