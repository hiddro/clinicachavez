package com.peru.srv.clinicachavez.controllers;

import com.peru.srv.clinicachavez.models.dto.RolDTO;
import com.peru.srv.clinicachavez.models.entities.Rol;
import com.peru.srv.clinicachavez.service.IRolService;
import com.peru.srv.clinicachavez.utils.Constant;
import com.peru.srv.clinicachavez.utils.EstadoConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = Constant.PATH_ROL)
public class RolController {

    @Autowired
    private IRolService rolService;

    @GetMapping(value = "/estado/{key}")
    public ResponseEntity<List<Rol>> obtenerRoles(@PathVariable("key") EstadoConstant key){
        Map<String, Object> response = new HashMap<>();

        List<Rol> roles = rolService.getRoles(key);

        response.put("mensaje", "Se obtuvo los roles!");
        response.put("Roles", roles);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{titulo}")
    public ResponseEntity<Rol> obtenerRolByTitulo(@PathVariable("titulo") String titulo){
        Map<String, Object> response = new HashMap<>();

        Rol role = rolService.getRol(titulo);

        response.put("mensaje", "Se Obtuvo al Rol!");
        response.put("Rol", role);

        return new ResponseEntity(response, HttpStatus.OK);

    }

    @PostMapping(value = "/registro",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Rol> registrarRol(@Valid @RequestBody RolDTO rolDTO){
        Map<String, Object> response = new HashMap<>();
        Rol role = null;

        try {
            role = rolService.saveRole(rolDTO);
        }catch (Exception e){
            response.put("mensaje", "error al registra el Rol");
            response.put("error", e.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Se registro el Rol!");
        response.put("Rol", role);

        return new ResponseEntity(response, HttpStatus.CREATED);
    }

}
