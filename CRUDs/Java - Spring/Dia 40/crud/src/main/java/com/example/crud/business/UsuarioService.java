package com.example.crud.business;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.crud.dto.AtualizacaoDTO;
import com.example.crud.dto.EntradaDTO;
import com.example.crud.dto.LoginDTO;
import com.example.crud.dto.SaidaDTO;
import com.example.crud.exception.EmailExistenteException;
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
		if(repository.findByEmail(entrada.getEmail()).isPresent()) {
			throw new EmailExistenteException();
		}
		Usuario usuario = mapper.dtoParaEntidade(entrada);
		usuario.setSenha(encoder.encode(entrada.getSenha()));
		repository.saveAndFlush(usuario);
		
		return mapper.entidadeParaDTO(usuario);
	}
	
	public SaidaDTO buscarUsuario(Long id) {
		Usuario usuario = buscarPorId(id);
		return mapper.entidadeParaDTO(usuario);
	}
	
	public SaidaDTO atualizarUsuario(Long id, AtualizacaoDTO atualizacao) {
		Usuario usuarioEntity = buscarPorId(id);
		Usuario usuarioAtualizado = mapper.atualizacao(atualizacao, usuarioEntity);
		usuarioAtualizado.setSenha(atualizacao.getSenha()!=null?encoder.encode(atualizacao.getSenha()):usuarioEntity.getSenha());
		repository.saveAndFlush(usuarioAtualizado);
		
		return mapper.entidadeParaDTO(usuarioAtualizado);
	}
	
	public void deletarUsuario(Long id) {
		if(!repository.existsById(id)) {
			throw new UsuarioNaoEncontradoException();
		}
		repository.deleteById(id);
	}

	public SaidaDTO login(LoginDTO login) {
		Usuario usuario = repository.findByEmail(login.getEmail()).orElseThrow(
				()-> new UsuarioNaoEncontradoException());
		if(!encoder.matches(login.getSenha(), usuario.getSenha())) {
			throw new SenhaIncorretaException();
		}
		
		return mapper.entidadeParaDTO(usuario);
	}
	
	public List<SaidaDTO> listarTodos(){
		return repository.findAll()
				.stream()
				.map(mapper::entidadeParaDTO)
				.toList();
	}
	
	private Usuario buscarPorId(Long id) {
		return repository.findById(id).orElseThrow(
				()-> new UsuarioNaoEncontradoException());
	}
}
