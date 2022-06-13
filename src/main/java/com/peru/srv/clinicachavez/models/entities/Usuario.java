package com.peru.srv.clinicachavez.models.entities;

import com.peru.srv.clinicachavez.models.entities.Rol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private Integer idUsuario;

    @Column(length = 50, name = "nombre")
    private String nombre;

    @Column(length = 1, name = "estado")
    private String estado;

    @Column(length = 25, name = "username")
    private String username;

    @Column(length = 25, name = "password")
    private String password;

    @ManyToMany
    @JoinTable(name = "usuario_rol",
            joinColumns = @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario"),
            inverseJoinColumns = @JoinColumn(name = "idRol", referencedColumnName = "idRol"))
    private List<Rol> roles;

}
