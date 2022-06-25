package com.peru.srv.clinicachavez.models.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import javax.persistence.*;
import java.util.*;

import static javax.persistence.TemporalType.TIMESTAMP;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Serializable {

    @Column(length = 1, name = "estado")
    private String estado;

    @Column(name = "create_time")
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", locale = "es_PE", timezone = "America/Lima")
    @Temporal(TIMESTAMP)
    private Date createTime;

    @Column(name = "update_time")
    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", locale = "es_PE", timezone = "America/Lima")
    @Temporal(TIMESTAMP)
    private Date updateTime;
}
