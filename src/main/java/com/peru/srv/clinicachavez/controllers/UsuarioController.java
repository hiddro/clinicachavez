package com.peru.srv.clinicachavez.controllers;

import com.peru.srv.clinicachavez.models.dto.UsuarioDTO;
import com.peru.srv.clinicachavez.models.entities.Usuario;
import com.peru.srv.clinicachavez.service.IUsuarioService;
import com.peru.srv.clinicachavez.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping(value = Constant.PATH_USUARIO)
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @PostMapping(value = "/registro",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Usuario> registerUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO){

        Map<String, Object> response = new HashMap<>();
        Usuario usuario = null;

        try {
            usuario = usuarioService.saveUsuario(usuarioDTO);
        }catch (Exception e){
            response.put("mensaje", "error al registra el usuario");
            response.put("error", e.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Se registro el usuario correctamente");
        response.put("usuario", usuario);

        return new ResponseEntity(response, HttpStatus.CREATED);

    }

}
