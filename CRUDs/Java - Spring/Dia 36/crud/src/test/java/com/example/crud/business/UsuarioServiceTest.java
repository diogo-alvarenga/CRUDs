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
	void criarUsuario() {

		EntradaDTO entrada = EntradaDTO.builder()
				.nome("Usuario")
				.email("usuario@email.com")
				.senha("123")
				.build();
		
		Usuario usuario = Usuario.builder()
				.nome("Usuario")
				.email("usuario@email.com")
				.senha("senhacriptografada")
				.build();
		
		Usuario usuarioSalvo = Usuario.builder()
				.id(1L)
				.nome("Usuario")
				.email("usuario@email.com")
				.senha("senhacriptografada")
				.build();
		
		SaidaDTO saida = SaidaDTO.builder()
				.nome("Usuario")
				.id(1L)
				.email("usuario@email.com")
				.build();
		
        when(mapper.dtoParaEntidade(entrada)).thenReturn(usuario);
        when(encoder.encode("123")).thenReturn("senhacriptografada");
        when(repository.saveAndFlush(usuario)).thenReturn(usuarioSalvo);
        when(mapper.entidadeParaDTO(any(Usuario.class))).thenReturn(saida);
        
        SaidaDTO resultado = service.adicionarUsuario(entrada);
        
        assertEquals(1L, resultado.getId());
        assertEquals("Usuario", resultado.getNome());
        assertEquals("usuario@email.com", resultado.getEmail());
	}
	
	@Test
	void buscarUsuario() {
	    Usuario usuario = Usuario.builder()
	            .id(1L)
	            .nome("Usuario")
	            .email("teste@email.com")
	            .senha("123")
	            .build();
	    
	    SaidaDTO dto = SaidaDTO.builder()
	    		.nome("Usuario")
	    		.id(1L)
	    		.email("teste@email.com")
	    		.build();

	    Optional<Usuario> optionalUsuario = Optional.of(usuario);

	    when(repository.findById(1L)).thenReturn(optionalUsuario);
	    when(mapper.entidadeParaDTO(usuario)).thenReturn(dto);
	    
	    SaidaDTO resultado = service.buscarUsuario(1L);
	    
	    assertEquals(1L, resultado.getId());
	    assertEquals("Usuario", resultado.getNome());
	    assertEquals("teste@email.com", resultado.getEmail());
	}
	
	

}
