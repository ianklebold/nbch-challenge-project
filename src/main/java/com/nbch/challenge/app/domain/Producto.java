package com.nbch.challenge.app.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Entity(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 5000)
    String descripcion;

    @Column(nullable = false)
    private double precio;

    @Version
    private Integer version;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime fechaCreacionProducto;

    @UpdateTimestamp
    private LocalDateTime fechaActualizacionProducto;
}
