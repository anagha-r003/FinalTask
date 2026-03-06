package com.rapidrise.task2_jwt_crud_api.repository;

import com.rapidrise.task2_jwt_crud_api.entity.BlackListedToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlackListedTokenRepository extends JpaRepository<BlackListedToken,Long> {

    Optional<BlackListedToken> findByToken(String token);

}

