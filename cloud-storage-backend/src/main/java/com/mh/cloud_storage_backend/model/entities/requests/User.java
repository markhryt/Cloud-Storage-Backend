package com.mh.cloud_storage_backend.model.entities.requests;

import lombok.Data;

@Data
public class User {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}