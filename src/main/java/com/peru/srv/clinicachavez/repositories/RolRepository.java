package com.peru.srv.clinicachavez.repositories;

import com.peru.srv.clinicachavez.models.entities.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByTitulo(String titulo);
}
