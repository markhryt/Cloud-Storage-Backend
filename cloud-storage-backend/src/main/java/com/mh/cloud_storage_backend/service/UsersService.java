package com.mh.cloud_storage_backend.service;

import com.mh.cloud_storage_backend.model.entities.Users;
import com.mh.cloud_storage_backend.model.entities.requests.User;
import com.mh.cloud_storage_backend.model.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    private final UsersRepo usersRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersService(UsersRepo usersRepo, PasswordEncoder passwordEncoder) {
        this.usersRepo = usersRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Users> getAllUsers() {
        return usersRepo.findAll();
    }


    public Users getUserByEmail(String email) {
        return usersRepo.findByEmail(email).orElse(null);
    }

    public void createUser(User userData) {
        if (userData == null || userData.getEmail() == null || userData.getPassword() == null) {
            throw new IllegalArgumentException("User and its email/password must not be null");
        }

        Users user = new Users();
        // Hash the password before saving
        user.setEmail(userData.getEmail());
        user.setPassword(passwordEncoder.encode(userData.getPassword()));
        user.setFirstName(userData.getFirstName());
        user.setLastName(userData.getLastName());
        user.setTier("f");
        usersRepo.save(user);
    }
}