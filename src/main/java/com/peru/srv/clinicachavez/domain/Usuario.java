package com.peru.srv.clinicachavez.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "USUARIO")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDUSUARIO")
    private Integer idUsuario;

    @Column(length = 50, name = "NOMBRE")
    private String nombre;

    @Column(length = 1, name = "ESTADO")
    private String estado;

    @Column(length = 25, name = "USERNAME")
    private String username;

    @Column(length = 25, name = "PASSWORD")
    private String password;

    @ManyToMany
    @JoinTable(name = "USUARIO_ROL",
            joinColumns = @JoinColumn(name = "IDUSUARIO", referencedColumnName = "IDUSUARIO"),
            inverseJoinColumns = @JoinColumn(name = "IDROL", referencedColumnName = "IDROL"))
    private List<Rol> roles;

}
