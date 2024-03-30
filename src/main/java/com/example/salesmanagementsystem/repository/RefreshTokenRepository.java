package com.example.salesmanagementsystem.repository;

import com.example.salesmanagementsystem.model.RefreshToken;
import com.example.salesmanagementsystem.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByClient(Client client);
    void deleteByToken(String token);

}
