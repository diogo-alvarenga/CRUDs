package com.example.crud.business;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.crud.exception.UsuarioNaoAutorizadoException;
import com.example.crud.security.JwtUtil;

@ExtendWith(MockitoExtension.class)
class PrivateServiceTest {

	@InjectMocks
	private PrivateService service;
	
	@Mock
	private JwtUtil jwt;
	
	@Test
	void acessoNegado() {

		String authHeader = "a";
		
		assertThrows(UsuarioNaoAutorizadoException.class,
				() -> service.acessar(authHeader));
	}

}