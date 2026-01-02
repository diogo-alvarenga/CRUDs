package com.example.crud.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.crud.infrastructure.entitty.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
