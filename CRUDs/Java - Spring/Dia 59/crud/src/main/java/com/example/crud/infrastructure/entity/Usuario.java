package com.example.crud.infrastructure.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="nome", nullable = false)
    private String nome;

    @Column(name ="email", nullable = false, unique = true)
    private String email;

    @Column(name ="senha", nullable = false)
    private String senha;

    @OneToMany(mappedBy = "usuario", //campo usuario na classe carro
            cascade = CascadeType.ALL, //tudo o que eu fizer em usuario tambem acontece com os carros dele
            orphanRemoval = true) //se um carro sair da lista de carros do usuario ele sera deletado do banco
    @JsonManagedReference
    private List<Carro> carros = new ArrayList<>();
}
