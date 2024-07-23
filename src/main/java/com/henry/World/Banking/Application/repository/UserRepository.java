package com.henry.World.Banking.Application.repository;

import com.henry.World.Banking.Application.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByAccountNumber(String accountNumber);

    Boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);

    UserEntity findByAccountNumber(String accountNumber);
}
