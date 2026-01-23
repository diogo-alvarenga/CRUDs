package com.example.crud.controller.assembler;

import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import com.example.crud.DTO.SaidaDTO;
import com.example.crud.controller.UsuarioController;

@Component
public class Assembler {

	public SaidaDTO adicionarHateoas(SaidaDTO dto) {
		
		dto.add(linkTo(methodOn(UsuarioController.class).buscarUsuario(dto.getId())).withSelfRel());
		dto.add(linkTo(methodOn(UsuarioController.class).adicionarUsuario(null)).withRel("adicionar"));
		dto.add(linkTo(UsuarioController.class).slash("/" + dto.getId()).withRel("delete"));		
		dto.add(linkTo(methodOn(UsuarioController.class).atualizarUsuario(null)).withRel("atualizar"));
		dto.add(linkTo(methodOn(UsuarioController.class).listarUsuarios()).withRel("listar"));
		
		return dto;
		
	}
}
