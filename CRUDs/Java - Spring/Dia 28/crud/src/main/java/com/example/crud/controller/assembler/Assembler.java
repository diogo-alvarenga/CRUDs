package com.example.crud.controller.assembler;

import com.example.crud.DTO.UsuarioDTO;
import com.example.crud.controller.UsuarioController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class Assembler {

	public UsuarioDTO adicionarHateoas(UsuarioDTO dto){
		dto.add(linkTo(methodOn(UsuarioController.class).adicionarUsuario(null)).withRel("adicionar"));
		dto.add(linkTo(methodOn(UsuarioController.class).buscarUsuario(dto.getId())).withSelfRel());
		dto.add(linkTo(methodOn(UsuarioController.class).deletarUsuario(dto.getId())).withRel("deletar"));
		dto.add(linkTo(methodOn(UsuarioController.class).atualizarUsuario(null)).withRel("atualizar"));
		dto.add(linkTo(methodOn(UsuarioController.class).listarTodos()).withRel("listar"));
		return dto;
	}
}
