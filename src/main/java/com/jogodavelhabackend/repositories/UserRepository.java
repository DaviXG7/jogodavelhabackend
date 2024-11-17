package com.jogodavelhabackend.repositories;

import com.jogodavelhabackend.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {
    Optional<UserModel> findByEmail(String email);
    Optional<UserModel> findByAccessHashcode(int accessHashcode);

    boolean existsByEmail(String email);

}
