package com.peru.srv.clinicachavez.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tokenproperties")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenProperties extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idToken")
    private Integer idToken;

    @Column(length = 255, name = "token")
    private String token;
}
