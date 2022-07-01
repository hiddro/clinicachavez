package com.peru.srv.clinicachavez.service.impl;

import com.peru.srv.clinicachavez.models.dto.UsuarioDTO;
import com.peru.srv.clinicachavez.models.entities.Rol;
import com.peru.srv.clinicachavez.models.entities.Usuario;
import com.peru.srv.clinicachavez.repositories.RolRepository;
import com.peru.srv.clinicachavez.repositories.UsuarioRepository;
import com.peru.srv.clinicachavez.service.IUsuarioService;
import com.peru.srv.clinicachavez.utils.EstadoConstant;
import com.peru.srv.clinicachavez.utils.UtilsConstant;
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
    private RolRepository rolRepository;

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
            throw new RuntimeException(username + " No Existe!");
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
            log.info("Usuario no Existe!");
            throw new RuntimeException(user.get().getUsername() + " No Existe!");
        }

        user.get().setEstado(INACTIVO);

        return usuarioRepository.save(user.get());
    }

    @Override
    public Usuario addRolToUsuario(String username, String titulo) {
        log.info("Asignando Rol para el usuario" + username);
        Optional<Usuario> user = usuarioRepository.findByUsername(username);
        Optional<Rol> role = rolRepository.findByTitulo(titulo);

        if (!user.isPresent()) {
            log.info("Usuario no Existe!");
            throw new RuntimeException(user.get().getUsername() + " No Existe!");
        }

        if (!role.isPresent()) {
            log.info("Rol no Existe!");
            throw new RuntimeException(role.get().getTitulo() + " No Existe!");
        }
        if(role.get().getEstado().equalsIgnoreCase(INACTIVO)){
            log.info("Rol no se puede agregar Eliminado!");
            throw new RuntimeException(role.get().getTitulo() + " Eliminado!");
        }

        List<Rol> roles = user.get().getRoles().size() == 0 ? new ArrayList<>() : user.get().getRoles();
        List<Rol> rolesUsuario = new ArrayList<>();

        roles.stream()
                .forEach(rol -> {
                    if(rol.getIdRol().equals(role.get().getIdRol())){
                        throw new RuntimeException(role.get().getTitulo() + " ya Existe para Usuario!");
                    }

                    rolesUsuario.add(rol);
                });

        rolesUsuario.add(role.get());
        user.get().setRoles(rolesUsuario);

        return user.get();

    }

    @Override
    public Usuario removeRolToUsuario(String username, String titulo) {
        log.info("Removiendo Rol para el usuario" + username);
        Optional<Usuario> user = usuarioRepository.findByUsername(username);
        Optional<Rol> role = rolRepository.findByTitulo(titulo);

        if (!user.isPresent()) {
            log.info("Usuario no Existe!");
            throw new RuntimeException(user.get().getUsername() + " No Existe!");
        }

        if (!role.isPresent()) {
            log.info("Rol no Existe!");
            throw new RuntimeException(role.get().getTitulo() + " No Existe!");
        }
        if(role.get().getEstado().equalsIgnoreCase(INACTIVO)){
            log.info("Rol no se puede agregar Eliminado!");
            throw new RuntimeException(role.get().getTitulo() + " Eliminado!");
        }

        List<Rol> roles = user.get().getRoles().size() == 0 ? new ArrayList<>() : user.get().getRoles();
        List<Rol> rolesUsuario = roles.stream()
                .filter(rol -> !rol.getIdRol().equals(role.get().getIdRol()))
                .collect(Collectors.toList());

        user.get().setRoles(rolesUsuario);

        return user.get();
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

        if(UtilsConstant.validateKey(keyToString)){
            return usuarios.stream()
                    .filter(usuario -> usuario.getEstado().equalsIgnoreCase(keyToString))
                    .collect(Collectors.toList());
        }

        return usuarios;
    }

}
