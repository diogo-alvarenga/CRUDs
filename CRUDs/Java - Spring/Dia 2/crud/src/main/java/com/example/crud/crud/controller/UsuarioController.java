package com.example.crud.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.crud.crud.business.UsuarioService;
import com.example.crud.crud.infrastructure.entitys.Usuario;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
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
	public ResponseEntity<Void> deletarUsuarioPorEmail(@RequestParam String email){
		usuarioService.deletarUsuarioPorEmail(email);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping
	public ResponseEntity<Void> atualizarUsuarioPorId(@RequestParam Integer id, @RequestBody Usuario usuario ){
		usuarioService.atualizarUsuarioPorId(id, usuario);
		return ResponseEntity.ok().build();
	}
	
	
}
