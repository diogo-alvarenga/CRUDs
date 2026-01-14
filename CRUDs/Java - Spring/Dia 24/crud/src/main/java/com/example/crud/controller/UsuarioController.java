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

import com.example.crud.DTO.LoginDTO;
import com.example.crud.DTO.UsuarioDTO;
import com.example.crud.business.UsuarioService;
import com.example.crud.infrastructure.entity.Usuario;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

	private final UsuarioService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<UsuarioDTO> buscarUsuario(@PathVariable Long id){
		UsuarioDTO dto = service.buscarUsuarioPorId(id);
        dto.add(linkTo(methodOn(UsuarioController.class).buscarUsuario(id)).withSelfRel());
        dto.add(linkTo(methodOn(UsuarioController.class).listarTodos()).withRel("listar"));
        dto.add(linkTo(methodOn(UsuarioController.class).deletarUsuario(id)).withRel("deletar"));
        dto.add(linkTo(methodOn(UsuarioController.class).atualizarUsuario(null, id)).withRel("atualizar"));
		
		return ResponseEntity.ok(dto);
	}
	
	@PutMapping
	public ResponseEntity<UsuarioDTO> atualizarUsuario(@RequestBody Usuario usuario, @RequestParam Long id){
		UsuarioDTO dto = service.atualizarUsuario(usuario, id);
	    dto.add(linkTo(methodOn(UsuarioController.class).atualizarUsuario(null, id)).withSelfRel());
	    dto.add(linkTo(methodOn(UsuarioController.class).listarTodos()).withRel("listar"));
	    dto.add(linkTo(methodOn(UsuarioController.class).deletarUsuario(id)).withRel("deletar"));
        dto.add(linkTo(methodOn(UsuarioController.class).buscarUsuario(id)).withRel("buscar"));
		return ResponseEntity.ok(dto);
	}
	
	@GetMapping
	public List<UsuarioDTO> listarTodos(){
		return service.listarTodos();
	}
	
	@PostMapping
	public ResponseEntity<UsuarioDTO> adicionarUsuario(@RequestBody Usuario usuario){
	    UsuarioDTO dto = service.adicionarUsuario(usuario);
		dto.add(linkTo(methodOn(UsuarioController.class).adicionarUsuario(null)).withSelfRel());
	    dto.add(linkTo(methodOn(UsuarioController.class).listarTodos()).withRel("listar"));
	    dto.add(linkTo(methodOn(UsuarioController.class).deletarUsuario(dto.getId())).withRel("deletar"));
        dto.add(linkTo(methodOn(UsuarioController.class).buscarUsuario(dto.getId())).withRel("buscar"));
		return ResponseEntity.ok(dto);
	}
	
	@DeleteMapping
	public ResponseEntity<Void> deletarUsuario(@RequestParam Long id) {
		service.deletarUsuario(id);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping(value ="/login")
	public ResponseEntity<UsuarioDTO> login(@RequestBody LoginDTO dto){
		return ResponseEntity.ok( service.verificarSenha(dto));
	}
	
	
}
