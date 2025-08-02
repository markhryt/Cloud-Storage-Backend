package com.mh.cloud_storage_backend.controller;

import com.mh.cloud_storage_backend.model.entities.dto.User;
import com.mh.cloud_storage_backend.model.entities.requests.AuthenticationRequest;
import com.mh.cloud_storage_backend.model.util.JWTUtil;
import com.mh.cloud_storage_backend.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authenticate")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsersService usersService;

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User usersData) {
        try {
            usersService.createUser(usersData);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error registering user: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody AuthenticationRequest authenticationRequest) {
    try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
            return ResponseEntity.ok().body(jwtUtil.generateToken(authenticationRequest.getUsername()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error logging in user: " + e.getMessage());
        }
    }
}
