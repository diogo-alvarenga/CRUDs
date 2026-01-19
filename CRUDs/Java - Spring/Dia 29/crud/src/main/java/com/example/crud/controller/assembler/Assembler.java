package com.example.crud.controller.assembler;

import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.example.crud.DTO.RetornoDTO;
import com.example.crud.controller.UsuarioController;

@Component
public class Assembler {

	public RetornoDTO adicionarHateoas(RetornoDTO dto) {
		dto.add(linkTo(methodOn(UsuarioController.class).buscarUsuario(dto.getId())).withSelfRel());
		dto.add(linkTo(methodOn(UsuarioController.class).adicioanarUsuario(null)).withRel("adicionar"));
		dto.add(linkTo(methodOn(UsuarioController.class).deletarUsuario(dto.getId())).withRel("deletar"));
		dto.add(linkTo(methodOn(UsuarioController.class).atualizarUsuario(null)).withRel("atualizar"));
		dto.add(linkTo(methodOn(UsuarioController.class).listarTodos()).withRel("listar"));
		return dto;
	}
}
