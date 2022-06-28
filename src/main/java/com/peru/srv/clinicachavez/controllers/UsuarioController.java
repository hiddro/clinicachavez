package com.peru.srv.clinicachavez.controllers;

import com.peru.srv.clinicachavez.models.dto.UsuarioDTO;
import com.peru.srv.clinicachavez.models.entities.Usuario;
import com.peru.srv.clinicachavez.service.IUsuarioService;
import com.peru.srv.clinicachavez.utils.Constant;
import com.peru.srv.clinicachavez.utils.EstadoConstant;
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

    @GetMapping(value = "/estado/{key}")
    public  ResponseEntity<List<Usuario>> obtenerUsuarios(@PathVariable("key") EstadoConstant key){
        Map<String, Object> response = new HashMap<>();

        List<Usuario> usuarios = usuarioService.getUsuarios(key);

        response.put("mensaje", "Se obtuvo los Usuarios!");
        response.put("Usuarios", usuarios);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<Usuario> obtenerUsuarioByUsername(@PathVariable("username") String username){
        Map<String, Object> response = new HashMap<>();

        Usuario usuario = usuarioService.getUsuario(username);

        response.put("mensaje", "Se Obtuvo al Usuario!");
        response.put("Usuario", usuario);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping(value = "/registro",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Usuario> registerUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO){

        Map<String, Object> response = new HashMap<>();
        Usuario usuario = null;

        try {
            usuario = usuarioService.saveUsuario(usuarioDTO);
        }catch (Exception e){
            response.put("mensaje", "error al registra el Usuario");
            response.put("error", e.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Se registro el Usuario correctamente!");
        response.put("Usuario", usuario);

        return new ResponseEntity(response, HttpStatus.CREATED);

    }

    @PutMapping(value = "/{username}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Usuario> actualizarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO,
                                                     @PathVariable("username") String username){

        Map<String, Object> response = new HashMap<>();
        Usuario usuario = null;

        try {
            usuario = usuarioService.updateUSuario(usuarioDTO, username);
        }catch (Exception e){
            response.put("mensaje", "error al actualizar el Usuario");
            response.put("error", e.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Se actualizo el Usuario correctamente!");
        response.put("Usuario", usuario);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{username}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable("username") String username){

        Map<String, Object> response = new HashMap<>();
        Usuario usuario = null;

        try {
            usuario = usuarioService.deleteUsuario(username);
        }catch (Exception e){
            response.put("mensaje", "error al eliminar el Usuario");
            response.put("error", e.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Se elimino el Usuario correctamente!");
        response.put("Usuario", usuario);

        return new ResponseEntity(response, HttpStatus.OK);
    }

}
