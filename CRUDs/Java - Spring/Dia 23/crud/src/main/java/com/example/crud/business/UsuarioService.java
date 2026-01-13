package com.example.crud.business;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
		
		return new UsuarioDTO(usuario.getId(),usuario.getNome(),usuario.getEmail());
	}
	
	public UsuarioDTO adicionarUsuario(Usuario usuario) {
		usuario.setSenha(encoder.encode(usuario.getSenha()));
		repository.saveAndFlush(usuario);
		
		return new UsuarioDTO(usuario.getId(),usuario.getNome(),usuario.getEmail());
	}
	
	public UsuarioDTO atualizarUsuario(Usuario usuario, Long id) {
		Usuario usuarioEntity = repository.findById(id).orElseThrow(
				()-> new EntityNotFoundException("Usuario não encontrado"));
		Usuario usuarioAtualizado = Usuario.builder()
				.nome(usuario.getNome()!=null? usuario.getNome():usuarioEntity.getNome())
				.email(usuario.getEmail()!=null? usuario.getEmail():usuarioEntity.getEmail())
				.senha(usuario.getSenha()!=null? encoder.encode(usuario.getSenha()): usuarioEntity.getSenha())
				.id(usuarioEntity.getId())
				.build();
		repository.saveAndFlush(usuarioAtualizado);
		
		return new UsuarioDTO(usuarioAtualizado.getId(),usuarioAtualizado.getNome(),usuarioAtualizado.getEmail());
	}
	
	public void deletarUsuario(Long id) {
		repository.deleteById(id);
	}
	
	public List<UsuarioDTO> listarTodos(){
		return repository.findAll()
				.stream()
				.map(usuario-> new UsuarioDTO(
						usuario.getId(),
						usuario.getNome(),
						usuario.getEmail()))
				.collect(Collectors.toList());
	}
}
