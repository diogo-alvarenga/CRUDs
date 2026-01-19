package com.example.crud.business;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.crud.DTO.EntradaComIdDTO;
import com.example.crud.DTO.EntradaDTO;
import com.example.crud.DTO.LoginDTO;
import com.example.crud.DTO.RetornoDTO;
import com.example.crud.exceptions.SenhaIncorretaException;
import com.example.crud.infrastructure.entity.Usuario;
import com.example.crud.infrastructure.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

	private final UsuarioRepository repository;
	private final PasswordEncoder encoder;

	public RetornoDTO adicionarUsuario(EntradaDTO dto) {
		Usuario usuario = Usuario.builder()
				.nome(dto.getNome())
				.email(dto.getEmail())
				.senha(encoder.encode(dto.getSenha()))
				.build();
		repository.saveAndFlush(usuario);
		
		return RetornoDTO.builder()
				.nome(usuario.getNome())
				.email(usuario.getEmail())
				.id(usuario.getId())
				.build();
	}
	
	public RetornoDTO buscarUsuario(Long id) {
		Usuario usuario = repository.findById(id).orElseThrow(
				()-> new EntityNotFoundException("Usuario não encontrado."));
		return RetornoDTO.builder()
				.nome(usuario.getNome())
				.email(usuario.getEmail())
				.id(usuario.getId())
				.build();
	}
	
	public void deletarUsuario(Long id) {
		if(!repository.existsById(id)) {
			throw new EntityNotFoundException("Usuario não encontrado.");
		}
		
		repository.deleteById(id);
	}
	
	public RetornoDTO atualizarUsuario(EntradaComIdDTO entrada) {
		Usuario usuarioEntity = repository.findById(entrada.getId()).orElseThrow(
				()-> new EntityNotFoundException("Usuario não encontrado."));
		Usuario usuarioAtualizado = Usuario.builder()
				.nome(entrada.getNome()!=null? entrada.getNome():usuarioEntity.getNome())
				.email(entrada.getEmail()!=null? entrada.getEmail():usuarioEntity.getEmail())
				.id(usuarioEntity.getId())
				.senha(entrada.getSenha()!=null? encoder.encode(entrada.getSenha()):usuarioEntity.getSenha())
				.build();
		repository.saveAndFlush(usuarioAtualizado);
		
		return RetornoDTO.builder()
				.nome(usuarioAtualizado.getNome())
				.email(usuarioAtualizado.getEmail())
				.id(usuarioAtualizado.getId())
				.build();
	}
	
	public List<RetornoDTO> listarTodos(){
		return repository.findAll()
				.stream()
				.map(usuario -> RetornoDTO.builder()
						.nome(usuario.getNome())
						.email(usuario.getEmail())
						.id(usuario.getId())
						.build())
				.collect(Collectors.toList());
	}
	
	public RetornoDTO login(LoginDTO login) {
		Usuario usuario = repository.findByEmail(login.getEmail())
				.orElseThrow(
						()-> new  EntityNotFoundException("Usuarionão encontrado."));
		
		if(!encoder.matches(login.getSenha(), usuario.getSenha())) {
			throw new SenhaIncorretaException();
		}
		
		return RetornoDTO.builder()
				.nome(usuario.getNome())
				.email(usuario.getEmail())
				.id(usuario.getId())
				.build();
	}
}
