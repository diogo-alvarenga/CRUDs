package com.example.crud.business;

import static org.mockito.ArgumentMatchers.any;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

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

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

	@InjectMocks
	private UsuarioService service;
	
	@Mock
	private UsuarioRepository repository;
	
	@Mock
	private PasswordEncoder encoder;
	
	@Mock
	private Mapper mapper;
	
	Usuario usuarioBanco = Usuario.builder()
			.nome("nome")
			.email("teste@teste.com")
			.id(1L)
			.senha("senhaCryp")
			.build();
	Usuario usuario = Usuario.builder()
			.nome("nome")
			.email("teste@teste.com")
			.senha("senha")
			.build();
	SaidaDTO saida = SaidaDTO.builder()
			.nome("nome")
			.email("teste@teste.com")
			.id(1L)
			.build();
	EntradaDTO entrada = EntradaDTO.builder()
			.nome("nome")
			.email("teste@teste.com")
			.senha("senha")
			.build();
	AtualizacaoDTO atl = AtualizacaoDTO.builder()
			.nome("novonome")
			.email("novo@teste.com")
			.senha("novasenha")
			.build();
	Usuario usuarioAtl = Usuario.builder()
			.nome("novonome")
			.id(1L)
			.email("novo@teste.com")
			.senha("novasenha")
			.build();
	SaidaDTO saidaAtl = SaidaDTO.builder()
			.nome("novonome")
			.email("novo@teste.com")
			.id(1L)
			.build();
	LoginDTO login = LoginDTO.builder()
			.email("teste@teste.com")
			.senha("senha")
			.build();
	
	@Test
	void adicionarUsuario() {
		when(repository.findByEmail(entrada.getEmail())).thenReturn(Optional.empty());
		when(mapper.dtoParaEntidade(entrada)).thenReturn(usuario);
		when(encoder.encode(entrada.getSenha())).thenReturn(usuarioBanco.getSenha());
		when(repository.saveAndFlush(usuario)).thenReturn(usuarioBanco);
		when(mapper.entidadeParaDto(any(Usuario.class))).thenReturn(saida);
		
		SaidaDTO teste = service.adicionarUsuario(entrada);
		assertEquals(1L, teste.getId());
		assertEquals("nome", teste.getNome());
		assertEquals("teste@teste.com", teste.getEmail());
	}
	
	@Test
	void buscarUsuario() {
		when(repository.findById(1L)).thenReturn(Optional.of(usuarioBanco));
		when(mapper.entidadeParaDto(any(Usuario.class))).thenReturn(saida);
		
		SaidaDTO teste = service.buscarUsuario(1L);
		assertEquals(1L, teste.getId());
		assertEquals("nome", teste.getNome());
		assertEquals("teste@teste.com", teste.getEmail());
	}
	
	@Test
	void atualizarUsuario() {
		when(repository.findById(1L)).thenReturn(Optional.of(usuarioBanco));
		when(mapper.atualizar(atl, usuarioBanco)).thenReturn(usuarioAtl);
		when(repository.saveAndFlush(usuarioAtl)).thenReturn(usuarioAtl);
		when(mapper.entidadeParaDto(usuarioAtl)).thenReturn(saidaAtl);
		
		SaidaDTO teste = service.atualizarUsuario(1L, atl);
		assertEquals(1L, teste.getId());
		assertEquals("novonome", teste.getNome());
		assertEquals("novo@teste.com", teste.getEmail());
	}
	
	@Test
	void login() {
		when(repository.findByEmail(login.getEmail())).thenReturn(Optional.of(usuarioBanco));
		when(encoder.matches(login.getSenha(), usuarioBanco.getSenha())).thenReturn(true);
		when(mapper.entidadeParaDto(usuarioBanco)).thenReturn(saida);
		
		SaidaDTO teste = service.login(login);
		assertEquals(1L, teste.getId());
		assertEquals("nome", teste.getNome());
		assertEquals("teste@teste.com", teste.getEmail());
	}

	@Test
	void usuarioNaoEncontrado() {
		when(repository.findById(1L)).thenReturn(Optional.empty());
		
		assertThrows(UsuarioNaoEncontradoException.class,
				()-> service.buscarUsuario(1L));
	}

	@Test
	void senhaIncorreta() {
		when(repository.findByEmail(login.getEmail())).thenReturn(Optional.of(usuarioBanco));
		when(encoder.matches(login.getSenha(), usuarioBanco.getSenha())).thenReturn(false);
		assertThrows(SenhaIncorretaException.class,
				()-> service.login(login));
	}
	
	@Test
	void emailExistente() {
		when(repository.findByEmail(entrada.getEmail())).thenReturn(Optional.of(usuarioBanco));
		assertThrows(EmailExistenteException.class,
				()-> service.adicionarUsuario(entrada));
	}
}
