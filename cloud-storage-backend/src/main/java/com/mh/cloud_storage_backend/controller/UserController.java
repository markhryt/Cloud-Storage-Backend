package com.mh.cloud_storage_backend.controller;

import com.mh.cloud_storage_backend.model.entities.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
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


    @RestController
    @RequestMapping("/api/auth")
    public static class AuthController {

        @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired
        private UsersService usersService;

        @PostMapping("/register")
        public ResponseEntity<String> registerUser(@RequestBody User usersData) {
            try {
                System.out.println(usersData);
                usersService.createUser(usersData);
                return ResponseEntity.ok("User registered successfully");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Error registering user: " + e.getMessage());
            }
        }
    }
}