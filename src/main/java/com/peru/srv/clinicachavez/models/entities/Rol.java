package com.peru.srv.clinicachavez.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "ROL")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rol {

    @Id
    @GeneratedValue
    @Column(name = "IDROL")
    private Integer idRol;

    @Column(length = 50, name = "NOMBRE")
    private String titulo;

    @Column(length = 1, name = "ESTADO")
    private String estado;

    @ManyToMany
    @JsonBackReference
    @JoinTable(name = "USUARIO_ROL",
            joinColumns = @JoinColumn(name = "IDROL", referencedColumnName = "IDROL"),
            inverseJoinColumns = @JoinColumn(name = "IDUSUARIO", referencedColumnName = "IDUSUARIO"))
    private List<Usuario> usuarios;
}
