package com.mh.cloud_storage_backend.model.entities.requests;
import lombok.Data;

@Data
public class FileUploadRequest {
    private String tags;
    private String folderName;
}
