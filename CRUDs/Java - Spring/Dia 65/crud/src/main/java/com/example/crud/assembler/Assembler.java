package com.example.crud.assembler;

import com.example.crud.controller.UsuarioController;
import com.example.crud.dto.UsuarioSaidaDTO;
import lombok.experimental.UtilityClass;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@UtilityClass
public class Assembler {
    public UsuarioSaidaDTO addHateoas(UsuarioSaidaDTO dto){
        dto.add(linkTo(methodOn(UsuarioController.class).buscarUsuario(dto.getId())).withSelfRel());
        dto.add(linkTo(methodOn(UsuarioController.class).removerUsuario(dto.getId())).withRel("deletar"));
        dto.add(linkTo(UsuarioController.class).slash("/"+dto.getId()).withRel("atualizar"));
        dto.add(linkTo(methodOn(UsuarioController.class).listarUsuarios()).withRel("listar"));
        return dto;
    }
}
