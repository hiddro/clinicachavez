package com.peru.srv.clinicachavez.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "rol")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rol extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "idRol")
    private Integer idRol;

    @Column(length = 50, name = "nombre")
    private String titulo;

    @ManyToMany
    @JsonBackReference
    @JoinTable(name = "usuario_rol",
            joinColumns = @JoinColumn(name = "idRol", referencedColumnName = "idRol"),
            inverseJoinColumns = @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario"))
    private List<Usuario> usuarios = new ArrayList<>();
}
