package com.mh.cloud_storage_backend.model.entities.requests;

import lombok.Data;


@Data
public class AuthenticationRequest {
    private String username;
    private String password;

    @Data
    public static class FolderCreateRequest {
        public String folderName;
    }
}