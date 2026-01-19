package com.example.crud.business;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.crud.DTO.EntradaDTO;
import com.example.crud.DTO.EntradacomIdDTO;
import com.example.crud.DTO.LoginDTO;
import com.example.crud.DTO.UsuarioDTO;
import com.example.crud.exception.SenhaIncorretaException;
import com.example.crud.infrastructure.entity.Usuario;
import com.example.crud.infrastructure.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

	private final UsuarioRepository repository;
	private final PasswordEncoder encoder;

	public UsuarioDTO adicionarUsuario(EntradaDTO entrada) {
		Usuario usuario = Usuario.builder()
				.nome(entrada.getNome())
				.email(entrada.getEmail())
				.senha(encoder.encode(entrada.getSenha()))
				.build();
		
		repository.saveAndFlush(usuario);
		
		return UsuarioDTO.builder()
				.nome(usuario.getNome())
				.email(usuario.getEmail())
				.id(usuario.getId())
				.build();
	}
	
	public UsuarioDTO atualizarUsuario(EntradacomIdDTO entrada) {
		Usuario usuarioEntity = repository.findById(entrada.getId()).orElseThrow(
				()-> new EntityNotFoundException("Usuario não encontrado"));
		Usuario usuarioAtualizado = Usuario.builder()
				.nome(entrada.getNome()!=null?entrada.getNome():usuarioEntity.getNome())
				.email(entrada.getEmail()!=null? entrada.getEmail():usuarioEntity.getEmail())
				.senha(entrada.getSenha()!=null? encoder.encode(entrada.getSenha()):usuarioEntity.getSenha())
				.id(usuarioEntity.getId())
				.build();
		repository.saveAndFlush(usuarioAtualizado);
		
		return UsuarioDTO.builder()
				.nome(usuarioAtualizado.getNome())
				.email(usuarioAtualizado.getEmail())
				.id(usuarioAtualizado.getId())
				.build();
	}
	
	public void deletarUsuario(Long id) {
		if(repository.existsById(id)) {
			repository.deleteById(id);
		}else {
			throw new EntityNotFoundException("Usuario não encontrado");
		}
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
		if(!encoder.matches(login.getSenha(), usuario.getSenha())) {
			throw new SenhaIncorretaException();
		}
		
		return UsuarioDTO.builder()
				.nome(usuario.getNome())
				.email(usuario.getEmail())
				.id(usuario.getId())
				.build();
		
	}
}
