package com.peru.srv.clinicachavez.service.impl;

import com.peru.srv.clinicachavez.models.dto.UsuarioDTO;
import com.peru.srv.clinicachavez.models.entities.Usuario;
import com.peru.srv.clinicachavez.repositories.UsuarioRepository;
import com.peru.srv.clinicachavez.service.IUsuarioService;
import com.peru.srv.clinicachavez.utils.EstadoConstant;
import com.peru.srv.clinicachavez.utils.FunctionsUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static com.peru.srv.clinicachavez.utils.Constant.*;

@Service
@Slf4j
@Transactional
public class UsuarioServiceImpl implements IUsuarioService, UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private FunctionsUtils functionsUtils;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> user = usuarioRepository.findByUsername(username);

        if(!user.isPresent()){
            log.error("Usuario no existe");
            throw new UsernameNotFoundException("Usuario no existe");
        }else{
            log.info("Usuario encontrado " + username);
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.get().getRoles()
                .forEach(role -> {authorities.add(new SimpleGrantedAuthority(role.getTitulo()));});

        return new org.springframework.security.core.userdetails.User(user.get().getUsername(),
                user.get().getPassword(),
                authorities);
    }

    @Override
    public Usuario saveUsuario(UsuarioDTO usuario) {
        log.info("Creando nuevo Usuario para la BD!");
        Optional<Usuario> user = usuarioRepository.findByUsername(usuario.getUsername());

        if (user.isPresent()) {
            log.info("Usurio ya existe");
            throw new RuntimeException(user.get().getUsername() + " Existe!");
        }

        Usuario userConvert = modelMapper.map(usuario, Usuario.class);
        userConvert.setEstado(ACTIVO);
        userConvert.setPassword(passwordEncoder.encode(usuario.getPassword()));

        return usuarioRepository.save(userConvert);

    }

    @Override
    public Usuario updateUSuario(UsuarioDTO usuario, String username) {
        log.info("Actualizando Usuario " + username);
        Optional<Usuario> user = usuarioRepository.findByUsername(username);

        if (!user.isPresent()) {
            log.info("Usurio no Existe!");
            throw new RuntimeException(user.get().getUsername() + " No Existe!");
        }

        user.get().setNombre(usuario.getNombre());
        user.get().setPassword(passwordEncoder.encode(usuario.getPassword()));

        return usuarioRepository.save(user.get());
    }

    @Override
    public Usuario deleteUsuario(String username) {
        log.info("Eliminando estado del Usuario " + username);
        Optional<Usuario> user = usuarioRepository.findByUsername(username);

        if (!user.isPresent()) {
            log.info("Usurio no Existe!");
            throw new RuntimeException(user.get().getUsername() + " No Existe!");
        }

        user.get().setEstado(INACTIVO);

        return usuarioRepository.save(user.get());
    }

    @Override
    public void addRolToUsuario(String username, String rol) {
        log.info("Asignando Rol para el usuario");

    }

    @Override
    public Usuario getUsuario(String username) {
        log.info("Obteniendo Usuario por su Username!");
        return usuarioRepository.findByUsername(username).get();
    }

    @Override
    public List<Usuario> getUsuarios(EstadoConstant key) {
        log.info("Obteniendo Usuarios");
        List<Usuario> usuarios = usuarioRepository.findAll();
        String keyToString = key.toString();

        if(functionsUtils.validateKey(keyToString)){
            return usuarios.stream()
                    .filter(usuario -> usuario.getEstado().equalsIgnoreCase(keyToString))
                    .collect(Collectors.toList());
        }

        return usuarios;
    }

}
