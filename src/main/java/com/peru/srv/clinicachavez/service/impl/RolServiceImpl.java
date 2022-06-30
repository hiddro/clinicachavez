package com.peru.srv.clinicachavez.service.impl;

import com.peru.srv.clinicachavez.models.dto.RolDTO;
import com.peru.srv.clinicachavez.models.entities.Rol;
import com.peru.srv.clinicachavez.repositories.RolRepository;
import com.peru.srv.clinicachavez.service.IRolService;
import com.peru.srv.clinicachavez.utils.EstadoConstant;
import com.peru.srv.clinicachavez.utils.UtilsConstant;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.peru.srv.clinicachavez.utils.Constant.*;

@Service
@Slf4j
@Transactional
public class RolServiceImpl implements IRolService {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Rol saveRole(RolDTO rolDTO) {
        log.info("Creando nuevo Rol para la BD!");

        Optional<Rol> role = rolRepository.findByTitulo(rolDTO.getTitulo());

        if (role.isPresent()) {
            log.info("Usurio ya existe");
            throw new RuntimeException(role.get().getTitulo() + " Existe!");
        }

        Rol rolConvert = modelMapper.map(rolDTO, Rol.class);
        rolConvert.setEstado(ACTIVO);

        return rolRepository.save(rolConvert);
    }

    @Override
    public Rol deleteRole(String titulo) {
        log.info("Eliminando estado del Rol " + titulo);
        Optional<Rol> role = rolRepository.findByTitulo(titulo);

        if (!role.isPresent()) {
            log.info("Rol no Existe!");
            throw new RuntimeException(role.get().getTitulo() + " No Existe!");
        }

        role.get().setEstado(INACTIVO);

        return rolRepository.save(role.get());
    }

    @Override
    public Rol getRol(String titulo) {
        log.info("Obteniendo Rol por su Titulo!");
        return rolRepository.findByTitulo(titulo).get();
    }

    @Override
    public List<Rol> getRoles(EstadoConstant key) {
        log.info("Obteniendo Roles");
        List<Rol> roles = rolRepository.findAll();
        String keyToString = key.toString();

        if(UtilsConstant.validateKey(keyToString)){
            return roles.stream()
                    .filter(rol -> rol.getEstado().equalsIgnoreCase(keyToString))
                    .collect(Collectors.toList());
        }

        return roles;
    }
}
