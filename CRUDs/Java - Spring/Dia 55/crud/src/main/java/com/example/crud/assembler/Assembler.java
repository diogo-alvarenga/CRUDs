package com.example.crud.assembler;

import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.example.crud.controller.UsuarioController;
import com.example.crud.dto.SaidaDTO;

@Component
public class Assembler {

	public SaidaDTO addHateoas(SaidaDTO dto) {
		dto.add(linkTo(methodOn(UsuarioController.class).buscarUsuario(dto.getId())).withSelfRel());
		dto.add(linkTo(methodOn(UsuarioController.class).deletarUsuario(dto.getId())).withRel("deletar"));
		dto.add(linkTo(UsuarioController.class).slash("/"+dto.getId()).withRel("atualizar"));
		dto.add(linkTo(methodOn(UsuarioController.class).listarTodos()).withRel("listar"));
		return dto;
	}
}
