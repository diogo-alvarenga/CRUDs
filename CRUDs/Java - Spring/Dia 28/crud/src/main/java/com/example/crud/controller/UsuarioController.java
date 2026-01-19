package com.example.crud.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.example.crud.DTO.EntradaDTO;
import com.example.crud.DTO.EntradacomIdDTO;
import com.example.crud.DTO.LoginDTO;
import com.example.crud.DTO.UsuarioDTO;
import com.example.crud.business.UsuarioService;
import com.example.crud.infrastructure.entity.Usuario;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

	private final UsuarioService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<UsuarioDTO> buscarUsuario(@PathVariable Long id){
		UsuarioDTO dto = service.buscarUsuario(id);
		dto.add(linkTo(methodOn(UsuarioController.class).adicionarUsuario(null)).withRel("adicionar"));
		dto.add(linkTo(methodOn(UsuarioController.class).buscarUsuario(null)).withSelfRel());
		dto.add(linkTo(methodOn(UsuarioController.class).deletarUsuario(null)).withRel("deletar"));
		dto.add(linkTo(methodOn(UsuarioController.class).atualizarUsuario( null)).withRel("atualizar"));
		dto.add(linkTo(methodOn(UsuarioController.class).listarTodos()).withRel("listar"));
		
		return ResponseEntity.ok(dto);
	}
	
	@PostMapping
	public ResponseEntity<UsuarioDTO> adicionarUsuario(@RequestBody EntradaDTO usuario){
		UsuarioDTO dto = service.adicionarUsuario(usuario);
		dto.add(linkTo(methodOn(UsuarioController.class).adicionarUsuario(null)).withSelfRel());
		dto.add(linkTo(methodOn(UsuarioController.class).buscarUsuario(null)).withRel("buscar"));
		dto.add(linkTo(methodOn(UsuarioController.class).deletarUsuario(null)).withRel("deletar"));
		dto.add(linkTo(methodOn(UsuarioController.class).atualizarUsuario(null)).withRel("atualizar"));
		dto.add(linkTo(methodOn(UsuarioController.class).listarTodos()).withRel("listar"));
		
		return ResponseEntity.ok(dto);
	}
	
	@DeleteMapping
	public ResponseEntity<Void> deletarUsuario(@RequestParam Long id){
		return ResponseEntity.ok().build();
	}
	
	@PutMapping
	public ResponseEntity<UsuarioDTO> atualizarUsuario(@RequestBody EntradacomIdDTO entrada){
		UsuarioDTO dto = service.atualizarUsuario(entrada);
		dto.add(linkTo(methodOn(UsuarioController.class).adicionarUsuario(null)).withRel("adicionar"));
		dto.add(linkTo(methodOn(UsuarioController.class).buscarUsuario(null)).withRel("buscar"));
		dto.add(linkTo(methodOn(UsuarioController.class).deletarUsuario(null)).withRel("deletar"));
		dto.add(linkTo(methodOn(UsuarioController.class).atualizarUsuario(null)).withSelfRel());
		dto.add(linkTo(methodOn(UsuarioController.class).listarTodos()).withRel("listar"));
		
		return ResponseEntity.ok(dto);
	}
	
	@GetMapping
	public List<UsuarioDTO> listarTodos(){
		return service.listarTodos();
	}
	
	@PostMapping(value = "/login")
	public ResponseEntity<UsuarioDTO> login(@RequestBody LoginDTO login){
		UsuarioDTO dto = service.login(login);
		dto.add(linkTo(methodOn(UsuarioController.class).adicionarUsuario(null)).withRel("adicionar"));
		dto.add(linkTo(methodOn(UsuarioController.class).buscarUsuario(null)).withRel("buscar"));
		dto.add(linkTo(methodOn(UsuarioController.class).deletarUsuario(null)).withRel("deletar"));
		dto.add(linkTo(methodOn(UsuarioController.class).atualizarUsuario( null)).withRel("atualizar"));
		dto.add(linkTo(methodOn(UsuarioController.class).listarTodos()).withRel("listar"));
		return ResponseEntity.ok(dto);
	}
}
