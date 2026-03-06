package com.rapidrise.task2_jwt_crud_api.repository;

import com.rapidrise.task2_jwt_crud_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
}
