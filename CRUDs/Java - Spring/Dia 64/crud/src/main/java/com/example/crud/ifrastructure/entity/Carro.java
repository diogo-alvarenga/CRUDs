package com.example.crud.ifrastructure.entity;

import com.example.crud.infrastructure.entity.Usuario;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name= "carro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "modelo", nullable = false)
    private String modelo;

    @Column(name = "marca", nullable = false)
    private String marca;

    @Column(name = "ano", nullable = false)
    private Integer ano;

    @Column(name ="kmrodados", nullable = false)
    private Float kmRodados;

    @Column(name = "placa", nullable = false, unique = true)
    private String placa;

    @Column(name = "email", nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name ="usuario_id", nullable = false)
    @JsonBackReference
    private Usuario usuario;
}
