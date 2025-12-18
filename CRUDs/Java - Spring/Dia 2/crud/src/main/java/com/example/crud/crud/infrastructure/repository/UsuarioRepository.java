package com.example.crud.crud.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.crud.crud.infrastructure.entitys.Usuario;

import jakarta.transaction.Transactional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

	
	Optional<Usuario> findByEmail(String email);
	
	@Transactional
	void deleteByEmail(String email);
	
}
