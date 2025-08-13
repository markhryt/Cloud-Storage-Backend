package com.mh.cloud_storage_backend.model.entities.requests;

import lombok.Data;


@Data
public class AuthenticationRequest {
    private String username;
    private String password;

}