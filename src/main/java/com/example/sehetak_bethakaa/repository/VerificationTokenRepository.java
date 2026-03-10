package com.example.sehetak_bethakaa.repository;

import com.example.sehetak_bethakaa.entity.User;
import com.example.sehetak_bethakaa.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);

    List<VerificationToken> findByUser(User user);
}
