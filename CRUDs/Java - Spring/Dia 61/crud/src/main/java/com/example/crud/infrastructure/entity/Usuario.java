package com.example.crud.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name ="usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="nome", nullable = false)
    private String nome;

    @Column(name ="email", nullable = false, unique = true)
    private String email;

    @Column(name = "senha", nullable = false)
    private String senha;

}
