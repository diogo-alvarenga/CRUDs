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

import com.example.crud.DTO.UsuarioDTO;
import com.example.crud.business.UsuarioService;
import com.example.crud.infrastructure.entity.Usuario;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

	private final UsuarioService service;
	
	@GetMapping
	public ResponseEntity<UsuarioDTO> buscarUsuario(@RequestParam Integer id){
		return ResponseEntity.ok(service.buscarUsuario(id));
	}
	
	@DeleteMapping
	public ResponseEntity<Void> deletarUsuario(@RequestParam Integer id){
		service.deletarUsuario(id);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping
	public ResponseEntity<UsuarioDTO> adicionarUsuario(@RequestBody Usuario usuario){
		return ResponseEntity.ok(service.adicionarUsuario(usuario));
	}
	
	@PutMapping
	public ResponseEntity<UsuarioDTO> atualizarUsuario(@RequestBody Usuario usuario, @RequestParam Integer id){
		return ResponseEntity.ok(service.atualizarUsuario(usuario, id));
	}
}
