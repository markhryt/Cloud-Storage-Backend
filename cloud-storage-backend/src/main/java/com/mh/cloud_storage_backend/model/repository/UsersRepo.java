package com.mh.cloud_storage_backend.model.repository;

import com.mh.cloud_storage_backend.model.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsersRepo extends JpaRepository<Users, Integer> {
    Optional<Users> findByEmail(String username);
}