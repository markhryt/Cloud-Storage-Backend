package com.mh.cloud_storage_backend.model.entities.requests;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class FileUploadDTO {

    private String name;

    private Long size;

    private Long numberOfChunks;

    private List<String> tags;

    private LocalDateTime createdAt;

    private String localDirectory;

}
