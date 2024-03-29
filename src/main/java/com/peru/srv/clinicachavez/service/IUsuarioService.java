package com.peru.srv.clinicachavez.service;

import com.peru.srv.clinicachavez.models.dto.UsuarioDTO;
import com.peru.srv.clinicachavez.models.entities.Usuario;
import com.peru.srv.clinicachavez.utils.EstadoConstant;

import java.util.*;

public interface IUsuarioService {

    Usuario saveUsuario(UsuarioDTO usuario);

    Usuario updateUSuario(UsuarioDTO usuario, String username);

    Usuario deleteUsuario(String username);

    Usuario addRolToUsuario(String username, String titulo);

    Usuario removeRolToUsuario(String username, String titulo);

    Usuario getUsuario(String username);

    List<Usuario> getUsuarios(EstadoConstant key);
}
