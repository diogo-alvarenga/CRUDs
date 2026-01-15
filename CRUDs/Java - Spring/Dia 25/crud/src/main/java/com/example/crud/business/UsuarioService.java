package com.example.crud.business;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.crud.DTO.LoginDTO;
import com.example.crud.DTO.UsuarioDTO;
import com.example.crud.infrastructure.entity.Usuario;
import com.example.crud.infrastructure.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

	private final UsuarioRepository repository;
	private final PasswordEncoder encoder;
	
	public UsuarioDTO buscarUsuario(Long id) {
		Usuario usuario = repository.findById(id).orElseThrow(
				()-> new EntityNotFoundException("Usuario não encontrado"));
	
		UsuarioDTO dto = UsuarioDTO.builder()
				.id(usuario.getId())
				.nome(usuario.getNome())
				.email(usuario.getEmail())
				.build();
		
		return dto;
	}
	
	public UsuarioDTO adicionarUsuario(Usuario usuario) {
		usuario.setSenha(encoder.encode(usuario.getSenha()));
		repository.saveAndFlush(usuario);
		
		UsuarioDTO dto = UsuarioDTO.builder()
				.id(usuario.getId())
				.nome(usuario.getNome())
				.email(usuario.getEmail())
				.build();
		
		return dto;
	}
	
	public UsuarioDTO atualizarUsuario(Usuario usuario, Long id) {
		Usuario usuarioEntity = repository.findById(id).orElseThrow(
				()-> new EntityNotFoundException("Usuario não encontrado"));
		
		Usuario usuarioAtualizado = Usuario.builder()
				.id(usuarioEntity.getId())
				.nome(usuario.getNome()!=null? usuario.getNome():usuarioEntity.getNome())
				.email(usuario.getEmail()!=null? usuario.getEmail():usuarioEntity.getEmail())
				.senha(usuario.getSenha()!=null? encoder.encode(usuario.getSenha()): usuarioEntity.getSenha())
				.build();
		repository.saveAndFlush(usuarioAtualizado);
		
		UsuarioDTO dto = UsuarioDTO.builder()
				.id(usuarioEntity.getId())
				.nome(usuarioAtualizado.getNome())
				.email(usuarioAtualizado.getEmail())
				.build();
		
		return dto;
	}
	
	public void deletarUsuario(Long id) {
		repository.deleteById(id);
	}
	
	public List<UsuarioDTO> listarTodos(){
		return repository.findAll()
				.stream()
				.map(usuario -> UsuarioDTO.builder()
						.id(usuario.getId())
						.nome(usuario.getNome())
						.email(usuario.getEmail())
						.build()).collect(Collectors.toList());
	}
	
	public UsuarioDTO login(LoginDTO login) {
		Usuario usuario = repository.findByEmail(login.getEmail()).orElseThrow(
				()-> new EntityNotFoundException("Usuario não encontrado"));
		
		if(!encoder.matches(login.getSenha(), usuario.getSenha())) {
			throw new RuntimeException("Senha incorreta");
		}
		
		return UsuarioDTO.builder()
				.id(usuario.getId())
				.nome(usuario.getNome())
				.email(usuario.getEmail())
				.build();
	}
}
