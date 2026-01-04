package com.example.crud.business;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.crud.infrastructure.entity.Usuario;
import com.example.crud.infrastructure.repository.UsuarioRepository;
import com.example.crudDTO.UsuarioDTO;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

	private final UsuarioRepository repository;
	private final PasswordEncoder encoder;
	
	public UsuarioDTO adicionarUsuario(Usuario usuario) {
		usuario.setSenha(encoder.encode(usuario.getSenha()));
		repository.saveAndFlush(usuario);
		
		return new UsuarioDTO(usuario.getId(),usuario.getNome(),usuario.getEmail());
	}
	
	public UsuarioDTO buscarUsuario(Long id) {
		Usuario usuario = repository.findById(id).orElseThrow(
				() -> new EntityNotFoundException("Usuario não encontrado"));
		
		return new UsuarioDTO(usuario.getId(),usuario.getNome(),usuario.getEmail());
	}
	
	public void deletarUsuario(Long id) {
		repository.deleteById(id);
	}
	
	public UsuarioDTO atualizarUsuario(Usuario usuario, Long id) {
		Usuario usuarioEntity = repository.findById(id).orElseThrow(
				() -> new EntityNotFoundException("Usuario não encontrado"));
		
		Usuario usuarioAtualizado = Usuario.builder()
				.nome(usuario.getNome()!=null? usuario.getNome():usuarioEntity.getNome())
				.email(usuario.getEmail()!=null? usuario.getEmail():usuarioEntity.getEmail())
				.id(usuarioEntity.getId())
				.senha(usuario.getSenha()!=null?encoder.encode(usuario.getSenha()) :usuarioEntity.getSenha())
				.build();
		repository.saveAndFlush(usuarioAtualizado);
		
		return new UsuarioDTO(usuarioAtualizado.getId(),usuarioAtualizado.getNome(),usuarioAtualizado.getEmail());
	}
}
