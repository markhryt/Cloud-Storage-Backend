package com.mh.cloud_storage_backend.model.entities.dto;

import lombok.Data;

@Data
public class FileChunkDTO {
    private Integer orderNumber;
    private String fileId;
}