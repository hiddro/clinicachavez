package com.peru.srv.clinicachavez.service;

import com.peru.srv.clinicachavez.models.dto.UsuarioDTO;
import com.peru.srv.clinicachavez.models.entities.Usuario;

import java.util.*;

public interface IUsuarioService {

    Usuario saveUsuario(UsuarioDTO usuario);

    void addRolToUsuario(String username, String rol);

    Usuario getUsuario(String username);

    List<Usuario> getUsuarios();
}
