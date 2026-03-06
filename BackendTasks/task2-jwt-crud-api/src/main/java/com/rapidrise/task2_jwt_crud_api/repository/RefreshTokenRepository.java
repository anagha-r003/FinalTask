package com.rapidrise.task2_jwt_crud_api.repository;

import com.rapidrise.task2_jwt_crud_api.entity.RefreshToken;
import com.rapidrise.task2_jwt_crud_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);
}
