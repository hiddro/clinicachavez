package com.peru.srv.clinicachavez.repositories;

import com.peru.srv.clinicachavez.models.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByUsername(String username);
}
