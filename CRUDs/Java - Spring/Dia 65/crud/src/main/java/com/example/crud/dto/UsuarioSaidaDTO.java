package com.example.crud.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioSaidaDTO extends RepresentationModel<UsuarioSaidaDTO> {

    private String nome;
    private String email;
    private Long id;
}
