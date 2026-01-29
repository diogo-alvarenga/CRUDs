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
	
	
	public SaidaDTO adicionarUsurio(EntradaDTO entrada) {
		if(repository.findByEmail(entrada.getEmail()).isPresent()) {
			throw new EmailExistenteException();
		}
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
	
	public SaidaDTO atualizarUsuario(Long id, AtualizacaoDTO atl) {
		Usuario usuarioEntity = buscarUsuarioPorId(id);
		Usuario usuarioAtl = mapper.atualizar(usuarioEntity, atl);
		usuarioAtl.setSenha(atl.getSenha()!=null?encoder.encode(atl.getSenha()):usuarioEntity.getSenha());
		repository.saveAndFlush(usuarioAtl);
		
		return mapper.entidadeParaDTO(usuarioAtl);
	}
	
	public List<SaidaDTO> listarUsuarios(){
		return repository.findAll()
				.stream()
				.map(mapper::entidadeParaDTO)
				.toList();
	}
	
	public SaidaDTO login(LoginDTO login) {
		Usuario usuario = repository.findByEmail(login.getEmail()).orElseThrow(
				()-> new UsuarioNaoEncontradoException());
		if(!encoder.matches(login.getSenha(), usuario.getSenha())) {
			throw new SenhaIncorretaException();
		}
		
		return mapper.entidadeParaDTO(usuario);
	}
	
	private Usuario buscarUsuarioPorId(Long id) {
		return repository.findById(id).orElseThrow(
				() -> new UsuarioNaoEncontradoException());
	}
}
