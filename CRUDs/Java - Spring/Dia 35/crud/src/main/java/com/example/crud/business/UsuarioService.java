package com.example.crud.business;

import java.util.List;

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
	private final Mapper mapper;
	private final PasswordEncoder encoder;
	
	public SaidaDTO adicionarUsuario(EntradaDTO entrada) {
		Usuario usuario = mapper.dtoParaEntidade(entrada);
		usuario.setSenha(encoder.encode(entrada.getSenha()));
		
		repository.saveAndFlush(usuario);
		
		return mapper.entidadeParaDTO(usuario);
	}
	
	public SaidaDTO buscarUsuario(Long id) {
		Usuario usuario = buscarUsuarioPorId(id);
		return mapper.entidadeParaDTO(usuario);
	}
	
	public void deletarUsuario(Long id) {
		if(!repository.existsById(id)) {
			throw new UsuarioNaoEncontradoException();
		}
		repository.deleteById(id);
	}
	
	public SaidaDTO atualizarUsuario(AtualizacaoDTO entrada, Long id) {
		Usuario usuarioEntity = buscarUsuarioPorId(id);
		Usuario usuarioAtualizado = Usuario.builder()
				.nome(entrada.getNome()!=null?entrada.getNome():usuarioEntity.getNome())
				.email(entrada.getEmail()!=null?entrada.getEmail():usuarioEntity.getEmail())
				.senha(entrada.getSenha()!=null?encoder.encode(entrada.getSenha()):usuarioEntity.getSenha())
				.id(usuarioEntity.getId())
				.build();
		repository.saveAndFlush(usuarioAtualizado);
		return mapper.entidadeParaDTO(usuarioAtualizado);
	}
	
	public List<SaidaDTO> listarTodos(){
		return repository.findAll()
				.stream()
				.map(mapper::entidadeParaDTO)
				.toList();
	}
	
	public SaidaDTO login(LoginDTO login) {
		System.out.println(login.getEmail());
		Usuario usuario = repository.findByEmail(login.getEmail()).orElseThrow(
				()-> new UsuarioNaoEncontradoException());
	
		if(!encoder.matches(login.getSenha(), usuario.getSenha())) {
			throw new SenhaIncorretaException();
		}
		
		return mapper.entidadeParaDTO(usuario);
	}

	private Usuario buscarUsuarioPorId(Long id) {
		return repository.findById(id).orElseThrow(
				()-> new UsuarioNaoEncontradoException());
	}
}
