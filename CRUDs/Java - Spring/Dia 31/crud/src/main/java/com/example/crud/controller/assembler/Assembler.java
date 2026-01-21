package com.example.crud.controller.assembler;

import org.springframework.stereotype.Component;

import com.example.crud.DTO.SaidaDTO;
import com.example.crud.controller.UsuarioController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class Assembler {

	public SaidaDTO adicionarHateoas(SaidaDTO dto) {
		System.out.println(dto.getId());
		dto.add(linkTo(methodOn(UsuarioController.class).adicionarUsuario(null)).withRel("adicionar"));
		dto.add(linkTo(methodOn(UsuarioController.class).atualizarUsuario(null)).withRel("atualizar"));
		dto.add(linkTo(methodOn(UsuarioController.class).deletarUsuario(dto.getId())).withRel("deletar"));
		dto.add(linkTo(methodOn(UsuarioController.class).buscarUsuario(dto.getId())).withSelfRel());
		dto.add(linkTo(methodOn(UsuarioController.class).listarTodos()).withRel("listar"));
		return dto;
	}
}
