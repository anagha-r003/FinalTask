package com.rapidrise.task2_jwt_crud_api.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Table(name = "blacklisted_tokens")
@Data
public class BlackListedToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, unique = true)
    private String token;

    private Instant blacklistedAt;
}
