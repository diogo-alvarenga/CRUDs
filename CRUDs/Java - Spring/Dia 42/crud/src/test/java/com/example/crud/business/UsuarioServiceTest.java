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
	private Mapper mapper;
	
	@Mock
	private PasswordEncoder encoder;
	
	Usuario usuarioBanco = Usuario.builder()
			.nome("nome")
			.email("email")
			.senha("senhaCryp")
			.id(1L)
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
	
	AtualizacaoDTO atl = AtualizacaoDTO.builder()
			.nome("novonome")
			.email("novoemail")
			.senha("novasenha")
			.build();
	
	Usuario usuarioAtl = Usuario.builder()
			.nome("novonome")
			.email("novoemail")
			.senha("novasenha")
			.build();
	
	SaidaDTO saidaAtl = SaidaDTO.builder()
			.nome("novonome")
			.email("novoemail")
			.id(1L)
			.build();
	LoginDTO login = LoginDTO.builder()
			.email("email")
			.senha("senha")
			.build();
	
	@Test
	void adicionarUsuario() {
		when(repository.findByEmail(entrada.email())).thenReturn(Optional.empty());
		when(mapper.dtoEmEntidade(entrada)).thenReturn(usuario);
		when(encoder.encode(entrada.senha())).thenReturn(usuarioBanco.getSenha());
		when(mapper.entidadeParaDTO(usuario)).thenReturn(saida);
		
		SaidaDTO teste = service.adicionarUsuario(entrada);
		
		assertEquals(1L, teste.getId());
		assertEquals("nome", teste.getNome());
		assertEquals("email", teste.getEmail());
	}
	
	@Test
	void buscarUsuario() {
		when(repository.findById(1L)).thenReturn(Optional.of(usuarioBanco));
		when(mapper.entidadeParaDTO(usuarioBanco)).thenReturn(saida);
		
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
		when(mapper.entidadeParaDTO(usuarioAtl)).thenReturn(saidaAtl);
		
		SaidaDTO teste = service.atualizarUsuario(1L, atl);
		
		assertEquals(1L, teste.getId());
		assertEquals("novonome", teste.getNome());
		assertEquals("novoemail", teste.getEmail());
	}
	
	@Test
	void usuarioNaoEncontrado() {
		when(repository.existsById(1L)).thenReturn(false);
		
		assertThrows(UsuarioNaoEncontradoException.class,
				()-> service.deletarUsuario(1L));
	}
	
	@Test
	void emailExistente() {
		when(repository.findByEmail(entrada.email())).thenReturn(Optional.of(usuarioBanco));

		assertThrows(EmailExistenteException.class,
				()-> service.adicionarUsuario(entrada));
	}

	@Test
	void senhaIncorreta() {
		when(repository.findByEmail(entrada.email())).thenReturn(Optional.of(usuarioBanco));
		when(encoder.matches(login.senha(), usuarioBanco.getSenha())).thenReturn(false);
		assertThrows(SenhaIncorretaException.class,
				()-> service.login(login));
	}
}
