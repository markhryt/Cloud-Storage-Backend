package com.mh.cloud_storage_backend.service;

import com.mh.cloud_storage_backend.model.entities.Users;
import org.springframework.stereotype.Service;
import com.mh.cloud_storage_backend.model.repository.UsersRepo;
import java.util.List;

@Service
public class UsersService {

    private final UsersRepo usersRepo;


    public UsersService(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    public List<Users> getAllUsers() {
        return usersRepo.findAll();
    }
}