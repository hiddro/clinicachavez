package com.peru.srv.clinicachavez.repositories;

import com.peru.srv.clinicachavez.models.entities.TokenProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface TokenRepository extends JpaRepository<TokenProperties, Integer> {
    Optional<TokenProperties> findByToken(String token);
}
