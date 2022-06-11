package com.peru.srv.clinicachavez.repositories;

import com.peru.srv.clinicachavez.domain.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
}
