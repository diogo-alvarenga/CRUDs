package com.example.crud.business;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.crud.DTO.AtualizacaoDTO;
import com.example.crud.DTO.EntradaDTO;
import com.example.crud.DTO.SaidaDTO;
import com.example.crud.infrastructure.entity.Usuario;
import com.example.crud.infrastructure.repository.UsuarioRepository;
import com.example.crud.mapper.Mapper;


@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

	@Mock
	private UsuarioRepository repository;
	
	@Mock
	private Mapper mapper;
	
	@Mock
	private PasswordEncoder encoder;
	
	@InjectMocks
	private UsuarioService service;
	
	@Test
	@DisplayName("Adicionar usuario")
	void adicionarUsuario() {
		EntradaDTO entrada = EntradaDTO.builder()
				.nome("Usuario")
				.email("email@email.com")
				.senha("123")
				.build();
		
		Usuario usuario = Usuario.builder()
				.nome("Usuario")
				.email("email@email.com")
				.senha("123")
				.build();
		
		Usuario usuarioSalvo = Usuario.builder()
				.id(1L)
				.nome("Usuario")
				.email("email@email.com")
				.senha("senhaCriptografada")
				.build();
		
		SaidaDTO saida = SaidaDTO.builder()
				.id(1L)
				.nome("Usuario")
				.email("email@email.com")
				.build();
		
		when(mapper.dtoParaEntidade(entrada)).thenReturn(usuario);
		when(repository.saveAndFlush(usuario)).thenReturn(usuarioSalvo);
		when(mapper.entidadeParaDTO(any(Usuario.class))).thenReturn(saida);
		
		SaidaDTO resultado = service.adicionarUsuario(entrada);
		
		assertEquals(1L, resultado.getId());
		assertEquals("Usuario", resultado.getNome());
		assertEquals("email@email.com", resultado.getEmail());
	}

	@Test
	@DisplayName("Buscar usuario")
	void buscarUsuario() {
		
		Usuario usuario = Usuario.builder()
				.id(1L)
				.nome("Usuario")
				.email("teste@teste.com")
				.senha("senhaCriptografada")
				.build();
		Optional<Usuario> opUsuario = Optional.of(usuario);
		
		SaidaDTO dto = SaidaDTO.builder()
				.id(1L)
				.nome("Usuario")
				.email("teste@teste.com")
				.build();
		
		when(repository.findById(1L)).thenReturn(opUsuario);
		when(mapper.entidadeParaDTO(usuario)).thenReturn(dto);

		SaidaDTO user = service.buscarUsuario(1L);
		
		assertEquals(1L, user.getId());
		assertEquals("Usuario", user.getNome());
		assertEquals("teste@teste.com", user.getEmail());
	}
	
	@Test
	void atualizarUsuario() {
		AtualizacaoDTO dto = AtualizacaoDTO.builder()
				.nome("NovoNome")
				.email("NovoEmail")
				.senha("NovaSenha")
				.build();
		Optional<Usuario> usuarioBanco = Optional.of(Usuario.builder()
				.id(1L)
				.nome("NomeAntigo")
				.email("EmailAntigo")
				.senha("SenhaAntiga")
				.build());
		Usuario novoUsuario = Usuario.builder()
				.id(1L)
				.nome("NovoNome")
				.email("NovoEmail")
				.senha("NovaSenha")
				.build();
		
		Usuario usuarioAtualizado = Usuario.builder()
				.id(1L)
				.nome("NovoNome")
				.email("NovoEmail")
				.senha("NovaSenha")
				.build();
		SaidaDTO saida = SaidaDTO.builder()
				.id(1L)
				.nome("NovoNome")
				.email("NovoEmail")
				.build();
		
		when(repository.findById(1L)).thenReturn(usuarioBanco);
		when(encoder.encode("NovaSenha")).thenReturn("NovaSenha");
		when(repository.saveAndFlush(any(Usuario.class))).thenReturn(usuarioAtualizado);
		when(mapper.entidadeParaDTO(any(Usuario.class))).thenReturn(saida);
	
		SaidaDTO teste = service.atualizarUsuario(dto, 1L);
		assertEquals(1L, teste.getId());
		assertEquals("NovoNome", teste.getNome());
		assertEquals("NovoEmail", teste.getEmail());
	}
}
