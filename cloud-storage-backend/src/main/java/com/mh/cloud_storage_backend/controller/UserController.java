package com.mh.cloud_storage_backend.controller;

import org.springframework.web.bind.annotation.*;
import com.mh.cloud_storage_backend.model.entities.Users;
import java.util.List;
import com.mh.cloud_storage_backend.service.UsersService;

@RestController
public class UserController {
    private final UsersService usersService;

    public UserController( UsersService usersService ) {
        this.usersService = usersService;
    }

    @GetMapping("/users")
    public List<Users> users() {
        return usersService.getAllUsers();
    }

}