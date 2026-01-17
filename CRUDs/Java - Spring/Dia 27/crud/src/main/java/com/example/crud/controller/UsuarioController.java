package com.example.crud.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
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


import com.example.crud.DTO.LoginDTO;
import com.example.crud.DTO.UsuarioDTO;
import com.example.crud.business.UsuarioService;
import com.example.crud.infrastructure.entity.Usuario;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
@Tag(name = "api")
public class UsuarioController {

	private final UsuarioService service;
	
	@Operation(summary = "Adiciona um novo usuario", method ="POST")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Usuario adicionado"),
			@ApiResponse(responseCode = "500", description = "Erro ao adicionar usuario")
	})
	@PostMapping
	public ResponseEntity<UsuarioDTO> adicionarUsuario(@Valid @RequestBody Usuario usuario){
		UsuarioDTO dto = service.adicionarUsuario(usuario);
		dto.add(linkTo(methodOn(UsuarioController.class).adicionarUsuario(null)).withSelfRel());
		dto.add(linkTo(methodOn(UsuarioController.class).buscarUsuario(null)).withRel("buscar"));
		dto.add(linkTo(methodOn(UsuarioController.class).deletarUsuario(null)).withRel("deletar"));
		dto.add(linkTo(methodOn(UsuarioController.class).atualizarUsuario(null, null)).withRel("atualizar"));
		dto.add(linkTo(methodOn(UsuarioController.class).listarTodos()).withRel("listar"));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(dto);
	}

	@Operation(summary ="Buscar usuario por Id", method = "GET")
	@ApiResponses(value = {
			@ApiResponse(responseCode="200", description = "Usuario encontrado e retornado"),
			@ApiResponse(responseCode="500", description = "Erro ao buscar usuario")
	})
	@GetMapping(value = "/{id}")
	public ResponseEntity<UsuarioDTO> buscarUsuario(@PathVariable Long id){
		UsuarioDTO dto = service.buscarUsuario(id);
		dto.add(linkTo(methodOn(UsuarioController.class).adicionarUsuario(null)).withRel("adicionar"));
		dto.add(linkTo(methodOn(UsuarioController.class).buscarUsuario(null)).withSelfRel());
		dto.add(linkTo(methodOn(UsuarioController.class).deletarUsuario(null)).withRel("deletar"));
		dto.add(linkTo(methodOn(UsuarioController.class).atualizarUsuario(null, null)).withRel("atualizar"));
		dto.add(linkTo(methodOn(UsuarioController.class).listarTodos()).withRel("listar"));
		
		return ResponseEntity.ok(dto);
	}
	
	@Operation(summary = "Deletar Usuario por ID", method = "DELETE")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Usuario deletado"),
			@ApiResponse(responseCode = "500", description = "Erro ao deletar usuario")
	})
	@DeleteMapping
	public ResponseEntity<Void> deletarUsuario(@RequestParam Long id){
		service.deletarUsuario(id);
		return ResponseEntity.noContent().build();
	}
	
	@Operation(summary = "Atualizar usuario", method = "PUT")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Usuario atualizado e retornado"),
			@ApiResponse(responseCode = "500", description = "Erro ao atualizar usuario")
	})
	@PutMapping
	public ResponseEntity<UsuarioDTO> atualizarUsuario(@Valid @RequestBody Usuario usuario, @RequestParam Long id){
		UsuarioDTO dto = service.atualizarUsuario(usuario, id);
		dto.add(linkTo(methodOn(UsuarioController.class).adicionarUsuario(null)).withRel("adicionar"));
		dto.add(linkTo(methodOn(UsuarioController.class).buscarUsuario(null)).withRel("buscar"));
		dto.add(linkTo(methodOn(UsuarioController.class).deletarUsuario(null)).withRel("deletar"));
		dto.add(linkTo(methodOn(UsuarioController.class).atualizarUsuario(null, null)).withSelfRel());
		dto.add(linkTo(methodOn(UsuarioController.class).listarTodos()).withRel("listar"));
		return ResponseEntity.ok(dto);
	}
	
	@Operation(summary = "Fazer login", method = "POST")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Login feito com sucesso"),
			@ApiResponse(responseCode = "500", description = "Erro ao fazer login")
	})
	@PostMapping(value = "/login")
	public ResponseEntity<UsuarioDTO> login(@RequestBody LoginDTO login){
		UsuarioDTO dto = service.login(login);
		dto.add(linkTo(methodOn(UsuarioController.class).adicionarUsuario(null)).withRel("adicionar"));
		dto.add(linkTo(methodOn(UsuarioController.class).buscarUsuario(null)).withRel("buscar"));
		dto.add(linkTo(methodOn(UsuarioController.class).deletarUsuario(null)).withRel("deletar"));
		dto.add(linkTo(methodOn(UsuarioController.class).atualizarUsuario(null, null)).withRel("atualizar"));
		dto.add(linkTo(methodOn(UsuarioController.class).listarTodos()).withRel("listar"));
		return ResponseEntity.ok(dto);
	}
	
	@Operation(summary = "Listar todos os usuaarios cadastrados", method = "GET")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Usuarios listados"),
			@ApiResponse(responseCode = "500", description = "Erro ao listar usuarios")
	})
	@GetMapping
	public List<UsuarioDTO> listarTodos(){
		return service.listarTodos();
	}
}
