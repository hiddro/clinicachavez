package com.peru.srv.clinicachavez.repositories;

import com.peru.srv.clinicachavez.models.entities.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface RolRepository extends JpaRepository<Rol, Integer> {
    //Optional<Rol> findByRol(String titulo);
}
