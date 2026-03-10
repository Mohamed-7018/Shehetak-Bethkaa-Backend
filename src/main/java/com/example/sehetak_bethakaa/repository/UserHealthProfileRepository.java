package com.example.sehetak_bethakaa.repository;

import com.example.sehetak_bethakaa.entity.UserHealthProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserHealthProfileRepository extends JpaRepository<UserHealthProfile, Long> {

    Optional<UserHealthProfile> findByUserId(UUID userId);

}