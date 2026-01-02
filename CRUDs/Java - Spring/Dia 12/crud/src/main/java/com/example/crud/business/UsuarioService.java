package com.example.crud.business;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.crud.DTO.UsuarioDTO;
import com.example.crud.infrastructure.entitty.Usuario;
import com.example.crud.infrastructure.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

	private final UsuarioRepository repository;
	
    private final PasswordEncoder passwordEncoder;

	public UsuarioDTO adicionarUsuario(Usuario usuario) {
		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		repository.saveAndFlush(usuario);	
		return new UsuarioDTO(usuario.getId(),usuario.getNome(),usuario.getEmail());
	}
	
	public void deleteById(Long id) {
		repository.deleteById(id);
	}
	
	public UsuarioDTO buscarUsuario(Long id) {
		Usuario usuario = repository.findById(id).orElseThrow(
				() -> new RuntimeException("Usuario não encontrado"));
		
		return new UsuarioDTO(usuario.getId(),usuario.getNome(),usuario.getEmail());
	}
	
	public UsuarioDTO atualizarUsuario(Usuario usuario, Long id) {
		Usuario usuarioEntity = repository.findById(id).orElseThrow(
				() -> new EntityNotFoundException("Usuario não encontrado"));
		
		Usuario usuarioAtualizado;
		
		if(usuario.getSenha()!= null) {
			usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		
			usuarioAtualizado = Usuario.builder()
					.nome(usuario.getNome()!= null? usuario.getNome():usuarioEntity.getNome())
					.email(usuario.getEmail()!= null? usuario.getEmail():usuarioEntity.getEmail())
					.id(usuarioEntity.getId())
					.senha(usuario.getSenha())
					.build();
		}else {
			usuarioAtualizado = Usuario.builder()
					.nome(usuario.getNome()!= null? usuario.getNome():usuarioEntity.getNome())
					.email(usuario.getEmail()!= null? usuario.getEmail():usuarioEntity.getEmail())
					.id(usuarioEntity.getId())
					.senha(usuarioEntity.getSenha())
					.build();
		}
		
		repository.saveAndFlush(usuarioAtualizado);
		
		return new UsuarioDTO(usuarioAtualizado.getId(),usuarioAtualizado.getNome(),usuarioAtualizado.getEmail());
		
	}
	
	
}
