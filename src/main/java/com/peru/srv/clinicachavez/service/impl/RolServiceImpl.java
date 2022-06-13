package com.peru.srv.clinicachavez.service.impl;

import com.peru.srv.clinicachavez.models.entities.Rol;
import com.peru.srv.clinicachavez.repositories.RolRepository;
import com.peru.srv.clinicachavez.service.IRolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class RolServiceImpl implements IRolService {

    @Autowired
    private RolRepository rolRepository;

    @Override
    public Optional<Rol> saveRole() {
        log.info("Creando nuevo Rol para la BD!");

        //Optional<Rol> role = rolRepository.findByRol();
        return Optional.empty();
    }
}
