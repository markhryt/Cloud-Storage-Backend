package com.mh.cloud_storage_backend.controller;

import com.mh.cloud_storage_backend.service.UsersService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mh.cloud_storage_backend.model.entities.Users;
import java.util.List;

@RestController
public class UserController {
    private final UsersService usersService;

    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/users")
    public List<Users> users() {
        return usersService.getAllUsers();
    }
}