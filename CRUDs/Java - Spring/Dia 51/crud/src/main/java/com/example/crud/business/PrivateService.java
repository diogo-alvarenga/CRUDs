package com.example.crud.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.crud.exception.UsuarioNaoAutorizadoException;
import com.example.crud.security.JwtUtil;

@Service
public class PrivateService {

	@Autowired
	private JwtUtil jwt;
    private static final Logger log = LoggerFactory.getLogger(PrivateService.class);

	
	public String acessar(String authHeader) {
		if(!authHeader.startsWith("Bearer ")) throw new UsuarioNaoAutorizadoException();
		log.info("Usuario autorizado.");
		String token = authHeader.substring(7);
		String nome = jwt.extrairUsuario(token);
		
		return "Ola, "+nome;
	}
}
