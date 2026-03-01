package com.example.crud.security;

import org.slf4j.Logger;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import org.slf4j.LoggerFactory;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String SECRET;
	
	@Value("${jwt.expiration}")
	private long expiration;

    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);
	
	public Key keyUnica() {
		byte[] keyBytes = SECRET.getBytes(StandardCharsets.UTF_8);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	public String gerarToken(String nome) {
		log.info("Gerando token.");
		return Jwts.builder()
				.setSubject(nome)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis()+expiration))
				.signWith(keyUnica(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	public String extrairUsuario(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(keyUnica())
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
}
