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
	
	private final PasswordEncoder encoder;
	private final UsuarioRepository repository;

	public UsuarioDTO adicionarUsuario(Usuario usuario) {
		usuario.setSenha(encoder.encode(usuario.getSenha()));
		repository.saveAndFlush(usuario);
		
		return UsuarioDTO.builder()
				.nome(usuario.getNome())
				.email(usuario.getEmail())
				.id(usuario.getId())
				.build();
	}
	
	public UsuarioDTO buscarUsuario(Long id) {
		Usuario usuario = repository.findById(id).orElseThrow(
				()-> new EntityNotFoundException("Usuario não encontrado."));
		
		return UsuarioDTO.builder()
				.nome(usuario.getNome())
				.email(usuario.getEmail())
				.id(usuario.getId())
				.build();
	}
	
	public UsuarioDTO atualizarUsuario(Usuario usuario, Long id) {
		Usuario usuarioEntity = repository.findById(id).orElseThrow(
				()-> new EntityNotFoundException("Usuario não encontrado."));
		Usuario usuarioAtualizado = Usuario.builder()
				.nome(usuario.getNome()!=null? usuario.getNome():usuarioEntity.getNome())
				.email(usuario.getEmail()!=null?usuario.getEmail():usuarioEntity.getEmail())
				.senha(usuario.getSenha()!=null? encoder.encode(usuario.getSenha()):usuarioEntity.getSenha())
				.id(usuarioEntity.getId())
				.build();
		repository.saveAndFlush(usuarioAtualizado);
		
		return UsuarioDTO.builder()
				.nome(usuarioAtualizado.getNome())
				.email(usuarioAtualizado.getEmail())
				.id(usuarioEntity.getId())
				.build();
	}
	
	public void deletarUsuario(Long id) {
		repository.deleteById(id);
	}
	
	public List<UsuarioDTO> listarTodos(){
		return repository.findAll()
				.stream()
				.map(usuario -> UsuarioDTO.builder()
						.nome(usuario.getNome())
						.email(usuario.getEmail())
						.id(usuario.getId())
						.build())
					.collect(Collectors.toList());
	}
	
	public UsuarioDTO login(LoginDTO login) {
		Usuario usuario = repository.findByEmail(login.getEmail()).orElseThrow(
				()-> new EntityNotFoundException("Usuario não encontrado."));
		
		if(!encoder.matches(usuario.getSenha(), login.getSenha())) {
			throw new RuntimeException("Senha incorreta.");
		}
		
		return UsuarioDTO.builder()
				.nome(usuario.getNome())
				.email(usuario.getEmail())
				.id(usuario.getId())
				.build();
	}
}
