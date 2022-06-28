package com.peru.srv.clinicachavez.service;

import com.peru.srv.clinicachavez.models.dto.RolDTO;
import com.peru.srv.clinicachavez.models.entities.Rol;
import com.peru.srv.clinicachavez.utils.EstadoConstant;

import java.util.List;
import java.util.Optional;

public interface IRolService {

    Rol saveRole(RolDTO rolDTO);

    Rol deleteRole(String titulo);

    Rol getRol(String titulo);

    List<Rol> getRoles(EstadoConstant key);

}
