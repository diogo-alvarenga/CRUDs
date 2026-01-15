package com.example.crud.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.crud.business.UsuarioService;
import com.example.crud.infrastructure.entitys.Usuario;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {
	
	private final UsuarioService usuarioService;
	
	@PostMapping
	public ResponseEntity<Void> salvarUsuario(@RequestBody Usuario usuario){
	    usuarioService.salvarUsuario(usuario);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping
	public ResponseEntity<Usuario> buscarUsuarioPorEmail(@RequestParam String email){
		return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email));
	}
	
	@DeleteMapping
	public ResponseEntity<Void> deletarPorEmail(@RequestParam String email){
		usuarioService.deletarUsuarioPorEmail(email);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping
	public ResponseEntity<Void> atualizarUsuarioPorId(@RequestParam Integer id, @RequestBody Usuario usuario){
		usuarioService.atualizarUsuarioPorId(id, usuario);
		return ResponseEntity.ok().build();
	}
}
