package com.peru.srv.clinicachavez.service.impl;

import com.peru.srv.clinicachavez.models.dto.UsuarioDTO;
import com.peru.srv.clinicachavez.models.entities.Usuario;
import com.peru.srv.clinicachavez.repositories.UsuarioRepository;
import com.peru.srv.clinicachavez.service.IUsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Usuario saveUsuario(UsuarioDTO usuarioDTO) {
        log.info("Creando nuevo Usuario para la BD!");
        Optional<Usuario> user = usuarioRepository.findByUsername(usuarioDTO.getUsername());

        if (user.isPresent()) {
            log.info("Usurio ya existe");
            throw new RuntimeException(user.get().getUsername() + " Existe!");
        }

        Usuario userConvert = modelMapper.map(usuarioDTO, Usuario.class);
        userConvert.setEstado("X");
        userConvert.setPassword(bCryptPasswordEncoder.encode(usuarioDTO.getPassword()));
        log.info("" + userConvert);
        //return usuarioRepository.save(userConvert);
        return null;

    }

    @Override
    public void addRolToUsuario(String username, String rol) {
        log.info("Asignando Rol para el usuario");

    }

    @Override
    public Optional<Usuario> getUsuario(String username) {
        return Optional.empty();
    }

    @Override
    public List<Usuario> getUsuarios() {
        return null;
    }
}
