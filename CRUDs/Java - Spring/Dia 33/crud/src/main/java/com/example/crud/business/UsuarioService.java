package com.example.crud.business;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.crud.DTO.AtualizacaoDTO;
import com.example.crud.DTO.EntradaDTO;
import com.example.crud.DTO.LoginDTO;
import com.example.crud.DTO.SaidaDTO;
import com.example.crud.exception.SenhaIncorretaException;
import com.example.crud.exception.UsuarioNaoEncontradoException;
import com.example.crud.infrastructure.entity.Usuario;
import com.example.crud.infrastructure.repository.UsuarioRepository;
import com.example.crud.mapper.Mapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

	private final UsuarioRepository repository;
	private final PasswordEncoder encoder;
	private final Mapper mapper;
	
	public SaidaDTO adicionarUsuario(EntradaDTO entrada) {
		Usuario usuario = mapper.entradaParaEntidade(entrada);
		usuario.setSenha(encoder.encode(entrada.getSenha()));
		
		repository.saveAndFlush(usuario);
		return mapper.entidadeParaDTO(usuario);
	}
	
	public SaidaDTO atualizarUsuario(AtualizacaoDTO entrada, Long id) {
		Usuario usuarioEntity = repository.findById(id).orElseThrow(
				() -> new UsuarioNaoEncontradoException());
		
		Usuario usuarioAtualizado = Usuario.builder()
				.nome(entrada.getNome()!=null? entrada.getNome():usuarioEntity.getNome())
				.email(entrada.getEmail()!=null?entrada.getEmail():usuarioEntity.getEmail())
				.senha(entrada.getSenha()!=null?encoder.encode(entrada.getSenha()):usuarioEntity.getSenha())
				.id(usuarioEntity.getId())
				.build();
		repository.saveAndFlush(usuarioAtualizado);
		
		return mapper.entidadeParaDTO(usuarioAtualizado);
	}
	
	public void deletarUsuario(Long id) {
		if(!repository.existsById(id)) {
			throw new UsuarioNaoEncontradoException();
		}
		repository.deleteById(id);
	}
	
	public SaidaDTO buscarUsuario(Long id) {
		Usuario usuario = repository.findById(id).orElseThrow(
				()-> new UsuarioNaoEncontradoException());
		
		return mapper.entidadeParaDTO(usuario);
	}
	
	public List<SaidaDTO> listar(){
		return repository.findAll()
				.stream()
				.map(usuario -> SaidaDTO
						.builder()
						.nome(usuario.getNome())
						.email(usuario.getEmail())
						.id(usuario.getId())
						.build())
				.collect(Collectors.toList());
	}
	
	public SaidaDTO login(LoginDTO login) {
		Usuario usuario = repository.findByEmail(login.getEmail()).orElseThrow(
				()-> new UsuarioNaoEncontradoException());
		
		if(!encoder.matches(login.getSenha(), usuario.getSenha())) {
			throw new SenhaIncorretaException();
		}
		
		return mapper.entidadeParaDTO(usuario);
	}
}
