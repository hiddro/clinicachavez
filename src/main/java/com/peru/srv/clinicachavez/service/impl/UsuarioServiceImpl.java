package com.peru.srv.clinicachavez.service.impl;

import com.peru.srv.clinicachavez.models.dto.UsuarioDTO;
import com.peru.srv.clinicachavez.models.entities.Usuario;
import com.peru.srv.clinicachavez.repositories.UsuarioRepository;
import com.peru.srv.clinicachavez.service.IUsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Slf4j
@Transactional
public class UsuarioServiceImpl implements IUsuarioService, UserDetailsService {
//
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> user = usuarioRepository.findByUsername(username);

        if(!user.isPresent()){
            log.error("Usuario no existe");
            throw new UsernameNotFoundException("Usuario no existe");
        }else{
            log.error("Usuario encontrado " + username);
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.get().getRoles()
                .forEach(role -> {authorities.add(new SimpleGrantedAuthority(role.getTitulo()));});

        return new org.springframework.security.core.userdetails.User(user.get().getUsername(),
                user.get().getPassword(),
                authorities);
    }

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
        userConvert.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        log.info("" + userConvert);
        //return usuarioRepository.save(userConvert);
        return null;

    }

    @Override
    public void addRolToUsuario(String username, String rol) {
        log.info("Asignando Rol para el usuario");

    }

    @Override
    public Usuario getUsuario(String username) {
        return usuarioRepository.findByUsername(username).get();
    }

    @Override
    public List<Usuario> getUsuarios() {
        return null;
    }

}
