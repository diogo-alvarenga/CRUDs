package com.example.crud.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "carro")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "placa", nullable = false, unique = true)
    private String placa;

    @Column(name = "modelo", nullable = false)
    private String modelo;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "ano", nullable = false)
    private Integer ano;

    @Column(name ="kmrodados", nullable = false)
    private Float kmRodados;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonBackReference
    private Usuario usuario;
}
