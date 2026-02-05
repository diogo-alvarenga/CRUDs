package com.example.crud.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private final String SECRET = "chave-secreta-1234567891011";
	private final long EXPIRATION = 1000 * 60 * 60;
	private final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	public String gerarToken(String nome) {
		return Jwts.builder()
				.setSubject(nome)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis()+ EXPIRATION))
				.signWith(KEY)
				.compact();
	}
	
	public String extrairUsuario(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(KEY)
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
}
