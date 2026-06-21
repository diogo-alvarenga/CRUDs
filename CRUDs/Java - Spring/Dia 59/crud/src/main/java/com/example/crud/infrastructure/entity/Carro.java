package com.example.crud.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "carro")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "modelo", nullable = false)
    private String modelo;

    @Column(name = "fabricante", nullable = false)
    private String fabricante;

    @Column(name = "ano", nullable = false)
    private Integer ano;

    @Column(name = "kmRodados", nullable = false)
    private Float kmRodados;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonBackReference
    private Usuario usuario;

}
