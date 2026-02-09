package com.example.crud.business;

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
import com.example.crud.exception.EmailCadastradoException;
import com.example.crud.exception.SenhaIncorretaException;
import com.example.crud.exception.UsuarioNaoAutorizadoException;
import com.example.crud.exception.UsuarioNaoEncontradoException;
import com.example.crud.infrastructure.entity.Usuario;
import com.example.crud.infrastructure.repository.UsuarioRepository;
import com.example.crud.mapper.Mapper;
import com.example.crud.security.JwtUtil;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

	@InjectMocks
	private UsuarioService service;

	@Mock
	private UsuarioRepository repository;
	
	@Mock
	private Mapper mapper;
	
	@Mock
	private PasswordEncoder encoder;

	@Mock
	private JwtUtil jwt;
	
	Usuario usuarioBanco = Usuario.builder()
			.nome("nome")
			.email("email")
			.id(1L)
			.senha("senhaC")
			.build();
	Usuario usuario = Usuario.builder()
			.nome("nome")
			.email("email")
			.senha("senha")
			.build();
	EntradaDTO entrada = EntradaDTO.builder()
			.nome("nome")
			.email("email")
			.senha("senha")
			.build();
	SaidaDTO saida = SaidaDTO.builder()
			.nome("nome")
			.email("email")
			.id(1L)
			.build();
	AtualizacaoDTO atl = AtualizacaoDTO
			.builder()
			.nome("novonome")
			.email("novoemail")
			.senha("novasenha")
			.build();
	Usuario usuarioAtl = Usuario.builder()
			.id(1L)
			.nome("novonome")
			.email("novoemail")
			.senha("novasenha")
			.build();
	SaidaDTO saidaAtl = SaidaDTO
			.builder()
			.id(1L)
			.nome("novonome")
			.email("novoemail")
			.build();
	LoginDTO login = LoginDTO.builder()
			.email("email")
			.senha("senha")
			.build();
	String token = "123";
	
	@Test
	void adicionarUsuario() {
		when(repository.findByEmail(entrada.email())).thenReturn(Optional.empty());
		when(mapper.dtoParaEntidade(entrada)).thenReturn(usuario);
		when(encoder.encode(usuario.getSenha())).thenReturn(usuarioBanco.getSenha());
		when(repository.saveAndFlush(usuario)).thenReturn(usuarioBanco);
		when(mapper.entidadeParaDto(usuario)).thenReturn(saida);
		
		SaidaDTO teste = service.adicionarUsuario(entrada);
		
		assertEquals(1L, teste.getId());
		assertEquals("nome", teste.getNome());
		assertEquals("email", teste.getEmail());
	}
	
	@Test
	void buscarUsuario() {
		when(repository.findById(1L)).thenReturn(Optional.of(usuarioBanco));
		when(mapper.entidadeParaDto(usuarioBanco)).thenReturn(saida);
		
		SaidaDTO teste = service.buscarUsuario(1L);
		
		assertEquals(1L, teste.getId());
		assertEquals("nome", teste.getNome());
		assertEquals("email", teste.getEmail());
	}
	
	@Test
	void atualizarUsuario() {
		when(repository.findById(1L)).thenReturn(Optional.of(usuarioBanco));
		when(mapper.atualizar(atl, usuarioBanco)).thenReturn(usuarioAtl);
		when(encoder.encode(atl.senha())).thenReturn(usuarioBanco.getSenha());
		when(repository.saveAndFlush(usuarioAtl)).thenReturn(usuarioAtl);
		when(mapper.entidadeParaDto(usuarioAtl)).thenReturn(saidaAtl);
		
		SaidaDTO teste = service.atualizarUsuario(1L, atl);
		
		assertEquals(1L, teste.getId());
		assertEquals("novonome", teste.getNome());
		assertEquals("novoemail", teste.getEmail());
	}
	
	@Test
	void gerarToken() {
		when(repository.findByEmail(login.email())).thenReturn(Optional.of(usuarioBanco));
		when(encoder.matches(login.senha(), usuarioBanco.getSenha())).thenReturn(true);
		when(jwt.gerarToken(usuarioBanco.getNome())).thenReturn(token);
		
		String teste = service.loginGerarToken(login);
		
		assertEquals("123", teste);
	}
	
	@Test
	void usuarioNaoEncontrado() {
		when(repository.findById(1L)).thenReturn(Optional.empty());
		assertThrows(UsuarioNaoEncontradoException.class,
				()-> service.buscarUsuario(1L));
	}

	@Test
	void senhaIncorreta() {
		when(repository.findByEmail(login.email())).thenReturn(Optional.of(usuarioBanco));
		when(encoder.matches(login.senha(), usuarioBanco.getSenha())).thenReturn(false);
		
		assertThrows(SenhaIncorretaException.class,
				()-> service.loginGerarToken(login));
	}
	
	@Test
	void emailCadastrado() {
		when(repository.findByEmail(entrada.email())).thenReturn(Optional.of(usuarioBanco));
		
		assertThrows(EmailCadastradoException.class,
				()-> service.adicionarUsuario(entrada));
	}
}
